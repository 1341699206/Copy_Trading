package com.xtq_ymt.copy_trading_backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CopiersDTO {
    private Long followerId;     // follower Id
    private String name;         // 复制者名称
    private BigDecimal profit;   // 跟单总收益
}
