package com.xtq_ymt.copy_trading_backend.handler;

import org.springframework.lang.NonNull;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xtq_ymt.copy_trading_backend.dto.NotificationRequest;
import com.xtq_ymt.copy_trading_backend.dto.NotificationResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * WebSocket处理器，用于管理通知的发送和广播。
 * 该类负责接收客户端的通知请求，并将响应广播给所有已连接的客户端。
 */
@Component
public class NotificationHandler extends TextWebSocketHandler {

    // 线程安全的列表，用于存储所有活动的WebSocket会话
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    
    // 用于将对象序列化和反序列化为JSON的工具
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 当WebSocket连接建立时调用。
     * 将新的WebSocket会话添加到活动会话列表中。
     *
     * @param session 新建立的WebSocket会话
     */
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        sessions.add(session);
    }

    /**
     * 处理来自客户端的文本消息。
     * 解析收到的通知请求，并根据请求生成通知响应并广播。
     *
     * @param session 当前的WebSocket会话
     * @param message 收到的文本消息
     * @throws IOException 如果在消息处理或发送时发生错误
     */
    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws IOException {
        // 将收到的消息解析为NotificationRequest对象
        NotificationRequest notificationRequest = objectMapper.readValue(message.getPayload(), NotificationRequest.class);
        // 调用发送通知的方法
        sendNotification(notificationRequest);
    }

    /**
     * 当WebSocket连接关闭时调用。
     * 将关闭的WebSocket会话从活动会话列表中移除。
     *
     * @param session 已关闭的WebSocket会话
     * @param status  WebSocket关闭的状态
     */
    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        sessions.remove(session);
    }

    /**
     * 发送单条通知给所有活动会话。
     * 根据收到的通知请求创建通知响应，并将其广播给所有已连接的客户端。
     *
     * @param notificationRequest 通知请求对象
     * @throws IOException 如果在消息发送过程中发生错误
     */
    private void sendNotification(NotificationRequest notificationRequest) throws IOException {
        // 创建通知响应对象
        NotificationResponse response = new NotificationResponse(
                notificationRequest.getUserId(),
                notificationRequest.getType(),
                notificationRequest.getMessage(),
                "success");
        // 将响应对象转换为JSON文本消息
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(response));
        // 广播消息给所有连接的WebSocket客户端
        for (WebSocketSession session : sessions) {
            session.sendMessage(textMessage);
        }
    }

    /**
     * 广播通知给所有活动会话。
     * 直接接收一条文本消息并将其发送给所有连接的客户端。
     *
     * @param message 要广播的文本消息
     */
    public void broadcastNotification(TextMessage message) {
        // 遍历所有活动会话，发送广播消息
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                System.err.println("Failed to send message to session: " + e.getMessage());
            }
        }
    }
}
