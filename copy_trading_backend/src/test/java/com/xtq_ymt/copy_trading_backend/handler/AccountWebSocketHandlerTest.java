package com.xtq_ymt.copy_trading_backend.handler;

import com.xtq_ymt.copy_trading_backend.model.Account;
import com.xtq_ymt.copy_trading_backend.model.User;
import com.xtq_ymt.copy_trading_backend.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;  // 引入ObjectMapper
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.Map;
import java.util.Set;

public class AccountWebSocketHandlerTest {

    @Mock
    private AccountService accountService; // 模拟 AccountService

    @Mock
    private WebSocketSession webSocketSession; // 模拟 WebSocket 会话

    @Mock
    private ObjectMapper objectMapper; // 模拟 ObjectMapper

    @InjectMocks
    private AccountWebSocketHandler accountWebSocketHandler; // 被测试的类

    private Account account;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // 初始化 mock 对象
        account = new Account(); // 初始化测试账户
        account.setId(1L);
        account.setBalance(1000.0);
        account.setEquity(1200.0);
        account.setMargin(100.0);
        account.setFreeMargin(1100.0);
        account.setWinRate(75.0);

        // 使用 User 的构造函数来初始化 User 对象
        User user = new User(1L, "testUser", User.Role.TRADER); // 创建一个 User 对象并设置 ID
        account.setUser(user); // 将 User 对象设置到 Account 对象中
    }

    @Test
    public void testPushAccountUpdate() throws Exception {
        // 模拟WebSocket会话
        when(webSocketSession.isOpen()).thenReturn(true); // 假设会话是打开的

        // 模拟会话建立，将会话添加到 accountSessions
        URI uri = new URI("ws://localhost:8081/ws/account?userId=1");
        when(webSocketSession.getUri()).thenReturn(uri);

        // 执行连接建立的逻辑
        accountWebSocketHandler.afterConnectionEstablished(webSocketSession);

        // 输出 accountSessions 以调试
        Map<Long, Set<WebSocketSession>> sessions = accountWebSocketHandler.getAccountSessions();
        assertTrue(sessions.containsKey(1L), "accountSessions should contain user ID 1");

        // 模拟 ObjectMapper.writeValueAsString(account) 返回期望的 JSON 字符串
        when(objectMapper.writeValueAsString(account)).thenReturn("{\"id\":1,\"balance\":1000.0}");

        // 执行账户更新
        accountWebSocketHandler.pushAccountUpdate(account);

        // 验证sendMessage方法被调用
        verify(webSocketSession, times(1)).sendMessage(any(TextMessage.class)); // 确保sendMessage被调用一次
    }

    @Test
    public void testAfterConnectionEstablished() throws Exception {
        // 模拟 WebSocketSession 的 URI，使用 userId 作为查询参数
        when(webSocketSession.getUri()).thenReturn(new URI("ws://localhost:8081/ws/account?userId=1"));

        // 调用 afterConnectionEstablished 进行连接建立
        accountWebSocketHandler.afterConnectionEstablished(webSocketSession);

        // 输出 accountSessions 以调试
        System.out.println(accountWebSocketHandler.getAccountSessions());

        // 验证 accountSessions 中是否包含 userId 为 1 的账户
        Map<Long, Set<WebSocketSession>> sessions = accountWebSocketHandler.getAccountSessions();
        assertTrue(sessions.containsKey(1L), "accountSessions should contain user ID 1");  // 确保 accountSessions 中包含 userId 为 1
        assertTrue(sessions.get(1L).contains(webSocketSession), "session should be added for user ID 1"); // 确保该会话被正确添加
    }

    @Test
    public void testAfterConnectionClosed() throws Exception {
        // 模拟 WebSocket 会话的关闭，传入一个有效的 CloseStatus
        accountWebSocketHandler.afterConnectionClosed(webSocketSession, CloseStatus.NORMAL);

        // 使用 getter 方法访问 accountSessions
        Map<Long, Set<WebSocketSession>> sessions = accountWebSocketHandler.getAccountSessions();
        assertNull(sessions.get(1L), "Sessions for userId 1 should be null after connection closed"); // 确保连接关闭后会话已移除
    }


}
