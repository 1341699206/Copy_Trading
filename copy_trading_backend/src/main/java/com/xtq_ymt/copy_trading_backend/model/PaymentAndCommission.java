package com.xtq_ymt.copy_trading_backend.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentAndCommission {

    // 基本支付信息字段
    private Long paymentId; // 支付的唯一标识符
    private Long followerId; // 关联的跟随者的唯一标识符
    private Long traderId; // 关联的交易员的唯一标识符（如果有）
    private String transactionId; // 交易ID，用于外部支付平台的唯一标识
    private Date paymentDate; // 支付日期
    private Double amount; // 支付金额
    private String currency; // 支付的货币种类（如USD, EUR等）
    private String paymentMethod; // 支付方式（如信用卡、PayPal、银行转账）

    // 佣金和费用字段
    private Double commissionRate; // 佣金率（如按交易金额的百分比）
    private String commissionType; // 佣金类型（如固定费用或按比例收费）
    private Double commissionAmount; // 计算的佣金金额
    private Double platformFee; // 平台收取的费用
    private Double totalAmount; // 交易的总金额，包括佣金和平台费用

    // 状态字段
    private String status; // 支付状态（如已完成、待处理、已退款、失败）
    private String description; // 交易的描述或备注信息

    // 构造函数
    public PaymentAndCommission() {
    }

    // 带所有字段的构造函数
    public PaymentAndCommission(Long paymentId, Long followerId, Long traderId, String transactionId, Date paymentDate, 
                                Double amount, String currency, String paymentMethod, Double commissionRate, 
                                String commissionType, Double commissionAmount, Double platformFee, 
                                Double totalAmount, String status, String description) {
        this.paymentId = paymentId;
        this.followerId = followerId;
        this.traderId = traderId;
        this.transactionId = transactionId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.commissionRate = commissionRate;
        this.commissionType = commissionType;
        this.commissionAmount = commissionAmount;
        this.platformFee = platformFee;
        this.totalAmount = totalAmount;
        this.status = status;
        this.description = description;
    }

    // Lombok 自动生成 Getter 和 Setter 方法
}
