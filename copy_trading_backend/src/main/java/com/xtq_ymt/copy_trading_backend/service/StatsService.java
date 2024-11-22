package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.TraderStats;
import com.xtq_ymt.copy_trading_backend.model.FollowerStats;

import java.util.List;

public interface StatsService {

    TraderStats calculateTraderStats(Long traderId);

    FollowerStats calculateFollowerStats(Long followerId);

    List<TraderStats> getTopTradersByProfit(int limit);

    List<FollowerStats> getTopFollowersByProfit(int limit);
}
