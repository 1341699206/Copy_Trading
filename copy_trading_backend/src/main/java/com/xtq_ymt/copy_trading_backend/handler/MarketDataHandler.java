package com.xtq_ymt.copy_trading_backend.handler;

import org.springframework.lang.NonNull;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xtq_ymt.copy_trading_backend.model.MarketData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * WebSocket处理器，用于广播市场数据。
 */
@Component
public class MarketDataHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper;

    @Autowired
    public MarketDataHandler() {
        // 初始化 ObjectMapper，并注册 JavaTimeModule 以支持 LocalDateTime
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session);
    }

    /**
     * 广播最新市场数据给所有连接的客户端。
     *
     * @param latestMarketDataList 最新的市场数据列表
     * @throws IOException 如果发送数据失败
     */
    public void broadcastLatestMarketData(List<MarketData> latestMarketDataList) throws IOException {
        // 序列化市场数据为 JSON
        String payload = objectMapper.writeValueAsString(latestMarketDataList);

        // 逐一发送给所有会话
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(payload));
            } catch (IOException e) {
                System.err.println("Failed to send message to session: " + e.getMessage());
            }
        }
    }
}
