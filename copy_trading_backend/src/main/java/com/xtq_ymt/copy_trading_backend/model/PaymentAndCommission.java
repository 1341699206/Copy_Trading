package com.xtq_ymt.copy_trading_backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_and_commission")
public class PaymentAndCommission {

    // 基本支付信息字段
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private Follower follower;

    @ManyToOne
    @JoinColumn(name = "trader_id")
    private Trader trader;

    @ManyToOne
    @JoinColumn(name = "trading_account_id", nullable = false)
    private TradingAccount tradingAccount;

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime paymentDate;

    // 修改为 BigDecimal，并使用 columnDefinition
    @Column(name = "amount", columnDefinition = "DECIMAL(18,2)")
    private BigDecimal amount;

    @Column(name = "currency", length = 3)
    private String currency;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    // 佣金和费用字段
    @Column(name = "commission_rate", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal commissionRate;

    @Column(name = "commission_type", length = 20)
    private String commissionType;

    @Column(name = "commission_amount", columnDefinition = "DECIMAL(18,2)")
    private BigDecimal commissionAmount;

    @Column(name = "platform_fee", columnDefinition = "DECIMAL(18,2)")
    private BigDecimal platformFee;

    @Column(name = "total_amount", columnDefinition = "DECIMAL(18,2)")
    private BigDecimal totalAmount;

    // 状态字段
    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "description", length = 255)
    private String description;
}
