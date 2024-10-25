package com.xtq_ymt.copy_trading_backend.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "follower")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "followerId")
public class Follower {

    // 主键 ID，自增生成策略
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follower_id")
    private Long followerId;  // 追随者的唯一标识符

    // 用户名，不能为空
    @Column(name = "name", nullable = false)
    private String name;  // 追随者的名字

    // 当前账户
    @ManyToOne
    @JoinColumn(name = "current_account_id", referencedColumnName = "account_id")
    private TradingAccount currentAccount;  // 追随者的当前账户

    // 追随者与多个交易账户之间的一对多关系
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TradingAccount> tradingAccounts;  // 该追随者的交易账户列表

    // 邮箱地址，不能为空且唯一
    @Column(name = "email", nullable = false, unique = true)
    private String email;  // 追随者的邮箱地址

    // 密码字段，用于存储加密后的密码
    @Column(name = "password", nullable = false)
    private String password;  // 追随者的密码（加密后）

    // 追随者的国家，允许为空
    @Column(name = "country")
    private String country;  // 追随者的国家信息

    // 注册日期，日期类型，映射到数据库中的日期格式
    @Column(name = "registration_date")
    @Temporal(TemporalType.DATE)
    private Date registrationDate;  // 追随者的注册日期

    // 是否活跃，默认为 true
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    @Builder.Default
    private Boolean isActive = true;  // 追随者是否活跃，默认为活跃

    // 追随者与多个交易员之间的多对多关系，映射表 follower_trader
    @ManyToMany
    @JoinTable(
        name = "follower_trader",
        joinColumns = @JoinColumn(name = "follower_id"),
        inverseJoinColumns = @JoinColumn(name = "trader_id")
    )
    private List<Trader> followingTraders;  // 追随者正在跟随的交易员列表

    // 追随者与多个交易账户之间的多对多关系，映射表 follower_trading_account
    @ManyToMany
    @JoinTable(
        name = "follower_trading_account",
        joinColumns = @JoinColumn(name = "follower_id"),
        inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private List<TradingAccount> followingAccounts;  // 追随者正在跟随的交易账户列表

    // 总投资金额，精度为 18 位整数，8 位小数
    @Column(name = "total_investment", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal totalInvestment;  // 追随者的总投资金额

    // 当前余额，精度为 18 位整数，8 位小数
    @Column(name = "current_balance", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal currentBalance;  // 追随者的当前账户余额

    // 总盈亏，精度为 18 位整数，8 位小数
    @Column(name = "profit_loss", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal profitLoss;  // 追随者的总盈亏情况

    // 风险管理设置，与风险管理设置表进行一对一关联
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "risk_settings_id")
    private RiskManagementSettings riskSettings;  // 追随者的风险管理设置

    // 交易员的分配百分比，使用 Map 类型存储多个交易员的分配情况
    @ElementCollection
    @CollectionTable(name = "allocation_percentage", joinColumns = @JoinColumn(name = "follower_id"))
    @MapKeyColumn(name = "trader_id")  // 映射的 Map 键为交易员的 ID
    @Column(name = "percentage", columnDefinition = "DECIMAL(5,2)")
    private Map<Long, BigDecimal> allocationPercentage;  // 追随者对各交易员的资金分配比例

    // 是否启用自动调整
    @Column(name = "auto_adjust", columnDefinition = "BOOLEAN")
    private Boolean autoAdjust;  // 是否启用自动调整功能

    // 调整频率
    @Column(name = "adjustment_frequency")
    private String adjustmentFrequency;  // 自动调整的频率（例如：每日，每周等）

    // 调整阈值，精度为 18 位整数，8 位小数
    @Column(name = "adjustment_threshold", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal adjustmentThreshold;  // 自动调整的触发阈值

    // 目标分配百分比，使用 Map 类型存储多个交易员的目标分配情况
    @ElementCollection
    @CollectionTable(name = "target_allocation_percentage", joinColumns = @JoinColumn(name = "follower_id"))
    @MapKeyColumn(name = "trader_id")  // 映射的 Map 键为交易员的 ID
    @Column(name = "target_percentage", columnDefinition = "DECIMAL(5,2)")
    private Map<Long, BigDecimal> targetAllocationPercentage;  // 追随者的目标资金分配比例

    // 最大调整金额，精度为 18 位整数，8 位小数
    @Column(name = "max_adjustment_amount", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal maxAdjustmentAmount;  // 自动调整的最大金额
}
