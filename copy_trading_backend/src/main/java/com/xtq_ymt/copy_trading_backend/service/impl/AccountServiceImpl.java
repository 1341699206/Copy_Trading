package com.xtq_ymt.copy_trading_backend.service.impl;


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
import com.xtq_ymt.copy_trading_backend.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private TradingAccountRepository tradingAccountRepository;

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private TraderRepository traderRepository;

    @Autowired
    private UserService userService;

    @Override
    public Result createAccount(CreateAccountRequest account) {
        log.info(account.getRole() + " " + "id: " + account.getId() + " create account");

        // 移除唯一性检查，允许重复的账户编号

        TradingAccount tradingAccount = new TradingAccount();

        // 设置账户基本信息
        tradingAccount.setAccountNumber(account.getAccountNumber());
        tradingAccount.setAccountType(account.getAccountType());
        tradingAccount.setCurrency(account.getCurrency());
        tradingAccount.setBalance(account.getBalance());  // 直接使用 account.getBalance()，它已经是 BigDecimal 类型
        tradingAccount.setLeverage(account.getLeverage()); // leverage 也是 BigDecimal 类型
        tradingAccount.setEquity(account.getEquity());
        tradingAccount.setRealisedPNL(account.getRealisedPNL());
        tradingAccount.setMargin(account.getMargin());
        tradingAccount.setFreeMargin(account.getFreeMargin());
        tradingAccount.setAvailableMargin(account.getAvailableMargin());
        tradingAccount.setStatus(account.getStatus());

        // 根据用户角色创建到对应的实体
        switch (account.getRole()) {
            case "FOLLOWER":
                Follower follower = followerRepository.findByFollowerId(account.getId()).orElse(null);
                if (follower == null) {
                    return Result.error("Follower not found");
                }
                tradingAccount.setFollower(follower);
                follower.getTradingAccounts().add(tradingAccount);

                // 如果没有当前账户，就设置创建的账户为当前账户
                if (follower.getCurrentAccount() == null) follower.setCurrentAccount(tradingAccount);

                // 保存到数据库中
                tradingAccountRepository.save(tradingAccount);

                // 传递更新信息给前端
                userService.sendFollowerUpdateToFrontEnd(follower);
                return Result.success("Trading Account created successfully");

            case "TRADER":
                Trader trader = traderRepository.findByTraderId(account.getId()).orElse(null);
                if (trader == null) {
                    return Result.error("Trader not found");
                }
                tradingAccount.setTrader(trader);
                trader.getTradingAccounts().add(tradingAccount);

                // 如果没有当前账户，就设置创建的账户为当前账户
                if (trader.getCurrentAccount() == null) trader.setCurrentAccount(tradingAccount);

                // 保存到数据库中
                tradingAccountRepository.save(tradingAccount);

                // 传递更新信息给前端
                userService.sendTraderUpdateToFrontEnd(trader);
                return Result.success("Trading Account created successfully");

            default:
                return Result.error("Role not found");
        }
    }
}
