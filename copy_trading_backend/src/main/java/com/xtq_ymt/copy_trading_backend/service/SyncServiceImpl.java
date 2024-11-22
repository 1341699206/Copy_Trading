package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SyncServiceImpl implements SyncService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public SyncServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void notifyTrade(Trade trade) {
        messagingTemplate.convertAndSend("/topic/trades", trade);
    }
}
