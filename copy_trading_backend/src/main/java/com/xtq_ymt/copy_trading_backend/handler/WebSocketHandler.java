package com.xtq_ymt.copy_trading_backend.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.xtq_ymt.copy_trading_backend.service.WebSocketManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    @SuppressWarnings("null")
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 处理来自客户端的消息
        String payload = message.getPayload();
        log.info("Received message: " + payload);
        
        // 业务逻辑处理
        String response = "Server response: " + payload;

        // 向客户端发送消息
        session.sendMessage(new TextMessage(response));
    }

    @SuppressWarnings("null")
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 从 session 获取用户 ID（假设用户 ID 是通过 URI 查询参数传递的）
        String userId = getUserIdFromSession(session);
        
        if (userId != null) {
            // 将 WebSocket 会话存储到 WebSocketManager 中
            WebSocketManager.addSession(userId, session);
            log.info("WebSocket connection established with client: " + session.getId() + ", UserID: " + userId);
        } else {
            log.warn("UserID not found in session for WebSocket connection: " + session.getId());
        }
    }

    @SuppressWarnings("null")
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 从 session 获取用户 ID
        String userId = getUserIdFromSession(session);
        
        if (userId != null) {
            // 移除 WebSocket 会话
            WebSocketManager.removeSession(userId);
            log.info("WebSocket connection closed with client: " + session.getId() + ", UserID: " + userId);
        } else {
            log.warn("UserID not found in session for closed WebSocket connection: " + session.getId());
        }
    }

    @SuppressWarnings("null")
    private String getUserIdFromSession(WebSocketSession session) {
        // 示例：从 WebSocketSession 的 URI 中获取 userId 参数
        // 例如： ws://localhost:8080/ws?userId=123
        String query = session.getUri().getQuery();
        if (query != null && query.contains("userId")) {
            for (String param : query.split("&")) {
                if (param.startsWith("userId=")) {
                    return param.split("=")[1];  // 提取 userId
                }
            }
        }
        return null; // 如果没有找到 userId 参数，则返回 null
    }
}
