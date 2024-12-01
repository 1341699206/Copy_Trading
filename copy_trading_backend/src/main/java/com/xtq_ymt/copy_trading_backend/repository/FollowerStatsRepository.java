package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.FollowerStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerStatsRepository extends JpaRepository<FollowerStats, Long> {

    // 根据 followerId 查询跟随者统计数据
    FollowerStats findByFollowerId(Long followerId);
}
