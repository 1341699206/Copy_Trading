package com.xtq_ymt.copy_trading_backend.exception;

/**
 * FollowerStatsNotFoundException：当指定的跟随者统计数据未找到时抛出
 */
public class FollowerStatsNotFoundException extends RuntimeException {
    public FollowerStatsNotFoundException(String message) {
        super(message);
    }
}
