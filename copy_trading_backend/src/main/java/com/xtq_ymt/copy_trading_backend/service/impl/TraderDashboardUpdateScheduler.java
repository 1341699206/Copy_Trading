package com.xtq_ymt.copy_trading_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.xtq_ymt.copy_trading_backend.repository.TradingAccountRepository;
import com.xtq_ymt.copy_trading_backend.model.TradingAccount;
import com.xtq_ymt.copy_trading_backend.repository.TradeRepository;
import com.xtq_ymt.copy_trading_backend.model.Trade;
import com.xtq_ymt.copy_trading_backend.model.MarketData;
import com.xtq_ymt.copy_trading_backend.repository.MarketDataRepository;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Service
public class TraderDashboardUpdateScheduler {

    @Autowired
    private TradingAccountRepository tradingAccountRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private MarketDataRepository marketDataRepository;

    @Scheduled(fixedRate = 10000) // 每10秒执行一次
    public void updateAccountMetrics() {
        log.info("Starting scheduled update for trading account metrics...");

        List<TradingAccount> accounts = tradingAccountRepository.findAll();
        for (TradingAccount account : accounts) {
            try {
                BigDecimal newEquity = calculateEquity(account);
                BigDecimal newBalance = calculateBalance(account);
                BigDecimal newRealisedPNL = calculateRealisedPNL(account);
                BigDecimal newMargin = calculateMargin(account);
                BigDecimal newFreeMargin = calculateFreeMargin(account);

                // 更新数据
                account.setEquity(newEquity);
                account.setBalance(newBalance);
                account.setRealisedPNL(newRealisedPNL);
                account.setMargin(newMargin);
                account.setFreeMargin(newFreeMargin);

                // 保存到数据库
                tradingAccountRepository.save(account);
            } catch (Exception e) {
                log.error("Error updating metrics for account: " + account.getAccountId(), e);
            }
        }

        log.info("Completed scheduled update for trading account metrics.");
    }

    private BigDecimal calculateEquity(TradingAccount account) {
        // 根据账户的持仓、市场价格、现金等计算 Equity
        BigDecimal balance = account.getBalance();
        BigDecimal unrealisedPNL = calculateUnrealisedPNL(account);
        return balance.add(unrealisedPNL);
    }

    private BigDecimal calculateBalance(TradingAccount account) {
        // 可能涉及到从最近的交易记录中获取结果
        return account.getBalance(); // 简化计算，实际计算可能更复杂
    }

    private BigDecimal calculateRealisedPNL(TradingAccount account) {
        // 计算已实现盈亏，累加所有已关闭交易的盈亏
        List<Trade> closedTrades = tradeRepository.findByTraderAccount_AccountIdAndIsOpenFalse(account.getAccountId());
        return closedTrades.stream()
                .map(Trade::getProfitLoss)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateUnrealisedPNL(TradingAccount account) {
        // 计算未实现盈亏，基于未平仓交易和当前市场价格
        List<Trade> openTrades = tradeRepository.findByTraderAccount_AccountIdAndIsOpenTrue(account.getAccountId());
        return openTrades.stream()
                .map(trade -> {
                    MarketData marketData = marketDataRepository.findLatestByInstrument(trade.getInstrument());
                    BigDecimal currentMarketPrice = marketData != null ? marketData.getCurrentPrice() : trade.getOpenPrice();
                    return currentMarketPrice.subtract(trade.getOpenPrice()).multiply(trade.getVolume());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateMargin(TradingAccount account) {
        // 根据持仓价值计算所需的保证金
        List<Trade> openTrades = tradeRepository.findByTraderAccount_AccountIdAndIsOpenTrue(account.getAccountId());
        return openTrades.stream()
                .map(trade -> trade.getOpenPrice().multiply(trade.getVolume()).divide(trade.getLeverage(), RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateFreeMargin(TradingAccount account) {
        // 自由保证金 = 净值 - 已用保证金
        return account.getEquity().subtract(account.getMargin());
    }
}
