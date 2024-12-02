package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.TraderStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TraderStatsRepositoryTest 用于测试 TraderStatsRepository 的相关数据库操作。
 * 主要测试通过 traderId 查找和保存 TraderStats 对象的功能。
 */
@SpringBootTest  // 启动 Spring Boot 应用上下文，进行集成测试
@Transactional  // 在整个测试类中启用事务，测试结束后自动回滚数据
public class TraderStatsRepositoryTest {

    // 自动注入 TraderStatsRepository 实例，供测试使用
    @Autowired
    private TraderStatsRepository traderStatsRepository;

    // 预先插入测试数据
    @BeforeEach
    public void setup() {
        // 清空数据库或进行数据准备（根据实际情况）
        traderStatsRepository.deleteAll();  // 这行可以确保每次测试前数据库清空

        // 创建并插入 TraderStats 对象
        TraderStats stats = new TraderStats();
        stats.setTraderId(1L);  // 设置交易员 ID 为 1
        stats.setTotalProfit(1000);  // 示例设置其他属性
        stats.setWinRate(80.0);
        stats.setMaxDrawdown(-50);
        stats.setTotalTrades(20);
        stats.setWinningTrades(16);
        stats.setTotalFollowers(50);

        traderStatsRepository.save(stats);  // 保存数据到数据库
    }

    /**
     * 测试通过 traderId 查找 TraderStats 数据的方法。
     * 这个测试方法会验证根据给定的 traderId 查找到的 TraderStats 是否为非空，并且 traderId 是否正确。
     */
    @Test
    public void testFindByTraderId() {
        // 通过 traderId 查找 TraderStats 数据
        TraderStats stats = traderStatsRepository.findByTraderId(1L);

        // 验证查找到的 TraderStats 对象不为 null
        assertNotNull(stats, "Trader stats should not be null");

        // 验证查找到的 TraderStats 对象的 traderId 是否为 1
        assertEquals(1L, stats.getTraderId(), "Trader ID should be 1");
    }

    /**
     * 测试保存 TraderStats 数据到数据库的方法。
     * 这个测试方法会验证是否能够成功保存一个新的 TraderStats 对象，并检查返回的对象是否正确。
     */
    @Test
    public void testSaveTraderStats() {
        // 创建一个新的 TraderStats 对象，设置其属性
        TraderStats stats = new TraderStats();
        stats.setTraderId(2L);  // 设置交易员 ID 为 2
        stats.setTotalProfit(500);  // 设置总利润为 500
        stats.setWinRate(75.0);  // 设置胜率为 75%
        stats.setMaxDrawdown(-100);  // 设置最大回撤为 -100
        stats.setTotalTrades(10);  // 设置总交易数量为 10
        stats.setWinningTrades(8);  // 设置赢得交易数量为 8
        stats.setTotalFollowers(100);  // 设置跟随者总数为 100

        // 保存 TraderStats 对象，并返回保存后的对象
        TraderStats savedStats = traderStatsRepository.save(stats);

        // 验证保存后的 TraderStats 对象不为 null
        assertNotNull(savedStats, "Saved stats should not be null");

        // 验证保存后的 TraderStats 对象的 traderId 是否为 2
        assertEquals(2L, savedStats.getTraderId(), "Trader ID should be 2");
    }
}
