package com.xtq_ymt.copy_trading_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * FollowerStats 实体：用于保存每个跟随者的统计信息
 */
@Entity
@Table(name = "follower_stats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowerStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long followerId; // 跟随者的用户ID

    /**
     * 建立与 User 实体的一对一关系
     */
    @OneToOne
    @JoinColumn(name = "followerId", referencedColumnName = "id", insertable = false, updatable = false)
    private User user; // 关联的 User 实体

    @Column(nullable = false)
    private double totalProfit; // 跟随总收益

    @Column(nullable = false)
    private double maxDrawdown; // 最大回撤

    @Column(nullable = false)
    private int totalFollowedTraders; // 跟随的交易员数量

    @Column(nullable = false)
    private int totalTrades; // 总交易数量

    /**
     * 更新最大回撤
     * @param profit 当前利润
     */
    public void updateMaxDrawdown(double profit) {
        if (profit < this.maxDrawdown) {
            this.maxDrawdown = profit;
        }
    }

    /**
     * 更新跟随的交易员数量
     * @param delta 增量，可以是正数（新增）或负数（减少）
     */
    public void updateTotalFollowedTraders(int delta) {
        this.totalFollowedTraders += delta;
    }
}
