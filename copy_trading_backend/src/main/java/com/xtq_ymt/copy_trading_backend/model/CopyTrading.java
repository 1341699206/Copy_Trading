package com.xtq_ymt.copy_trading_backend.model;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor // 生成包含所有字段的构造函数
@NoArgsConstructor  // 生成无参构造函数
public class CopyTrading {

    // 基本信息字段
    private Long copyTradingId; // 复制交易的唯一标识
    private Follower follower; // 关联的跟随者
    private Trader trader; // 被跟随的交易者
    private Date startDate; // 复制交易开始的时间
    private Date endDate; // 复制交易终止的时间
    private Boolean isActive; // trader是否处于活跃交易状态

    // 风险管理
    private Double allocation; // 当前的该跟随的总资金
    
    private Boolean isStopLoss; // 是否启用止损限额
    private Double stopLoss; // 止损限额

    private Boolean isStopLossPercentage; // 是否启用止损
    private Double stopLossPercentage; // 止损百分比

    private Boolean isTakeProfit; // 是否启用止盈限额
    private Double takeProfit; // 止盈限额

    private Boolean isMaxOpenPositions; // 是否启用跟随持有最大仓位限制
    private Double maxOpenPositions; // 跟随持有的最大仓位

    private Boolean isMaxLots; // 是否启用跟随的最大交易手数
    private Double maxLots; // 跟随的最大交易手数

    private Boolean autoAdjust; // 是否启用自动调整策略

    // 交易信息
    private List<Trade> copiedTrades; // 已复制的交易列表
    private Double totalProfit; // 该跟随的总收益
    private Double totalLoss; // 该跟随的总损失

    // Lombok 自动生成 Getter 和 Setter 方法
}
