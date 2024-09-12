package com.xtq_ymt.copy_trading_backend.model;

import javax.persistence.*; // 导入 JPA 注解
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor  // 自动生成包含所有字段的构造函数
@NoArgsConstructor   // 自动生成无参构造函数
@Entity // 标识此类为 JPA 实体类
@Table(name = "market_data_history") // 指定表名
public class MarketDataHistory {

    @Id // 标识主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略
    @Column(name = "id") // 指定列名
    private Long id; // 历史数据记录的唯一标识符

    @Column(name = "instrument", nullable = false) // 指定列名，不能为空
    private String instrument; // 市场工具的名称或符号，例如：EUR/USD、AAPL

    @Column(name = "price") // 指定列名
    private Double price; // 该时间点的价格

    @Column(name = "open_price") // 指定列名
    private Double openPrice; // 开盘价格

    @Column(name = "close_price") // 指定列名
    private Double closePrice; // 收盘价格（前一交易日）

    @Column(name = "high_price") // 指定列名
    private Double highPrice; // 最高价格

    @Column(name = "low_price") // 指定列名
    private Double lowPrice; // 最低价格

    @Column(name = "volume") // 指定列名
    private Double volume; // 交易量

    @Column(name = "volatility") // 指定列名
    private Double volatility; // 市场波动率

    @Column(name = "timestamp") // 指定列名
    @Temporal(TemporalType.TIMESTAMP) // 指定时间类型为时间戳
    private Date timestamp; // 该历史数据点的时间戳

    // Lombok 将自动生成 Getter、Setter、全参构造函数和无参构造函数
}
