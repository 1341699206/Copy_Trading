package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.FollowerStats;
import com.xtq_ymt.copy_trading_backend.model.User; // 导入 User 实体类
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
@Transactional  // 在整个测试类中启用事务，测试结束后自动回滚数据
public class FollowerStatsRepositoryTest {

    // 自动注入 FollowerStatsRepository 实例，供测试使用
    @Autowired
    private FollowerStatsRepository followerStatsRepository;

    // 自动注入 UserRepository 实例，供测试使用
    @Autowired
    private UserRepository userRepository;

    // 保存的跟随者用户
    private User follower;

    /**
     * 在每个测试方法执行之前都会调用这个方法来准备测试数据。
     * 这里我们创建一个 Follower 用户和一个 FollowerStats 对象并将它们保存到数据库中。
     */
    @BeforeEach
    public void setUp() {
        // 清空 FollowerStats 表，确保测试数据的独立性
        followerStatsRepository.deleteAll();

        // 创建并保存一个 User 对象，模拟一个跟随者用户
        User user = new User();
        user.setUsername("followerUser");
        user.setEmail("followerUser@example.com"); // 设置 email 字段
        user.setPassword("password123"); // 根据实际 User 类字段设置
        user.setRole(User.Role.FOLLOWER); // 正确的设置方法，使用枚举类型
        follower = userRepository.save(user); // 保存 User 对象到数据库，并赋值回 follower 变量

        // 创建一个 FollowerStats 对象，关联到刚刚保存的 Follower User
        FollowerStats stats = new FollowerStats();
        stats.setFollowerId(follower.getId());  // 设置跟随者 ID 为保存后的 User ID
        stats.setTotalProfit(500);  // 设置总利润为 500
        stats.setMaxDrawdown(-50.0);  // 设置最大回撤为 -50
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
    public void testFindByFollowerId() {
        // 通过 followerId 查找 FollowerStats
        FollowerStats stats = followerStatsRepository.findByFollowerId(follower.getId());

        // 验证查找到的 FollowerStats 对象不为 null
        assertNotNull(stats, "Follower stats should not be null");

        // 验证查找到的 FollowerStats 对象的 followerId 是否与保存的 followerId 一致
        assertEquals(follower.getId(), stats.getFollowerId(), "Follower ID should match the saved follower's ID");

        // 其他断言（根据需要）
        assertEquals(500.0, stats.getTotalProfit(), "Total profit should be 500");
        assertEquals(-50.0, stats.getMaxDrawdown(), "Max drawdown should be -50.0");
        assertEquals(3, stats.getTotalFollowedTraders(), "Total followed traders should be 3");
        assertEquals(10, stats.getTotalTrades(), "Total trades should be 10");
    }

    /**
     * 测试保存 FollowerStats 数据到数据库的方法。
     * 这个测试方法会验证是否能够成功保存一个新的 FollowerStats 对象，并检查返回的对象是否正确。
     */
    @Test
    public void testSaveFollowerStats() {
        // 创建并保存一个新的 User 对象，模拟另一个跟随者用户
        User newFollower = new User();
        newFollower.setUsername("newFollowerUser");
        newFollower.setEmail("newFollowerUser@example.com"); // 设置 email 字段
        newFollower.setPassword("password456"); // 根据实际 User 类字段设置
        newFollower.setRole(User.Role.FOLLOWER); // 正确的设置方法，使用枚举类型
        newFollower = userRepository.save(newFollower); // 保存 User 对象到数据库，并赋值回 newFollower 变量

        // 创建一个新的 FollowerStats 对象，关联到刚刚保存的 newFollower User
        FollowerStats stats = new FollowerStats();
        stats.setFollowerId(newFollower.getId());  // 设置跟随者 ID 为保存后的 newFollower User ID
        stats.setTotalProfit(200.0);  // 设置总利润为 200
        stats.setMaxDrawdown(-50.0);  // 设置最大回撤为 -50
        stats.setTotalFollowedTraders(5);  // 设置跟随的交易员数量为 5
        stats.setTotalTrades(20);  // 设置总交易数量为 20

        // 保存 FollowerStats 对象，并返回保存后的对象
        FollowerStats savedStats = followerStatsRepository.save(stats);

        // 验证保存后的 FollowerStats 对象不为 null
        assertNotNull(savedStats, "Saved stats should not be null");

        // 验证保存后的 FollowerStats 对象的 followerId 是否为 newFollower.getId()
        assertEquals(newFollower.getId(), savedStats.getFollowerId(), "Follower ID should match the new follower's ID");

        // 其他断言（根据需要）
        assertEquals(200.0, savedStats.getTotalProfit(), "Total profit should be 200");
        assertEquals(-50.0, savedStats.getMaxDrawdown(), "Max drawdown should be -50.0");
        assertEquals(5, savedStats.getTotalFollowedTraders(), "Total followed traders should be 5");
        assertEquals(20, savedStats.getTotalTrades(), "Total trades should be 20");
    }
}
