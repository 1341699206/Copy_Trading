package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Trade;
import com.xtq_ymt.copy_trading_backend.repository.*;
import com.xtq_ymt.copy_trading_backend.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.transaction.annotation.Transactional;  // 导入 @Transactional 注解
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)  // 使用 MockitoExtension 来避免加载 Spring 上下文
@Transactional  // 确保测试方法中的数据库操作都在事务中执行，测试结束后会回滚
public class TradeServiceImplTest {

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private StrategyRepository strategyRepository;

    @Mock
    private TraderStatsRepository traderStatsRepository;

    @Mock
    private FollowerStatsRepository followerStatsRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;

    private Account account;
    private Strategy strategy;

    @BeforeEach
    public void setUp() {
        // 初始化 Account 对象
        account = new Account();
        account.setId(1L);
        account.setUser(new User(1L, "trader", User.Role.TRADER));

        // 初始化 Strategy 对象
        strategy = new Strategy();
        strategy.setId(1L);
    }

    @Test
    public void testOpenTrade() {
        // 创建模拟的 Trade 对象
        Trade trade = new Trade();
        trade.setAccount(account);
        trade.setStrategy(strategy);
        trade.setSymbol("EURUSD");
        trade.setType("BUY");
        trade.setLotSize(1.0);
        trade.setPriceOpen(1.2000);

        // 设置模拟的返回值
        when(accountRepository.findById(1L)).thenReturn(java.util.Optional.of(account));
        when(strategyRepository.findById(1L)).thenReturn(java.util.Optional.of(strategy));
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        // 调用 service 的 openTrade 方法
        Trade result = tradeService.openTrade(1L, 1L, "EURUSD", "BUY", 1.0, 1.2000);

        // 验证返回结果不为 null
        assertNotNull(result);
        // 验证 tradeRepository.save() 被调用了一次
        verify(tradeRepository, times(1)).save(any(Trade.class));
        verify(traderStatsRepository, times(1)).save(any(TraderStats.class));
    }

    @Test
    public void testCloseTrade() {
        // 创建模拟的 Trade 对象
        Trade trade = new Trade();
        trade.setId(1L);
        trade.setAccount(account);
        trade.setPriceClose(1.2100);
        trade.setProfit(100);
        trade.setClosed(false);

        // 设置模拟的返回值
        when(tradeRepository.findById(1L)).thenReturn(java.util.Optional.of(trade));
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        // 调用 service 的 closeTrade 方法
        Trade result = tradeService.closeTrade(1L, 1.2100);

        // 验证返回结果不为 null
        assertNotNull(result);
        // 验证交易状态已更新为已关闭
        assertTrue(result.isClosed());
        // 验证 traderStatsRepository.save() 被调用了一次
        verify(traderStatsRepository, times(1)).save(any(TraderStats.class));
    }

    @Test
    public void testUpdateTraderStats() {
        // 创建模拟的 Trade 对象
        Trade trade = new Trade();
        trade.setProfit(100);
        trade.setAccount(account);

        // 创建并设置 TraderStats 对象
        TraderStats stats = new TraderStats();
        stats.setTraderId(1L);
        stats.setTotalProfit(500);

        // 设置模拟的返回值
        when(traderStatsRepository.findByTraderId(1L)).thenReturn(stats);

        // 调用 service 的 updateTraderStats 方法
        tradeService.updateTraderStats(1L, trade);

        // 验证利润更新
        assertEquals(600, stats.getTotalProfit());
        // 验证 traderStatsRepository.save() 被调用了一次
        verify(traderStatsRepository, times(1)).save(stats);
    }

    @Test
    public void testUpdateFollowerStats() {
        // 创建模拟的 Trade 对象
        Trade trade = new Trade();
        trade.setProfit(50);
        trade.setAccount(account);

        // 创建并设置 FollowerStats 对象
        FollowerStats stats = new FollowerStats();
        stats.setFollowerId(1L);
        stats.setTotalProfit(200);

        // 设置模拟的返回值
        when(followerStatsRepository.findByFollowerId(1L)).thenReturn(stats);

        // 调用 service 的 updateFollowerStats 方法
        tradeService.updateFollowerStats(1L, trade);

        // 验证利润更新
        assertEquals(250, stats.getTotalProfit());
        // 验证 followerStatsRepository.save() 被调用了一次
        verify(followerStatsRepository, times(1)).save(stats);
    }
}
