package com.xtq_ymt.copy_trading_backend.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import java.math.BigDecimal; // 导入 BigDecimal
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "tradeId")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    private Long tradeId; // 交易的唯一标识符

    @ManyToOne
    @JoinColumn(name = "trader_account_id", nullable = false)
    private TradingAccount traderAccount; // Trader's trading account associated with the trade

    @ManyToOne
    @JoinColumn(name = "follower_account_id")
    private TradingAccount followerAccount; // Follower's trading account (if it's a copy trade)

    @ManyToOne
    @JoinColumn(name = "trader_id", nullable = false)
    private Trader trader; // 关联的交易员

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Follower follower; // 关联的跟随者（如果有）

    @ManyToOne
    @JoinColumn(name = "copy_trading_id")
    private CopyTrading copyTrading; // 关联的CopyTrading（如果是跟随交易）

    @Column(name = "instrument", nullable = false)
    private String instrument; // 交易的金融工具

    @Column(name = "open_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openTime; // 开仓时间

    @Column(name = "close_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeTime; // 平仓时间

    // 修改为 BigDecimal，并指定 columnDefinition
    @Column(name = "open_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal openPrice; // 开仓价格

    @Column(name = "close_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal closePrice; // 平仓价格

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_action_type", nullable = false)
    private TradeActionType tradeActionType;// 交易类型

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_nature", nullable = false)
    private TradeNature tradeNature; // Original or Copy
    
    @Column(name = "is_open", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isOpen = true; // 交易是否仍然开仓

    @Column(name = "volume", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal volume; // 交易量

    @Column(name = "profit_loss", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal profitLoss; // 当前的利润或损失

    @Column(name = "commission", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal commission; // 交易佣金

    @Column(name = "swap", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal swap; // 掉期费用

    @Column(name = "stop_loss", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal stopLoss; // 止损

    @Column(name = "take_profit", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal takeProfit; // Take profit level

    @Column(name = "margin_used", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal marginUsed; // Margin used for this trade

    @Column(name = "leverage", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal leverage; // Leverage used for this trade

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
            if (openPrice != null && closePrice != null) {
                // Profit/Loss = (Close Price - Open Price) * Volume
                profitLoss = closePrice.subtract(openPrice).multiply(volume);
            }
        }
    }

    // 添加 getProfitLoss 方法，确保返回 BigDecimal 类型
    public BigDecimal getProfitLoss() {
        return profitLoss != null ? profitLoss : BigDecimal.ZERO;
    }
}
