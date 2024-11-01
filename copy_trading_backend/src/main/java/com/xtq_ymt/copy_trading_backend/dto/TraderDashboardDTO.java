package com.xtq_ymt.copy_trading_backend.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TraderDashboardDTO {
    private BigDecimal amountFollowing;  // 跟随金额
    private Integer investors;           // 投资者人数
    private BigDecimal equity;           // 账户净值
    private BigDecimal balance;          // 账户余额
    private BigDecimal realisedPNL;      // 已实现盈亏
    private BigDecimal margin;           // 已用保证金
    private BigDecimal freeMargin;       // 可用保证金
}

