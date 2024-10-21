package com.xtq_ymt.copy_trading_backend.service;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.socket.WebSocketSession;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import org.springframework.web.socket.TextMessage;

@Slf4j
public class WebSocketManager {

    // 用于保存所有 WebSocket 连接
    private static ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public static void addSession(String id, WebSocketSession session) {
        sessions.put(id, session);
    }

    public static WebSocketSession getSession(String id) {
        return sessions.get(id);
    }

    public static void removeSession(String id) {
        sessions.remove(id);
    }

    public static void sendMessageToUser(String id, String message) throws IOException {
        WebSocketSession session = sessions.get(id);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }
}
