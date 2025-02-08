package com.xtq_ymt.copy_trading_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admins")  // 数据库表名为 admins
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 管理员的唯一标识符

    @Column(nullable = false, unique = true)
    private String username;  // 管理员的用户名

    @Column(nullable = false, unique = true)
    private String email;  // 管理员的邮箱

    @Column(nullable = false)
    private String password;  // 管理员的密码

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;  // 管理员的角色，默认是 ADMIN

    @Column(nullable = false)
    private LocalDateTime createdAt;  // 创建时间

    @Column(nullable = false)
    private LocalDateTime updatedAt;  // 更新时间

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum Role {
        ADMIN  // 仅包含管理员角色
    }

    // 构造器，用于通过字段创建 Admin 实例
    @Builder
    public Admin(Long id, String username, String email, String password, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role != null ? role : Role.ADMIN;  // 默认角色为 ADMIN
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
