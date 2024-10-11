package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.CopyTrading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

@Repository
public interface CopyTradingRepository extends JpaRepository<CopyTrading, Long> {

    // 根据跟随者ID查找所有复制交易（支持分页）
    Page<CopyTrading> findByFollowerFollowerId(Long followerId, Pageable pageable);

    // 根据交易者ID查找所有复制交易（支持分页）
    Page<CopyTrading> findByTraderTraderId(Long traderId, Pageable pageable);

    // 根据是否活跃状态查找复制交易（支持分页）
    Page<CopyTrading> findByIsActive(Boolean isActive, Pageable pageable);

    // 查找在特定时间段内开始的所有复制交易（支持分页）
    Page<CopyTrading> findByStartDateBetween(Date startDate, Date endDate, Pageable pageable);

    // 查找所有自动调整策略启用的复制交易（支持分页）
    Page<CopyTrading> findByAutoAdjustTrue(Pageable pageable);

    // 查找总利润大于指定值的复制交易（支持分页）
    Page<CopyTrading> findByTotalProfitGreaterThan(BigDecimal profit, Pageable pageable);

    // 查找总损失小于指定值的复制交易（支持分页）
    Page<CopyTrading> findByTotalLossLessThan(BigDecimal loss, Pageable pageable);

    // 根据交易者和跟随者的ID查找所有复制交易
    List<CopyTrading> findByFollowerFollowerIdAndTraderTraderId(Long followerId, Long traderId);

    // 查找指定交易手数限制启用的复制交易
    List<CopyTrading> findByIsMaxLotsTrue();



    // 批量删除所有不活跃的复制交易
    void deleteByIsActiveFalse();

    // 批量更新复制交易的活跃状态
    @Modifying
    @Query("UPDATE CopyTrading c SET c.isActive = :status WHERE c.follower.followerId = :followerId")
    void updateStatusByFollowerId(@Param("status") Boolean status, @Param("followerId") Long followerId);

    // 自定义查询：查找活跃且总利润大于指定值的复制交易
    @Query("SELECT c FROM CopyTrading c WHERE c.isActive = :isActive AND c.totalProfit > :profit")
    List<CopyTrading> findActiveWithProfitGreaterThan(@Param("isActive") Boolean isActive, @Param("profit") BigDecimal profit);
}
