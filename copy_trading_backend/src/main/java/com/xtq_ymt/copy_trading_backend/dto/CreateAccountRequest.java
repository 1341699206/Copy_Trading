package com.xtq_ymt.copy_trading_backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateAccountRequest {
    private Long id;
    private String role;
    private String accountNumber;
    private String accountType;
    private String currency;
    private BigDecimal balance;
    private BigDecimal equity;
    private BigDecimal realisedPNL;
    private BigDecimal margin;
    private BigDecimal freeMargin;
    private BigDecimal availableMargin;
    private String status;
    private BigDecimal leverage;
}
