package com.xtq_ymt.copy_trading_backend.model;

import java.util.Date;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "trader_account_id", nullable = false)
    private TradingAccount traderAccount; // Trader's trading account associated with the trade

    @ManyToOne
    @JoinColumn(name = "follower_account_id")
    private TradingAccount followerAccount; // Follower's trading account (if it's a copy trade)

    @ManyToOne
    @JoinColumn(name = "trader_id", nullable = false)
    private Trader trader; // Associated trader

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Follower follower; // Associated follower (if applicable)

    @ManyToOne
    @JoinColumn(name = "copy_trading_id")
    private CopyTrading copyTrading; // Associated copy trading instance (if applicable)

    @Column(name = "instrument", nullable = false)
    private String instrument; // Financial instrument

    @Column(name = "open_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openTime; // Opening time

    @Column(name = "close_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeTime; // Closing time

    @Column(name = "open_price", nullable = false, columnDefinition = "DECIMAL(18,8)")
    private BigDecimal openPrice; // Opening price

    @Column(name = "close_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal closePrice; // Closing price

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_action_type", nullable = false)
    private TradeActionType tradeActionType; // Buy/Sell or Long/Short

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_nature", nullable = false)
    private TradeNature tradeNature; // Original or Copy

    @Column(name = "is_open", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isOpen = true; // Whether trade is still open

    @Column(name = "volume", nullable = false, columnDefinition = "DECIMAL(18,8)")
    private BigDecimal volume; // Trade volume

    @Column(name = "profit_loss", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal profitLoss; // Profit or loss

    @Column(name = "commission", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal commission; // Commission

    @Column(name = "swap", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal swap; // Swap fee

    @Column(name = "stop_loss", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal stopLoss; // Stop loss level

    @Column(name = "take_profit", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal takeProfit; // Take profit level

    @Column(name = "margin_used", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal marginUsed; // Margin used for this trade

    @Column(name = "leverage", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal leverage; // Leverage used for this trade

    @PrePersist
    protected void onCreate() {
        if (openTime == null) {
            openTime = new Date();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (!isOpen && closeTime == null) {
            closeTime = new Date();
            if (openPrice != null && closePrice != null) {
                // Profit/Loss = (Close Price - Open Price) * Volume
                profitLoss = closePrice.subtract(openPrice).multiply(volume);
            }
        }
    }
}
