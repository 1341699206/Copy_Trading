package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.FollowerStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FollowerStatsRepositoryTest 用于测试 FollowerStatsRepository 的相关数据库操作。
 * 使用 Spring Boot 集成测试框架，并通过 @Transactional 注解确保测试后数据回滚。
 */
@SpringBootTest  // 启动 Spring Boot 应用上下文，进行集成测试
public class FollowerStatsRepositoryTest {

    // 自动注入 FollowerStatsRepository 实例，供测试使用
    @Autowired
    private FollowerStatsRepository followerStatsRepository;

    /**
     * 在每个测试方法执行之前都会调用这个方法来准备测试数据。
     * 这里我们创建一个 FollowerStats 对象并将其保存到数据库中。
     * 使用 @BeforeEach 确保每个测试方法执行之前都进行数据准备。
     */
    @BeforeEach
    public void setUp() {
        // 创建一个 FollowerStats 对象，模拟一个跟随者的统计数据
        FollowerStats stats = new FollowerStats();
        stats.setFollowerId(1L);  // 设置跟随者 ID 为 1
        stats.setTotalProfit(500);  // 设置总利润为 500
        stats.setMaxDrawdown(-50);  // 设置最大回撤为 -50
        stats.setTotalFollowedTraders(3);  // 设置跟随的交易员数量为 3
        stats.setTotalTrades(10);  // 设置总交易数量为 10

        // 将 FollowerStats 对象保存到数据库中
        followerStatsRepository.save(stats);
    }

    /**
     * 测试通过 followerId 查找跟随者统计数据的方法。
     * 这个测试方法会验证根据给定的 followerId 查找到的 FollowerStats 是否为非空，并且 followerId 是否正确。
     */
    @Test
    @Transactional  // 每次测试会启动一个事务，执行完后会回滚，保证测试数据不污染数据库
    public void testFindByFollowerId() {
        // 通过 followerId 查找跟随者统计数据
        FollowerStats stats = followerStatsRepository.findByFollowerId(1L);

        // 验证查找到的 FollowerStats 对象不为 null
        assertNotNull(stats, "Follower stats should not be null");

        // 验证查找到的 FollowerStats 对象的 followerId 是否为 1
        assertEquals(1L, stats.getFollowerId(), "Follower ID should be 1");
    }

    /**
     * 测试保存 FollowerStats 数据到数据库的方法。
     * 这个测试方法会验证是否能够成功保存一个新的 FollowerStats 对象，并检查返回的对象是否正确。
     */
    @Test
    @Transactional  // 每次测试会启动一个事务，执行完后会回滚，保证测试数据不污染数据库
    public void testSaveFollowerStats() {
        // 创建一个新的 FollowerStats 对象，设置其属性
        FollowerStats stats = new FollowerStats();
        stats.setFollowerId(3L);  // 设置跟随者 ID 为 3
        stats.setTotalProfit(200);  // 设置总利润为 200
        stats.setMaxDrawdown(-50);  // 设置最大回撤为 -50
        stats.setTotalFollowedTraders(5);  // 设置跟随的交易员数量为 5
        stats.setTotalTrades(20);  // 设置总交易数量为 20

        // 保存 FollowerStats 对象，并返回保存后的对象
        FollowerStats savedStats = followerStatsRepository.save(stats);

        // 验证保存后的 FollowerStats 对象不为 null
        assertNotNull(savedStats, "Saved stats should not be null");

        // 验证保存后的 FollowerStats 对象的 followerId 是否为 3
        assertEquals(3L, savedStats.getFollowerId(), "Follower ID should be 3");
    }
}
