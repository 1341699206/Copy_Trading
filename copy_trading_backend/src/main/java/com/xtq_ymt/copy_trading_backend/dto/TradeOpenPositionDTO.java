package com.xtq_ymt.copy_trading_backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TradeOpenPositionDTO {
    private Long trade;            // tradeId
    private String currency;       // instrument
    private LocalDateTime dateOpen; // 开仓时间
    private String type;           // 类型 (买/卖)
    private BigDecimal standardLots; // 标准手数
    private BigDecimal priceOpen;   // 开仓价格
    private BigDecimal priceCurrent; // 当前价格
    private BigDecimal profit;      // 收益
    private BigDecimal stop;        // 止损线
    private BigDecimal limit;       // 止盈线
}
