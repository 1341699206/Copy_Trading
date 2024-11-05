package com.xtq_ymt.copy_trading_backend.model;

import jakarta.persistence.*; // 导入 JPA 注解
import java.math.BigDecimal; // 导入 BigDecimal
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime; // 使用 LocalDateTime
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trading_account") // 指定数据库表名为 "trading_account"
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "accountId")
public class TradingAccount {

    // 主键 ID，自增生成策略
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId; // 账户唯一标识符

    // 账户名称，不能为空
    @Column(name = "accountName", unique = false, nullable = false)
    private String accountNumber; // 账户编号

    // 账户类型，如 "模拟账户" 或 "真实账户"
    @Column(name = "account_type", nullable = false)
    private String accountType; // 账户类型
    
    @OneToMany(mappedBy = "traderAccount", cascade = CascadeType.ALL)
    private List<Trade> trades; // 与 Trade 的关联

    // 新增盈利占比字段
    @Column(name = "win_rate", columnDefinition = "DECIMAL(5,2)")
    private Double winRate; // 盈利占比

    // 关联的交易员，可为空
    @ManyToOne
    @JoinColumn(name = "trader_id", nullable = true)
    private Trader trader; // 交易员

    // 关联的跟随者，可为空
    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = true)
    private Follower follower; // 跟随者

    // 账户余额，使用 BigDecimal 存储高精度数值
    @Column(name = "balance", columnDefinition = "DECIMAL(18,2)", nullable = false)
    private BigDecimal balance; // 账户余额

    // 账户权益，表示所有资产的总价值
    @Column(name = "equity", columnDefinition = "DECIMAL(18,2)", nullable = false)
    private BigDecimal equity; // 账户权益

    // 新增的字段：已实现盈亏，用于存储已实现的交易盈亏
    @Column(name = "realised_pnl", columnDefinition = "DECIMAL(18,2)", nullable = false)
    private BigDecimal realisedPNL; // 已实现盈亏

    // 保证金金额，使用 BigDecimal 存储
    @Column(name = "margin", columnDefinition = "DECIMAL(18,2)", nullable = false)
    private BigDecimal margin; // 保证金金额

    // 可用保证金
    @Column(name = "free_margin", columnDefinition = "DECIMAL(18,2)", nullable = false)
    private BigDecimal freeMargin; // 可用保证金

    @Column(name = "available_margin", columnDefinition = "DECIMAL(18,2)", nullable = true)
    private BigDecimal availableMargin;

    // 账户状态，例如 "Active", "Closed", "Suspended"
    @Column(name = "status", nullable = false)
    private String status; // 账户状态

    // 账户的基础货币类型
    @Column(name = "currency", length = 3, nullable = false)
    private String currency; // 基础货币类型

    // 保证金比例，记录账户的杠杆比率
    @Column(name = "margin_level", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal marginLevel; // 保证金比例

    // 新增杠杆字段，用于存储账户杠杆倍数
    @Column(name = "leverage", columnDefinition = "DECIMAL(5,2)", nullable = false)
    private BigDecimal leverage; // 杠杆倍数

    // 关联的风险管理设置
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "risk_settings_id")
    private RiskManagementSettings riskSettings; // 风险管理设置

    // API 访问密钥，唯一且可空
    @Column(name = "api_key", unique = true)
    private String apiKey; // API访问密钥

    // 最后一次交易时间
    @Column(name = "last_trade_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastTradeTime; // 最后一次交易时间

    // 创建时间，记录账户的创建时间
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt; // 创建时间

    // 更新时间，记录每次修改时的时间
    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt; // 更新时间

    // 关联的跟随者与交易账户的多对多关系
    @ManyToMany(mappedBy = "followingAccounts")
    private List<Follower> followers; // 跟随该交易账户的追随者列表

    // 计算盈利占比的方法
    public void calculateWinRate() {
        if (trades == null || trades.isEmpty()) {
            this.winRate = 0.0;
            return;
        }

        long closedTradesCount = trades.stream().filter(trade -> trade.getDateClose() != null).count();
        if (closedTradesCount == 0) {
            this.winRate = 0.0;
            return;
        }

        long winningTradesCount = trades.stream()
                .filter(trade -> trade.getDateClose() != null && trade.getProfitUsd().compareTo(BigDecimal.ZERO) > 0)
                .count();

        this.winRate = (double) winningTradesCount / closedTradesCount * 100;
    }

    // 预存时自动设置创建时间
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // 更新时自动设置更新时间
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
