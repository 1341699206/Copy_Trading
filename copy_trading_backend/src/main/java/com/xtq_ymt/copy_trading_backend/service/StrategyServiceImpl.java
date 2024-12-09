package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Strategy;
import com.xtq_ymt.copy_trading_backend.model.User;
import com.xtq_ymt.copy_trading_backend.repository.StrategyRepository;
import com.xtq_ymt.copy_trading_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StrategyServiceImpl implements StrategyService {

    private final StrategyRepository strategyRepository;
    private final UserRepository userRepository;

    @Autowired
    public StrategyServiceImpl(StrategyRepository strategyRepository, UserRepository userRepository) {
        this.strategyRepository = strategyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Strategy createStrategy(Long traderId, String name, String description, String scriptContent) {
        User trader = userRepository.findById(traderId)
                .orElseThrow(() -> new IllegalArgumentException("Trader not found with ID: " + traderId));
        if (strategyRepository.findByTraderId(traderId).isPresent()) {
            throw new IllegalArgumentException("Trader already has a strategy.");
        }
        Strategy strategy = new Strategy();
        strategy.setTrader(trader);
        strategy.setName(name);
        strategy.setDescription(description);
        strategy.setScriptContent(scriptContent);
        strategy.setActive(true);
        return strategyRepository.save(strategy);
    }

    @Override
    public Strategy updateStrategyByTraderId(Long traderId, String name, String description, String scriptContent, boolean isActive) {
        Strategy strategy = strategyRepository.findByTraderId(traderId)
                .orElseThrow(() -> new IllegalArgumentException("Strategy not found for trader ID: " + traderId));
        strategy.setName(name);
        strategy.setDescription(description);
        strategy.setScriptContent(scriptContent);
        strategy.setActive(isActive);
        return strategyRepository.save(strategy);
    }

    @Override
    public void deleteStrategyByTraderId(Long traderId) {
        Strategy strategy = strategyRepository.findByTraderId(traderId)
                .orElseThrow(() -> new IllegalArgumentException("Strategy not found for trader ID: " + traderId));
        strategyRepository.delete(strategy);
    }

    @Override
    public Strategy getStrategyByTraderId(Long traderId) {
        return strategyRepository.findByTraderId(traderId)
                .orElseThrow(() -> new IllegalArgumentException("Strategy not found for trader ID: " + traderId));
    }

    @Override
    public Long getStrategyIdByUserId(Long userId) {
        return strategyRepository.findByTraderId(userId)
                .map(Strategy::getId)
                .orElseThrow(() -> new IllegalArgumentException("Strategy not found for user ID: " + userId));
    }
}
