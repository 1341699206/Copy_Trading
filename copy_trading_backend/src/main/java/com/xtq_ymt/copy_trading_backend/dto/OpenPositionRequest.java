package com.xtq_ymt.copy_trading_backend.dto;

import com.xtq_ymt.copy_trading_backend.model.TradeActionType;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class OpenPositionRequest {
    private Long accountId;
    private String instrument;
    private BigDecimal volume;
    private BigDecimal leverage;
    private TradeActionType actionType;
}