package com.xtq_ymt.copy_trading_backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

// 注解：Lombok 提供的 Getter 和 Setter 自动生成 get 和 set 方法
@Getter
@Setter
@AllArgsConstructor // 自动生成包含所有字段的构造函数
@NoArgsConstructor  // 自动生成无参构造函数
@Entity  // 表示这个类是一个 JPA 实体，映射到数据库表
@Table(name = "market_data")  // 指定表名为 "market_data"
public class MarketData {

    // 主键字段，自动生成ID（自增）
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // 指定数据库列名
    private Long id; // 市场数据的唯一标识符

    @Column(name = "symbol", nullable = false)
    private String symbol;

    // 市场数据所关联的金融工具（比如股票代码）
    @Column(name = "instrument", nullable = false)  // 数据库列名，并设为非空
    private String instrument;  // 金融工具的名称（例如：股票代码、货币对）

    // 当前价格
    @Column(name = "current_price", columnDefinition = "DECIMAL(18,8)")  // 精确到8位小数
    private BigDecimal currentPrice;  // 金融工具的当前市场价格

    // 开盘价格
    @Column(name = "open_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal openPrice;  // 当日的开盘价格

    // 收盘价格
    @Column(name = "close_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal closePrice;  // 当日的收盘价格

    // 最高价格
    @Column(name = "high_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal highPrice;  // 当日的最高价格

    // 最低价格
    @Column(name = "low_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal lowPrice;  // 当日的最低价格

    // 成交量
    @Column(name = "volume", columnDefinition = "DECIMAL(18,8)")  // 成交量，精确度较高
    private BigDecimal volume;  // 金融工具的交易量

    // 波动率
    @Column(name = "volatility", columnDefinition = "DECIMAL(18,8)")  // 波动率，精确到小数点8位
    private BigDecimal volatility;  // 市场波动性（衡量价格变动的幅度）

    // 时间戳，标记市场数据的获取时间
    @Column(name = "timestamp")  
    @Temporal(TemporalType.TIMESTAMP)  // 定义为时间戳，存储日期和时间
    private Date timestamp;  // 市场数据的时间戳，用于记录数据的时间点
}
