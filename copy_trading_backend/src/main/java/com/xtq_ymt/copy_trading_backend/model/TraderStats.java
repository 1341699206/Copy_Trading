package com.xtq_ymt.copy_trading_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

/**
 * TraderStats 模型：用于保存每个交易员的统计信息
 */
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
    private double totalProfit; // 总收益，所有已关闭的交易的收益总和

    @Column(nullable = false)
    private double winRate; // 胜率，盈利交易占总交易的比例

    @Column(nullable = false)
    private double maxDrawdown; // 最大回撤，最大亏损的幅度

    @Column(nullable = false)
    private int totalTrades; // 总交易数量，所有已关闭的交易数量

    @Column(nullable = false)
    private int winningTrades; // 赢的交易数量，盈利交易的数量

    @Column(nullable = false)
    private int totalFollowers; // 该交易员的跟随者总数

    /**
     * 更新最大回撤
     * 计算最大回撤的逻辑可以在实际交易计算过程中更新
     */
    public void updateMaxDrawdown(double profit) {
        // 如果当前利润更低，则更新最大回撤
        if (profit < this.maxDrawdown) {
            this.maxDrawdown = profit;
        }
    }

    /**
     * 更新跟随者数量
     * 每当跟随者新增或取消时，可以调用此方法来更新 totalFollowers 字段
     */
    public void updateTotalFollowers(int delta) {
        this.totalFollowers += delta;
    }
}
