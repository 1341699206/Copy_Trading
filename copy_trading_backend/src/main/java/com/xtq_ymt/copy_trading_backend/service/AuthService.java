package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.dto.UserRegisterDTO;
import com.xtq_ymt.copy_trading_backend.dto.UserResponseDTO;

/**
 * 用户认证服务接口。
 * 定义用户注册和登录的服务逻辑。
 */
public interface AuthService {

    /**
     * 用户注册逻辑。
     *
     * @param userRegisterDTO 包含注册信息的数据传输对象
     * @throws IllegalArgumentException 如果用户名或邮箱已存在
     */
    void register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录逻辑。
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 包含用户基本信息的响应数据传输对象
     */
    UserResponseDTO login(String username, String password);
}
