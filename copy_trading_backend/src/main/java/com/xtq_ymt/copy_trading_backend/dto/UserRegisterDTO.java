package com.xtq_ymt.copy_trading_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 用于用户注册请求的数据传输对象 (DTO)。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    private String username; // 用户名
    private String email;    // 邮箱
    private String password; // 密码
    private String role;     // 用户角色，例如 "TRADER" 或 "FOLLOWER"
}
