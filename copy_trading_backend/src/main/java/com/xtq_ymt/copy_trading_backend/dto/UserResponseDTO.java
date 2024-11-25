package com.xtq_ymt.copy_trading_backend.dto;

import com.xtq_ymt.copy_trading_backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户响应 DTO。
 * 用于向客户端返回用户基本信息。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private User.Role role;
    private LocalDateTime createdAt;
    private String token; // 新增字段：JWT 令牌
}
