package com.xtq_ymt.copy_trading_backend.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor  // 自动生成包含所有字段的构造函数
@NoArgsConstructor   // 自动生成无参构造函数
public class MarketDataHistory {

    private Long id; // 历史数据记录的唯一标识符
    private String instrument; // 市场工具的名称或符号，例如：EUR/USD、AAPL
    private Double price; // 该时间点的价格
    private Double openPrice; // 开盘价格
    private Double closePrice; // 收盘价格（前一交易日）
    private Double highPrice; // 最高价格
    private Double lowPrice; // 最低价格
    private Double volume; // 交易量
    private Double volatility; // 市场波动率
    private Date timestamp; // 该历史数据点的时间戳

    // Lombok 将自动生成 Getter、Setter、全参构造函数和无参构造函数
}
