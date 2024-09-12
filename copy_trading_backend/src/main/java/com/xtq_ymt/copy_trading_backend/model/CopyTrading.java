package com.xtq_ymt.copy_trading_backend.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity // 表明这是一个JPA实体类
@Table(name = "copy_trading") // 指定数据库表名为 "copy_trading"
@Getter
@Setter
@AllArgsConstructor // 生成包含所有字段的构造函数
@NoArgsConstructor  // 生成无参构造函数
@Builder // 使用 Builder 模式
public class CopyTrading {

    @Id // 指定主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略为自增
    @Column(name = "copy_trading_id") // 对应数据库中的列名
    private Long copyTradingId;

    @ManyToOne // 多对一关系：多个复制交易对应一个跟随者
    @JoinColumn(name = "follower_id", nullable = false) // 外键列名为 "follower_id"，不允许为空
    private Follower follower; // 关联的跟随者

    @ManyToOne // 多对一关系：多个复制交易对应一个交易者
    @JoinColumn(name = "trader_id", nullable = false) // 外键列名为 "trader_id"，不允许为空
    private Trader trader; // 被跟随的交易者

    @Column(name = "start_date") // 对应数据库中的列名
    @Temporal(TemporalType.TIMESTAMP) // 指定时间类型为时间戳
    private Date startDate; // 复制交易开始的时间

    @Column(name = "end_date") // 对应数据库中的列名
    @Temporal(TemporalType.TIMESTAMP) // 指定时间类型为时间戳
    private Date endDate; // 复制交易终止的时间

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    @Builder.Default
    private Boolean isActive = true; // 指示交易是否仍然活跃，默认为true

    // 风险管理字段
    @Column(name = "allocation", precision = 18, scale = 8) // 设置字段精度和小数位数
    private BigDecimal allocation; // 当前的该跟随的总资金

    @Column(name = "is_stop_loss", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean isStopLoss = false; // 是否启用止损限额，默认为false

    @Column(name = "stop_loss", precision = 18, scale = 8)
    private BigDecimal stopLoss; // 止损限额

    @Column(name = "is_stop_loss_percentage", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean isStopLossPercentage = false; // 是否启用止损百分比，默认为false

    @Column(name = "stop_loss_percentage", precision = 5, scale = 2)
    private BigDecimal stopLossPercentage; // 止损百分比

    @Column(name = "is_take_profit", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean isTakeProfit = false; // 是否启用止盈限额，默认为false

    @Column(name = "take_profit", precision = 18, scale = 8)
    private BigDecimal takeProfit; // 止盈限额

    @Column(name = "is_max_open_positions", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean isMaxOpenPositions = false; // 是否启用最大持仓限制，默认为false

    @Column(name = "max_open_positions", precision = 18, scale = 8)
    private BigDecimal maxOpenPositions; // 最大持仓数

    @Column(name = "is_max_lots", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean isMaxLots = false; // 是否启用最大交易手数限制，默认为false

    @Column(name = "max_lots", precision = 18, scale = 8)
    private BigDecimal maxLots; // 最大交易手数

    @Column(name = "auto_adjust", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean autoAdjust = false; // 是否启用自动调整策略，默认为false

    @OneToMany(mappedBy = "copyTrading", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Trade> copiedTrades; // 已复制的交易列表

    @Column(name = "total_profit", precision = 18, scale = 8)
    private BigDecimal totalProfit; // 总收益

    @Column(name = "total_loss", precision = 18, scale = 8)
    private BigDecimal totalLoss; // 总损失

    @PrePersist // 在持久化之前自动调用的方法
    protected void onCreate() {
        if (startDate == null) {
            startDate = new Date(); // 如果开始时间为空，则设置为当前时间
        }
    }

    @PreUpdate // 在更新之前自动调用的方法
    protected void onUpdate() {
        if (endDate == null && !isActive) {
            endDate = new Date(); // 如果结束时间为空且交易不活跃，则设置为当前时间
        }
    }
}
