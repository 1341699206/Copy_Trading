package com.xtq_ymt.copy_trading_backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "market_data")
public class MarketData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "instrument", nullable = false)
    private String instrument;

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

    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
}
