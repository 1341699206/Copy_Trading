package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.dto.TradeOpenPositionDTO;
import org.springframework.data.domain.Page;

public interface TradePositionService {
    Page<TradeOpenPositionDTO> getOpenPositions(Long traderId, int pageSize, int currentPage);
}
