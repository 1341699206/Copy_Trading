package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.dto.CopyTradeRequest;
import com.xtq_ymt.copy_trading_backend.dto.StopCopyRequest;
import com.xtq_ymt.copy_trading_backend.model.CopyTrading;
import org.springframework.stereotype.Service;

@Service
public interface CopyTradingService {
    CopyTrading startCopying(CopyTradeRequest request);
    void stopCopying(StopCopyRequest request);
}