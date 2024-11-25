package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.MarketData;
import com.xtq_ymt.copy_trading_backend.repository.MarketDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class MarketDataServiceImpl implements MarketDataService {

    // 数据库交互的仓库对象
    private final MarketDataRepository marketDataRepository;

    // 随机数生成器，用于生成市场数据
    private final Random random = new Random();

    @Autowired
    public MarketDataServiceImpl(MarketDataRepository marketDataRepository) {
        this.marketDataRepository = marketDataRepository;
    }

    /**
     * 定时任务：每5秒生成一次随机市场数据。
     * 使用 @Scheduled 注解设置固定频率的任务。
     */
    @Override
    @Scheduled(fixedRate = 5000) // 每5秒执行一次
    public void generateMarketData() {
        // 预定义的交易品种
        String[] symbols = {"EUR/USD", "GBP/USD", "USD/JPY"};
        
        // 为每种交易品种生成随机的市场数据
        for (String symbol : symbols) {
            // 去掉斜杠，生成存储用的 symbol
            String storageSymbol = symbol.replace("/", "");

            // 生成开盘价
            double openPrice = random.nextDouble() * 100 + 1;
            // 生成最高价（高于开盘价的随机数）
            double highPrice = openPrice + random.nextDouble() * 5;
            // 生成最低价（低于开盘价的随机数）
            double lowPrice = openPrice - random.nextDouble() * 5;
            // 生成当前价格（在最低价和最高价之间随机）
            double currentPrice = lowPrice + random.nextDouble() * (highPrice - lowPrice);

            // 创建 MarketData 对象，包含随机生成的价格和时间戳
            MarketData marketData = new MarketData(
                null,  // ID 由数据库自动生成
                storageSymbol,  // 去掉斜杠后的交易品种
                openPrice,  // 开盘价
                currentPrice,  // 当前价格
                highPrice,  // 最高价
                lowPrice,  // 最低价
                LocalDateTime.now()  // 当前时间戳
            );

            // 保存生成的市场数据到数据库
            marketDataRepository.save(marketData);
        }
    }

    /**
     * 获取所有市场数据的列表。
     * @return 所有市场数据
     */
    @Override
    public List<MarketData> getAllMarketData() {
        return marketDataRepository.findAll();
    }

    /**
     * 获取指定交易品种的最新市场数据。
     * @param symbol 交易品种（如 "EURUSD"）
     * @return 最新的市场数据对象
     */
    @Override
    public MarketData getLatestMarketData(String symbol) {
        // 查询数据库中某个交易品种的最新数据（按时间戳降序排序，取第一条）
        return marketDataRepository.findTopBySymbolOrderByTimestampDesc(symbol);
    }
}
