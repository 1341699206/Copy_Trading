package com.xtq_ymt.copy_trading_backend.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follower_id")
    private Long followerId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TradingAccount> tradingAccounts;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "country")
    private String country;

    @Column(name = "registration_date")
    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    @Builder.Default
    private Boolean isActive = true;

    @ManyToMany
    @JoinTable(
        name = "follower_trader",
        joinColumns = @JoinColumn(name = "follower_id"),
        inverseJoinColumns = @JoinColumn(name = "trader_id")
    )
    private List<Trader> followingTraders;

    // 修改为 BigDecimal，并指定 columnDefinition
    @Column(name = "total_investment", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal totalInvestment;

    @Column(name = "current_balance", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal currentBalance;

    @Column(name = "profit_loss", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal profitLoss;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "risk_settings_id")
    private RiskManagementSettings riskSettings;

    // 修改 Map 的值类型为 BigDecimal，并指定 columnDefinition
    @ElementCollection
    @CollectionTable(name = "allocation_percentage", joinColumns = @JoinColumn(name = "follower_id"))
    @MapKeyColumn(name = "trader_id")
    @Column(name = "percentage", columnDefinition = "DECIMAL(5,2)")
    private HashMap<Long, BigDecimal> allocationPercentage;

    @Column(name = "auto_adjust", columnDefinition = "BOOLEAN")
    private Boolean autoAdjust;

    @Column(name = "adjustment_frequency")
    private String adjustmentFrequency;

    @Column(name = "adjustment_threshold", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal adjustmentThreshold;

    @ElementCollection
    @CollectionTable(name = "target_allocation_percentage", joinColumns = @JoinColumn(name = "follower_id"))
    @MapKeyColumn(name = "trader_id")
    @Column(name = "target_percentage", columnDefinition = "DECIMAL(5,2)")
    private HashMap<Long, BigDecimal> targetAllocationPercentage;

    @Column(name = "max_adjustment_amount", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal maxAdjustmentAmount;
}
