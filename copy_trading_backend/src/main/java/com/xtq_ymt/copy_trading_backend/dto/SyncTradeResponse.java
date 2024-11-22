package com.xtq_ymt.copy_trading_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTradeResponse {
    private Long traderId;
    private Long tradeId;
    private String action;
    private double tradeAmount;
    private double price;
    private String status; // success or failed

    // 新增构造函数
    public SyncTradeResponse(SyncTradeRequest request) {
        this.traderId = request.getTraderId();
        this.tradeId = request.getTradeId();
        this.action = request.getAction();
        this.tradeAmount = request.getTradeAmount();
        this.price = request.getPrice();
        this.status = "success"; // 默认值
    }
}
