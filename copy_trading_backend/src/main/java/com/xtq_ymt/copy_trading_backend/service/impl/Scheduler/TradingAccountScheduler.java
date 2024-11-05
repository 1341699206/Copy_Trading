package com.xtq_ymt.copy_trading_backend.service.impl.Scheduler;

import com.xtq_ymt.copy_trading_backend.model.TradingAccount;
import com.xtq_ymt.copy_trading_backend.repository.TradingAccountRepository;
import com.xtq_ymt.copy_trading_backend.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TradingAccountScheduler {

    @Autowired
    private TradingAccountRepository tradingAccountRepository;

    // 定时任务：每隔10秒更新所有账户的winRate
    @Scheduled(fixedRate = 10000) // 10秒执行一次
    public void updateWinRates() {
        List<TradingAccount> accounts = tradingAccountRepository.findAll();
        
        for (TradingAccount account : accounts) {
            calculateWinRate(account);
            tradingAccountRepository.save(account); // 保存更新后的 winRate
        }
    }

    /**
     * 计算并更新给定账户的winRate
     */
    private void calculateWinRate(TradingAccount account) {
        List<Trade> trades = account.getTrades();
        if (trades == null || trades.isEmpty()) {
            account.setWinRate(0.0);
            return;
        }

        long winCount = trades.stream()
            .filter(trade -> trade.getProfitPips() != null && trade.getProfitPips().compareTo(BigDecimal.ZERO) > 0)
            .count();

        double winRate = ((double) winCount / trades.size()) * 100;
        account.setWinRate(winRate);
    }
}
