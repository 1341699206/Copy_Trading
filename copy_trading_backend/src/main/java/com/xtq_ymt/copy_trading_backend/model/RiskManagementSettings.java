package com.xtq_ymt.copy_trading_backend.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor  // 自动生成包含所有字段的构造函数
@NoArgsConstructor   // 自动生成无参构造函数
@Entity // 标识为JPA实体类
@Table(name = "risk_management_settings") // 指定表名
public class RiskManagementSettings {

    @Id // 标识主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略
    @Column(name = "id") // 指定列名
    private Long id; // 风险管理设置的唯一标识符

    @Column(name = "is_stop_loss_percentage", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE") // 指定列名，默认值为FALSE
    private Boolean isStopLossPercentage; // 是否启用最大亏损百分比

    @Column(name = "stop_loss_percentage", precision = 5, scale = 2) // 指定列名和精度
    private Double stopLossPercentage; // 强制平仓的亏损百分比

    @Column(name = "is_stop_loss_threshold", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE") // 指定列名，默认值为FALSE
    private Boolean isStopLossThreshold; // 是否启用最大亏损限额

    @Column(name = "stop_loss_threshold", precision = 18, scale = 8) // 指定列名和精度
    private Double stopLossThreshold; // 止损阈值
    
    @Column(name = "capital_protection", precision = 18, scale = 8) // 指定列名和精度
    private Double capitalProtection; // 不参与交易的锁定资金

    @Column(name = "enable_trailing_stop", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE") // 指定列名，默认值为FALSE
    private Boolean enableTrailingStop; // 是否启用移动止损

    // Lombok 将自动生成 Getter、Setter、全参构造函数和无参构造函数
}
