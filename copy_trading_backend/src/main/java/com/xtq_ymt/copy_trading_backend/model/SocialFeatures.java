package com.xtq_ymt.copy_trading_backend.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialFeatures {
    
    //基本信息字段
    private Long commentId; //评论的唯一标识
    private Long traderId; //评论主体的id（用于锁定评论位置）
    private Long followerId; //回复对象id（如果存在的话）（用于锁定评论位置）
    private String content; //评论的文本
    private Integer likeNum; //点赞数
    private Date commentTimestamp; //评论的时间戳

    //构造函数
    public SocialFeatures(){}

    public SocialFeatures(Long commentId,Long traderId,Long followerId,String content,Integer likeNum,Date commentTimestamp){

        this.commentId=commentId;
        this.traderId=traderId;
        this.followerId=followerId;
        this.content=content;
        this.likeNum=likeNum;
        this.commentTimestamp=commentTimestamp;

    }

    // Lombok 自动生成 Getter 和 Setter 方法
}
