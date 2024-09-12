package com.xtq_ymt.copy_trading_backend.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor  // 自动生成包含所有字段的构造函数
@NoArgsConstructor   // 自动生成无参构造函数
public class SocialFeatures {
    
    // 基本信息字段
    private Long commentId; // 评论的唯一标识
    private Long traderId; // 评论主体的id（用于锁定评论位置）
    private Long followerId; // 回复对象id（如果存在的话）（用于锁定评论位置）
    private String content; // 评论的文本
    private Integer likeNum; // 点赞数
    private Date commentTimestamp; // 评论的时间戳

    // Lombok 将自动生成 Getter、Setter、全参构造函数和无参构造函数
}
