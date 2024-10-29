package com.xtq_ymt.copy_trading_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor  // 生成包含所有字段的构造函数
@NoArgsConstructor   // 生成无参构造函数
public class MarketDataDTO {

    private String symbol;       // 资产类型
    private String instrument;   // 金融工具名称
    private BigDecimal currentPrice; // 当前价格
    private BigDecimal highPrice;    // 最高价格
    private BigDecimal lowPrice;     // 最低价格
    private LocalDateTime timestamp;          // 时间戳
}
