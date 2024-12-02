package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Account;
import com.xtq_ymt.copy_trading_backend.model.User;
import com.xtq_ymt.copy_trading_backend.repository.AccountRepository;
import com.xtq_ymt.copy_trading_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // 标注为服务类，Spring 会自动注册为 Bean
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;  // 注入账户数据访问层
    private final UserRepository userRepository;  // 注入用户数据访问层

    // 构造方法，Spring 会自动注入依赖
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    // 创建账户
    @Override
    public Account createAccount(Long userId, double initialBalance) {
        // 通过用户ID查找对应的用户，若未找到则抛出异常
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("未找到ID为 " + userId + " 的用户"));

        // 创建新的账户对象，并设置相关属性
        Account account = new Account();
        account.setUser(user);  // 设置用户
        account.setBalance(initialBalance);  // 设置账户余额
        account.setEquity(initialBalance);  // 设置账户权益，默认为初始余额
        account.setMargin(0.0);  // 设置保证金，初始为0
        account.setFreeMargin(initialBalance);  // 设置自由保证金，初始为余额
        account.setWinRate(0.0);  // 设置赢利率，初始为0

        // 保存账户到数据库
        return accountRepository.save(account);
    }

    // 根据用户ID获取账户信息
    @Override
    public Account getAccountByUserId(Long userId) {
        // 查找用户对应的账户，若未找到则抛出异常
        return accountRepository.findByUser_Id(userId)
                .orElseThrow(() -> new IllegalArgumentException("未找到ID为 " + userId + " 的账户"));
    }

    // 更新账户信息
    @Override
    public Account updateAccount(Account account) {
        // 保存更新后的账户信息
        return accountRepository.save(account);
    }

    // 新增方法：获取所有账户信息
    @Override
    public List<Account> getAllAccounts() {
        // 获取所有账户信息，并返回
        return accountRepository.findAll();
    }
}
