package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.FollowerTrader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerTraderRepository extends JpaRepository<FollowerTrader, Long> {

    List<FollowerTrader> findByFollowerAccountId(Long followerAccountId);

    boolean existsByFollowerAccountIdAndTraderAccountId(Long followerAccountId, Long traderAccountId);
}
