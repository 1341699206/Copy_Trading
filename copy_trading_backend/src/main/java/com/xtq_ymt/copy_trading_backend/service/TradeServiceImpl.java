package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Account;
import com.xtq_ymt.copy_trading_backend.model.Strategy;
import com.xtq_ymt.copy_trading_backend.model.Trade;
import com.xtq_ymt.copy_trading_backend.model.FollowerStats;
import com.xtq_ymt.copy_trading_backend.model.TraderStats;
import com.xtq_ymt.copy_trading_backend.repository.AccountRepository;
import com.xtq_ymt.copy_trading_backend.repository.StrategyRepository;
import com.xtq_ymt.copy_trading_backend.repository.TradeRepository;
import com.xtq_ymt.copy_trading_backend.repository.FollowerStatsRepository;
import com.xtq_ymt.copy_trading_backend.repository.TraderStatsRepository;
import com.xtq_ymt.copy_trading_backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final AccountRepository accountRepository;
    private final StrategyRepository strategyRepository;
    private final TraderStatsRepository traderStatsRepository;  // 注入 TraderStatsRepository
    private final FollowerStatsRepository followerStatsRepository;  // 注入 FollowerStatsRepository

    @Autowired
    public TradeServiceImpl(TradeRepository tradeRepository, 
                            AccountRepository accountRepository, 
                            StrategyRepository strategyRepository,
                            TraderStatsRepository traderStatsRepository,
                            FollowerStatsRepository followerStatsRepository) {
        this.tradeRepository = tradeRepository;
        this.accountRepository = accountRepository;
        this.strategyRepository = strategyRepository;
        this.traderStatsRepository = traderStatsRepository;
        this.followerStatsRepository = followerStatsRepository;
    }

    @Override
    public Trade openTrade(Long accountId, Long strategyId, String symbol, String type, double lotSize, double priceOpen) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accountId));

        Strategy strategy = null;
        if (strategyId != null) {
            strategy = strategyRepository.findById(strategyId)
                    .orElseThrow(() -> new IllegalArgumentException("Strategy not found with ID: " + strategyId));
        }

        Trade trade = new Trade();
        trade.setAccount(account);
        trade.setStrategy(strategy);
        trade.setSymbol(symbol);
        trade.setType(type);
        trade.setLotSize(lotSize);
        trade.setPriceOpen(priceOpen);
        trade.setClosed(false);
        trade = tradeRepository.save(trade);

        // 更新交易员统计数据
        updateTraderStats(account.getUser().getId(), trade);

        // 如果是跟单交易，更新跟随者的统计数据
        if (account.getUser().getRole() == User.Role.FOLLOWER) {
            updateFollowerStats(account.getUser().getId(), trade);
        }

        return trade;
    }

    @Override
    public Trade closeTrade(Long tradeId, double priceClose) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found"));
        if (trade.isClosed()) {
            throw new IllegalStateException("Trade is already closed");
        }
        trade.setPriceClose(priceClose);
        trade.setProfit((priceClose - trade.getPriceOpen()) * trade.getLotSize());
        trade.setClosed(true);
        trade.setDateClose(LocalDateTime.now());
        trade = tradeRepository.save(trade);

        // 更新交易员统计数据
        updateTraderStats(trade.getAccount().getUser().getId(), trade);

        // 如果是跟单交易，更新跟随者的统计数据
        if (trade.getAccount().getUser().getRole() == User.Role.FOLLOWER) {
            updateFollowerStats(trade.getAccount().getUser().getId(), trade);
        }

        return trade;
    }

    @Override
    public List<Trade> getOpenTradesByAccountId(Long accountId) {
        return tradeRepository.findByAccountIdAndIsClosedFalse(accountId);
    }

    @Override
    public List<Trade> getTradesByStrategyId(Long strategyId) {
        return tradeRepository.findByStrategyId(strategyId);
    }

    // 实现接口中的方法，更新交易员和跟随者的统计数据
    @Override
    public void updateTraderAndFollowerStats(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found"));

        // 更新交易员统计数据
        updateTraderStats(trade.getAccount().getUser().getId(), trade);

        // 如果是跟单交易，更新跟随者的统计数据
        if (trade.getAccount().getUser().getRole() == User.Role.FOLLOWER) {
            updateFollowerStats(trade.getAccount().getUser().getId(), trade);
        }
    }

    // 更新交易员统计数据
    public void updateTraderStats(Long traderId, Trade trade) {
        TraderStats stats = traderStatsRepository.findByTraderId(traderId);
        if (stats == null) {
            stats = new TraderStats();
            stats.setTraderId(traderId);
            stats.setTotalProfit(0);
            stats.setWinRate(0);
            stats.setMaxDrawdown(0);
            stats.setTotalTrades(0);
            stats.setWinningTrades(0);
        }

        stats.setTotalProfit(stats.getTotalProfit() + trade.getProfit());
        stats.setTotalTrades(stats.getTotalTrades() + 1);
        if (trade.getProfit() > 0) {
            stats.setWinningTrades(stats.getWinningTrades() + 1);
        }

        // 更新胜率
        stats.setWinRate((double) stats.getWinningTrades() / stats.getTotalTrades() * 100);

        // 最大回撤的更新
        if (trade.getProfit() < stats.getMaxDrawdown()) {
            stats.setMaxDrawdown(trade.getProfit());
        }

        traderStatsRepository.save(stats);
    }

    // 更新跟随者统计数据
    public void updateFollowerStats(Long followerId, Trade trade) {
        FollowerStats stats = followerStatsRepository.findByFollowerId(followerId);
        if (stats == null) {
            stats = new FollowerStats();
            stats.setFollowerId(followerId);
            stats.setTotalProfit(0);
            stats.setMaxDrawdown(0);
            stats.setTotalFollowedTraders(0);
            stats.setTotalTrades(0);
        }

        stats.setTotalProfit(stats.getTotalProfit() + trade.getProfit());
        stats.setTotalTrades(stats.getTotalTrades() + 1);

        // 更新最大回撤
        if (trade.getProfit() < stats.getMaxDrawdown()) {
            stats.setMaxDrawdown(trade.getProfit());
        }

        followerStatsRepository.save(stats);
    }
}
