package com.xtq_ymt.copy_trading_backend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "admin")  // 指定数据库表名为 "admin"
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId; // 管理员的唯一标识符

    @Column(name = "username", nullable = false, unique = true)
    private String username; // 管理员用户名，唯一

    @Column(name = "password", nullable = false)
    private String password; // 管理员密码（应进行加密存储）

    @Column(name = "email", nullable = false, unique = true)
    private String email; // 管理员电子邮件，唯一

    @Enumerated(EnumType.STRING)  // 使用枚举类型表示角色
    @Column(name = "role", nullable = false)
    private AdminRole role; // 管理员角色

    @Builder.Default
    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive = true; // 管理员是否处于活跃状态，默认值为 true

    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin; // 上次登录时间

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt; // 创建时间

    @PrePersist
    protected void onCreate() {
        createdAt = new Date(); // 在保存到数据库之前设置创建时间
    }

    @PreUpdate
    protected void onUpdate() {
        lastLogin = new Date(); // 更新最后登录时间
    }
}
