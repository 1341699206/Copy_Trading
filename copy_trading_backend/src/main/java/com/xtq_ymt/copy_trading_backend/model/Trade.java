package com.xtq_ymt.copy_trading_backend.model;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trade") // 指定数据库表名为 "trade"
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    private Long tradeId; // 交易的唯一标识符

    @ManyToOne // 多个交易可以对应一个交易账户
    @JoinColumn(name = "account_id", nullable = false)
    private TradingAccount tradingAccount; // 关联的交易账户
    
    @ManyToOne // 多个交易可以对应一个Trader
    @JoinColumn(name = "trader_id", nullable = false)
    private Trader trader; // 关联的交易员

    @ManyToOne // 多个交易可以对应一个Follower（如果有）
    @JoinColumn(name = "follower_id")
    private Follower follower; // 关联的跟随者（如果有）

    @ManyToOne // 多个Trade可以对应一个CopyTrading（如果是跟随交易）
    @JoinColumn(name = "copy_trading_id")
    private CopyTrading copyTrading; // 关联的CopyTrading（如果是跟随交易）

    @Column(name = "instrument", nullable = false)
    private String instrument; // 交易的金融工具（如外汇对、股票符号等）

    @Column(name = "open_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openTime; // 开仓时间

    @Column(name = "close_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeTime; // 平仓时间

    @Column(name = "open_price")
    private Double openPrice; // 开仓价格

    @Column(name = "close_price")
    private Double closePrice; // 平仓价格

    @Column(name = "trade_type", nullable = false)
    private String tradeType; // 交易类型（例如：买入/卖出）

    @Column(name = "is_open", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isOpen = true; // 交易是否仍然开仓

    @Column(name = "volume")
    private Double volume; // 交易量（手数或股数等）

    @Column(name = "profit_loss")
    private Double profitLoss; // 当前的利润或损失

    @Column(name = "commission")
    private Double commission; // 交易佣金

    @Column(name = "swap")
    private Double swap; // 掉期费用（如果适用）

    @Column(name = "stop_loss")
    private Double stopLoss; // 止损

    @Column(name = "take_profit")
    private Double takeProfit; // 止盈

    @PrePersist
    protected void onCreate() {
        if (openTime == null) {
            openTime = new Date(); // 如果开仓时间为空，则设置为当前时间
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (!isOpen && closeTime == null) {
            closeTime = new Date(); // 如果交易不活跃且没有平仓时间，则设置为当前时间
        }
    }
}
