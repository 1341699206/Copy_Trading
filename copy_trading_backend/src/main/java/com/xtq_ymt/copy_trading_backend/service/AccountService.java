package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Account;
import java.util.List;

public interface AccountService {

    Account createAccount(Long userId, double initialBalance);

    Account getAccountByUserId(Long userId);

    Account updateAccount(Account account);

    List<Account> getAllAccounts(); //此方法，获取所有账户
}
