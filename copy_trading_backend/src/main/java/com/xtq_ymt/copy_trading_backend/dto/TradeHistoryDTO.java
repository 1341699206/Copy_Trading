package com.xtq_ymt.copy_trading_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TradeHistoryDTO {
    private Long tradeId;
    private String currency;
    private LocalDateTime dateOpen;
    private String type;
    private LocalDateTime dateClose;
    private BigDecimal standardLots;
    private BigDecimal priceOpen;
    private BigDecimal priceClose;
    private BigDecimal highestProfit;
    private BigDecimal worstDrawdown;
    private BigDecimal interest;
    private BigDecimal profit;
}
