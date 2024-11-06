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
        log.info("开始更新交易账户指标...");

        List<TradingAccount> accounts = tradingAccountRepository.findAll();
        for (TradingAccount account : accounts) {
            try {
                updateAccountMetrics(account);

                // 保存更新后的账户信息
                tradingAccountRepository.save(account);
            } catch (Exception e) {
                log.error("更新账户 ID: " + account.getAccountId() + " 时发生错误", e);
            }
        }

        log.info("交易账户指标更新完成。");
    }

    private void updateAccountMetrics(TradingAccount account) {
        BigDecimal newEquity = calculateEquity(account);
        BigDecimal newRealisedPNL = calculateRealisedPNL(account);
        BigDecimal newMargin = calculateMargin(account);
        BigDecimal newFreeMargin = calculateFreeMargin(newEquity, newMargin);

        // 更新账户的各项指标
        account.setEquity(newEquity);
        account.setRealisedPNL(newRealisedPNL);
        account.setMargin(newMargin);
        account.setFreeMargin(newFreeMargin);
    }

    private BigDecimal calculateEquity(TradingAccount account) {
        BigDecimal balance = account.getBalance();
        BigDecimal unrealisedPNL = calculateUnrealisedPNL(account);
        return balance.add(unrealisedPNL);
    }

    private BigDecimal calculateRealisedPNL(TradingAccount account) {
        List<Trade> closedTrades = tradeRepository.findByTraderAccount_AccountIdAndIsOpenFalse(account.getAccountId());
        return closedTrades.stream()
                .map(Trade::getProfitUsd)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateUnrealisedPNL(TradingAccount account) {
        List<Trade> openTrades = tradeRepository.findByTraderAccount_AccountIdAndIsOpenTrue(account.getAccountId());
        return openTrades.stream()
                .map(this::calculateSingleTradeUnrealisedPNL)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateSingleTradeUnrealisedPNL(Trade trade) {
        MarketData marketData = marketDataRepository.findLatestByInstrument(trade.getCurrency());
        BigDecimal currentMarketPrice = marketData != null ? marketData.getCurrentPrice() : trade.getPriceOpen();
        return currentMarketPrice.subtract(trade.getPriceOpen()).multiply(trade.getStandardLots());
    }

    private BigDecimal calculateMargin(TradingAccount account) {
        List<Trade> openTrades = tradeRepository.findByTraderAccount_AccountIdAndIsOpenTrue(account.getAccountId());
        return openTrades.stream()
                .map(trade -> trade.getPriceOpen().multiply(trade.getStandardLots())
                        .divide(account.getLeverage(), RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateFreeMargin(BigDecimal equity, BigDecimal margin) {
        return equity.subtract(margin);
    }
}