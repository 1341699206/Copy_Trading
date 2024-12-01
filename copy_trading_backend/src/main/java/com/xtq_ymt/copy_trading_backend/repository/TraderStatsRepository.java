package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.TraderStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraderStatsRepository extends JpaRepository<TraderStats, Long> {

    // 根据 traderId 查询交易员统计数据
    TraderStats findByTraderId(Long traderId);
}
