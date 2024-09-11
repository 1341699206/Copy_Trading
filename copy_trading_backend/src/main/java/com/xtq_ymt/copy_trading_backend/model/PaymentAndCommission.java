package com.xtq_ymt.copy_trading_backend.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentAndCommission {

    // 基本信息字段
    private Long paymentAndCommissionId; //支付订单的唯一标识
    private Long followerId;//关联跟随者的唯一标识
    private Double amount; //支付额
    private String currency;//支付的币种
    private String payment_method; //支付方式
    private Boolean status; //支付状态(完成支付 or 未完成支付)
    private Double trader_commission; //交易员的抽成，佣金
    private Double trade_commission; //平台的抽成，佣金
    private Date payment_timestamp; //支付的时间戳

    //构造函数
    public PaymentAndCommission(){}

    //带所有字段的构造函数
    public PaymentAndCommission(Long paymentAndCommissionId,Long followerId,Double amount,String currency,String payment_method,
                                Boolean status,Double trader_commission,Double trade_commission,Date payment_timestamp){
        this.paymentAndCommissionId=paymentAndCommissionId;
        this.followerId=followerId;
        this.amount=amount;
        this.currency=currency;
        this.payment_method=payment_method;
        this.status=status;
        this.trader_commission=trade_commission;
        this.trade_commission=trade_commission;
        this.payment_timestamp=payment_timestamp;

    }

    // Lombok 自动生成 Getter 和 Setter 方法
}