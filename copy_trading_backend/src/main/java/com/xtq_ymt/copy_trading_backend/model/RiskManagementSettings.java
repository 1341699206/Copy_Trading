package com.xtq_ymt.copy_trading_backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor  // 自动生成包含所有字段的构造函数
@NoArgsConstructor   // 自动生成无参构造函数
public class RiskManagementSettings {

    private Boolean isStopLossPercentage; // 是否启用最大亏损百分比
    private Double stopLossPercentage; // 强制平仓的亏损百分比

    private Boolean isStopLossThreshold; // 是否启用最大亏损限额
    private Double stopLossThreshold; // 止损阈值
    
    private Double capitalProtection; // 不参与交易的锁定资金

    private Boolean enableTrailingStop; // 是否启用移动止损

    // Lombok 将自动生成 Getter、Setter、全参构造函数和无参构造函数
}
