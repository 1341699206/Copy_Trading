package com.xtq_ymt.copy_trading_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DataSimulationScheduler {

    @Autowired
    private AccountSimulationService accountSimulationService;

    @Autowired
    private TradeSimulationService tradeSimulationService;

    @Autowired
    private CommentSimulationService commentSimulationService;

    @Scheduled(fixedRate = 3600000) // 每小时生成一次模拟数据
    public void runSimulations() {
        accountSimulationService.createSampleTradersAndFollowers();
        tradeSimulationService.generateTradesAndCopyTrading(); // 调用正确的方法生成交易和跟单数据
        commentSimulationService.generateRandomComments();
    }
}
