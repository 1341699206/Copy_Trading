package com.xtq_ymt.copy_trading_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private Long userId;
    private String type;
    private String message;
    private String status; // success, failed
}
