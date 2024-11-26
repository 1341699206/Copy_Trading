package com.xtq_ymt.copy_trading_backend.config;

import org.springframework.lang.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 配置类，用于设置 WebSocket 消息代理。
 * 启用 STOMP 协议以支持消息广播和点对点通信。
 */
@Configuration
@EnableWebSocketMessageBroker // 启用基于消息代理的 WebSocket 功能
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册 STOMP 协议的端点，并指定支持 SockJS 回退选项。
     * 客户端通过连接此端点建立 WebSocket 通信。
     *
     * @param registry STOMP 端点注册器
     */
    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // 设置客户端连接的 WebSocket 端点为 "/ws"
                .setAllowedOrigins("http://localhost:8081") // 允许来自任何源的跨域请求（生产环境建议指定具体域名）
                .withSockJS(); // 启用 SockJS 支持，提供对不支持 WebSocket 的浏览器的兼容性
    }

    /**
     * 配置消息代理（Message Broker），定义消息的路由规则。
     *
     * @param registry 消息代理注册器
     */
    @Override
    public void configureMessageBroker(
            @NonNull org.springframework.messaging.simp.config.MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        // 配置简单消息代理，广播目的地以 "/topic" 开头的消息
        // 例如："/topic/trades" 用于推送交易消息

        registry.setApplicationDestinationPrefixes("/app");
        // 配置应用程序消息的前缀，客户端发送消息时需要添加 "/app" 前缀
        // 例如：客户端向服务器发送 "/app/trade" 消息
    }
    /*
    * 前端订阅消息的示例
    前端可以使用 SockJS 和 Stomp 客户端订阅 /topic/trades：
    
    javascript:
    
    const socket = new SockJS('http://localhost:8080/ws');
    const stompClient = Stomp.over(socket);
    
    stompClient.connect({}, () => {
        stompClient.subscribe('/topic/trades', (message) => {
            console.log('Received trade:', JSON.parse(message.body));
        });
    });
    */
    
}
