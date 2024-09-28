package com.xtq_ymt.copy_trading_backend.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "copy_trading")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CopyTrading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "copy_trading_id")
    private Long copyTradingId;

    // 跟随者的交易账户
    @ManyToOne
    @JoinColumn(name = "follower_account_id", nullable = false)
    private TradingAccount followerTradingAccount;

    // 交易员的交易账户
    @ManyToOne
    @JoinColumn(name = "trader_account_id", nullable = false)
    private TradingAccount traderTradingAccount;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    @Builder.Default
    private Boolean isActive = true;

    // 风险管理字段
    @Column(name = "allocation", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal allocation;

    @Column(name = "is_stop_loss", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean isStopLoss = false;

    @Column(name = "stop_loss", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal stopLoss;

    @Column(name = "is_stop_loss_percentage", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean isStopLossPercentage = false;

    @Column(name = "stop_loss_percentage", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal stopLossPercentage;

    @Column(name = "is_take_profit", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean isTakeProfit = false;

    @Column(name = "take_profit", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal takeProfit;

    @Column(name = "is_max_open_positions", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean isMaxOpenPositions = false;

    @Column(name = "max_open_positions", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal maxOpenPositions;

    @Column(name = "is_max_lots", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean isMaxLots = false;

    @Column(name = "max_lots", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal maxLots;

    @Column(name = "auto_adjust", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean autoAdjust = false;

    @OneToMany(
        mappedBy = "copyTrading",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        fetch = FetchType.LAZY
    )
    private List<Trade> copiedTrades;

    @Column(name = "total_profit", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal totalProfit;

    @Column(name = "total_loss", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal totalLoss;

    @PrePersist
    protected void onCreate() {
        if (startDate == null) {
            startDate = new Date();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (endDate == null && !isActive) {
            endDate = new Date();
        }
    }
}
