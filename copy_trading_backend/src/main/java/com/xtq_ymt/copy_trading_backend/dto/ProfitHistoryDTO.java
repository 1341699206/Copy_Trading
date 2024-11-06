package com.xtq_ymt.copy_trading_backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProfitHistoryDTO {
    private BigDecimal profit; // 收益
    private LocalDateTime timeStamp; // 时间戳
}
