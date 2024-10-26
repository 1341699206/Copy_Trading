package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.Result.Result;

public interface FollowerService {
    /**
     * 
     * @param quantity //需要的数量
     * @param timePeriod //时间限制
     * @return
     */
    public Result getTopTradersData(Integer quantity,Integer timePeriod);
}
