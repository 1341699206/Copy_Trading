package com.xtq_ymt.copy_trading_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TraderDTO {

    private Long traderId;                      // trader id
    private BigDecimal profit;                  // 盈亏
    private int trades;                         // 平仓的交易
    private int maxOpenTrades;                  // 最大同时持仓
    private BigDecimal avgProfit;               // 平均盈亏
    private int winTrades;                      // 获利的交易次数
    private BigDecimal recommendedMinInvestment; // 推荐最小投资
    private BigDecimal maxDrawDown;             // 最大撤回资金
    private int avgTradeTime;                   // 平均持仓时间
    private BigDecimal avgPips;                 // 平均点差
    private List<TraderHistory> traderHistoryData; // trader历史数据

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TraderHistory {
        private Long traderId;              // trader id
        private BigDecimal ROI;             // 盈利率
        private BigDecimal profit;          // 盈亏
        private BigDecimal invest;          // 投资量
        private BigDecimal pips;            // 点差
        private LocalDateTime timeStamp;    // 时间戳
    }
}
