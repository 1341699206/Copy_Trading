package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.dto.ClosePositionRequest;
import com.xtq_ymt.copy_trading_backend.dto.OpenPositionRequest;
import com.xtq_ymt.copy_trading_backend.model.Trade;
import org.springframework.stereotype.Service;

@Service
public interface TradeService {
    Trade openPosition(OpenPositionRequest request);
    Trade closePosition(ClosePositionRequest request);
}