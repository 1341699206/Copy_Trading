package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.*;
import com.xtq_ymt.copy_trading_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final AccountRepository accountRepository;
    private final StrategyRepository strategyRepository;
    private final TraderStatsRepository traderStatsRepository;
    private final FollowerStatsRepository followerStatsRepository;
    private final MarketDataService marketDataService;

    @Autowired
    public TradeServiceImpl(TradeRepository tradeRepository,
                            AccountRepository accountRepository,
                            StrategyRepository strategyRepository,
                            TraderStatsRepository traderStatsRepository,
                            FollowerStatsRepository followerStatsRepository,
                            MarketDataService marketDataService) {
        this.tradeRepository = tradeRepository;
        this.accountRepository = accountRepository;
        this.strategyRepository = strategyRepository;
        this.traderStatsRepository = traderStatsRepository;
        this.followerStatsRepository = followerStatsRepository;
        this.marketDataService = marketDataService;
    }

    @Override
    public Trade openTrade(Long accountId, Long strategyId, String symbol, String type, double lotSize) {
        double currentPrice = marketDataService.getCurrentPrice(symbol);

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
        trade.setPriceOpen(currentPrice);
        trade.setClosed(false);
        trade = tradeRepository.save(trade);

        updateTraderStats(account.getUser().getId(), trade);

        if (account.getUser().getRole() == User.Role.FOLLOWER) {
            updateFollowerStats(account.getUser().getId(), trade);
        }

        return trade;
    }

    @Override
    public Trade closeTrade(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found with ID: " + tradeId));

        double currentPrice = marketDataService.getCurrentPrice(trade.getSymbol());

        if (trade.isClosed()) {
            throw new IllegalStateException("Trade is already closed");
        }

        trade.setPriceClose(currentPrice);
        trade.setProfit(calculateProfit(trade));
        trade.setClosed(true);
        trade.setDateClose(LocalDateTime.now());
        trade = tradeRepository.save(trade);

        updateTraderStats(trade.getAccount().getUser().getId(), trade);

        if (trade.getAccount().getUser().getRole() == User.Role.FOLLOWER) {
            updateFollowerStats(trade.getAccount().getUser().getId(), trade);
        }

        return trade;
    }

    private double calculateProfit(Trade trade) {
        double priceChange = trade.getPriceClose() - trade.getPriceOpen();
        if (trade.getType().equals("SELL")) {
            priceChange = -priceChange;
        }
        return priceChange * trade.getLotSize();
    }

    @Override
    public List<Trade> getOpenTradesByAccountId(Long accountId) {
        return tradeRepository.findByAccountIdAndIsClosedFalse(accountId);
    }

    @Override
    public List<Trade> getTradesByStrategyId(Long strategyId) {
        return tradeRepository.findByStrategyId(strategyId);
    }

    @Override
    public void updateTraderAndFollowerStats(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found with ID: " + tradeId));

        updateTraderStats(trade.getAccount().getUser().getId(), trade);

        if (trade.getAccount().getUser().getRole() == User.Role.FOLLOWER) {
            updateFollowerStats(trade.getAccount().getUser().getId(), trade);
        }
    }

    public void updateTraderStats(Long traderId, Trade trade) {
        TraderStats stats = traderStatsRepository.findByTraderId(traderId);
        if (stats == null) {
            stats = new TraderStats();
            stats.setTraderId(traderId);
            User user = trade.getAccount().getUser();
            stats.setUser(user);
            stats.setTotalProfit(0.0);
            stats.setWinRate(0.0);
            stats.setMaxDrawdown(0.0);
            stats.setTotalTrades(0);
            stats.setWinningTrades(0);
            stats.setTotalFollowers(0);
        }

        stats.setTotalProfit(stats.getTotalProfit() + trade.getProfit());
        stats.setTotalTrades(stats.getTotalTrades() + 1);
        if (trade.getProfit() > 0) {
            stats.setWinningTrades(stats.getWinningTrades() + 1);
        }

        stats.setWinRate(stats.getTotalTrades() > 0 ?
                ((double) stats.getWinningTrades() / stats.getTotalTrades()) * 100 : 0.0);

        if (trade.getProfit() < stats.getMaxDrawdown()) {
            stats.setMaxDrawdown(trade.getProfit());
        }

        traderStatsRepository.save(stats);
    }

    public void updateFollowerStats(Long followerId, Trade trade) {
        FollowerStats stats = followerStatsRepository.findByFollowerId(followerId);
        if (stats == null) {
            stats = new FollowerStats();
            stats.setFollowerId(followerId);
            User user = trade.getAccount().getUser();
            stats.setUser(user);
            stats.setTotalProfit(0.0);
            stats.setMaxDrawdown(0.0);
            stats.setTotalFollowedTraders(0);
            stats.setTotalTrades(0);
        }

        stats.setTotalProfit(stats.getTotalProfit() + trade.getProfit());
        stats.setTotalTrades(stats.getTotalTrades() + 1);

        if (trade.getProfit() < stats.getMaxDrawdown()) {
            stats.setMaxDrawdown(trade.getProfit());
        }

        followerStatsRepository.save(stats);
    }
}