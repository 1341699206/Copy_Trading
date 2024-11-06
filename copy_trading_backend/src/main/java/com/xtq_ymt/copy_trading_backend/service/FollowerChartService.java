package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.dto.FollowerCopyDTO;

public interface FollowerChartService {

    // 根据 traderId 和 followerId 获取跟单的历史数据用于绘图
    String getFollowedChartData(Long traderId, Long followerId);

    // 根据 traderId 和 followerId 获取 Follower 的复制交易收益信息
    FollowerCopyDTO getFollowerCopyInfo(Long traderId, Long followerId);
}
