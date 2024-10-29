package com.xtq_ymt.copy_trading_backend.service.impl;

import com.xtq_ymt.copy_trading_backend.service.ImportExternalAssetInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DataSimulationScheduler {

    @Autowired
    private AccountSimulationService accountSimulationService;

    @Autowired
    private ImportExternalAssetInformationService importExternalAssetInformationService;

    @Autowired
    private TradeSimulationService tradeSimulationService;

    @Autowired
    private CommentSimulationService commentSimulationService;

    @Scheduled(initialDelay = 3000, fixedRate = 10000) // 每小时生成一次模拟数据，启动后延迟30秒
    public void runSimulations() {
        importExternalAssetInformationService.fetchAndStoreMarketData(); // 先生成市场数据
        accountSimulationService.createSampleTradersAndFollowers();
        tradeSimulationService.generateTradesAndCopyTrading(); // 调用正确的方法生成交易和跟单数据
        commentSimulationService.generateRandomComments();
    }
}