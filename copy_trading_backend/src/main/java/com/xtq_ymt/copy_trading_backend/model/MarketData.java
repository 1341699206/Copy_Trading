package com.xtq_ymt.copy_trading_backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "market_data", uniqueConstraints = {@UniqueConstraint(columnNames = {"instrument"})})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "instrument")
public class MarketData {

    @Id
    @Column(name = "instrument", nullable = false)
    private String instrument;

    @Column(name = "symbol", nullable = false)
    private String symbol;

    @Column(name = "current_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal currentPrice;

    @Column(name = "open_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal openPrice;

    @Column(name = "close_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal closePrice;

    @Column(name = "high_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal highPrice;

    @Column(name = "low_price", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal lowPrice;

    @Column(name = "volume", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal volume;

    @Column(name = "volatility", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal volatility;

    @ManyToMany(mappedBy = "collectedMarketData", cascade = CascadeType.ALL)
    private Set<Trader> traders;

    @ManyToMany(mappedBy = "collectedMarketData", cascade = CascadeType.ALL)
    private Set<Follower> followers;

    @Column(name = "timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
