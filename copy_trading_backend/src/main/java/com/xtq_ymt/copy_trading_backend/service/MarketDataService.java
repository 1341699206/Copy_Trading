package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.Result.Result;
import com.xtq_ymt.copy_trading_backend.dto.MarketDataDTO;

import java.util.List;

public interface MarketDataService {
    // 获取指定 symbol 的市场数据（返回多个数据）
    List<MarketDataDTO> getMarketDataBySymbol(String symbol);

    // 获取所有可用的市场数据
    List<MarketDataDTO> getAllAvailableMarketData();

    // 获取指定某项市场数据的详细信息
    Result getMarketDataDetail(String instrument,Integer timePeriod);
}
