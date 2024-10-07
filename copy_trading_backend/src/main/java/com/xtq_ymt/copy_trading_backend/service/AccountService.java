package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.Result.Result;
import com.xtq_ymt.copy_trading_backend.dto.CreateAccountRequest;

public interface AccountService {
    /**
     * 创建账户
     * @param accountRequest 
     * @return
     */
    public Result createAccount(CreateAccountRequest accountRequest);
}
