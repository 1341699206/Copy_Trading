package com.xtq_ymt.copy_trading_backend.model;

import jakarta.persistence.*; // 导入 JPA 注解
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trader_scripts") // 指定数据库表名为 "trader_scripts"
public class TraderScripts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "script_id")
    private Long scriptId; // 脚本的唯一标识符

    @ManyToOne // 一个交易者可以有多个脚本
    @JoinColumn(name = "trader_id", nullable = false)
    private Trader trader; // 关联的交易者

    @Column(name = "script_name", nullable = false)
    private String scriptName; // 脚本名称

    @Column(name = "language", nullable = false)
    private String language = "Python"; // 脚本语言（固定为Python）

    @Lob // 大字段对象，用于存储大文本数据
    @Column(name = "script_content", nullable = false, columnDefinition = "TEXT")
    private String scriptContent; // 脚本的内容

    @Column(name = "description")
    private String description; // 脚本的描述信息

    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive = true; // 脚本是否激活

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt; // 脚本创建时间

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt; // 脚本更新时间

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    // Lombok 自动生成的 Getter、Setter、全参构造函数和无参构造函数
}
