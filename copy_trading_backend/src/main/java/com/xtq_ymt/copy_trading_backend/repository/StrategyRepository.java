package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StrategyRepository extends JpaRepository<Strategy, Long> {

    // 根据 traderId 查找唯一的策略
    Optional<Strategy> findByTraderId(Long traderId);

    // 检查特定 Trader 是否有一个特定名称的策略
    boolean existsByNameAndTraderId(String name, Long traderId);
}
