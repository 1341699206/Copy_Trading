package com.xtq_ymt.copy_trading_backend.service.impl;

import com.xtq_ymt.copy_trading_backend.dto.MarketDataDTO;
import com.xtq_ymt.copy_trading_backend.model.MarketData;
import com.xtq_ymt.copy_trading_backend.repository.MarketDataRepository;
import com.xtq_ymt.copy_trading_backend.service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketDataServiceImpl implements MarketDataService {

    @Autowired
    private MarketDataRepository marketDataRepository;

    @Override
    public MarketDataDTO getMarketDataBySymbol(String symbol) {
        // 从 repository 获取 MarketData，并转换为 MarketDataDTO
        MarketData marketData = marketDataRepository.findBySymbol(symbol);

        if (marketData != null) {
            return new MarketDataDTO(
                marketData.getSymbol(),
                marketData.getInstrument(),
                marketData.getCurrentPrice(),
                marketData.getHighPrice(),
                marketData.getLowPrice(),
                marketData.getTimestamp()
            );
        }
        // 如果没有找到该 symbol 对应的数据，则可以返回 null 或抛出异常
        return null;
    }
}
