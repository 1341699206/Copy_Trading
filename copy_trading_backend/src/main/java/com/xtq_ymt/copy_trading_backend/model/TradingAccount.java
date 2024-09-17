package com.xtq_ymt.copy_trading_backend.model;

import jakarta.persistence.*; // 导入 JPA 注解
import java.math.BigDecimal; // 导入 BigDecimal
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trading_account") // 指定数据库表名为 "trading_account"
public class TradingAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId; // 账户唯一标识符

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber; // 账户编号

    @Column(name = "account_type", nullable = false)
    private String accountType; // 账户类型（如模拟账户、真实账户）

    @ManyToOne
    @JoinColumn(name = "trader_id", nullable = true)
    private Trader trader; // 关联的交易员（可空）

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = true)
    private Follower follower; // 关联的跟随者（可空）

    // 修改为 BigDecimal，并使用 columnDefinition
    @Column(name = "balance", columnDefinition = "DECIMAL(18,2)", nullable = false)
    private BigDecimal balance; // 账户余额

    @Column(name = "equity", columnDefinition = "DECIMAL(18,2)", nullable = false)
    private BigDecimal equity; // 账户权益

    @Column(name = "status", nullable = false)
    private String status; // 账户状态，例如 "Active", "Closed", "Suspended"

    @Column(name = "currency", length = 3, nullable = false)
    private String currency; // 账户的基础货币类型

    @Column(name = "margin_level", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal marginLevel; // 保证金比例

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "risk_settings_id")
    private RiskManagementSettings riskSettings; // 关联的风险管理设置

    @Column(name = "api_key", unique = true)
    private String apiKey; // API访问密钥

    @Column(name = "last_trade_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTradeTime; // 最后一次交易的时间

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt; // 创建时间

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt; // 更新时间

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
