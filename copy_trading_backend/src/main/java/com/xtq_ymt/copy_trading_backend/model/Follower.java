package com.xtq_ymt.copy_trading_backend.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.*;  // 导入 JPA 相关注解

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "follower")  // 指定数据库表名
@Getter
@Setter
@AllArgsConstructor  // 生成包含所有字段的构造函数
@NoArgsConstructor   // 生成无参构造函数
@Builder  // 使用Builder模式
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 主键生成策略
    @Column(name = "follower_id")  // 指定列名
    private Long followerId;

    @Column(name = "name", nullable = false)  // 不可为空的列
    private String name;

    @Column(name = "email", nullable = false, unique = true)  // 不可为空且唯一的列
    private String email;

    @Column(name = "country")  // 指定列名
    private String country;

    @Column(name = "registration_date")
    @Temporal(TemporalType.DATE)  // 指定时间类型
    private Date registrationDate;

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")  // 指定列名和默认值
    @Builder.Default
    private Boolean isActive = true;  // 默认值设置为true

    @ManyToMany  // 多对多关系
    @JoinTable(
        name = "follower_trader",  // 中间表名
        joinColumns = @JoinColumn(name = "follower_id"),  // 本表关联列
        inverseJoinColumns = @JoinColumn(name = "trader_id")  // 对方表关联列
    )
    private List<Trader> followingTraders;  // 正在跟随的交易员列表

    @Column(name = "total_investment")  // 总投资
    private Double totalInvestment;

    @Column(name = "current_balance")  // 当前余额
    private Double currentBalance;

    @Column(name = "profit_loss")  // 总利润或损失
    private Double profitLoss;

    @OneToOne(cascade = CascadeType.ALL)  // 一对一关系，级联所有操作
    @JoinColumn(name = "risk_settings_id")  // 外键列名
    private RiskManagementSettings riskSettings;  // 风险管理设置

    @ElementCollection  // 映射基本类型集合
    @CollectionTable(name = "allocation_percentage", joinColumns = @JoinColumn(name = "follower_id"))  // 集合表
    @MapKeyColumn(name = "trader_id")  // Map的键
    @Column(name = "percentage")  // Map的值
    private HashMap<Long, Double> allocationPercentage;  // 资金分配比例

    @Column(name = "auto_adjust")  // 是否自动调整
    private Boolean autoAdjust;

    @Column(name = "adjustment_frequency")  // 调整频率
    private String adjustmentFrequency;

    @Column(name = "adjustment_threshold")  // 调整阈值
    private Double adjustmentThreshold;

    @ElementCollection  // 映射基本类型集合
    @CollectionTable(name = "target_allocation_percentage", joinColumns = @JoinColumn(name = "follower_id"))  // 集合表
    @MapKeyColumn(name = "trader_id")  // Map的键
    @Column(name = "target_percentage")  // Map的值
    private HashMap<Long, Double> targetAllocationPercentage;  // 目标分配比例

    @Column(name = "max_adjustment_amount")  // 最大调整幅度
    private Double maxAdjustmentAmount;
}
