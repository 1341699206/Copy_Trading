package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.dto.MarketDataDTO;

public interface MarketDataService {
    // 获取指定 symbol 的市场数据
    MarketDataDTO getMarketDataBySymbol(String symbol);
}
