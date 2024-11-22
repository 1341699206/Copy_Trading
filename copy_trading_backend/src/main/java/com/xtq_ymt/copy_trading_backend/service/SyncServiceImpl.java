package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.dto.NotificationRequest;
import com.xtq_ymt.copy_trading_backend.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SyncServiceImpl implements SyncService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    @Autowired
    public SyncServiceImpl(SimpMessagingTemplate messagingTemplate, NotificationService notificationService) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }

    @Override
    public void notifyTrade(Trade trade) {
        // WebSocket 广播交易事件
        messagingTemplate.convertAndSend("/topic/trades", trade);

        // 发送通知给交易员
        NotificationRequest traderNotification = new NotificationRequest(
                trade.getAccount().getUser().getId(), // 获取交易员的 ID
                "Trade Update", // 通知标题
                String.format("Trade ID %d has been executed.", trade.getId()) // 通知内容
        );
        notificationService.sendNotification(traderNotification);

        // 如果交易与策略关联，发送通知给跟随者
        if (trade.getStrategy() != null && trade.getStrategy().getTrader() != null) {
            NotificationRequest followerNotification = new NotificationRequest(
                    trade.getStrategy().getTrader().getId(), // 获取跟随者的 ID
                    "Trade Update", // 通知标题
                    String.format("Trade ID %d by Trader %s has been executed.",
                                  trade.getId(), trade.getAccount().getUser().getUsername()) // 通知内容
            );
            notificationService.sendNotification(followerNotification);
        }
    }
}
