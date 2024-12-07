package com.xtq_ymt.copy_trading_backend.config;

import com.xtq_ymt.copy_trading_backend.handler.AccountWebSocketHandler;
import com.xtq_ymt.copy_trading_backend.handler.TradeSyncHandler;
import com.xtq_ymt.copy_trading_backend.handler.MarketDataHandler;
import com.xtq_ymt.copy_trading_backend.handler.NotificationHandler;
import com.xtq_ymt.copy_trading_backend.handler.TradeWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 配置类，用于定义基于 WebSocket 的通信处理。
 */
@Configuration
@EnableWebSocket
public class WebSocketHandlerConfig implements WebSocketConfigurer {

    private final TradeSyncHandler tradeSyncHandler;
    private final NotificationHandler notificationHandler;
    private final MarketDataHandler marketDataHandler;
    private final AccountWebSocketHandler accountWebSocketHandler;
    // 新增：交易数据 WebSocket 处理器
    private final TradeWebSocketHandler tradeWebSocketHandler;

    public WebSocketHandlerConfig(TradeSyncHandler tradeSyncHandler, 
                                  NotificationHandler notificationHandler, 
                                  MarketDataHandler marketDataHandler,
                                  AccountWebSocketHandler accountWebSocketHandler,
                                  TradeWebSocketHandler tradeWebSocketHandler) {  // 添加新的处理器
        this.tradeSyncHandler = tradeSyncHandler;
        this.notificationHandler = notificationHandler;
        this.marketDataHandler = marketDataHandler;
        this.accountWebSocketHandler = accountWebSocketHandler;
        this.tradeWebSocketHandler = tradeWebSocketHandler;  // 初始化新的处理器
    }

    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        // 注册交易同步 WebSocket 处理器
        registry.addHandler(tradeSyncHandler, "/ws/trade-sync")
                .setAllowedOrigins("*");

        // 注册通知 WebSocket 处理器
        registry.addHandler(notificationHandler, "/ws/notification")
                .setAllowedOrigins("*");

        // 注册市场数据 WebSocket 处理器
        registry.addHandler(marketDataHandler, "/ws/market-data")
                .setAllowedOrigins("*");

        // 注册账户信息 WebSocket 处理器
        registry.addHandler(accountWebSocketHandler, "/ws/account")
                .setAllowedOrigins("*");

        // 新增：注册交易数据 WebSocket 处理器
        registry.addHandler(tradeWebSocketHandler, "/ws/trades/{accountId}")
                .setAllowedOrigins("*");
    }
}