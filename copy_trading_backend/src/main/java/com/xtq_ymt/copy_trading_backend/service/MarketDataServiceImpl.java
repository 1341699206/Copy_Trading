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

    private final MarketDataRepository marketDataRepository;
    private final Random random = new Random();

    @Autowired
    public MarketDataServiceImpl(MarketDataRepository marketDataRepository) {
        this.marketDataRepository = marketDataRepository;
    }

    @Override
    @Scheduled(fixedRate = 5000) // 每5秒生成一次数据
    public void generateMarketData() {
        String[] symbols = {"EUR/USD", "GBP/USD", "USD/JPY"};
        for (String symbol : symbols) {
            double openPrice = random.nextDouble() * 100 + 1;
            double highPrice = openPrice + random.nextDouble() * 5;
            double lowPrice = openPrice - random.nextDouble() * 5;
            double currentPrice = lowPrice + random.nextDouble() * (highPrice - lowPrice);

            MarketData marketData = new MarketData(null, symbol, openPrice, currentPrice, highPrice, lowPrice, LocalDateTime.now());
            marketDataRepository.save(marketData);
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
}
