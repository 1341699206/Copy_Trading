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
 * <p>
 * 此类实现了 WebSocketConfigurer 接口，用于注册 WebSocket 的处理器（Handler）和指定其对应的访问路径。
 * <p>
 * 启用了 @EnableWebSocket 注解，表示这是一个 WebSocket 配置类。
 */
@Configuration
@EnableWebSocket
public class WebSocketHandlerConfig implements WebSocketConfigurer {

    // TradeSyncHandler 用于处理交易同步的 WebSocket 请求
    private final TradeSyncHandler tradeSyncHandler;

    // NotificationHandler 用于处理通知的 WebSocket 请求
    private final NotificationHandler notificationHandler;

    // MarketDataHandler 用于更新市场数据的 WebSocket 请求
    private final MarketDataHandler marketDataHandler;

    /**
     * 构造函数，用于注入所需的 WebSocket Handler。
     *
     * @param tradeSyncHandler    交易同步处理器
     * @param notificationHandler 通知处理器
     */
    public WebSocketHandlerConfig(TradeSyncHandler tradeSyncHandler, NotificationHandler notificationHandler, MarketDataHandler marketDataHandler) {
        this.tradeSyncHandler = tradeSyncHandler;
        this.notificationHandler = notificationHandler;
        this.marketDataHandler = marketDataHandler;
    }

    /**
     * 注册 WebSocket 处理器（Handlers），并为其指定访问路径。
     *
     * @param registry 用于注册 WebSocket Handler 的注册器
     */
    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        // 注册用于处理交易同步的 WebSocket Handler，路径为 "/ws/trade-sync"
        registry.addHandler(tradeSyncHandler, "/ws/trade-sync")
                .setAllowedOrigins("*"); // 允许所有来源的跨域请求（生产环境建议指定具体域名）

        // 注册用于处理通知的 WebSocket Handler，路径为 "/ws/notification"
        registry.addHandler(notificationHandler, "/ws/notification")
                .setAllowedOrigins("*"); // 允许所有来源的跨域请求（生产环境建议指定具体域名）

        // 新增市场数据的 WebSocket 路径
        registry.addHandler(marketDataHandler, "/ws/market-data")
                .setAllowedOrigins("*");
    }
}
