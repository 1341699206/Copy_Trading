package com.xtq_ymt.copy_trading_backend.model;

import jakarta.persistence.*; // 导入 JPA 注解
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Column(name = "symbol", nullable = false)
    private String symbol;
    
    @Column(name = "instrument", nullable = false) // 指定列名，不能为空
    private String instrument; // 市场工具的名称或符号，例如：EUR/USD、AAPL

    // 将 Double 修改为 BigDecimal，并指定 columnDefinition
    @Column(name = "price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal price; // 该时间点的价格

    @Column(name = "open_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal openPrice; // 开盘价格

    @Column(name = "close_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal closePrice; // 收盘价格（前一交易日）

    @Column(name = "high_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal highPrice; // 最高价格

    @Column(name = "low_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal lowPrice; // 最低价格

    @Column(name = "volume", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal volume; // 交易量

    @Column(name = "volatility", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal volatility; // 市场波动率

    @Column(name = "timestamp") // 指定列名
    @Temporal(TemporalType.TIMESTAMP) // 指定时间类型为时间戳
    private LocalDateTime timestamp; // 该历史数据点的时间戳

    // Lombok 将自动生成 Getter、Setter、全参构造函数和无参构造函数
}

