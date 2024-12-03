package com.xtq_ymt.copy_trading_backend.handler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper; // 导入 Jackson ObjectMapper
import com.xtq_ymt.copy_trading_backend.model.Account;
import com.xtq_ymt.copy_trading_backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@Component
public class AccountWebSocketHandler extends TextWebSocketHandler {

    private final AccountService accountService; // 注入AccountService来获取或更新账户信息
    private final Map<Long, Set<WebSocketSession>> accountSessions = new HashMap<>(); // 用于管理账户与WebSocket会话的映射关系（支持多个会话）

    // 创建 ObjectMapper 用于处理 JSON 转换
    private final ObjectMapper objectMapper;

    // 添加一个 getter 方法用于获取 accountSessions
    public Map<Long, Set<WebSocketSession>> getAccountSessions() {
        return accountSessions;
    }

    @Autowired
    public AccountWebSocketHandler(AccountService accountService, ObjectMapper objectMapper) {
        this.accountService = accountService; // 注入accountService
        this.objectMapper = objectMapper; // 注入ObjectMapper
    }

    // WebSocket连接建立后，处理会话与账户的映射
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        Optional.ofNullable(session.getUri())
                .map(uri -> uri.getQuery())
                .ifPresent(query -> {
                    String userIdStr = getQueryParamValue(query, "userId"); // 提取用户ID参数
                    if (userIdStr != null && !userIdStr.isEmpty()) {
                        try {
                            Long userId = Long.parseLong(userIdStr); // 解析用户ID
                            accountSessions.computeIfAbsent(userId, k -> new HashSet<>()).add(session); // 将用户ID与WebSocket会话关联
                        } catch (NumberFormatException e) {
                            System.err.println("无效的用户ID: " + userIdStr);
                        }
                    } else {
                        System.err.println("WebSocket连接没有提供有效的用户ID");
                    }
                });
    }

    // 处理收到的消息
    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
        // 假设消息内容是用户ID和新的账户余额
        String payload = message.getPayload();
        Long userId = parseUserIdFromMessage(payload); // 解析用户ID
        double newBalance = parseNewBalanceFromMessage(payload); // 解析新余额

        // 获取账户并更新
        Account account = accountService.getAccountByUserId(userId); // 根据用户ID查询账户
        account.setBalance(newBalance); // 设置新的账户余额
        accountService.updateAccount(account); // 更新账户

        // 更新账户后，推送账户信息
        pushAccountUpdate(account);
    }

    // 关闭连接时，从会话映射中移除WebSocket会话
    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull org.springframework.web.socket.CloseStatus status) throws Exception {
        // 获取会话对应的 userId
        Optional.ofNullable(session.getUri())
                .map(uri -> uri.getQuery())
                .ifPresent(query -> {
                    String userIdStr = getQueryParamValue(query, "userId"); // 提取用户ID参数
                    if (userIdStr != null && !userIdStr.isEmpty()) {
                        try {
                            Long userId = Long.parseLong(userIdStr); // 解析用户ID
                            Set<WebSocketSession> sessions = accountSessions.get(userId); // 获取对应的会话集合
                            if (sessions != null && sessions.remove(session)) {
                                if (sessions.isEmpty()) {
                                    accountSessions.remove(userId); // 如果没有剩余会话，则移除该用户的映射
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("无效的用户ID: " + userIdStr);
                        }
                    }
                });
    }


    // 推送账户更新信息给指定账户的客户端
    public void pushAccountUpdate(Account updatedAccount) {
        Long userId = updatedAccount.getUser().getId(); // 获取更新的用户ID
        Set<WebSocketSession> sessions = accountSessions.get(userId); // 查找与该用户ID关联的会话

        if (sessions != null) {
            for (WebSocketSession session : sessions) {
                if (session != null && session.isOpen()) {
                    try {
                        // 将账户信息转换为JSON格式
                        String accountJson = objectMapper.writeValueAsString(updatedAccount); // 账户信息转为JSON
                        session.sendMessage(new TextMessage(accountJson)); // 发送消息到客户端
                    } catch (IOException e) {
                        System.err.println("发送消息失败: " + e.getMessage());
                    }
                }
            }
        }
    }

    // 辅助方法：从消息中解析用户ID
    private Long parseUserIdFromMessage(String message) {
        // 这里可以用正则表达式或其他方法从消息内容中提取userId
        // 假设消息内容为 JSON 格式 {"userId": 1, "balance": 1000}
        try {
            Map<String, Object> map = objectMapper.readValue(message, new TypeReference<Map<String, Object>>(){});
            return map.containsKey("userId") ? Long.valueOf(map.get("userId").toString()) : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 辅助方法：从消息中解析新账户余额
    private double parseNewBalanceFromMessage(String message) {
        try {
            Map<String, Object> map = objectMapper.readValue(message, new TypeReference<Map<String, Object>>(){});
            return map.containsKey("balance") ? Double.parseDouble(map.get("balance").toString()) : 0.0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    // 辅助方法：从查询参数中获取特定的值
    private String getQueryParamValue(String query, String param) {
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue[0].equals(param)) {
                return keyValue[1];
            }
        }
        return null;
    }
}
