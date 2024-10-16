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
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    private final String API_KEY = "cs1qgs9r01qsperuf570cs1qgs9r01qsperuf57g";

    @Scheduled(fixedRate = 3600000) // 每小时执行一次
    @Override
    public void fetchAndStoreMarketData() {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        for (String symbol : symbols) {
            try {
                String apiUrl = "https://finnhub.io/api/v1/quote?symbol=" + symbol + "&token=" + API_KEY;
                ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

                // 检查响应状态
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    JsonNode jsonNode = objectMapper.readTree(response.getBody());
                    
                    // 检查字段是否存在并合理
                    if (jsonNode.has("c") && jsonNode.get("c").decimalValue().compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal price = jsonNode.get("c").decimalValue();  // 当前价格
                        BigDecimal high = jsonNode.has("h") ? jsonNode.get("h").decimalValue() : BigDecimal.ZERO;
                        BigDecimal low = jsonNode.has("l") ? jsonNode.get("l").decimalValue() : BigDecimal.ZERO;
                        BigDecimal open = jsonNode.has("o") ? jsonNode.get("o").decimalValue() : BigDecimal.ZERO;
                        long time = System.currentTimeMillis();

                        // 查找或创建 MarketData 实例
                        MarketData marketData = marketDataRepository.findBySymbol(symbol);
                        if (marketData == null) {
                            marketData = new MarketData();
                            marketData.setSymbol(symbol);
                            marketData.setInstrument(symbol);
                        }

                        // 更新 MarketData 实例
                        marketData.setCurrentPrice(price);
                        marketData.setHighPrice(high);
                        marketData.setLowPrice(low);
                        marketData.setOpenPrice(open);
                        marketData.setTimestamp(new Date(time));

                        // 保存 MarketData 到数据库
                        marketDataRepository.save(marketData);
                        logger.info("MarketData saved for symbol: " + symbol);

                        // 记录历史数据
                        MarketDataHistory marketDataHistory = new MarketDataHistory();
                        marketDataHistory.setSymbol(symbol);
                        marketDataHistory.setInstrument(symbol);
                        marketDataHistory.setPrice(price);
                        marketDataHistory.setHighPrice(high);
                        marketDataHistory.setLowPrice(low);
                        marketDataHistory.setOpenPrice(open);
                        marketDataHistory.setTimestamp(new Date(time));

                        // 保存 MarketDataHistory 到数据库
                        marketDataHistoryRepository.save(marketDataHistory);
                        logger.info("MarketDataHistory saved for symbol: " + symbol);
                    } else {
                        logger.warn("Invalid data received for symbol: " + symbol);
                    }
                } else {
                    logger.error("Failed to fetch market data for symbol: " + symbol + ". Status: " + response.getStatusCode());
                }
            } catch (Exception e) {
                logger.error("Error fetching market data for symbol: " + symbol, e);
            }
        }
    }
}
*/