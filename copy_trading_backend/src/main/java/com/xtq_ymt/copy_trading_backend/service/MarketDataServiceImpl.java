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
     * 定时任务：每5秒生成一次随机市场数据。
     */
    @Override
    @Scheduled(fixedRate = 5000)
    public synchronized void generateMarketData() {
        String[] symbols = {"EUR/USD", "GBP/USD", "USD/JPY"};

        for (String symbol : symbols) {
            String storageSymbol = symbol.replace("/", "");
            double openPrice = random.nextDouble() * 100 + 1;
            double highPrice = openPrice + random.nextDouble() * 5;
            double lowPrice = openPrice - random.nextDouble() * 5;
            double currentPrice = lowPrice + random.nextDouble() * (highPrice - lowPrice);

            MarketData marketData = new MarketData(
                    null,
                    storageSymbol,
                    openPrice,
                    currentPrice,
                    highPrice,
                    lowPrice,
                    LocalDateTime.now()
            );

            marketDataRepository.save(marketData);
        }

        // 在生成数据后广播最新市场数据
        broadcastLatestMarketData();
    }

    private void broadcastLatestMarketData() {
        List<MarketData> latestData = marketDataRepository.findLatestForAllSymbols();

        try {
            marketDataHandler.broadcastLatestMarketData(latestData);
        } catch (Exception e) {
            System.err.println("Error broadcasting market data: " + e.getMessage());
        }
    }

    @Override
    public List<MarketData> getAllMarketData() {
        return marketDataRepository.findAll();
    }

    @Override
    public MarketData getLatestMarketData(String symbol) {
        return marketDataRepository.findTopBySymbolOrderByTimestampDesc(symbol);
    }

    /**
     * 获取指定 symbol 的历史市场数据（支持分页）
     *
     * @param symbol 交易品种
     * @param page 页码，从 0 开始
     * @param size 每页记录数
     * @return 指定 symbol 的历史 MarketData 列表
     */
    @Override
    public List<MarketData> getHistoricalMarketData(String symbol, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        // 使用 getContent() 提取内容部分
        return marketDataRepository.findBySymbol(symbol, pageable).getContent();
    }
}
