package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.Result.Result;

public interface AuthService {

    /**
     * 处理用户的认证请求，返回详细的 Result。
     * @param email 用户的邮箱
     * @param password 用户的密码
     * @param role 用户的角色（ADMIN、FOLLOWER、TRADER）
     * @return 返回认证的结果封装为 Result 对象
     */
    public Result authenticate(String email, String password, String role);

    /**
     * 响应用户注册请求，返回注册的结果。
     * @param name 用户名
     * @param email 用户的邮箱
     * @param password 用户的密码
     * @param role 用户的角色
     * @param country 用户的国家
     * @return 返回注册的结果封装为 Result 对象
     */
    public Result register(String name, String email, String password, String role, String country);
}
