package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.Result.AuthResult;

public interface AuthService {

    /**
     * 处理用户的认证请求，返回更详细的结果类AuthResult。
     * @param email
     * @param password
     * @param role
     * @return
     */
    public AuthResult authenticate(String email, String password, String role);

    /**
     * 响应用户注册请求，
     * @param name
     * @param email
     * @param password
     * @param role
     * @param country
     * @return
     */
    public boolean register(String name, String email, String password, String role, String country);
}
