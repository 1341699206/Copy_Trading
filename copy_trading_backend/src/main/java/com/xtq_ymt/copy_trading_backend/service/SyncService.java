package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Trade;

public interface SyncService {

    void notifyTrade(Trade trade); // 通知实时交易
}
