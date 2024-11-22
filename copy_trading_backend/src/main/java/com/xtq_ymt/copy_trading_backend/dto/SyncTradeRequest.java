package com.xtq_ymt.copy_trading_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTradeRequest {
    private Long traderId;
    private Long strategyId;
    private Long tradeId;
    private String action; // open, close, stop-loss, take-profit
    private double tradeAmount;
    private double price;
}
