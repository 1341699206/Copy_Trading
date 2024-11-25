package com.xtq_ymt.copy_trading_backend.handler;

import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xtq_ymt.copy_trading_backend.model.MarketData;
import com.xtq_ymt.copy_trading_backend.service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * WebSocket处理器，用于向连接的客户端广播市场数据。
 * 该类管理WebSocket会话，并向所有连接的客户端发送实时市场数据更新。
 */
@Component
public class MarketDataHandler extends TextWebSocketHandler {

    // 线程安全的列表，用于存储活动的WebSocket会话
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    
    // 用于获取市场数据的服务
    private final MarketDataService marketDataService;
    
    // 用于将对象序列化为JSON的工具
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * MarketDataHandler的构造函数。
     *
     * @param marketDataService 市场数据服务，用于提供市场数据。
     */
    @Autowired
    public MarketDataHandler(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    /**
     * 当新建WebSocket连接时调用。
     * 将新的会话添加到活动会话列表中。
     *
     * @param session 新建立的WebSocket会话。
     */
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        sessions.add(session);
    }

    /**
     * 当WebSocket连接关闭时调用。
     * 将关闭的会话从活动会话列表中移除。
     *
     * @param session 已关闭的WebSocket会话。
     * @param status  关闭状态。
     */
    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        sessions.remove(session);
    }

    /**
     * 广播最新的市场数据给所有连接的WebSocket客户端。
     * 从MarketDataService中获取市场数据，并将其作为JSON消息发送给所有活动会话。
     *
     * @throws IOException 如果发送消息时发生错误。
     */
    public void broadcastMarketData() throws IOException {
        // 从服务中获取所有市场数据
        List<MarketData> marketDataList = marketDataService.getAllMarketData();
        
        // 将市场数据列表转换为JSON字符串
        String payload = objectMapper.writeValueAsString(marketDataList);
        
        // 向所有连接的WebSocket客户端发送JSON消息
        for (WebSocketSession session : sessions) {
            session.sendMessage(new TextMessage(payload));
        }
    }
}
