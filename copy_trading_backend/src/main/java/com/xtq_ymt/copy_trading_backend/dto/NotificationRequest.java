package com.xtq_ymt.copy_trading_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private Long userId;
    private String type; // trade_complete, strategy_update, follow_update
    private String message;
}
