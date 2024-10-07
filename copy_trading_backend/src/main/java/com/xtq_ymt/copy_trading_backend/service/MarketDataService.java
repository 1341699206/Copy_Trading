package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.dto.MarketDataDTO;

import java.util.List;

public interface MarketDataService {
    // 获取指定 symbol 的市场数据
    MarketDataDTO getMarketDataBySymbol(String symbol);

    // 新增方法：获取所有可用的市场数据
    List<MarketDataDTO> getAllAvailableMarketData();
}
