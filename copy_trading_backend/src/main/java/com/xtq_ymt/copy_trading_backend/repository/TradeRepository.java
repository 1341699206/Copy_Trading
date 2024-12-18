package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    // 获取账户的所有交易记录
    @Query("SELECT t FROM Trade t WHERE t.account.id = :accountId ORDER BY t.dateOpen DESC")
    List<Trade> findByAccountId(@Param("accountId") Long accountId);

    // 获取账户的未平仓交易
    @Query("SELECT t FROM Trade t WHERE t.account.id = :accountId AND t.isClosed = false")
    List<Trade> findByAccountIdAndIsClosedFalse(@Param("accountId") Long accountId);

    // 获取账户的已平仓交易
    @Query("SELECT t FROM Trade t WHERE t.account.id = :accountId AND t.isClosed = true")
    List<Trade> findByAccountIdAndIsClosedTrue(@Param("accountId") Long accountId);

    // 获取与策略相关的所有交易
    @Query("SELECT t FROM Trade t WHERE t.strategy.id = :strategyId")
    List<Trade> findByStrategyId(@Param("strategyId") Long strategyId);

    // 根据账户ID和交易品种获取未平仓交易
    @Query("SELECT t FROM Trade t WHERE t.account.id = :accountId AND t.symbol = :symbol AND t.isClosed = false")
    Optional<Trade> findByAccountIdAndSymbolAndIsClosedFalse(
            @Param("accountId") Long accountId,
            @Param("symbol") String symbol);

    // 获取交易员的总收益，防止 null 返回值
    @Query("SELECT COALESCE(SUM(t.profit), 0.0) FROM Trade t WHERE t.account.id = :accountId AND t.isClosed = true")
    Double getTotalProfitByTraderId(@Param("accountId") Long accountId);

    // 获取交易员的总交易数量，防止 null 返回值
    @Query("SELECT COALESCE(COUNT(t), 0) FROM Trade t WHERE t.account.id = :accountId")
    Integer getTotalTradesByTraderId(@Param("accountId") Long accountId);

    // 获取交易员的盈利交易数量，防止 null 返回值
    @Query("SELECT COALESCE(COUNT(t), 0) FROM Trade t WHERE t.account.id = :accountId AND t.profit > 0")
    Integer getWinningTradesByTraderId(@Param("accountId") Long accountId);

    // 获取交易员的最大回撤，防止 null 返回值
    @Query("SELECT COALESCE(MAX(t.profit) - MIN(t.profit), 0.0) FROM Trade t WHERE t.account.id = :accountId AND t.isClosed = true")
    Double getMaxDrawdownByTraderId(@Param("accountId") Long accountId);

    // 获取跟随者的总收益，防止 null 返回值
    @Query("SELECT COALESCE(SUM(t.profit), 0.0) FROM Trade t WHERE t.account.id = :followerId AND t.isClosed = true")
    Double getTotalProfitByFollowerId(@Param("followerId") Long followerId);

    // 获取跟随者的总交易数量，防止 null 返回值
    @Query("SELECT COALESCE(COUNT(t), 0) FROM Trade t WHERE t.account.id = :followerId")
    Integer getTotalTradesByFollowerId(@Param("followerId") Long followerId);

    // 获取跟随者的最大回撤，防止 null 返回值
    @Query("SELECT COALESCE(MAX(t.profit) - MIN(t.profit), 0.0) FROM Trade t WHERE t.account.id = :followerId AND t.isClosed = true")
    Double getMaxDrawdownByFollowerId(@Param("followerId") Long followerId);

    // 获取跟随者总共跟随的交易员数量，防止 null 返回值
    @Query("SELECT COALESCE(COUNT(DISTINCT t.strategy.id), 0) FROM Trade t WHERE t.account.id = :followerId")
    Integer getTotalFollowedTraders(@Param("followerId") Long followerId);

    // 获取利润最高的前 N 名交易员
    @Query("SELECT t.account.id, COALESCE(SUM(t.profit), 0.0) FROM Trade t GROUP BY t.account.id ORDER BY SUM(t.profit) DESC")
    List<Object[]> getTopTradersByProfit(Pageable pageable);

    // 获取利润最高的前 N 名跟随者
    @Query("SELECT t.account.id, COALESCE(SUM(t.profit), 0.0) FROM Trade t GROUP BY t.account.id ORDER BY SUM(t.profit) DESC")
    List<Object[]> getTopFollowersByProfit(Pageable pageable);
}
