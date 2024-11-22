package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Account;
import com.xtq_ymt.copy_trading_backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(Long userId, double initialBalance) {
        Account account = new Account();
        account.setUserId(userId);
        account.setBalance(initialBalance);
        account.setEquity(initialBalance);
        account.setMargin(0.0);
        account.setFreeMargin(initialBalance);
        account.setWinRate(0.0);
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountByUserId(Long userId) {
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found for user ID: " + userId));
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }
}
