package com.xtq_ymt.copy_trading_backend.dto;

import lombok.Data;


@Data
public class LoginRequest {
    private String email;
    private String password;
    private String role;
}
