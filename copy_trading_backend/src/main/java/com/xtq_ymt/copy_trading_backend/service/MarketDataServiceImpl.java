package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.handler.MarketDataHandler;
import com.xtq_ymt.copy_trading_backend.model.MarketData;
import com.xtq_ymt.copy_trading_backend.repository.MarketDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class MarketDataServiceImpl implements MarketDataService {

    private final MarketDataRepository marketDataRepository;
    private final MarketDataHandler marketDataHandler;
    private final Random random = new Random();

    @Autowired
    public MarketDataServiceImpl(MarketDataRepository marketDataRepository, MarketDataHandler marketDataHandler) {
        this.marketDataRepository = marketDataRepository;
        this.marketDataHandler = marketDataHandler;
    }

    /**
     * 定时任务：每5秒生成一次实时市场数据。
     * 数据生成逻辑确保每次插入的时间为当前时间，并且数据库查询时按升序返回数据。
     */
    @Override
    @Scheduled(fixedRate = 5000) // 每5秒生成一批市场数据
    public synchronized void generateMarketData() {
        // 交易品种列表
        String[] symbols = {"EUR/USD", "GBP/USD", "USD/JPY"};

        // 遍历每种交易品种，生成一条市场数据
        for (String symbol : symbols) {
            String storageSymbol = symbol.replace("/", ""); // 去掉符号中的斜杠，适配数据库存储

            // 随机生成价格数据
            double openPrice = random.nextDouble() * 100 + 1; // 开盘价
            double highPrice = openPrice + random.nextDouble() * 5; // 最高价
            double lowPrice = openPrice - random.nextDouble() * 5; // 最低价
            double currentPrice = lowPrice + random.nextDouble() * (highPrice - lowPrice); // 当前价

            // 创建 MarketData 实体，时间为当前时间
            MarketData marketData = new MarketData(
                    null, // ID 由数据库自动生成
                    storageSymbol,
                    openPrice,
                    currentPrice,
                    highPrice,
                    lowPrice,
                    LocalDateTime.now() // 当前时间
            );

            // 保存市场数据到数据库
            marketDataRepository.save(marketData);
        }

        // 生成数据后广播最新市场数据
        broadcastLatestMarketData();
    }

    /**
     * 广播所有交易品种的最新市场数据。
     */
    private void broadcastLatestMarketData() {
        // 查询每种交易品种的最新市场数据
        List<MarketData> latestData = marketDataRepository.findLatestForAllSymbols();

        try {
            marketDataHandler.broadcastLatestMarketData(latestData); // 使用 WebSocket 广播
        } catch (Exception e) {
            System.err.println("Error broadcasting market data: " + e.getMessage());
        }
    }

    /**
     * 获取所有市场数据。
     *
     * @return 市场数据列表
     */
    @Override
    public List<MarketData> getAllMarketData() {
        return marketDataRepository.findAll();
    }

    /**
     * 获取指定交易品种的最新市场数据。
     *
     * @param symbol 交易品种符号
     * @return 最新的市场数据
     */
    @Override
    public MarketData getLatestMarketData(String symbol) {
        return marketDataRepository.findTopBySymbolOrderByTimestampDesc(symbol);
    }

    /**
     * 获取指定交易品种的历史市场数据（支持分页），按时间升序排序。
     *
     * @param symbol 交易品种符号
     * @param page   页码，从0开始
     * @param size   每页记录数
     * @return 指定交易品种的历史市场数据
     */
    @Override
    public List<MarketData> getHistoricalMarketData(String symbol, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "timestamp")); // 按时间升序排序
        return marketDataRepository.findBySymbol(symbol, pageable).getContent(); // 返回分页后的数据
    }
}
