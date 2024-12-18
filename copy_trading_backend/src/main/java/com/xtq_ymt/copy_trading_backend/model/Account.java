package com.xtq_ymt.copy_trading_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;


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
    @JsonManagedReference
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
