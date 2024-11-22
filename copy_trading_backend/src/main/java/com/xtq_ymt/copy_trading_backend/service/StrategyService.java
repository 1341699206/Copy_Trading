package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Strategy;

import java.util.List;

public interface StrategyService {

    Strategy createStrategy(Long traderId, String name, String description, String scriptContent);

    Strategy updateStrategy(Long id, String name, String description, String scriptContent, boolean isActive);

    void deleteStrategy(Long id);

    List<Strategy> getStrategiesByTraderId(Long traderId);

    Strategy getStrategyById(Long id);
}
