package com.xtq_ymt.copy_trading_backend.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xtq_ymt.copy_trading_backend.model.Trade;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TradeWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(TradeWebSocketHandler.class);
    private static final Pattern ACCOUNT_ID_PATTERN = Pattern.compile("/trades/(\\d+)");
    private static final int SEND_TIME_LIMIT = 1000;
    private static final int BUFFER_SIZE_LIMIT = 512 * 1024;

    private final Map<Long, Set<WebSocketSession>> accountSessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    public TradeWebSocketHandler() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        URI uri = session.getUri();
        if (uri == null) {
            logger.error("WebSocket URI is null");
            session.close(CloseStatus.BAD_DATA.withReason("Invalid URI"));
            return;
        }

        Long accountId = extractAccountId(uri.getPath());
        if (accountId != null) {
            WebSocketSession decoratedSession = new ConcurrentWebSocketSessionDecorator(
                session, SEND_TIME_LIMIT, BUFFER_SIZE_LIMIT);

            accountSessions.computeIfAbsent(accountId, k -> new CopyOnWriteArraySet<>())
                          .add(decoratedSession);

            logger.info("WebSocket connection established for account ID: {}", accountId);

            // Send the full list of trades to the connected session
            sendFullTradeList(accountId, decoratedSession);
        } else {
            logger.error("Failed to establish WebSocket connection: Invalid account ID");
            session.close(CloseStatus.BAD_DATA.withReason("Invalid account ID"));
        }
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        URI uri = session.getUri();
        if (uri != null) {
            Long accountId = extractAccountId(uri.getPath());
            if (accountId != null) {
                Set<WebSocketSession> sessions = accountSessions.get(accountId);
                if (sessions != null) {
                    sessions.remove(session);
                    if (sessions.isEmpty()) {
                        accountSessions.remove(accountId);
                    }
                }
                logger.info("WebSocket connection closed for account ID: {}", accountId);
            }
        }
    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) {
        logger.error("WebSocket transport error: ", exception);
        try {
            session.close(CloseStatus.SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error closing WebSocket session: ", e);
        }
    }

    public void broadcastNewTradeUpdate(Long accountId, Trade trade) {
        Set<WebSocketSession> sessions = accountSessions.get(accountId);

        if (sessions != null && !sessions.isEmpty()) {
            try {
                trade.getAccount().getId();
                if (trade.getStrategy() != null) {
                    trade.getStrategy().getId();
                }

                String message = objectMapper.writeValueAsString(trade);
                TextMessage textMessage = new TextMessage(message);

                for (WebSocketSession session : sessions) {
                    try {
                        if (session.isOpen()) {
                            session.sendMessage(textMessage);
                            logger.info("Message sent to session: {}", session.getId());
                        } else {
                            logger.warn("Skipped closed session: {}", session.getId());
                        }
                    } catch (Exception e) {
                        logger.error("Error sending message to session: {}, removing session.", session.getId(), e);
                        sessions.remove(session);
                    }
                }
            } catch (Exception e) {
                logger.error("Error broadcasting trade update for accountId: {}", accountId, e);
            }
        } else {
            logger.warn("No active WebSocket sessions found for accountId: {}", accountId);
        }
    }

    public void broadcastFullTradeList(Long accountId, List<Trade> trades) {
        Set<WebSocketSession> sessions = accountSessions.get(accountId);

        if (sessions != null && !sessions.isEmpty()) {
            try {
                for (Trade trade : trades) {
                    trade.getAccount().getId();
                    if (trade.getStrategy() != null) {
                        trade.getStrategy().getId();
                    }
                }

                String message = objectMapper.writeValueAsString(trades);
                TextMessage textMessage = new TextMessage(message);

                for (WebSocketSession session : sessions) {
                    try {
                        if (session.isOpen()) {
                            session.sendMessage(textMessage);
                            logger.info("Full trade list sent to session: {}", session.getId());
                        } else {
                            logger.warn("Skipped closed session: {}", session.getId());
                        }
                    } catch (Exception e) {
                        logger.error("Error sending full trade list to session: {}, removing session.", session.getId(), e);
                        sessions.remove(session);
                    }
                }
            } catch (Exception e) {
                logger.error("Error broadcasting full trade list for accountId: {}", accountId, e);
            }
        } else {
            logger.warn("No active WebSocket sessions found for accountId: {}", accountId);
        }
    }

    private void sendFullTradeList(Long accountId, WebSocketSession session) {
        try {
            List<Trade> trades = fetchTradesForAccount(accountId);
            if (trades != null) {
                for (Trade trade : trades) {
                    trade.getAccount().getId();
                    if (trade.getStrategy() != null) {
                        trade.getStrategy().getId();
                    }
                }

                String message = objectMapper.writeValueAsString(trades);
                session.sendMessage(new TextMessage(message));
                logger.info("Sent full trade list to session: {}", session.getId());
            }
        } catch (Exception e) {
            logger.error("Error sending full trade list to session: {}", session.getId(), e);
        }
    }

    private Long extractAccountId(String uri) {
        if (uri != null) {
            Matcher matcher = ACCOUNT_ID_PATTERN.matcher(uri);
            if (matcher.find()) {
                try {
                    return Long.parseLong(matcher.group(1));
                } catch (NumberFormatException e) {
                    logger.error("Failed to parse account ID from URI: {}", uri);
                }
            }
        }
        return null;
    }

    private List<Trade> fetchTradesForAccount(Long accountId) {
        // Placeholder for fetching trades from the database or service
        // Replace with actual logic to retrieve trades for the given account ID
        return List.of();
    }
}
