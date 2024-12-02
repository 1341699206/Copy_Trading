package com.xtq_ymt.copy_trading_backend.task;
import com.xtq_ymt.copy_trading_backend.handler.AccountWebSocketHandler;
import com.xtq_ymt.copy_trading_backend.model.Account;
import com.xtq_ymt.copy_trading_backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountUpdateTask {

    private final AccountService accountService;
    private final AccountWebSocketHandler accountWebSocketHandler;

    // 存储上一次推送的账户信息，以便进行对比
    private List<Account> lastPushedAccounts;

    @Autowired
    public AccountUpdateTask(AccountService accountService, AccountWebSocketHandler accountWebSocketHandler) {
        this.accountService = accountService;
        this.accountWebSocketHandler = accountWebSocketHandler;
    }

    // 定时任务：每5分钟检查一次账户信息更新
    @Scheduled(fixedRate = 1000) // 1s执行一次
    public void checkAndPushAccountUpdates() {
        List<Account> currentAccounts = accountService.getAllAccounts(); // 查询所有账户

        // 如果当前账户数据与上次推送的数据不同，则推送给前端
        if (lastPushedAccounts == null || hasAccountDataChanged(lastPushedAccounts, currentAccounts)) {
            // 推送更新的账户信息
            for (Account account : currentAccounts) {
                accountWebSocketHandler.pushAccountUpdate(account);
            }
            lastPushedAccounts = currentAccounts; // 更新为当前数据
        }
    }

    // 比较上次推送的账户数据与当前数据是否有变化
    private boolean hasAccountDataChanged(List<Account> lastPushed, List<Account> current) {
        if (lastPushed.size() != current.size()) return true;

        for (int i = 0; i < lastPushed.size(); i++) {
            Account lastAccount = lastPushed.get(i);
            Account currentAccount = current.get(i);

            // 比较账户的各个字段
            if (lastAccount.getBalance() != currentAccount.getBalance()) {
                return true;
            }
            if (lastAccount.getEquity() != currentAccount.getEquity()) {
                return true;
            }
            if (lastAccount.getMargin() != currentAccount.getMargin()) {
                return true;
            }
            if (lastAccount.getFreeMargin() != currentAccount.getFreeMargin()) {
                return true;
            }
            if (lastAccount.getWinRate() != currentAccount.getWinRate()) {
                return true;
            }
        }

        return false;
    }

}
