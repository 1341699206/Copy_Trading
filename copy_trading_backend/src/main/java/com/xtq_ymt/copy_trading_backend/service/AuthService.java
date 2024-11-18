package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.User;

public interface AuthService {

    User register(User user);

    String login(String username, String password);
}
