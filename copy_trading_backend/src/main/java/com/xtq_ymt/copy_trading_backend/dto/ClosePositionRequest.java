package com.xtq_ymt.copy_trading_backend.dto;

import lombok.Data;

@Data
public class ClosePositionRequest {
    private Long tradeId;
}