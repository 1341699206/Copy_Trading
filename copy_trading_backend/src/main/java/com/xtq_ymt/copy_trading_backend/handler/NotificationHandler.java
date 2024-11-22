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

@Component
public class NotificationHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws IOException {
        NotificationRequest notificationRequest = objectMapper.readValue(message.getPayload(), NotificationRequest.class);
        sendNotification(notificationRequest);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        sessions.remove(session);
    }

    private void sendNotification(NotificationRequest notificationRequest) throws IOException {
        NotificationResponse response = new NotificationResponse(
                notificationRequest.getUserId(),
                notificationRequest.getType(),
                notificationRequest.getMessage(),
                "success");
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(response));
        for (WebSocketSession session : sessions) {
            session.sendMessage(textMessage);
        }
    }
    
    public void broadcastNotification(TextMessage message) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                System.err.println("Failed to send message to session: " + e.getMessage());
            }
        }
    }
}
