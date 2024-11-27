package com.xtq_ymt.copy_trading_backend.config;

import com.xtq_ymt.copy_trading_backend.handler.TradeSyncHandler;
import com.xtq_ymt.copy_trading_backend.handler.MarketDataHandler;
import com.xtq_ymt.copy_trading_backend.handler.NotificationHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 配置类，用于定义基于 WebSocket 的通信处理。
 */
@Configuration
@EnableWebSocket // 启用纯 WebSocket 功能
public class WebSocketHandlerConfig implements WebSocketConfigurer {

    private final TradeSyncHandler tradeSyncHandler;
    private final NotificationHandler notificationHandler;
    private final MarketDataHandler marketDataHandler;

    /**
     * 构造函数，用于注入所需的 WebSocket Handler。
     *
     * @param tradeSyncHandler    交易同步处理器
     * @param notificationHandler 通知处理器
     * @param marketDataHandler   市场数据处理器
     */
    public WebSocketHandlerConfig(TradeSyncHandler tradeSyncHandler, NotificationHandler notificationHandler, MarketDataHandler marketDataHandler) {
        this.tradeSyncHandler = tradeSyncHandler;
        this.notificationHandler = notificationHandler;
        this.marketDataHandler = marketDataHandler;
    }

    /**
     * 注册 WebSocket 处理器。
     */
    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        // 注册交易同步 WebSocket 处理器，路径为 "/ws/trade-sync"
        registry.addHandler(tradeSyncHandler, "/ws/trade-sync")
                .setAllowedOrigins("*"); // 允许所有来源跨域请求

        // 注册通知 WebSocket 处理器，路径为 "/ws/notification"
        registry.addHandler(notificationHandler, "/ws/notification")
                .setAllowedOrigins("*");

        // 注册市场数据 WebSocket 处理器，路径为 "/ws/market-data"
        registry.addHandler(marketDataHandler, "/ws/market-data")
                .setAllowedOrigins("*");
    }
}
