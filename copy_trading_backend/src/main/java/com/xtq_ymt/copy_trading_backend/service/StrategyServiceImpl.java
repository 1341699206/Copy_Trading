package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Strategy;
import com.xtq_ymt.copy_trading_backend.repository.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StrategyServiceImpl implements StrategyService {

    private final StrategyRepository strategyRepository;

    @Autowired
    public StrategyServiceImpl(StrategyRepository strategyRepository) {
        this.strategyRepository = strategyRepository;
    }

    @Override
    public Strategy createStrategy(Long traderId, String name, String description, String scriptContent) {
        if (strategyRepository.existsByNameAndTraderId(name, traderId)) {
            throw new IllegalArgumentException("Strategy with the same name already exists for this trader.");
        }
        Strategy strategy = new Strategy();
        strategy.setTraderId(traderId);
        strategy.setName(name);
        strategy.setDescription(description);
        strategy.setScriptContent(scriptContent);
        strategy.setActive(true);
        return strategyRepository.save(strategy);
    }

    @Override
    public Strategy updateStrategy(Long id, String name, String description, String scriptContent, boolean isActive) {
        Strategy strategy = strategyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Strategy not found."));
        strategy.setName(name);
        strategy.setDescription(description);
        strategy.setScriptContent(scriptContent);
        strategy.setActive(isActive);
        return strategyRepository.save(strategy);
    }

    @Override
    public void deleteStrategy(Long id) {
        if (!strategyRepository.existsById(id)) {
            throw new IllegalArgumentException("Strategy not found.");
        }
        strategyRepository.deleteById(id);
    }

    @Override
    public List<Strategy> getStrategiesByTraderId(Long traderId) {
        return strategyRepository.findByTraderId(traderId);
    }

    @Override
    public Strategy getStrategyById(Long id) {
        return strategyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Strategy not found."));
    }
}
