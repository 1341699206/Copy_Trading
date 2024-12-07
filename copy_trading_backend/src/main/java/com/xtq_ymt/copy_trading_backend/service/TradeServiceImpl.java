package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.*;
import com.xtq_ymt.copy_trading_backend.repository.*;
import com.xtq_ymt.copy_trading_backend.handler.TradeWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    // 注入所需的存储库和服务
    private final TradeRepository tradeRepository;
    private final AccountRepository accountRepository;
    private final StrategyRepository strategyRepository;
    private final TraderStatsRepository traderStatsRepository;
    private final FollowerStatsRepository followerStatsRepository;
    private final MarketDataService marketDataService;
    // WebSocket处理器，用于实时推送交易数据
    private final TradeWebSocketHandler tradeWebSocketHandler;

    @Autowired
    public TradeServiceImpl(TradeRepository tradeRepository,
                            AccountRepository accountRepository,
                            StrategyRepository strategyRepository,
                            TraderStatsRepository traderStatsRepository,
                            FollowerStatsRepository followerStatsRepository,
                            MarketDataService marketDataService,
                            TradeWebSocketHandler tradeWebSocketHandler) {
        this.tradeRepository = tradeRepository;
        this.accountRepository = accountRepository;
        this.strategyRepository = strategyRepository;
        this.traderStatsRepository = traderStatsRepository;
        this.followerStatsRepository = followerStatsRepository;
        this.marketDataService = marketDataService;
        this.tradeWebSocketHandler = tradeWebSocketHandler;
    }

    /**
     * 开仓操作
     * @param accountId 账户ID
     * @param strategyId 策略ID（可选）
     * @param symbol 交易品种
     * @param type 交易类型（买入/卖出）
     * @param lotSize 交易手数
     * @return 创建的交易对象
     */
    @Override
    public Trade openTrade(Long accountId, Long strategyId, String symbol, String type, double lotSize) {
        // 获取当前市场价格
        double currentPrice = marketDataService.getCurrentPrice(symbol);

        // 获取账户信息
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accountId));

        // 获取策略信息（如果有）
        Strategy strategy = null;
        if (strategyId != null) {
            strategy = strategyRepository.findById(strategyId)
                    .orElseThrow(() -> new IllegalArgumentException("Strategy not found with ID: " + strategyId));
        }

        // 创建新的交易对象
        Trade trade = new Trade();
        trade.setAccount(account);
        trade.setStrategy(strategy);
        trade.setSymbol(symbol);
        trade.setType(type);
        trade.setLotSize(lotSize);
        trade.setPriceOpen(currentPrice);
        trade.setClosed(false);
        trade = tradeRepository.save(trade);

        // 更新交易员统计数据
        updateTraderStats(account.getUser().getId(), trade);

        // 如果是跟随者，更新跟随者统计数据
        if (account.getUser().getRole() == User.Role.FOLLOWER) {
            updateFollowerStats(account.getUser().getId(), trade);
        }

        // 通过WebSocket推送交易更新
        tradeWebSocketHandler.broadcastTradeUpdate(accountId, trade);

        return trade;
    }

    /**
     * 平仓操作
     * @param tradeId 要平仓的交易ID
     * @return 更新后的交易对象
     */
    @Override
    public Trade closeTrade(Long tradeId) {
        // 获取交易信息
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found with ID: " + tradeId));

        // 获取当前市场价格
        double currentPrice = marketDataService.getCurrentPrice(trade.getSymbol());

        // 检查交易是否已经平仓
        if (trade.isClosed()) {
            throw new IllegalStateException("Trade is already closed");
        }

        // 更新交易信息
        trade.setPriceClose(currentPrice);
        trade.setProfit(calculateProfit(trade));
        trade.setClosed(true);
        trade.setDateClose(LocalDateTime.now());
        trade = tradeRepository.save(trade);

        // 更新交易员统计数据
        updateTraderStats(trade.getAccount().getUser().getId(), trade);

        // 如果是跟随者，更新跟随者统计数据
        if (trade.getAccount().getUser().getRole() == User.Role.FOLLOWER) {
            updateFollowerStats(trade.getAccount().getUser().getId(), trade);
        }

        // 通过WebSocket推送交易更新
        tradeWebSocketHandler.broadcastTradeUpdate(trade.getAccount().getId(), trade);

        return trade;
    }

    /**
     * 获取账户的所有交易记录
     * @param accountId 账户ID
     * @return 所有交易记录列表
     */
    @Override
    public List<Trade> getAllTradesByAccountId(Long accountId) {
        List<Trade> trades = tradeRepository.findByAccountId(accountId);
        // 获取所有交易记录时，通过WebSocket推送每个交易的最新状态
        trades.forEach(trade -> tradeWebSocketHandler.broadcastTradeUpdate(accountId, trade));
        return trades;
    }

    /**
     * 获取账户的所有未平仓交易
     * @param accountId 账户ID
     * @return 未平仓交易列表
     */
    @Override
    public List<Trade> getOpenTradesByAccountId(Long accountId) {
        List<Trade> trades = tradeRepository.findByAccountIdAndIsClosedFalse(accountId);
        // 获取交易列表时，通过WebSocket推送每个交易的最新状态
        trades.forEach(trade -> tradeWebSocketHandler.broadcastTradeUpdate(accountId, trade));
        return trades;
    }

    /**
     * 获取账户的已平仓交易
     * @param accountId 账户ID
     * @return 已平仓交易列表
     */
    @Override
    public List<Trade> getClosedTradesByAccountId(Long accountId) {
        List<Trade> trades = tradeRepository.findByAccountIdAndIsClosedTrue(accountId);
        // 获取已平仓交易时，通过WebSocket推送每个交易的最新状态
        trades.forEach(trade -> tradeWebSocketHandler.broadcastTradeUpdate(accountId, trade));
        return trades;
    }

    /**
     * 获取策略相关的所有交易
     * @param strategyId 策略ID
     * @return 策略相关的交易列表
     */
    @Override
    public List<Trade> getTradesByStrategyId(Long strategyId) {
        List<Trade> trades = tradeRepository.findByStrategyId(strategyId);
        // 获取策略相关交易时，通过WebSocket推送每个交易的最新状态
        trades.forEach(trade -> 
            tradeWebSocketHandler.broadcastTradeUpdate(trade.getAccount().getId(), trade));
        return trades;
    }

    /**
     * 更新交易员和跟随者的统计数据
     * @param tradeId 交易ID
     */
    @Override
    public void updateTraderAndFollowerStats(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found with ID: " + tradeId));

        updateTraderStats(trade.getAccount().getUser().getId(), trade);

        if (trade.getAccount().getUser().getRole() == User.Role.FOLLOWER) {
            updateFollowerStats(trade.getAccount().getUser().getId(), trade);
        }

        // 统计数据更新后，通过WebSocket推送交易更新
        tradeWebSocketHandler.broadcastTradeUpdate(trade.getAccount().getId(), trade);
    }

    /**
     * 计算交易利润
     * @param trade 交易对象
     * @return 计算得到的利润
     */
    private double calculateProfit(Trade trade) {
        double priceChange = trade.getPriceClose() - trade.getPriceOpen();
        if (trade.getType().equals("SELL")) {
            priceChange = -priceChange;
        }
        return priceChange * trade.getLotSize();
    }

    /**
     * 更新交易员统计数据
     * @param traderId 交易员ID
     * @param trade 交易对象
     */
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

    /**
     * 更新跟随者统计数据
     * @param followerId 跟随者ID
     * @param trade 交易对象
     */
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