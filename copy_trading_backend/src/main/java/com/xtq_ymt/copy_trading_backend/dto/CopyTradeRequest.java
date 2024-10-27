package com.xtq_ymt.copy_trading_backend.dto;

import lombok.Data;

@Data
public class CopyTradeRequest {
    private Long traderId;
    private Long followerId;
    private Long traderAccountId;  // 新增
    private Long followerAccountId; // 新增
}
