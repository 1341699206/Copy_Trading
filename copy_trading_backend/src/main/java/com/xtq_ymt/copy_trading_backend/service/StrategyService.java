package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Strategy;

public interface StrategyService {

    Strategy createStrategy(Long traderId, String name, String description, String scriptContent);

    Strategy updateStrategyByTraderId(Long traderId, String name, String description, String scriptContent, boolean isActive);

    void deleteStrategyByTraderId(Long traderId);

    Strategy getStrategyByTraderId(Long traderId);

    Long getStrategyIdByUserId(Long userId);
}

