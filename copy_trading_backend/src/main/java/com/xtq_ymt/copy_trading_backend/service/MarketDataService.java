package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.dto.MarketDataDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MarketDataService {
    // 获取指定 symbol 的市场数据并分页返回
    Page<MarketDataDTO> getMarketDataBySymbol(String symbol, Pageable pageable);
}
