package com.xtq_ymt.copy_trading_backend.service.impl;

import com.xtq_ymt.copy_trading_backend.model.MarketData;
import com.xtq_ymt.copy_trading_backend.model.MarketDataHistory;
import com.xtq_ymt.copy_trading_backend.repository.MarketDataRepository;
import com.xtq_ymt.copy_trading_backend.repository.MarketDataHistoryRepository;
import com.xtq_ymt.copy_trading_backend.service.ImportExternalAssetInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Random;

@Service
public class ImportExternalAssetInformationServiceImpl implements ImportExternalAssetInformationService {

    private static final Logger logger = LoggerFactory.getLogger(ImportExternalAssetInformationServiceImpl.class);

    @Autowired
    private MarketDataRepository marketDataRepository;

    @Autowired
    private MarketDataHistoryRepository marketDataHistoryRepository;

    private final String[] symbols = {
        "EURJPY", "EURCHF", "CADJPY", "EURUSD", "USDCAD", "GBPUSD", "USDJPY", "EURGBP", "USDCHF", "GBPJPY",
        "EURCAD", "GBPCHF", "CADCHF"
    };

    private final Random random = new Random();

    @Scheduled(fixedRate = 60000) // 每分钟执行一次
    @Override
    public void fetchAndStoreMarketData() {
        Date currentTime = new Date();

        for (String symbol : symbols) {
            try {
                // 模拟生成价格数据
                BigDecimal currentPrice = BigDecimal.valueOf(100 + (500 - 100) * random.nextDouble()).setScale(8, RoundingMode.HALF_UP);
                BigDecimal highPrice = currentPrice.add(BigDecimal.valueOf(random.nextDouble() * 10)).setScale(8, RoundingMode.HALF_UP);
                BigDecimal lowPrice = currentPrice.subtract(BigDecimal.valueOf(random.nextDouble() * 10)).setScale(8, RoundingMode.HALF_UP);
                BigDecimal volume = BigDecimal.valueOf(1000 + (5000 - 1000) * random.nextDouble()).setScale(8, RoundingMode.HALF_UP);
                BigDecimal volatility = BigDecimal.valueOf(random.nextDouble()).setScale(8, RoundingMode.HALF_UP);

                // 查找或创建 MarketData 实例
                MarketData marketData = marketDataRepository.findBySymbol(symbol);
                if (marketData == null) {
                    marketData = new MarketData();
                    marketData.setSymbol(symbol);
                    marketData.setInstrument(symbol);
                }

                // 更新 MarketData 实例
                marketData.setCurrentPrice(currentPrice);
                marketData.setHighPrice(highPrice);
                marketData.setLowPrice(lowPrice);
                marketData.setVolume(volume);
                marketData.setVolatility(volatility);
                marketData.setTimestamp(currentTime);
                marketData.setUpdatedAt(currentTime);

                // 保存 MarketData 到数据库
                marketDataRepository.save(marketData);
                logger.info("MarketData saved for symbol: " + symbol);

                // 记录历史数据
                MarketDataHistory marketDataHistory = new MarketDataHistory();
                marketDataHistory.setSymbol(symbol);
                marketDataHistory.setInstrument(symbol);
                marketDataHistory.setPrice(currentPrice);
                marketDataHistory.setHighPrice(highPrice);
                marketDataHistory.setLowPrice(lowPrice);
                marketDataHistory.setVolume(volume);
                marketDataHistory.setVolatility(volatility);
                marketDataHistory.setTimestamp(currentTime);

                // 保存 MarketDataHistory 到数据库
                marketDataHistoryRepository.save(marketDataHistory);
                logger.info("MarketDataHistory saved for symbol: " + symbol);
            } catch (Exception e) {
                logger.error("Error generating market data for symbol: " + symbol, e);
            }
        }
    }
}