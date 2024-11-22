package com.xtq_ymt.copy_trading_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long accountId; // 关联的账户 ID

    @Column(nullable = false)
    private Long strategyId; // 关联的策略 ID

    @Column(nullable = false)
    private String symbol; // 交易品种，例如 "EUR/USD"

    @Column(nullable = false)
    private String type; // 交易类型：BUY/SELL

    @Column(nullable = false)
    private double lotSize; // 手数

    @Column(nullable = false)
    private double priceOpen; // 开仓价格

    @Column(nullable = true)
    private double priceClose; // 平仓价格

    @Column(nullable = true)
    private double profit; // 收益

    @Column(nullable = false)
    private LocalDateTime dateOpen;

    @Column(nullable = true)
    private LocalDateTime dateClose;

    @Column(nullable = false)
    private boolean isClosed; // 是否平仓

    @PrePersist
    protected void onCreate() {
        dateOpen = LocalDateTime.now();
    }
}
