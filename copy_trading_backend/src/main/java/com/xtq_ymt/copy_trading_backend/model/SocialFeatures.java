package com.xtq_ymt.copy_trading_backend.model;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "social_features")
public class SocialFeatures {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId; // 评论的唯一标识

    @ManyToOne // 多个评论可以对应一个Trader
    @JoinColumn(name = "trader_id", nullable = false)
    private Trader trader; // 评论所属的Trader

    @ManyToOne // 多个评论可以对应一个Follower
    @JoinColumn(name = "follower_id")
    private Follower follower; // 评论的Follower（如果有）

    @Column(name = "content", nullable = false)
    private String content; // 评论的文本

    @Column(name = "like_num", columnDefinition = "INTEGER DEFAULT 0")
    private Integer likeNum; // 点赞数

    @Column(name = "comment_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentTimestamp; // 评论的时间戳

    @Column(name = "author_type", nullable = false) // 指示评论者的类型（Trader 或 Follower）
    private String authorType;

    @PrePersist
    private void determineAuthorType() {
        if (follower != null) {
            this.authorType = "Follower";
        } else {
            this.authorType = "Trader";
        }
    }
}
