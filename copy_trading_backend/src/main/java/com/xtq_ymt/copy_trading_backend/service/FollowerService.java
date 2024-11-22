package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.FollowerTrader;

import java.util.List;

public interface FollowerService {

    FollowerTrader followTrader(Long followerAccountId, Long traderAccountId);

    void unfollowTrader(Long followerAccountId, Long traderAccountId);

    List<FollowerTrader> getFollowedTraders(Long followerAccountId);
}
