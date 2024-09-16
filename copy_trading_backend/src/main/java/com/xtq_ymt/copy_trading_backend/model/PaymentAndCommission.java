package com.xtq_ymt.copy_trading_backend.model;

import javax.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor  // 自动生成包含所有字段的构造函数
@NoArgsConstructor   // 自动生成无参构造函数
@Entity // 标识为JPA实体类
@Table(name = "payment_and_commission") // 指定表名
public class PaymentAndCommission {

    // 基本支付信息字段
    @Id // 标识主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略
    @Column(name = "payment_id") // 指定列名
    private Long paymentId; // 支付的唯一标识符

    @ManyToOne // 多个支付可以对应一个跟随者
    @JoinColumn(name = "follower_id", nullable = false) // 指定外键列名
    private Follower follower; // 关联的跟随者

    @ManyToOne // 多个支付可以对应一个交易者
    @JoinColumn(name = "trader_id") // 指定外键列名
    private Trader trader; // 被跟随的交易者（如果有）

    @ManyToOne // 多个支付可以关联一个交易账户
    @JoinColumn(name = "trading_account_id", nullable = false) // 指定外键列名
    private TradingAccount tradingAccount; // 关联的交易账户

    @Column(name = "transaction_id", unique = true) // 指定列名和唯一性
    private String transactionId; // 交易ID，用于外部支付平台的唯一标识

    @Column(name = "payment_date") // 指定列名
    @Temporal(TemporalType.TIMESTAMP) // 指定时间类型为 TIMESTAMP
    private Date paymentDate; // 支付日期

    @Column(name = "amount", precision = 18, scale = 2) // 指定列名，精度和小数位数
    private Double amount; // 支付金额

    @Column(name = "currency", length = 3) // 指定列名和长度
    private String currency; // 支付的货币种类（如USD, EUR等）

    @Column(name = "payment_method", length = 50) // 指定列名和长度
    private String paymentMethod; // 支付方式（如信用卡、PayPal、银行转账）

    // 佣金和费用字段
    @Column(name = "commission_rate", precision = 5, scale = 2) // 指定列名，精度和小数位数
    private Double commissionRate; // 佣金率（如按交易金额的百分比）

    @Column(name = "commission_type", length = 20) // 指定列名和长度
    private String commissionType; // 佣金类型（如固定费用或按比例收费）

    @Column(name = "commission_amount", precision = 18, scale = 2) // 指定列名，精度和小数位数
    private Double commissionAmount; // 计算的佣金金额

    @Column(name = "platform_fee", precision = 18, scale = 2) // 指定列名，精度和小数位数
    private Double platformFee; // 平台收取的费用

    @Column(name = "total_amount", precision = 18, scale = 2) // 指定列名，精度和小数位数
    private Double totalAmount; // 交易的总金额，包括佣金和平台费用

    // 状态字段
    @Column(name = "status", length = 20) // 指定列名和长度
    private String status; // 支付状态（如已完成、待处理、已退款、失败）

    @Column(name = "description", length = 255) // 指定列名和长度
    private String description; // 交易的描述或备注信息

    // Lombok 将自动生成 Getter、Setter、全参构造函数和无参构造函数
}
