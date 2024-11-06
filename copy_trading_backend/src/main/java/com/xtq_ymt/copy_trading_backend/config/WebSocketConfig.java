package com.xtq_ymt.copy_trading_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.xtq_ymt.copy_trading_backend.handler.TraderDashboardWebSocketHandler;
import com.xtq_ymt.copy_trading_backend.handler.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSocket
@SuppressWarnings("null")
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private TraderDashboardWebSocketHandler traderDashboardWebSocketHandler;

    @Autowired
    private WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(traderDashboardWebSocketHandler, "/trader-dashboard")
                .setAllowedOrigins("*");
        registry.addHandler(webSocketHandler, "/ws")
                .setAllowedOrigins("*");
    }
}