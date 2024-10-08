package com.xtq_ymt.copy_trading_backend.service.impl;

import com.xtq_ymt.copy_trading_backend.dto.MarketDataDTO;
import com.xtq_ymt.copy_trading_backend.model.MarketData;
import com.xtq_ymt.copy_trading_backend.repository.MarketDataRepository;
import com.xtq_ymt.copy_trading_backend.service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarketDataServiceImpl implements MarketDataService {

    private static final Logger logger = LoggerFactory.getLogger(MarketDataServiceImpl.class);

    @Autowired
    private MarketDataRepository marketDataRepository;

    @Override
    public List<MarketDataDTO> getMarketDataBySymbol(String symbol) {  
        logger.info("Entering getMarketDataBySymbol with symbol: {}", symbol);
        // 从 repository 获取 MarketData 列表，并转换为 MarketDataDTO
        List<MarketData> marketDataList = marketDataRepository.findBySymbol(symbol);

        // 检查是否找到了数据
        if (marketDataList == null || marketDataList.isEmpty()) {
            logger.warn("No market data found for symbol: {}", symbol);
            return List.of(); // 返回一个空列表，而不是 null
        }

        logger.info("Fetched {} market data records for symbol: {}", marketDataList.size(), symbol);

        // 转换为 DTO 列表并返回
        return marketDataList.stream().map(marketData -> {
            logger.info("Processing market data for instrument: {}", marketData.getInstrument());
            return new MarketDataDTO(
                marketData.getSymbol(),
                marketData.getInstrument(),
                marketData.getCurrentPrice(),
                marketData.getHighPrice(),
                marketData.getLowPrice(),
                marketData.getTimestamp()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public List<MarketDataDTO> getAllAvailableMarketData() {
        logger.info("Entering getAllAvailableMarketData");
        
        // 获取所有实时市场数据
        List<MarketData> marketDataList = marketDataRepository.findAll(); 
        
        // 新增：打印 marketDataList
        logger.info("MarketData list fetched from database: {}", marketDataList);
        if (marketDataList == null || marketDataList.isEmpty()) {
            logger.warn("No market data found in the database.");
            return List.of(); // 返回空列表
        }

        logger.info("Fetched {} market data records from database.", marketDataList.size());

        // 转换为 DTO 列表并返回
        return marketDataList.stream().map(marketData -> {
            logger.info("Processing market data for instrument: {}", marketData.getInstrument());
            return new MarketDataDTO(
                marketData.getSymbol(),
                marketData.getInstrument(),
                marketData.getCurrentPrice(),
                marketData.getHighPrice(),
                marketData.getLowPrice(),
                marketData.getTimestamp()
            );
        }).collect(Collectors.toList());
    }
}
