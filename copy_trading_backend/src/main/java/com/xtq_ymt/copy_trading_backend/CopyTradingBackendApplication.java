package com.xtq_ymt.copy_trading_backend;

import com.xtq_ymt.copy_trading_backend.service.ImportExternalAssetInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 启用定时任务
public class CopyTradingBackendApplication implements CommandLineRunner {

    @Autowired
    private ImportExternalAssetInformationService importExternalAssetInformationService;

    public static void main(String[] args) {
        SpringApplication.run(CopyTradingBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 启动时生成市场数据
        importExternalAssetInformationService.fetchAndStoreMarketData();
        System.out.println("Market data generated on startup.");
    }
}
