package com.xtq_ymt.copy_trading_backend.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xtq_ymt.copy_trading_backend.dto.SyncTradeRequest;
import com.xtq_ymt.copy_trading_backend.dto.SyncTradeResponse;
import com.xtq_ymt.copy_trading_backend.service.NotificationService;
import com.xtq_ymt.copy_trading_backend.dto.NotificationRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TradeSyncHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NotificationService notificationService;

    // 构造器注入 NotificationService
    public TradeSyncHandler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws IOException {
        // 将接收到的消息解析为 SyncTradeRequest
        SyncTradeRequest tradeRequest = objectMapper.readValue(message.getPayload(), SyncTradeRequest.class);

        // 广播交易数据
        broadcastTrade(tradeRequest);

        // 发送通知
        sendNotification(tradeRequest);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        sessions.remove(session);
    }

    // 广播交易数据给所有已连接的 WebSocket 会话
    private void broadcastTrade(SyncTradeRequest tradeRequest) throws IOException {
        SyncTradeResponse response = new SyncTradeResponse(tradeRequest);
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(response));
        for (WebSocketSession session : sessions) {
            session.sendMessage(textMessage);
        }
    }

    // 调用 NotificationService 发送通知
    private void sendNotification(SyncTradeRequest tradeRequest) {
        NotificationRequest notification = new NotificationRequest(
                tradeRequest.getTraderId(),
                "Trade Sync",
                String.format("Trade ID %d has been synced.", tradeRequest.getTradeId())
        );
        notificationService.sendNotification(notification);
    }
}
