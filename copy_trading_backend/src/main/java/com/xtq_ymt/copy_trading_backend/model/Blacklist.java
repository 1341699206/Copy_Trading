package com.xtq_ymt.copy_trading_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "blacklist")  // 数据库表名为 blacklist
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 每条黑名单记录的唯一标识符

    @Column(nullable = false)
    private Long userId;  // 被封禁的用户ID

    @Column(nullable = false)
    private LocalDateTime bannedAt;  // 封禁时间

    @Column(nullable = true)
    private String reason;  // 可选：封禁原因，记录为什么封禁用户

    @PrePersist
    protected void onCreate() {
        bannedAt = LocalDateTime.now();  // 封禁时自动填充当前时间
    }
}
