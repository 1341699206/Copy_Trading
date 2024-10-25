package com.xtq_ymt.copy_trading_backend.service.impl;

import com.xtq_ymt.copy_trading_backend.model.Trader;
import com.xtq_ymt.copy_trading_backend.model.Follower;
import com.xtq_ymt.copy_trading_backend.repository.TraderRepository;
import com.xtq_ymt.copy_trading_backend.repository.FollowerRepository;
import com.xtq_ymt.copy_trading_backend.repository.TradingAccountRepository;
import java.math.BigDecimal;
import com.xtq_ymt.copy_trading_backend.model.TradingAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.util.UUID;

@Service
public class AccountSimulationService {

    @Autowired
    private TraderRepository traderRepository;

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private TradingAccountRepository tradingAccountRepository;  // 新增 TradingAccount 的仓库

    private static final String[] COUNTRIES = {
        "China", "United States", "United Kingdom", "Canada", "Australia",
        "Germany", "France", "Japan", "Brazil", "India"
    };

    public void createSampleTradersAndFollowers() {
        // 检查是否已经有足够的交易员和跟随者
        if (traderRepository.count() >= 10 && followerRepository.count() >= 20) {
            System.out.println("已有足够的交易员和跟随者，跳过创建步骤。");
            return;
        }

        List<Trader> traders = new ArrayList<>();
        List<Follower> followers = new ArrayList<>();
        Random random = new Random();

        // 生成模拟 Trader
        for (int i = 1; i <= 10; i++) {
            Trader trader = new Trader();
            trader.setName("Trader" + i);
            trader.setEmail("trader" + i + "@example.com");
            trader.setCountryName(COUNTRIES[random.nextInt(COUNTRIES.length)]); // 从国家列表中随机选择
            trader.setPassword("111111"); // 设置初始密码
            traders.add(trader);
        }
        traderRepository.saveAll(traders);

        // 为每个 Trader 生成多个 Demo TradingAccount 并设置初始资金
        for (Trader trader : traders) {
            int numAccounts = random.nextInt(3) + 1; // 每个 Trader 随机创建 1-3 个交易账户
            for (int j = 0; j < numAccounts; j++) {
                TradingAccount account = new TradingAccount();
                account.setTrader(trader);
                account.setAccountNumber("T" + UUID.randomUUID().toString());  // 使用 UUID 生成唯一的账户编号
                account.setAccountType("Demo");  // 设置账户类型为 Demo
                account.setBalance(new BigDecimal("10000.00"));  // 设置初始余额
                account.setEquity(new BigDecimal("10000.00"));  // 设置初始权益
                account.setMargin(new BigDecimal("2000.00"));  // 设置初始保证金
                account.setFreeMargin(new BigDecimal("8000.00")); // 设置初始可用保证金
                account.setRealisedPNL(BigDecimal.ZERO); // 设置初始已实现盈亏为 0
                account.setStatus("Active");  // 设置账户状态
                account.setCurrency("USD");  // 设置账户货币
                tradingAccountRepository.save(account);
            }
        }

        // 生成模拟 Follower
        for (int i = 1; i <= 20; i++) {
            Follower follower = new Follower();
            follower.setName("Follower" + i);
            follower.setEmail("follower" + i + "@example.com");
            follower.setCountry(COUNTRIES[random.nextInt(COUNTRIES.length)]); // 从国家列表中随机选择
            follower.setPassword("111111"); // 设置初始密码
            followers.add(follower);
        }
        followerRepository.saveAll(followers);

        // 为每个 Follower 生成多个 Demo TradingAccount 并设置初始资金
        for (Follower follower : followers) {
            int numAccounts = random.nextInt(3) + 1; // 每个 Follower 随机创建 1-3 个交易账户
            for (int j = 0; j < numAccounts; j++) {
                TradingAccount account = new TradingAccount();
                account.setFollower(follower);
                account.setAccountNumber("F" + UUID.randomUUID().toString());  // 使用 UUID 生成唯一的账户编号
                account.setAccountType("Demo");  // 设置账户类型为 Demo
                account.setBalance(new BigDecimal("5000.00"));  // 设置初始余额
                account.setEquity(new BigDecimal("5000.00"));  // 设置初始权益
                account.setMargin(new BigDecimal("1000.00"));  // 设置初始保证金
                account.setFreeMargin(new BigDecimal("4000.00")); // 设置初始可用保证金
                account.setRealisedPNL(BigDecimal.ZERO); // 设置初始已实现盈亏为 0
                account.setStatus("Active");  // 设置账户状态
                account.setCurrency("USD");  // 设置账户货币
                tradingAccountRepository.save(account);
            }
        }
    }
}
