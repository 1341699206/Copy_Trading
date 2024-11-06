package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.Result.Result;

public interface FollowerService {
    /**
     * 获取指定数量的 top trader 数据
     *
     * @param quantity //需要的数量
     * @param timePeriod //时间限制
     * @return 包含top trader数据的结果对象
     */
    Result getTopTradersData(Integer quantity, Integer timePeriod);

    /**
     * 获取指定 trader 的基本信息
     *
     * @param id // trader 的唯一标识符
     * @return 包含trader基本信息的结果对象
     */
    Result getTraderBasicInfo(Long id);

    /**
     * 获取指定 trader 的详细信息，包含时间段限制
     *
     * @param id // trader 的唯一标识符
     * @param timePeriod // 时间限制
     * @return 包含trader详细信息的结果对象
     */
    Result getTraderDetailedInfo(Long id, Integer timePeriod);
}
