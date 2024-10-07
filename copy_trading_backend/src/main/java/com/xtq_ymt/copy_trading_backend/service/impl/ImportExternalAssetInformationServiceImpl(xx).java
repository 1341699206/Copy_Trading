/* 
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
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class ImportExternalAssetInformationServiceImpl implements ImportExternalAssetInformationService {

    private static final Logger logger = LoggerFactory.getLogger(ImportExternalAssetInformationServiceImpl.class);

    @Autowired
    private MarketDataRepository marketDataRepository;

    @Autowired
    private MarketDataHistoryRepository marketDataHistoryRepository;

    private final String[] symbols = {
        "EURJPY", "EURCHF", "CADJPY", "EURUSD", "USDCAD", "GBPUSD", "USDJPY", "EURGBP", "USDCHF", "GBPJPY"
    };

    @Scheduled(fixedRate = 3600000) // 每1小时执行一次
    @Override
    public void fetchAndStoreMarketData() {
        int requestsPerMinute = 0; // 记录每分钟请求次数
        
        for (int i = 0; i < symbols.length; i++) {
            String symbol = symbols[i];
            try {
                Stock stock = YahooFinance.get(symbol + "=X");
                if (stock != null && stock.getQuote() != null) {
                    BigDecimal price = stock.getQuote().getPrice();
                    BigDecimal high = stock.getQuote().getDayHigh();
                    BigDecimal low = stock.getQuote().getDayLow();
                    BigDecimal open = stock.getQuote().getOpen();
                    long time = System.currentTimeMillis();

                    logger.debug("Fetched data for symbol " + symbol + " - Price: " + price + ", High: " + high + ", Low: " + low + ", Open: " + open);

                    MarketData marketData = marketDataRepository.findBySymbol(symbol);
                    if (marketData == null) {
                        marketData = new MarketData();
                        marketData.setSymbol(symbol);
                        marketData.setInstrument(symbol);
                    }

                    marketData.setCurrentPrice(price);
                    marketData.setHighPrice(high);
                    marketData.setLowPrice(low);
                    marketData.setOpenPrice(open);
                    marketData.setTimestamp(new Date(time));

                    marketDataRepository.save(marketData);
                    logger.info("MarketData saved for symbol: " + symbol);

                    MarketDataHistory marketDataHistory = new MarketDataHistory();
                    marketDataHistory.setSymbol(symbol);
                    marketDataHistory.setInstrument(symbol);
                    marketDataHistory.setPrice(price);
                    marketDataHistory.setHighPrice(high);
                    marketDataHistory.setLowPrice(low);
                    marketDataHistory.setOpenPrice(open);
                    marketDataHistory.setTimestamp(new Date(time));

                    marketDataHistoryRepository.save(marketDataHistory);
                    logger.info("MarketDataHistory saved for symbol: " + symbol);
                } else {
                    logger.warn("No data available for symbol: " + symbol);
                }
            } catch (IOException e) {
                logger.error("Error fetching market data for symbol: " + symbol, e);
            }

            // 控制每分钟请求数不超过2次
            requestsPerMinute++;
            if (requestsPerMinute >= 2) {
                try {
                    logger.info("Reached 2 requests, waiting 1 minute before proceeding...");
                    Thread.sleep(60000); // 暂停1分钟
                    requestsPerMinute = 0; // 重置请求计数
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("Thread interrupted during wait", e);
                }
            }
        }
    }
}
*/