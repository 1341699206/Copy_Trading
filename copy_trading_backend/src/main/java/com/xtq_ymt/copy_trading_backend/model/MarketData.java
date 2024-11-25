package com.xtq_ymt.copy_trading_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "market_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String symbol; // 交易品种，例如 EUR/USD

    @Column(nullable = false)
    private double openPrice; // 开盘价

    @Column(nullable = false)
    private double currentPrice; // 当前价格

    @Column(nullable = false)
    private double highPrice; // 最高价

    @Column(nullable = false)
    private double lowPrice; // 最低价

    @Column(nullable = false)
    private LocalDateTime timestamp; // 数据生成时间
}
