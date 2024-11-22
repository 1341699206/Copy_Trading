package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.dto.NotificationRequest;

public interface NotificationService {
    void sendNotification(NotificationRequest notificationRequest);
}
