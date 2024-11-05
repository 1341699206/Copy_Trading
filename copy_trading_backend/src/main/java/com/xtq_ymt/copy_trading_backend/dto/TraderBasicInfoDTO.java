package com.xtq_ymt.copy_trading_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TraderBasicInfoDTO {

    private Long traderId;     // trader id
    private String name;       // 用户名
    private BigDecimal ROI;    // 盈利率
    private int copiers;       // 跟随者数
    private double winRate;    // 盈利占比
}
