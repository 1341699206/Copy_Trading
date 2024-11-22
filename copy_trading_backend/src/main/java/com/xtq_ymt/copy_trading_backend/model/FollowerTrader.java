package com.xtq_ymt.copy_trading_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "follower_trader")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowerTrader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long followerAccountId; // 追随者的账户 ID

    @Column(nullable = false)
    private Long traderAccountId; // 交易员的账户 ID
}
