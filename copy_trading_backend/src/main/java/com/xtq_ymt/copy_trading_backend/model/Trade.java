package com.xtq_ymt.copy_trading_backend.model;

import java.util.Date;
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
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    private Long tradeId; // 交易的唯一标识符

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private TradingAccount tradingAccount; // 关联的交易账户

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

    @Column(name = "trade_type", nullable = false)
    private String tradeType; // 交易类型

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
    private BigDecimal takeProfit; // 止盈

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
