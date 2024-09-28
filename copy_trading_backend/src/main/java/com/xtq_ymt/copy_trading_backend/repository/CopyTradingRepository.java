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

    // 根据跟随者的交易账户ID查找所有复制交易（支持分页）
    Page<CopyTrading> findByFollowerTradingAccountAccountId(Long accountId, Pageable pageable);

    // 根据交易者的交易账户ID查找所有复制交易（支持分页）
    Page<CopyTrading> findByTraderTradingAccountAccountId(Long accountId, Pageable pageable);

    // 查找特定跟随者交易账户中所有活跃的复制交易
    List<CopyTrading> findByIsActiveTrueAndFollowerTradingAccountAccountId(Long accountId);

    // 根据跟随者和交易者的交易账户ID查找所有复制交易
    List<CopyTrading> findByFollowerTradingAccountAccountIdAndTraderTradingAccountAccountId(Long followerAccountId, Long traderAccountId);

    // 查找在特定时间段内开始的所有复制交易（支持分页）
    Page<CopyTrading> findByStartDateBetween(Date startDate, Date endDate, Pageable pageable);

    // 查找所有自动调整策略启用的复制交易（支持分页）
    Page<CopyTrading> findByAutoAdjustTrue(Pageable pageable);

    // 根据交易账户查找所有总利润大于指定值的复制交易
    Page<CopyTrading> findByTotalProfitGreaterThanAndTraderTradingAccountAccountId(BigDecimal profit, Long accountId, Pageable pageable);

    // 批量更新复制交易的活跃状态
    @Modifying
    @Query("UPDATE CopyTrading c SET c.isActive = :status WHERE c.followerTradingAccount.accountId = :accountId")
    void updateStatusByFollowerTradingAccountId(@Param("status") Boolean status, @Param("accountId") Long accountId);

    // 查找总损失小于指定值的复制交易（支持分页）
    Page<CopyTrading> findByTotalLossLessThan(BigDecimal loss, Pageable pageable);

    // 批量删除所有不活跃的复制交易
    void deleteByIsActiveFalse();

    // 自定义查询：查找活跃且总利润大于指定值的复制交易
    @Query("SELECT c FROM CopyTrading c WHERE c.isActive = :isActive AND c.totalProfit > :profit")
    List<CopyTrading> findActiveWithProfitGreaterThan(@Param("isActive") Boolean isActive, @Param("profit") BigDecimal profit);
}
