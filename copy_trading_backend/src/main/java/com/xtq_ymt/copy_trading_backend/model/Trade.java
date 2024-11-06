package com.xtq_ymt.copy_trading_backend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trade")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "tradeId")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    private Long tradeId; // 交易的唯一标识符

    // 保留的原有字段
    @ManyToOne
    @JoinColumn(name = "trader_account_id", nullable = false)
    private TradingAccount traderAccount; // 交易者的账户

    @ManyToOne
    @JoinColumn(name = "follower_account_id")
    private TradingAccount followerAccount; // 跟随者的账户（如果是复制交易）

    @ManyToOne
    @JoinColumn(name = "trader_id", nullable = false)
    private Trader trader; // 交易员

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Follower follower; // 跟随者

    @ManyToOne
    @JoinColumn(name = "copy_trading_id")
    private CopyTrading copyTrading; // 复制交易信息（如果是复制交易）

    // 替换原有字段名为 CSV 中的字段名
    @Column(name = "Currency", nullable = false)
    private String currency; // 交易的货币对（原字段名：instrument）

    @Column(name = "Date_Open", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOpen; // 开仓时间（原字段名：openTime）

    @Column(name = "Date_Close")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateClose; // 平仓时间（原字段名：closeTime）

    @Column(name = "Price_Open", nullable = false, precision = 18, scale = 8)
    private BigDecimal priceOpen; // 开仓价格（原字段名：openPrice）

    @Column(name = "Price_Close", precision = 18, scale = 8)
    private BigDecimal priceClose; // 平仓价格（原字段名：closePrice）

    @Enumerated(EnumType.STRING)
    @Column(name = "Type", nullable = false)
    private TradeActionType type; // 交易类型（BUY/SELL）（原字段名：tradeActionType）

    @Column(name = "Standard_Lots", nullable = false, precision = 10, scale = 2)
    private BigDecimal standardLots; // 标准手数（原字段名：volume）

    @Column(name = "Profit_Pips", precision = 10, scale = 2)
    private BigDecimal profitPips; // 利润（点数）（原字段名：profitLoss）

    @Column(name = "Interest_USD", precision = 10, scale = 2)
    private BigDecimal interestUsd; // 利息（美元）（原字段名：swap）

    // 新增的字段以匹配 CSV 文件
    @Column(name = "Provider_Ticket", nullable = false)
    private String providerTicket; // 提供者票据编号

    @Column(name = "Broker_Ticket", nullable = false)
    private String brokerTicket; // 经纪商票据编号

    @Column(name = "Highest_Profit_Pips", precision = 10, scale = 2)
    private BigDecimal highestProfitPips; // 最高利润（点数）

    @Column(name = "Worst_Drawdown_Pips", precision = 10, scale = 2)
    private BigDecimal worstDrawdownPips; // 最大回撤（点数）

    @Column(name = "Profit_USD", precision = 10, scale = 2)
    private BigDecimal profitUsd; // 利润（美元）

    // 新增的状态字段：是否开仓
    @Column(name = "Is_Open", nullable = false)
    private Boolean isOpen = true; // 默认为 true，表示开仓状态

    // 新增的杠杆字段
    @Column(name = "Leverage", columnDefinition = "DECIMAL(5,2)", nullable = false)
    private BigDecimal leverage; // 杠杆倍数

    @PrePersist
    protected void onCreate() {
        if (dateOpen == null) {
            dateOpen = LocalDateTime.now(); // 如果开仓时间为空，则设置为当前时间
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (dateClose == null && priceOpen != null && priceClose != null && standardLots != null) {
            // 计算利润/损失
            profitPips = priceClose.subtract(priceOpen).multiply(standardLots);
        }

        // 自动检测是否已开仓
        if (priceClose != null) {
            isOpen = false; // 如果平仓价格不为空，表示已平仓
        } else {
            isOpen = true; // 否则为开仓状态
        }

        // 自动检测并填充 leverage 字段
        if (leverage == null) {
            if (traderAccount != null && traderAccount.getLeverage() != null) {
                leverage = traderAccount.getLeverage(); // 使用交易者账户的杠杆
            } else if (followerAccount != null && followerAccount.getLeverage() != null) {
                leverage = followerAccount.getLeverage(); // 使用跟随者账户的杠杆
            }
        }
    }

    // 获取利润/损失，确保返回 BigDecimal 类型
    public BigDecimal getProfitPips() {
        return profitPips != null ? profitPips : BigDecimal.ZERO;
    }
}
