package com.xtq_ymt.copy_trading_backend.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trade {

    // 基本交易信息
    private Long tradeId; // 交易的唯一标识符
    private Long traderId; // 关联的交易员的唯一标识符
    private Long followerId; // 关联的跟随者的唯一标识符（如果有）
    private String instrument; // 交易的金融工具（如外汇对、股票符号等）
    private Date openTime; // 开仓时间
    private Date closeTime; // 平仓时间
    private Double openPrice; // 开仓价格
    private Double closePrice; // 平仓价格
    private String tradeType; // 交易类型（例如：买入/卖出）

    // 交易状态
    private Boolean isOpen; // 交易是否仍然开仓
    private Double volume; // 交易量（手数或股数等）
    private Double profitLoss; // 当前的利润或损失
    private Double commission; // 交易佣金
    private Double swap; // 掉期费用（如果适用）
    private String status; // 交易状态（例如：已完成、已取消）

    // 风险管理字段
    private Double stopLoss; // 止损
    private Double takeProfit; // 止盈

    // 构造函数
    public Trade() {
    }

    // 带所有字段的构造函数
    public Trade(Long tradeId, Long traderId, Long followerId, String instrument, Date openTime, Date closeTime,
                 Double openPrice, Double closePrice, String tradeType, Boolean isOpen, Double volume, Double profitLoss,
                 Double commission, Double swap, String status, Double stopLoss, Double takeProfit) {
        this.tradeId = tradeId;
        this.traderId = traderId;
        this.followerId = followerId;
        this.instrument = instrument;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.tradeType = tradeType;
        this.isOpen = isOpen;
        this.volume = volume;
        this.profitLoss = profitLoss;
        this.commission = commission;
        this.swap = swap;
        this.status = status;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
    }

    // Lombok 自动生成 Getter 和 Setter 方法
}
