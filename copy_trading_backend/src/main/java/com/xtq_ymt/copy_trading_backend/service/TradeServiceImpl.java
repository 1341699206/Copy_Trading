package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.*;
import com.xtq_ymt.copy_trading_backend.repository.*;
import com.xtq_ymt.copy_trading_backend.handler.TradeWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final AccountRepository accountRepository;
    private final StrategyRepository strategyRepository;
    private final TraderStatsRepository traderStatsRepository;
    private final FollowerStatsRepository followerStatsRepository;
    private final FollowerTraderRepository followerTraderRepository;
    private final MarketDataService marketDataService;
    private final TradeWebSocketHandler tradeWebSocketHandler;

    @Autowired
    public TradeServiceImpl(TradeRepository tradeRepository,
                            AccountRepository accountRepository,
                            StrategyRepository strategyRepository,
                            TraderStatsRepository traderStatsRepository,
                            FollowerStatsRepository followerStatsRepository,
                            FollowerTraderRepository followerTraderRepository,
                            MarketDataService marketDataService,
                            TradeWebSocketHandler tradeWebSocketHandler) {
        this.tradeRepository = tradeRepository;
        this.accountRepository = accountRepository;
        this.strategyRepository = strategyRepository;
        this.traderStatsRepository = traderStatsRepository;
        this.followerStatsRepository = followerStatsRepository;
        this.followerTraderRepository = followerTraderRepository;
        this.marketDataService = marketDataService;
        this.tradeWebSocketHandler = tradeWebSocketHandler;
    }

    @Override
    public List<Trade> getClosedTradesByAccountId(Long accountId) {
        return tradeRepository.findByAccountIdAndIsClosedTrue(accountId);
    }

    @Override
    public List<Trade> getOpenTradesByAccountId(Long accountId) {
        return tradeRepository.findByAccountIdAndIsClosedFalse(accountId);
    }

    @Override
    public List<Trade> getAllTradesByAccountId(Long accountId) {
        return tradeRepository.findByAccountId(accountId);
    }

    @Override
    public List<Trade> getTradesByStrategyId(Long strategyId) {
        return tradeRepository.findByStrategyId(strategyId);
    }

    @Override
    public void updateTraderAndFollowerStats(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accountId));
        updateStats(account);
    }

    @Override
    @Transactional
    public Trade openTrade(Long accountId, Long strategyId, String symbol, String type, double lotSize) {
        Account traderAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        User user = traderAccount.getUser();
        if (user == null) throw new IllegalStateException("User not associated with account");

        double currentPrice = marketDataService.getCurrentPrice(symbol);
        double requiredMargin = lotSize * currentPrice * 0.1;
        if (traderAccount.getFreeMargin() < requiredMargin) {
            throw new IllegalArgumentException("Insufficient free margin");
        }

        Trade trade = new Trade();
        trade.setAccount(traderAccount);
        trade.setStrategy(strategyId != null ? strategyRepository.findById(strategyId).orElse(null) : null);
        trade.setSymbol(symbol);
        trade.setType(type);
        trade.setLotSize(lotSize);
        trade.setPriceOpen(currentPrice);
        trade.setClosed(false);
        tradeRepository.save(trade);

        updateStats(traderAccount);
        updateFollowerTrades(accountId, trade);

        tradeWebSocketHandler.broadcastNewTradeUpdate(accountId, trade);
        return trade;
    }

    @Override
    @Transactional
    public Trade closeTrade(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found"));

        double currentPrice = marketDataService.getCurrentPrice(trade.getSymbol());
        trade.setPriceClose(currentPrice);
        trade.setProfit(calculateProfit(trade));
        trade.setClosed(true);
        trade.setDateClose(LocalDateTime.now());
        tradeRepository.save(trade);

        updateStats(trade.getAccount());

        tradeWebSocketHandler.broadcastNewTradeUpdate(trade.getAccount().getId(), trade);
        return trade;
    }

    private void updateStats(Account account) {
        User.Role role = account.getUser().getRole();
        if (role == User.Role.TRADER) {
            updateTraderStats(account);
        } else if (role == User.Role.FOLLOWER) {
            updateFollowerStats(account);
        }
    }

    private void updateFollowerTrades(Long traderAccountId, Trade trade) {
        List<FollowerTrader> followers = followerTraderRepository.findByTraderAccountId(traderAccountId);
        for (FollowerTrader follower : followers) {
            Account followerAccount = accountRepository.findById(follower.getFollowerAccountId())
                    .orElseThrow(() -> new IllegalArgumentException("Follower account not found"));
            Trade followerTrade = new Trade();
            followerTrade.setAccount(followerAccount);
            followerTrade.setSymbol(trade.getSymbol());
            followerTrade.setType(trade.getType());
            followerTrade.setLotSize(trade.getLotSize());
            followerTrade.setPriceOpen(trade.getPriceOpen());
            followerTrade.setClosed(false);
            tradeRepository.save(followerTrade);
            updateFollowerStats(followerAccount);
        }
    }

    private double calculateProfit(Trade trade) {
        double priceChange = trade.getPriceClose() - trade.getPriceOpen();
        if ("SELL".equalsIgnoreCase(trade.getType())) {
            priceChange = -priceChange;
        }
        return priceChange * trade.getLotSize();
    }

    private void updateTraderStats(Account account) {
        TraderStats stats = traderStatsRepository.findByTraderId(account.getId());
        if (stats == null) {
            stats = new TraderStats(
                    null,
                    account.getId(),
                    account.getUser(),
                    0.0,
                    0.0,
                    0.0,
                    0,
                    0,
                    0
            );
        }
        Double totalProfit = tradeRepository.getTotalProfitByTraderId(account.getId());
        stats.setTotalProfit(totalProfit != null ? totalProfit : 0.0);

        Integer totalTrades = tradeRepository.getTotalTradesByTraderId(account.getId());
        stats.setTotalTrades(totalTrades != null ? totalTrades : 0);

        Integer winningTrades = tradeRepository.getWinningTradesByTraderId(account.getId());
        stats.setWinningTrades(winningTrades != null ? winningTrades : 0);

        stats.setWinRate(stats.getTotalTrades() > 0
                ? (stats.getWinningTrades() / (double) stats.getTotalTrades()) * 100
                : 0.0);

        Double maxDrawdown = tradeRepository.getMaxDrawdownByTraderId(account.getId());
        stats.setMaxDrawdown(maxDrawdown != null ? maxDrawdown : 0.0);

        stats.setTotalFollowers(followerTraderRepository.findByTraderAccountId(account.getId()).size());

        traderStatsRepository.save(stats);
    }

    private void updateFollowerStats(Account account) {
        FollowerStats stats = followerStatsRepository.findByFollowerId(account.getId());
        if (stats == null) {
            stats = new FollowerStats(
                    null,
                    account.getId(),
                    account.getUser(),
                    0.0,
                    0.0,
                    0,
                    0
            );
        }
        Double totalProfit = tradeRepository.getTotalProfitByFollowerId(account.getId());
        stats.setTotalProfit(totalProfit != null ? totalProfit : 0.0);

        Integer totalTrades = tradeRepository.getTotalTradesByFollowerId(account.getId());
        stats.setTotalTrades(totalTrades != null ? totalTrades : 0);

        Double maxDrawdown = tradeRepository.getMaxDrawdownByFollowerId(account.getId());
        stats.setMaxDrawdown(maxDrawdown != null ? maxDrawdown : 0.0);

        Integer totalFollowedTraders = tradeRepository.getTotalFollowedTraders(account.getId());
        stats.setTotalFollowedTraders(totalFollowedTraders != null ? totalFollowedTraders : 0);

        followerStatsRepository.save(stats);
    }
}
