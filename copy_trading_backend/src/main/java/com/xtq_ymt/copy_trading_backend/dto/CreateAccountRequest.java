package com.xtq_ymt.copy_trading_backend.dto;

import lombok.Data;

@Data
public class CreateAccountRequest {
    private Long id;
    private String role;
    private String accountNumber;
    private String accountType;
    private String currency;
    private Double balance;
}
