package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.model.Trade;
import com.xtq_ymt.copy_trading_backend.service.SyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Tag(name = "Real-Time Synchronization", description = "Handles real-time trade synchronization")
public class SyncController {

    private final SyncService syncService;

    @Autowired
    public SyncController(SyncService syncService) {
        this.syncService = syncService;
    }

    @MessageMapping("/trade")
    @SendTo("/topic/trades")
    @Operation(summary = "Real-time trade synchronization", description = "Broadcasts trade data to all followers")
    public Trade syncTrade(Trade trade) {
        syncService.notifyTrade(trade);
        return trade;
    }
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