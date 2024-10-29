package com.xtq_ymt.copy_trading_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 生成包含所有字段的构造函数
@NoArgsConstructor // 生成无参构造函数
public class MarketDataHistoryDTO {
    private String instrument; // 金融工具名称
    private BigDecimal price; // 该时间点的价格
    private BigDecimal openPrice; // 开盘价格
    private BigDecimal closePrice; // 收盘价格（前一交易日）
    private LocalDateTime timestamp; // 时间戳
}
