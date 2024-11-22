package com.xtq_ymt.copy_trading_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "strategies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Strategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trader_id", nullable = false)
    private User trader; // 关联交易员对象

    @OneToMany(mappedBy = "strategy", cascade = CascadeType.ALL)
    private List<Trade> trades; // 与 Trade 的双向关联

    @Column(nullable = false)
    private String name; // 策略名称

    @Column(nullable = true, columnDefinition = "TEXT")
    private String description; // 策略描述

    @Column(nullable = false, columnDefinition = "TEXT")
    private String scriptContent; // 策略脚本内容（如 Python 脚本）

    @Column(nullable = false)
    private boolean isActive; // 策略是否启用

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
