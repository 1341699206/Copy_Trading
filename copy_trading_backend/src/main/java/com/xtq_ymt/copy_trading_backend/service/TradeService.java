package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Trade;

import java.util.List;

public interface TradeService {
    /**
     * 开启新交易
     */
    Trade openTrade(Long accountId, Long strategyId, String symbol, String type, double lotSize);
    
    /**
     * 关闭交易
     */
    Trade closeTrade(Long tradeId);
    
    /**
     * 获取账户的所有交易记录
     */
    List<Trade> getAllTradesByAccountId(Long accountId);
    
    /**
     * 获取账户的未平仓交易
     */
    List<Trade> getOpenTradesByAccountId(Long accountId);
    
    /**
     * 获取账户的已平仓交易
     */
    List<Trade> getClosedTradesByAccountId(Long accountId);
    
    /**
     * 获取策略相关的所有交易
     */
    List<Trade> getTradesByStrategyId(Long strategyId);
    
    /**
     * 更新交易员和跟随者统计数据
     */
    void updateTraderAndFollowerStats(Long tradeId);
}