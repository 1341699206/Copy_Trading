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
    public TradeServiceImpl(
            TradeRepository tradeRepository,
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

    /**
     * 开仓操作，带有边界条件检查和事务管理
     * @param accountId 账户ID
     * @param strategyId 策略ID（可选）
     * @param symbol 交易品种
     * @param type 交易类型（买入/卖出）
     * @param lotSize 交易手数
     * @return 创建的交易对象
     */
    @Override
    @Transactional
    public Trade openTrade(Long accountId, Long strategyId, String symbol, String type, double lotSize) {
        double currentPrice = marketDataService.getCurrentPrice(symbol);

        // 获取交易员账户信息
        Account traderAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Trader account not found with ID: " + accountId));

        // 检查交易员账户余额
        double requiredMargin = lotSize * currentPrice * 0.1; // 假设 10% 的保证金
        if (traderAccount.getBalance() < requiredMargin) {
            throw new IllegalArgumentException("Insufficient balance in trader account.");
        }

        // 创建交易员交易
        Trade trade = new Trade();
        trade.setAccount(traderAccount);
        trade.setStrategy(strategyId != null ? strategyRepository.findById(strategyId)
                .orElseThrow(() -> new IllegalArgumentException("Strategy not found with ID: " + strategyId)) : null);
        trade.setSymbol(symbol);
        trade.setType(type);
        trade.setLotSize(lotSize);
        trade.setPriceOpen(currentPrice);
        trade.setClosed(false);
        tradeRepository.save(trade);

        // 更新交易员统计数据
        updateTraderStats(traderAccount.getUser().getId(), trade);

        // 获取所有跟随者
        List<FollowerTrader> followers = followerTraderRepository.findByTraderAccountId(accountId);
        for (FollowerTrader follower : followers) {
            try {
                Account followerAccount = accountRepository.findById(follower.getFollowerAccountId())
                        .orElseThrow(() -> new IllegalArgumentException("Follower account not found with ID: " + follower.getFollowerAccountId()));

                // 检查跟随者账户余额
                if (followerAccount.getBalance() < requiredMargin) {
                    System.err.println("Skipping follower account " + follower.getFollowerAccountId() + " due to insufficient balance.");
                    continue; // 跳过余额不足的跟随者
                }

                // 创建跟随者交易
                Trade followerTrade = new Trade();
                followerTrade.setAccount(followerAccount);
                followerTrade.setStrategy(null); // 跟随者不使用独立策略
                followerTrade.setSymbol(symbol);
                followerTrade.setType(type);
                followerTrade.setLotSize(lotSize);
                followerTrade.setPriceOpen(currentPrice);
                followerTrade.setClosed(false);
                tradeRepository.save(followerTrade);

                // 更新跟随者统计数据
                updateFollowerStats(followerAccount.getUser().getId(), followerTrade);
            } catch (Exception e) {
                System.err.println("Error processing follower with account ID " + follower.getFollowerAccountId() + ": " + e.getMessage());
            }
        }

        // 通过WebSocket推送交易更新
        tradeWebSocketHandler.broadcastTradeUpdate(accountId, trade);
        return trade;
    }

    /**
     * 平仓操作，带有事务管理
     */
    @Override
    @Transactional
    public Trade closeTrade(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found with ID: " + tradeId));

        if (trade.isClosed()) {
            throw new IllegalStateException("Trade is already closed");
        }

        double currentPrice = marketDataService.getCurrentPrice(trade.getSymbol());
        trade.setPriceClose(currentPrice);
        trade.setProfit(calculateProfit(trade));
        trade.setClosed(true);
        trade.setDateClose(LocalDateTime.now());
        tradeRepository.save(trade);

        updateTraderStats(trade.getAccount().getUser().getId(), trade);

        if (trade.getAccount().getUser().getRole() == User.Role.FOLLOWER) {
            updateFollowerStats(trade.getAccount().getUser().getId(), trade);
        }

        tradeWebSocketHandler.broadcastTradeUpdate(trade.getAccount().getId(), trade);
        return trade;
    }

    @Override
    public List<Trade> getAllTradesByAccountId(Long accountId) {
        List<Trade> trades = tradeRepository.findByAccountId(accountId);
        trades.forEach(trade -> tradeWebSocketHandler.broadcastTradeUpdate(accountId, trade));
        return trades;
    }

    @Override
    public List<Trade> getOpenTradesByAccountId(Long accountId) {
        List<Trade> trades = tradeRepository.findByAccountIdAndIsClosedFalse(accountId);
        trades.forEach(trade -> tradeWebSocketHandler.broadcastTradeUpdate(accountId, trade));
        return trades;
    }

    @Override
    public List<Trade> getClosedTradesByAccountId(Long accountId) {
        List<Trade> trades = tradeRepository.findByAccountIdAndIsClosedTrue(accountId);
        trades.forEach(trade -> tradeWebSocketHandler.broadcastTradeUpdate(accountId, trade));
        return trades;
    }

    @Override
    public List<Trade> getTradesByStrategyId(Long strategyId) {
        List<Trade> trades = tradeRepository.findByStrategyId(strategyId);
        trades.forEach(trade -> tradeWebSocketHandler.broadcastTradeUpdate(trade.getAccount().getId(), trade));
        return trades;
    }

    @Override
    public void updateTraderAndFollowerStats(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found with ID: " + tradeId));

        updateTraderStats(trade.getAccount().getUser().getId(), trade);

        if (trade.getAccount().getUser().getRole() == User.Role.FOLLOWER) {
            updateFollowerStats(trade.getAccount().getUser().getId(), trade);
        }

        tradeWebSocketHandler.broadcastTradeUpdate(trade.getAccount().getId(), trade);
    }

    private double calculateProfit(Trade trade) {
        double priceChange = trade.getPriceClose() - trade.getPriceOpen();
        if (trade.getType().equals("SELL")) {
            priceChange = -priceChange;
        }
        return priceChange * trade.getLotSize();
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
