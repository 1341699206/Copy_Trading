package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.MarketData;

import java.util.List;

public interface MarketDataService {
    void generateMarketData(); // 生成随机市场数据
    List<MarketData> getAllMarketData(); // 获取所有市场数据
    MarketData getLatestMarketData(String symbol); // 获取指定交易品种的最新数据
}
