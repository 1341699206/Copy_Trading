package com.xtq_ymt.copy_trading_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "trader_stats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraderStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long traderId; // 交易员的用户ID

    @Column(nullable = false)
    private double totalProfit; // 总收益

    @Column(nullable = false)
    private double winRate; // 胜率

    @Column(nullable = false)
    private double maxDrawdown; // 最大回撤

    @Column(nullable = false)
    private int totalTrades; // 总交易数量

    @Column(nullable = false)
    private int winningTrades; // 赢的交易数量
}
