package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Trade;

import java.util.List;

public interface TradeService {

    Trade openTrade(Long accountId, Long strategyId, String symbol, String type, double lotSize, double priceOpen);

    Trade closeTrade(Long tradeId, double priceClose);

    List<Trade> getOpenTradesByAccountId(Long accountId);

    List<Trade> getTradesByStrategyId(Long strategyId);
}
