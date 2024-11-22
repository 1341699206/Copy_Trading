package com.xtq_ymt.copy_trading_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 关联用户对象

    @Column(nullable = false)
    private double balance; // 账户余额

    @Column(nullable = false)
    private double equity; // 净值

    @Column(nullable = false)
    private double margin; // 已用保证金

    @Column(nullable = false)
    private double freeMargin; // 可用保证金

    @Column(nullable = false)
    private double winRate; // 胜率

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Trade> trades; // 与 Trade 的双向关联

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
