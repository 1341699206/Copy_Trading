package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Follower;
import com.xtq_ymt.copy_trading_backend.model.Trader;

public interface UserService {
    /*
     * 通过 WebSocket 发送用户数据更新
     */
    public void sendFollowerUpdateToFrontEnd(Follower follower);

    public void sendTraderUpdateToFrontEnd(Trader trader);
}
