package com.xtq_ymt.copy_trading_backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RiskManagementSettings {

    private Double maxAcceptableLoss; // 可接受的最大损失
    private Double stopLossThreshold; // 止损阈值
    private Boolean enableTrailingStop; // 是否启用移动止损

    // 构造函数
    public RiskManagementSettings() {
    }

    public RiskManagementSettings(Double maxAcceptableLoss, Double stopLossThreshold, Boolean enableTrailingStop) {
        this.maxAcceptableLoss = maxAcceptableLoss;
        this.stopLossThreshold = stopLossThreshold;
        this.enableTrailingStop = enableTrailingStop;
    }

    // Lombok 自动生成 Getter 和 Setter 方法
}
