package com.xtq_ymt.copy_trading_backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

// Lombok 注解，自动生成 Getter, Setter 和构造函数
@Getter
@Setter
@AllArgsConstructor // 自动生成全参构造函数
@NoArgsConstructor  // 自动生成无参构造函数
@Entity  // 表示这个类是一个 JPA 实体
@Table(name = "market_data", uniqueConstraints = {@UniqueConstraint(columnNames = {"instrument"})}) // instrument 设置唯一约束
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "instrument")
public class MarketData {

    // instrument 作为主键，用来唯一标识市场数据
    @Id
    @Column(name = "instrument", nullable = false)
    private String instrument;  // 金融工具的名称（例如股票代码、货币对）

    // symbol 不再是主键，但仍用于表示金融工具的符号
    @Column(name = "symbol", nullable = false)
    private String symbol;  // 金融工具的符号，例如：EURUSD=X

    // 当前价格
    @Column(name = "current_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal currentPrice;

    // 开盘价格
    @Column(name = "open_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal openPrice;

    // 收盘价格
    @Column(name = "close_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal closePrice;

    // 最高价格
    @Column(name = "high_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal highPrice;

    // 最低价格
    @Column(name = "low_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal lowPrice;

    // 成交量
    @Column(name = "volume", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal volume;

    // 波动率
    @Column(name = "volatility", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal volatility;

    @ManyToMany(mappedBy = "collectedMarketData", cascade = CascadeType.ALL)
    private Set<Trader> traders; //被哪些trader收藏了

    @ManyToMany(mappedBy = "collectedMarketData", cascade = CascadeType.ALL)
    private Set<Follower> followers; //被哪些follower收藏了

    // 获取数据的时间戳
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp; // 市场数据的时间戳

    // 最后更新时间，用于标识数据更新的时间
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt; // 数据最后一次更新的时间
}

