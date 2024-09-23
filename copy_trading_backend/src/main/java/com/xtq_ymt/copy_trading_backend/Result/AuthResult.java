package com.xtq_ymt.copy_trading_backend.Result;


// 登录操作的后端内部响应返回类,返回到AuthController
// 新的响应对象，用于返回详细的认证结果
public class AuthResult {
    private boolean success;
    private String message;

    public AuthResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
