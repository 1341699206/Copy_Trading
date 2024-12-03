package com.xtq_ymt.copy_trading_backend.task;

import com.xtq_ymt.copy_trading_backend.handler.AccountWebSocketHandler;
import com.xtq_ymt.copy_trading_backend.model.Account;
import com.xtq_ymt.copy_trading_backend.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class AccountUpdateTaskTest {

    @Mock
    private AccountService accountService; // 模拟AccountService

    @Mock
    private AccountWebSocketHandler accountWebSocketHandler; // 模拟AccountWebSocketHandler

    @InjectMocks
    private AccountUpdateTask accountUpdateTask; // 被测试的类

    private Account account1;
    private Account account2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        account1 = new Account();
        account1.setId(1L);
        account1.setBalance(1000.0);
        account1.setEquity(1200.0);
        
        account2 = new Account();
        account2.setId(2L);
        account2.setBalance(1500.0);
        account2.setEquity(1700.0);
    }

    @Test
    public void testCheckAndPushAccountUpdates() {
        List<Account> currentAccounts = Arrays.asList(account1, account2);
        
        when(accountService.getAllAccounts()).thenReturn(currentAccounts);

        // 执行定时任务
        accountUpdateTask.checkAndPushAccountUpdates();

        // 验证是否推送了账户更新
        verify(accountWebSocketHandler, times(1)).pushAccountUpdate(account1);
        verify(accountWebSocketHandler, times(1)).pushAccountUpdate(account2);
    }

    @Test
    public void testCheckAndPushAccountUpdatesNoChange() {
        // 模拟没有账户信息变动
        List<Account> currentAccounts = Arrays.asList(account1, account2);
        when(accountService.getAllAccounts()).thenReturn(currentAccounts);

        // 如果没有变化，则不推送
        accountUpdateTask.checkAndPushAccountUpdates();
        verify(accountWebSocketHandler, times(2)).pushAccountUpdate(any(Account.class));
    }
}
