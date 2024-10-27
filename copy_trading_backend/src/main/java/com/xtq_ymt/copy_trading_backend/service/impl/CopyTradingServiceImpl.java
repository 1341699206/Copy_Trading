package com.xtq_ymt.copy_trading_backend.service.impl;

import com.xtq_ymt.copy_trading_backend.dto.CopyTradeRequest;
import com.xtq_ymt.copy_trading_backend.dto.StopCopyRequest;
import com.xtq_ymt.copy_trading_backend.model.CopyTrading;
import com.xtq_ymt.copy_trading_backend.model.Trader;
import com.xtq_ymt.copy_trading_backend.model.Follower;
import com.xtq_ymt.copy_trading_backend.model.TradingAccount;
import com.xtq_ymt.copy_trading_backend.repository.CopyTradingRepository;
import com.xtq_ymt.copy_trading_backend.repository.TraderRepository;
import com.xtq_ymt.copy_trading_backend.repository.FollowerRepository;
import com.xtq_ymt.copy_trading_backend.repository.TradingAccountRepository;
import com.xtq_ymt.copy_trading_backend.service.CopyTradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CopyTradingServiceImpl implements CopyTradingService {

    @Autowired
    private CopyTradingRepository copyTradingRepository;

    @Autowired
    private TraderRepository traderRepository;

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private TradingAccountRepository tradingAccountRepository;

    @Override
    public CopyTrading startCopying(CopyTradeRequest request) {
        Trader trader = traderRepository.findById(request.getTraderId())
                .orElseThrow(() -> new IllegalArgumentException("交易员未找到"));
        Follower follower = followerRepository.findById(request.getFollowerId())
                .orElseThrow(() -> new IllegalArgumentException("跟随者未找到"));

        TradingAccount traderAccount = tradingAccountRepository.findById(request.getTraderAccountId())
                .orElseThrow(() -> new IllegalArgumentException("交易员账户未找到"));
        TradingAccount followerAccount = tradingAccountRepository.findById(request.getFollowerAccountId())
                .orElseThrow(() -> new IllegalArgumentException("跟随者账户未找到"));

        CopyTrading copyTrading = new CopyTrading();
        copyTrading.setTrader(trader);
        copyTrading.setFollower(follower);
        copyTrading.setTraderAccount(traderAccount);
        copyTrading.setFollowerAccount(followerAccount);
        copyTrading.setStartDate(new Date());
        copyTrading.setIsActive(true);
        return copyTradingRepository.save(copyTrading);
    }

    @Override
    public void stopCopying(StopCopyRequest request) {
        CopyTrading copyTrading = copyTradingRepository.findById(request.getCopyTradingId())
                .orElseThrow(() -> new IllegalArgumentException("跟单关系未找到"));
        copyTrading.setIsActive(false);
        copyTrading.setEndDate(new Date());
        copyTradingRepository.save(copyTrading);
    }
}
