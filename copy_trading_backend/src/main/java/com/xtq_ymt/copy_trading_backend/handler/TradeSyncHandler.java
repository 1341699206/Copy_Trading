package com.xtq_ymt.copy_trading_backend.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xtq_ymt.copy_trading_backend.dto.SyncTradeRequest;
import com.xtq_ymt.copy_trading_backend.dto.SyncTradeResponse;
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

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws IOException {
        SyncTradeRequest tradeRequest = objectMapper.readValue(message.getPayload(), SyncTradeRequest.class);
        broadcastTrade(tradeRequest);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        sessions.remove(session);
    }

    private void broadcastTrade(SyncTradeRequest tradeRequest) throws IOException {
        SyncTradeResponse response = new SyncTradeResponse(tradeRequest);
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(response));
        for (WebSocketSession session : sessions) {
            session.sendMessage(textMessage);
        }
    }
}
