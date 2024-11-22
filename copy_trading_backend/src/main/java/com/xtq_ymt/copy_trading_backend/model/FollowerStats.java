package com.xtq_ymt.copy_trading_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "follower_stats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowerStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long followerId; // 跟随者的用户ID

    @Column(nullable = false)
    private double totalProfit; // 跟随总收益

    @Column(nullable = false)
    private double maxDrawdown; // 最大回撤

    @Column(nullable = false)
    private int totalFollowedTraders; // 跟随的交易员数量

    @Column(nullable = false)
    private int totalTrades; // 总交易数量
}
