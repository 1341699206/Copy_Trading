package com.xtq_ymt.copy_trading_backend.service.impl;

import com.xtq_ymt.copy_trading_backend.dto.MarketDataDTO;
import com.xtq_ymt.copy_trading_backend.repository.MarketDataRepository;
import com.xtq_ymt.copy_trading_backend.service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MarketDataServiceImpl implements MarketDataService {

    @Autowired
    private MarketDataRepository marketDataRepository;

    @Override
    public Page<MarketDataDTO> getMarketDataBySymbol(String symbol, Pageable pageable) {
        // 从 repository 获取 MarketData 并转换为 MarketDataDTO
        return marketDataRepository.findBySymbol(symbol, pageable)
                .map(marketData -> new MarketDataDTO(
                    marketData.getSymbol(),
                    marketData.getInstrument(),
                    marketData.getCurrentPrice(),
                    marketData.getHighPrice(),
                    marketData.getLowPrice(),
                    marketData.getTimestamp()
                ));
    }
}
