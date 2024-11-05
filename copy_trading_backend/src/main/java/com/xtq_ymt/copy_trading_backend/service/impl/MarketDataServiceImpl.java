package com.xtq_ymt.copy_trading_backend.service.impl;

import com.xtq_ymt.copy_trading_backend.Result.Result;
import com.xtq_ymt.copy_trading_backend.dto.MarketDataDTO;
import com.xtq_ymt.copy_trading_backend.dto.MarketDataHistoryDTO;
import com.xtq_ymt.copy_trading_backend.model.MarketData;
import com.xtq_ymt.copy_trading_backend.model.MarketDataHistory;
import com.xtq_ymt.copy_trading_backend.repository.MarketDataHistoryRepository;
import com.xtq_ymt.copy_trading_backend.repository.MarketDataRepository;
import com.xtq_ymt.copy_trading_backend.service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MarketDataServiceImpl implements MarketDataService {

    private static final Logger logger = LoggerFactory.getLogger(MarketDataServiceImpl.class);

    @Autowired
    private MarketDataRepository marketDataRepository;

    @Autowired
    private MarketDataHistoryRepository marketDataHistoryRepository;

    @Override
    public List<MarketDataDTO> getMarketDataBySymbol(String symbol) {
        logger.info("Entering getMarketDataBySymbol with symbol: {}", symbol);

        List<MarketData> marketDataList = marketDataRepository.findBySymbol(symbol);

        if (marketDataList == null || marketDataList.isEmpty()) {
            logger.warn("No market data found for symbol: {}", symbol);
            return List.of();
        }

        logger.info("Fetched {} market data records for symbol: {}", marketDataList.size(), symbol);

        return marketDataList.stream().map(marketData -> {
            logger.info("Processing market data for instrument: {}", marketData.getInstrument());
            return new MarketDataDTO(
                    marketData.getSymbol(),
                    marketData.getInstrument(),
                    marketData.getOpenPrice(),        // 添加 openPrice 字段
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

        List<MarketData> marketDataList = marketDataRepository.findAll();

        logger.info("MarketData list fetched from database: {}", marketDataList);
        if (marketDataList == null || marketDataList.isEmpty()) {
            logger.warn("No market data found in the database.");
            return List.of();
        }

        logger.info("Fetched {} market data records from database.", marketDataList.size());

        return marketDataList.stream().map(marketData -> {
            logger.info("Processing market data for instrument: {}", marketData.getInstrument());
            return new MarketDataDTO(
                    marketData.getSymbol(),
                    marketData.getInstrument(),
                    marketData.getOpenPrice(),
                    marketData.getCurrentPrice(),
                    marketData.getHighPrice(),
                    marketData.getLowPrice(),
                    marketData.getTimestamp()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public BigDecimal getCurrentPrice(String instrument) {
        logger.info("Entering getCurrentPrice with instrument: {}", instrument);
        Optional<MarketData> marketDataOptional = marketDataRepository.findById(instrument);
        if (marketDataOptional.isPresent()) {
            BigDecimal currentPrice = marketDataOptional.get().getCurrentPrice();
            logger.info("Current price for instrument {} is: {}", instrument, currentPrice);
            return currentPrice;
        } else {
            logger.warn("No market data found for instrument: {}", instrument);
            throw new IllegalArgumentException("No market data available for the given instrument.");
        }
    }

    @Override
    public Result getMarketDataDetail(String instrument, Integer timePeriod) {
        List<MarketDataHistory> marketDataDetail = new ArrayList<>();
        if (timePeriod == null || timePeriod == 0) {
            marketDataDetail = marketDataHistoryRepository.findByInstrumentOrderByTimestampAsc(instrument);
        } else {
            LocalDateTime startDate = LocalDateTime.now().minusDays(timePeriod);
            LocalDateTime endDate = LocalDateTime.now();

            marketDataDetail = marketDataHistoryRepository
                    .findByInstrumentAndTimestampBetweenOrderByTimestampAsc(instrument, startDate, endDate);
        }

        List<MarketDataHistoryDTO> marketDataHistory = new ArrayList<>();
        for (MarketDataHistory history : marketDataDetail) {
            MarketDataHistoryDTO historyData = new MarketDataHistoryDTO(
                    instrument,
                    history.getPrice(),
                    history.getOpenPrice(),
                    history.getClosePrice(),
                    history.getTimestamp()
            );
            marketDataHistory.add(historyData);
        }

        return Result.success("Obtaining instrument historical data succeeded.", marketDataHistory);
    }
}
