package com.xtq_ymt.copy_trading_backend.handler;

import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.xtq_ymt.copy_trading_backend.dto.TraderDashboardDTO;
import com.xtq_ymt.copy_trading_backend.service.TraderService;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class TraderDashboardWebSocketHandler extends TextWebSocketHandler {

    private final TraderService traderService;
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public TraderDashboardWebSocketHandler(TraderService traderService) {
        this.traderService = traderService;
    }

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("Received message: {}", payload);
        
        String[] parts = payload.split(",");
        if (parts.length == 2) {
            String traderId = parts[0].split(":")[1].trim();
            String accountId = parts[1].split(":")[1].trim();
            session.getAttributes().put("traderId", traderId);
            session.getAttributes().put("accountId", accountId);

            // 立即推送所选账户的更新数据
            pushUpdateForSession(session, traderId, accountId);
        }
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        sessions.put(sessionId, session);
        log.info("WebSocket connection established with client: " + sessionId);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        String sessionId = session.getId();
        sessions.remove(sessionId);
        log.info("WebSocket connection closed with client: " + sessionId);
    }

    @Scheduled(fixedRate = 10000)
    public void pushUpdates() {
        for (WebSocketSession session : sessions.values()) {
            if (session.isOpen()) {
                try {
                    String traderId = (String) session.getAttributes().get("traderId");
                    String accountId = (String) session.getAttributes().get("accountId");
                    if (traderId != null && accountId != null) {
                        pushUpdateForSession(session, traderId, accountId);
                    }
                } catch (Exception e) {
                    log.error("Error while pushing updates: ", e);
                }
            }
        }
    }

    private void pushUpdateForSession(WebSocketSession session, String traderId, String accountId) throws Exception {
        TraderDashboardDTO dashboardDTO = traderService.getTraderDashboardInfo(Long.parseLong(traderId), Long.parseLong(accountId));
        session.sendMessage(new TextMessage(dashboardDTO.toString()));
    }
}
