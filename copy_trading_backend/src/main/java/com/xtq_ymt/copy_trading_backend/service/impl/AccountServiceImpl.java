package com.xtq_ymt.copy_trading_backend.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtq_ymt.copy_trading_backend.Result.Result;
import com.xtq_ymt.copy_trading_backend.dto.CreateAccountRequest;
import com.xtq_ymt.copy_trading_backend.model.Follower;
import com.xtq_ymt.copy_trading_backend.model.Trader;
import com.xtq_ymt.copy_trading_backend.model.TradingAccount;
import com.xtq_ymt.copy_trading_backend.repository.FollowerRepository;
import com.xtq_ymt.copy_trading_backend.repository.TraderRepository;
import com.xtq_ymt.copy_trading_backend.repository.TradingAccountRepository;
import com.xtq_ymt.copy_trading_backend.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService{

    @Autowired
    private TradingAccountRepository tradingAccountRepository;

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private TraderRepository traderRepository;

    @Override
    public Result createAccount(CreateAccountRequest account){
        log.info(account.getRole()+" "+"id: "+account.getId()+" create account");

        //查看账号是否已存在
        if(tradingAccountRepository.findByAccountNumber(account.getAccountNumber()).isPresent()){
            return Result.error("Account with this number already exists.");
        }

        TradingAccount tradingAccount=new TradingAccount();

        //需要传入参数
        tradingAccount.setAccountNumber(account.getAccountNumber());
        tradingAccount.setAccountType(account.getAccountType());
        tradingAccount.setCurrency(account.getCurrency());
        tradingAccount.setBalance(BigDecimal.valueOf(account.getBalance()));
        //创建到对应的role对象中，
        switch (account.getRole()) {
            case "FOLLOWER":
                Follower follower=followerRepository.findByFollowerId(account.getId()).get();
                tradingAccount.setFollower(follower);
                follower.getTradingAccounts().add(tradingAccount);
                break;
            case "TRADER":
                Trader trader=traderRepository.findByTraderId(account.getId()).get();
                tradingAccount.setTrader(trader);
                trader.getTradingAccounts().add(tradingAccount);
                break;
        }

        //补齐其他数据
        tradingAccount.setEquity(BigDecimal.valueOf(account.getBalance()));
        tradingAccount.setRealisedPNL(BigDecimal.valueOf(0));
        tradingAccount.setMargin(BigDecimal.valueOf(0));
        tradingAccount.setFreeMargin(BigDecimal.valueOf(0));
        tradingAccount.setStatus("Active");

        //保存到数据库中
        tradingAccountRepository.save(tradingAccount);
        return Result.success("Trading Account created successfully");
    }

}