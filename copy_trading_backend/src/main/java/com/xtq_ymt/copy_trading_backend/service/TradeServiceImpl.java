package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.*;
import com.xtq_ymt.copy_trading_backend.repository.*;
import com.xtq_ymt.copy_trading_backend.handler.TradeWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TradeServiceImpl 实现了 TradeService 接口，
 * 负责处理交易的开启、关闭和统计数据更新等核心业务逻辑。
 * 包含账户余额检查、保证金计算、利润计算、统计更新和 WebSocket 推送功能。
 */
@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final AccountRepository accountRepository;
    private final StrategyRepository strategyRepository;
    private final TraderStatsRepository traderStatsRepository;
    private final MarketDataService marketDataService;
    private final TradeWebSocketHandler tradeWebSocketHandler;
    private final AccountService accountService;

    /**
     * 依赖注入构造函数。
     *
     * @param tradeRepository        交易数据访问层
     * @param accountRepository      账户数据访问层
     * @param strategyRepository     策略数据访问层
     * @param traderStatsRepository  交易员统计数据访问层
     * @param marketDataService      市场数据服务
     * @param tradeWebSocketHandler  WebSocket 推送处理器
     * @param accountService         账户服务接口
     */
    @Autowired
    public TradeServiceImpl(
            TradeRepository tradeRepository,
            AccountRepository accountRepository,
            StrategyRepository strategyRepository,
            TraderStatsRepository traderStatsRepository,
            MarketDataService marketDataService,
            TradeWebSocketHandler tradeWebSocketHandler,
            AccountService accountService) {
        this.tradeRepository = tradeRepository;
        this.accountRepository = accountRepository;
        this.strategyRepository = strategyRepository;
        this.traderStatsRepository = traderStatsRepository;
        this.marketDataService = marketDataService;
        this.tradeWebSocketHandler = tradeWebSocketHandler;
        this.accountService = accountService;
    }

    /**
     * 获取指定账户下已平仓的交易记录。
     *
     * @param accountId 账户 ID
     * @return 已平仓的交易列表
     */
    @Override
    public List<Trade> getClosedTradesByAccountId(Long accountId) {
        System.out.println("Fetching closed trades for accountId: " + accountId);
        return tradeRepository.findByAccountIdAndIsClosedTrue(accountId);
    }

    /**
     * 获取指定账户下未平仓的交易记录。
     *
     * @param accountId 账户 ID
     * @return 未平仓的交易列表
     */
    @Override
    public List<Trade> getOpenTradesByAccountId(Long accountId) {
        System.out.println("Fetching open trades for accountId: " + accountId);
        return tradeRepository.findByAccountIdAndIsClosedFalse(accountId);
    }

    /**
     * 获取指定账户下所有的交易记录。
     *
     * @param accountId 账户 ID
     * @return 所有交易列表
     */
    @Override
    public List<Trade> getAllTradesByAccountId(Long accountId) {
        System.out.println("Fetching all trades for accountId: " + accountId);
        return tradeRepository.findByAccountId(accountId);
    }

    /**
     * 根据策略 ID 获取相关的交易记录。
     *
     * @param strategyId 策略 ID
     * @return 相关交易列表
     */
    @Override
    public List<Trade> getTradesByStrategyId(Long strategyId) {
        System.out.println("Fetching trades for strategyId: " + strategyId);
        return tradeRepository.findByStrategyId(strategyId);
    }

    /**
     * 更新交易员和跟随者的统计数据。
     *
     * @param tradeId 交易 ID
     */
    @Override
    public void updateTraderAndFollowerStats(Long tradeId) {
        System.out.println("Updating stats for tradeId: " + tradeId);
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found with ID: " + tradeId));
        updateTraderStats(trade.getAccount().getUser().getId(), trade);
    }

    /**
     * 开启一笔新交易。
     *
     * @param accountId  账户 ID
     * @param strategyId 策略 ID（可选）
     * @param symbol     交易品种
     * @param type       交易类型（买入/卖出）
     * @param lotSize    交易手数
     * @return 新创建的交易对象
     */
    @Override
    @Transactional
    public Trade openTrade(Long accountId, Long strategyId, String symbol, String type, double lotSize) {
        System.out.println("Opening trade for accountId: " + accountId + ", symbol: " + symbol + ", type: " + type + ", lotSize: " + lotSize);
        double currentPrice = marketDataService.getCurrentPrice(symbol);

        // 获取账户信息
        Account traderAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Trader account not found"));

        // 检查账户的可用保证金
        double requiredMargin = lotSize * currentPrice * 0.1;
        if (traderAccount.getFreeMargin() < requiredMargin) {
            throw new IllegalArgumentException("Insufficient free margin");
        }

        // 创建交易对象
        Trade trade = new Trade();
        trade.setAccount(traderAccount);
        trade.setStrategy(strategyId != null ? strategyRepository.findById(strategyId).orElse(null) : null);
        trade.setSymbol(symbol);
        trade.setType(type);
        trade.setLotSize(lotSize);
        trade.setPriceOpen(currentPrice);
        trade.setClosed(false);
        tradeRepository.save(trade);

        System.out.println("Trade successfully opened with ID: " + trade.getId());
        accountService.updateAccount(traderAccount);
        updateTraderStats(traderAccount.getUser().getId(), trade);

        tradeWebSocketHandler.broadcastNewTradeUpdate(accountId, trade);
        return trade;
    }

    /**
     * 平仓指定交易。
     *
     * @param tradeId 交易 ID
     * @return 平仓后的交易对象
     */
    @Override
    @Transactional
    public Trade closeTrade(Long tradeId) {
        System.out.println("Closing trade with ID: " + tradeId);
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found"));

        double currentPrice = marketDataService.getCurrentPrice(trade.getSymbol());
        trade.setPriceClose(currentPrice);
        trade.setProfit(calculateProfit(trade));
        trade.setClosed(true);
        trade.setDateClose(LocalDateTime.now());
        tradeRepository.save(trade);

        System.out.println("Trade closed with profit: " + trade.getProfit());
        accountService.updateAccount(trade.getAccount());
        updateTraderStats(trade.getAccount().getUser().getId(), trade);

        tradeWebSocketHandler.broadcastNewTradeUpdate(trade.getAccount().getId(), trade);
        return trade;
    }

    /**
     * 计算交易的利润。
     *
     * @param trade 交易对象
     * @return 交易的利润
     */
    private double calculateProfit(Trade trade) {
        double priceChange = trade.getPriceClose() - trade.getPriceOpen();
        if ("SELL".equalsIgnoreCase(trade.getType())) {
            priceChange = -priceChange;
        }
        double profit = priceChange * trade.getLotSize();
        System.out.println("Calculated profit for trade ID: " + trade.getId() + " is: " + profit);
        return profit;
    }

    /**
     * 更新交易员的统计数据。
     *
     * @param traderId 交易员 ID
     * @param trade    交易对象
     */
    private void updateTraderStats(Long traderId, Trade trade) {
        System.out.println("Updating trader stats for traderId: " + traderId);
        TraderStats stats = traderStatsRepository.findByTraderId(traderId);
        if (stats == null) {
            stats = new TraderStats();
            stats.setTraderId(traderId);
            stats.setTotalProfit(0.0);
            stats.setTotalTrades(0);
            stats.setWinningTrades(0);
        }

        stats.setTotalProfit(stats.getTotalProfit() + trade.getProfit());
        stats.setTotalTrades(stats.getTotalTrades() + 1);
        if (trade.getProfit() > 0) {
            stats.setWinningTrades(stats.getWinningTrades() + 1);
        }

        stats.setWinRate(stats.getTotalTrades() > 0
                ? (double) stats.getWinningTrades() / stats.getTotalTrades() * 100 : 0.0);

        System.out.println("Updated trader stats: TotalProfit = " + stats.getTotalProfit() + ", WinRate = " + stats.getWinRate());
        traderStatsRepository.save(stats);
    }
}
