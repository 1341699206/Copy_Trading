package com.xtq_ymt.copy_trading_backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//由于难以对应到每个trader上，所以该项应设为follower总体的风险策略，我对此进行了修改。
//将针对各个trader的风险策略，将放在copy trade内.
public class RiskManagementSettings {

    private Boolean isStopLossPercentage;//是否启用最大亏损百分比
    private Double stopLossPercentage; //强制平仓的亏损百分比

    private Boolean isStopLossThreshold; //是否启用最大亏损限额
    private Double stopLossThreshold; // 止损阈值
    
    private Double capitalProtection; //不参与交易的锁定资金

    private Boolean enableTrailingStop; // 是否启用移动止损

    // 构造函数
    public RiskManagementSettings() {
    }

    public RiskManagementSettings(Boolean isStopLossPercentage,Double stopLossPercentage,Boolean isStopLossThreshold, Double stopLossThreshold,
                                    Double capitalProtection,Boolean enableTrailingStop) {
        
        this.isStopLossPercentage=isStopLossPercentage;
        this.stopLossPercentage=stopLossPercentage;
        this.isStopLossThreshold = isStopLossThreshold;
        this.stopLossThreshold = stopLossThreshold;
        this.capitalProtection=capitalProtection;
        this.enableTrailingStop=enableTrailingStop;
    }

    // Lombok 自动生成 Getter 和 Setter 方法
}
