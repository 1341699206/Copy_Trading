package com.xtq_ymt.copy_trading_backend.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xtq_ymt.copy_trading_backend.model.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

class TradeSyncHandlerTest {

    private TradeSyncHandler tradeSyncHandler;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        tradeSyncHandler = new TradeSyncHandler();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testBroadcastMessage() throws IOException {
        // Mock WebSocketSession
        WebSocketSession session = mock(WebSocketSession.class);
        tradeSyncHandler.afterConnectionEstablished(session);

        // Create a Trade object to broadcast
        Trade trade = new Trade();
        trade.setId(1L);
        trade.setSymbol("BTC/USD");

        // Call the method to be tested
        tradeSyncHandler.broadcastMessage(trade);

        // Verify that the message was sent
        String expectedPayload = objectMapper.writeValueAsString(trade);
        verify(session, times(1)).sendMessage(new TextMessage(expectedPayload));
    }
}
