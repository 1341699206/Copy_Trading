package com.xtq_ymt.copy_trading_backend.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String role;
    private String country;  // 国家 ISO 代码
}
