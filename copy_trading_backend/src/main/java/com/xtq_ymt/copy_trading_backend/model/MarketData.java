package com.xtq_ymt.copy_trading_backend.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketData {

    private String instrument; // 市场工具的名称或符号，例如：EUR/USD、AAPL
    private Double currentPrice; // 当前价格
    private Double openPrice; // 开盘价格
    private Double closePrice; // 收盘价格（前一交易日）
    private Double highPrice; // 最高价格
    private Double lowPrice; // 最低价格
    private Double volume; // 交易量
    private Double volatility; // 市场波动率
    private Date timestamp; // 数据的时间戳

    // 构造函数
    public MarketData() {
    }

    public MarketData(String instrument, Double currentPrice, Double openPrice, Double closePrice, 
                      Double highPrice, Double lowPrice, Double volume, Double volatility, Date timestamp) {
        this.instrument = instrument;
        this.currentPrice = currentPrice;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.volume = volume;
        this.volatility = volatility;
        this.timestamp = timestamp;
    }

    // Lombok 自动生成 Getter 和 Setter 方法
}
