package com.xtq_ymt.copy_trading_backend.dto;

import java.math.BigDecimal;

import lombok.*;

@Data
@AllArgsConstructor  // 生成包含所有字段的构造函数
@NoArgsConstructor   // 生成无参构造函数
public class TradersDataDTO {
    private Long traderId; // 交易者的唯一标识符
    private String name; // 交易者的用户名或显示名称
    private BigDecimal ROI; // 投资回报率
    private Integer followers; // 跟随者数量
    private Integer collectedFollowersAmount; // 被收藏的数量
    private String performance; // 历史数据
}
