package com.xtq_ymt.copy_trading_backend.dto;

import lombok.Data;

@Data
public class TraderSimpleInfDTO {
    private Long traderId; // 交易者的唯一标识符
    private String name; // 交易者的名称
}
