package com.xtq_ymt.copy_trading_backend.exception;

/**
 * TradeNotFoundException：当指定的交易未找到时抛出
 */
public class TradeNotFoundException extends RuntimeException {
    public TradeNotFoundException(String message) {
        super(message);
    }
}
