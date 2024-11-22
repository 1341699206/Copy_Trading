package com.xtq_ymt.copy_trading_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xtq_ymt.copy_trading_backend.dto.NotificationRequest;
import com.xtq_ymt.copy_trading_backend.handler.NotificationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationHandler notificationHandler;

    @Autowired
    public NotificationServiceImpl(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }

    @Override
    public void sendNotification(NotificationRequest notificationRequest) {
        try {
            // Convert the notificationRequest to a JSON message and broadcast it
            String notificationJson = new ObjectMapper().writeValueAsString(notificationRequest);
            notificationHandler.broadcastNotification(new TextMessage(notificationJson));
        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Failed to send notification: " + e.getMessage());
        }
    }
}
