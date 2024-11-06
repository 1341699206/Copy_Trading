package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.dto.TradeHistoryDTO;
import org.springframework.data.domain.Page;

public interface TradeHistoryService {
    Page<TradeHistoryDTO> getTradeHistory(Long traderId, int pageSize, int currentPage);
}
