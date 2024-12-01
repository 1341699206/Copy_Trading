package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Account;
import com.xtq_ymt.copy_trading_backend.model.User;
import com.xtq_ymt.copy_trading_backend.repository.AccountRepository;
import com.xtq_ymt.copy_trading_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service  // 标记该类为Spring的服务层（业务逻辑层）组件，Spring会自动扫描并注册为Bean
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;  // 用于操作账户数据的仓库
    private final UserRepository userRepository;  // 用于操作用户数据的仓库

    // 构造器注入AccountRepository和UserRepository，确保依赖注入
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    /**
     * 创建一个新的交易账户
     * @param userId 用户ID
     * @param initialBalance 初始余额
     * @return 返回创建的账户对象
     */
    @Override
    public Account createAccount(Long userId, double initialBalance) {
        // 根据用户ID查找用户，如果未找到则抛出异常
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("未找到ID为 " + userId + " 的用户"));
        
        // 创建一个新的账户对象
        Account account = new Account();
        account.setUser(user);  // 设置账户所属的用户
        account.setBalance(initialBalance);  // 设置账户的初始余额
        account.setEquity(initialBalance);  // 设置账户的初始权益
        account.setMargin(0.0);  // 设置账户的初始保证金为0
        account.setFreeMargin(initialBalance);  // 设置账户的初始自由保证金等于初始余额
        account.setWinRate(0.0);  // 设置账户的初始胜率为0
        return accountRepository.save(account);  // 保存并返回创建的账户
    }

    /**
     * 根据用户ID获取账户信息
     * @param userId 用户ID
     * @return 返回该用户的账户信息
     */
    @Override
    public Account getAccountByUserId(Long userId) {
        // 根据用户ID查找账户，如果未找到则抛出异常
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("未找到ID为 " + userId + " 的账户"));
    }

    /**
     * 更新账户信息
     * @param account 需要更新的账户对象
     * @return 返回更新后的账户对象
     */
    @Override
    public Account updateAccount(Account account) {
        // 保存并返回更新后的账户对象
        return accountRepository.save(account);
    }
}
