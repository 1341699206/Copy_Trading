package com.xtq_ymt.copy_trading_backend.exception;

/**
 * UserNotFoundException：当指定的用户未找到时抛出
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
