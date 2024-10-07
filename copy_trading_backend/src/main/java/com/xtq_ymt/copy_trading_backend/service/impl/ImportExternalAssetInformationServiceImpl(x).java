
/* 
package com.xtq_ymt.copy_trading_backend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

@Service
public class ImportExternalAssetInformationServiceImpl implements ImportExternalAssetInformationService {

    private static final Logger logger = LoggerFactory.getLogger(ImportExternalAssetInformationServiceImpl.class);

    @Autowired
    private MarketDataRepository marketDataRepository;

    @Autowired
    private MarketDataHistoryRepository marketDataHistoryRepository;

    private final String apiKey = "91F3HOJSK4YI488I"; // Alpha Vantage API 密钥
  
    private final String[] symbols = {
    // Major FX
    "EURJPY", "EURUSD", "GBPUSD", "EURCHF", "USDCAD", "USDJPY", "EURGBP", "USDCHF", "GBPJPY", "CADJPY",
    "GBPCAD", "GBPCHF", "CHFJPY", "EURCAD", "CADCHF"
    };


    // 定义常量来确保字段名的一致性
    private static final String CLOSE_FIELD = "4. close";
    private static final String HIGH_FIELD = "2. high";
    private static final String LOW_FIELD = "3. low";

    @Scheduled(fixedRate = 86400000) // 每天执行一次
    @Override
    public void fetchAndStoreMarketData() {
        int requestsPerMinute = 0;
        for (int i = 0; i < symbols.length; i++) {
            String symbol = symbols[i];
            boolean success = false;
            int attempts = 0;
            while (!success && attempts < 3) { // 重试机制，最多尝试3次
                attempts++;
                logger.debug("Attempt " + attempts + " to fetch data for symbol: " + symbol);
                try {
                    // 构建请求 URL
                    String apiUrl = "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol="
                                    + symbol.substring(0, 3) + "&to_symbol=" + symbol.substring(3)
                                    + "&outputsize=compact&apikey=" + apiKey;
                    URL url = new URL(apiUrl);
                    
                    logger.debug("Request URL: " + apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0");

                    int responseCode = connection.getResponseCode();
                    logger.debug("Response code for symbol " + symbol + ": " + responseCode);
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        logger.debug("Response for symbol " + symbol + ": " + response.toString());

                        // 使用 Jackson 解析响应数据
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonResponse = objectMapper.readTree(response.toString());

                        // 捕获完整的 JSON 响应日志
                        logger.info("Full JSON response for symbol " + symbol + ": " + jsonResponse.toString());

                        // 检查是否包含 "Note" 字段
                        if (jsonResponse.has("Note")) {
                            logger.warn("API limit reached for symbol: " + symbol + ". Response: " + jsonResponse.get("Note").asText());
                            break;
                        }

                        // 检查是否包含 "Error Message" 字段
                        if (jsonResponse.has("Error Message")) {
                            logger.warn("Error message from API for symbol: " + symbol + ". Response: " + jsonResponse.get("Error Message").asText());
                            break;
                        }

                        // 如果响应包含 "Time Series FX (Daily)" 字段，处理时间序列数据
                        if (jsonResponse.has("Time Series FX (Daily)")) {
                            JsonNode timeSeries = jsonResponse.get("Time Series FX (Daily)");
                            String latestTime = timeSeries.fieldNames().next(); // 获取最近时间点的数据
                            JsonNode latestData = timeSeries.get(latestTime);

                            // 获取字段值，确保字段存在
                            BigDecimal price = getBigDecimalField(latestData, CLOSE_FIELD, symbol);
                            BigDecimal high = getBigDecimalField(latestData, HIGH_FIELD, symbol);
                            BigDecimal low = getBigDecimalField(latestData, LOW_FIELD, symbol);

                            // 检查值是否为 null，避免保存空数据
                            if (price != null && high != null && low != null) {
                                logger.debug("Fetched data for symbol " + symbol + " - Price: " + price + ", High: " + high + ", Low: " + low);

                                // 查找数据库中是否已有该符号的数据，如果没有则创建新的 MarketData 实例
                                MarketData marketData = marketDataRepository.findBySymbol(symbol);
                                if (marketData == null) {
                                    logger.debug("No existing MarketData found for symbol: " + symbol + ". Creating new entry.");
                                    marketData = new MarketData();
                                    marketData.setSymbol(symbol);
                                    marketData.setInstrument(symbol);
                                }

                                // 更新 MarketData 实例的数据
                                marketData.setCurrentPrice(price);
                                marketData.setHighPrice(high);
                                marketData.setLowPrice(low);
                                marketData.setTimestamp(new Date());

                                // 保存更新后的 MarketData 实例到数据库
                                marketDataRepository.save(marketData);
                                logger.info("MarketData saved for symbol: " + symbol);

                                // 记录历史数据，创建新的 MarketDataHistory 实例
                                MarketDataHistory marketDataHistory = new MarketDataHistory();
                                marketDataHistory.setSymbol(symbol);
                                marketDataHistory.setInstrument(symbol);
                                marketDataHistory.setPrice(price);
                                marketDataHistory.setHighPrice(high);
                                marketDataHistory.setLowPrice(low);
                                marketDataHistory.setTimestamp(new Date());

                                // 保存历史数据到数据库
                                marketDataHistoryRepository.save(marketDataHistory);
                                logger.info("MarketDataHistory saved for symbol: " + symbol);
                                
                                success = true;
                                requestsPerMinute++;
                            } else {
                                logger.warn("Some expected fields (close, high, low) are missing in the response for symbol: " + symbol);
                            }
                        } else {
                            logger.warn("Time Series FX data not available for symbol: " + symbol);
                            break;
                        }
                    } else if (responseCode == 429) { 
                        logger.warn("Rate limit reached for symbol: " + symbol + ". Waiting for 1 minute before retrying.");
                        Thread.sleep(60000); 
                    } else {
                        logger.error("GET request failed for symbol: " + symbol + ". Response Code: " + responseCode);
                        Thread.sleep(5000);
                    }
                } catch (Exception e) {
                    logger.error("Error fetching market data for symbol: " + symbol + ". Attempt: " + attempts, e);
                    try {
                        Thread.sleep(5000); 
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        logger.error("Thread interrupted while waiting to retry", ie);
                    }
                }
            }
            // 每请求5个符号后等待一分钟，以符合 Alpha Vantage 的速率限制
            if (requestsPerMinute >= 5) {
                try {
                    logger.info("Rate limit reached. Waiting for 1 minute before proceeding.");
                    Thread.sleep(60000); 
                    requestsPerMinute = 0;
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    logger.error("Thread interrupted while waiting for rate limit cooldown", ie);
                }
            }
        }
    }

    // 通用的字段获取方法，避免重复代码
    private BigDecimal getBigDecimalField(JsonNode data, String fieldName, String symbol) {
        if (data.has(fieldName)) {
            return new BigDecimal(data.get(fieldName).asText());
        } else {
            logger.warn("Field '" + fieldName + "' not found for symbol: " + symbol);
            return null;
        }
    }
}
*/