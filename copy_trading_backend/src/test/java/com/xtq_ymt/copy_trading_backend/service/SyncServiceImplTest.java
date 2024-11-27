package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.dto.NotificationRequest;
import com.xtq_ymt.copy_trading_backend.handler.TradeSyncHandler;
import com.xtq_ymt.copy_trading_backend.model.Account;
import com.xtq_ymt.copy_trading_backend.model.Trade;
import com.xtq_ymt.copy_trading_backend.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class SyncServiceImplTest {

    private SyncServiceImpl syncService;
    private TradeSyncHandler tradeSyncHandler;
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        tradeSyncHandler = mock(TradeSyncHandler.class);
        notificationService = mock(NotificationService.class);
        syncService = new SyncServiceImpl(tradeSyncHandler, notificationService);
    }

    @Test
    void testNotifyTrade() {
        // 创建 Mock 的 User 和 Account 对象
        User mockUser = mock(User.class);
        when(mockUser.getId()).thenReturn(1L);
        when(mockUser.getUsername()).thenReturn("Trader123");

        Account mockAccount = mock(Account.class);
        when(mockAccount.getUser()).thenReturn(mockUser);

        // 创建测试用的 Trade 对象
        Trade trade = new Trade();
        trade.setId(1L);
        trade.setSymbol("BTC/USD");
        trade.setAccount(mockAccount); // 设置有效的 Account 对象

        // 调用被测方法
        syncService.notifyTrade(trade);

        // 验证广播行为
        try {
            verify(tradeSyncHandler, times(1)).broadcastMessage(trade);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 验证通知行为
        verify(notificationService, times(1)).sendNotification(any(NotificationRequest.class));
    }
}
