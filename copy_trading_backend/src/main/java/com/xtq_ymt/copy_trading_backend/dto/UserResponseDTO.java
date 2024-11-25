package com.xtq_ymt.copy_trading_backend.dto;

import com.xtq_ymt.copy_trading_backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用于返回用户信息的数据传输对象 (DTO)。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;             // 用户ID
    private String username;     // 用户名
    private String email;        // 邮箱
    private String role;         // 用户角色（存储为字符串类型）
    private LocalDateTime createdAt; // 用户创建时间

    public UserResponseDTO(Long id, String username, String email, User.Role role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role.toString(); // 转换为字符串类型
        this.createdAt = createdAt;
    }
}
