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
import java.util.Optional;

@Repository
public interface CopyTradingRepository extends JpaRepository<CopyTrading, Long> {

    // 根据跟随者的交易账户查找所有复制交易（支持分页）
    Page<CopyTrading> findByFollowerFollowerIdAndFollowerAccount_AccountNumber(Long followerId, String accountNumber, Pageable pageable);

    // 根据交易员的交易账户查找所有复制交易（支持分页）
    Page<CopyTrading> findByTraderTraderIdAndTraderAccount_AccountNumber(Long traderId, String accountNumber, Pageable pageable);
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

    // 根据交易者、跟随者和跟随者账户的ID查找所有复制交易
    @Query("SELECT c FROM CopyTrading c WHERE c.follower.followerId = :followerId AND c.trader.traderId = :traderId AND c.followerAccount.accountId = :accountId")
    Optional<CopyTrading> findByFollowerFollowerIdAndTraderTraderIdAndFollowerAccountAccountId(
            @Param("followerId") Long followerId,
            @Param("traderId") Long traderId,
            @Param("accountId") Long accountId);

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
