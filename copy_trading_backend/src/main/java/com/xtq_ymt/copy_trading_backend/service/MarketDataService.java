package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.MarketData;

import java.util.List;

public interface MarketDataService {
    void generateMarketData(); // 生成随机市场数据
    List<MarketData> getAllMarketData(); // 获取所有市场数据
    MarketData getLatestMarketData(String symbol); // 获取指定交易品种的最新数据

    /**
     * 获取指定 symbol 的历史市场数据
     * 支持分页
     *
     * @param symbol 交易品种
     * @param page 页码，从 0 开始
     * @param size 每页记录数
     * @return 指定 symbol 的历史 MarketData 列表
     */
    List<MarketData> getHistoricalMarketData(String symbol, int page, int size);
}
