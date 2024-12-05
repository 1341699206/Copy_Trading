package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.FollowerTrader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FollowerTraderRepository 接口用于管理 FollowerTrader 实体的持久化操作。
 * 继承自 JpaRepository，提供了基本的 CRUD（创建、读取、更新、删除）操作。
 */
@Repository  // 标识这是一个 Spring Data Repository，方便 Spring 进行组件扫描和依赖注入
public interface FollowerTraderRepository extends JpaRepository<FollowerTrader, Long> {

    /**
     * 根据关注者的账户ID查找所有与之相关的跟随关系记录。
     *
     * @param followerAccountId 关注者的账户ID
     * @return 返回一个包含所有匹配的 FollowerTrader 实体的列表
     */
    List<FollowerTrader> findByFollowerAccountId(Long followerAccountId);

    /**
     * 检查是否存在特定的跟随关系，即某个关注者是否已关注某个交易者。
     *
     * @param followerAccountId 关注者的账户ID
     * @param traderAccountId   交易者的账户ID
     * @return 如果存在这样的跟随关系，返回 true；否则，返回 false
     */
    boolean existsByFollowerAccountIdAndTraderAccountId(Long followerAccountId, Long traderAccountId);
}
