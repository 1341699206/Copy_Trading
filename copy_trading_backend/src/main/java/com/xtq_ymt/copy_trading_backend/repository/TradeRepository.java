package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.Trade;
import com.xtq_ymt.copy_trading_backend.model.TradeActionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    // 根据 Trader ID 查找所有交易（分页）
    Page<Trade> findByTrader_TraderId(Long traderId, Pageable pageable);
    List<Trade> findByTrader_TraderId(Long traderId);

    // 根据 Follower ID 查找所有交易（分页）
    Page<Trade> findByFollower_FollowerId(Long followerId, Pageable pageable);
    List<Trade> findByFollower_FollowerId(Long followerId);

    // 根据 Trader 的交易账户查找交易记录（分页）
    Page<Trade> findByTraderAccount_AccountId(Long accountId, Pageable pageable);
    List<Trade> findByTraderAccount_AccountId(Long accountId);

    // 根据 Follower 的交易账户查找交易记录（如果是跟单交易）（分页）
    Page<Trade> findByFollowerAccount_AccountId(Long accountId, Pageable pageable);
    List<Trade> findByFollowerAccount_AccountId(Long accountId);

    // 根据是否开仓查找交易（分页）
    Page<Trade> findByIsOpen(Boolean isOpen, Pageable pageable);
    List<Trade> findByIsOpen(Boolean isOpen);

    // 查找特定交易类型的交易（分页）
    Page<Trade> findByType(TradeActionType type, Pageable pageable);
    List<Trade> findByType(TradeActionType type);

    // 查找在某个时间范围内的所有交易（分页）
    Page<Trade> findByDateOpenBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    List<Trade> findByDateOpenBetween(LocalDateTime startDate, LocalDateTime endDate);

    // 自定义查询：查找在某个价格范围内的所有交易（分页）
    @Query("SELECT t FROM Trade t WHERE t.priceOpen BETWEEN :minPrice AND :maxPrice")
    Page<Trade> findByPriceOpenBetween(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice, Pageable pageable);
    @Query("SELECT t FROM Trade t WHERE t.priceOpen BETWEEN :minPrice AND :maxPrice")
    List<Trade> findByPriceOpenBetween(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    // 查找特定货币对的所有交易（分页）
    Page<Trade> findByCurrency(String currency, Pageable pageable);
    List<Trade> findByCurrency(String currency);

    // 批量关闭特定交易（修改 isOpen 字段）
    @Transactional
    @Modifying
    @Query("UPDATE Trade t SET t.isOpen = false, t.dateClose = CURRENT_TIMESTAMP WHERE t.tradeId IN :tradeIds")
    void closeTradesByIds(@Param("tradeIds") List<Long> tradeIds);

    // 批量删除交易
    void deleteByTradeIdIn(List<Long> tradeIds);

    // 查找某交易员的所有已平仓交易（分页）
    Page<Trade> findByTrader_TraderIdAndIsOpenFalse(Long traderId, Pageable pageable);
    List<Trade> findByTrader_TraderIdAndIsOpenFalse(Long traderId);

    // 查找特定交易员在某个时间范围内的所有交易（分页）
    @Query("SELECT t FROM Trade t WHERE t.trader.traderId = :traderId AND t.dateOpen BETWEEN :startDate AND :endDate")
    Page<Trade> findByTrader_TraderIdAndDateOpenBetween(
        @Param("traderId") Long traderId, 
        @Param("startDate") LocalDateTime startDate, 
        @Param("endDate") LocalDateTime endDate, 
        Pageable pageable
    );
    @Query("SELECT t FROM Trade t WHERE t.trader.traderId = :traderId AND t.dateOpen BETWEEN :startDate AND :endDate")
    List<Trade> findByTrader_TraderIdAndDateOpenBetween(
        @Param("traderId") Long traderId, 
        @Param("startDate") LocalDateTime startDate, 
        @Param("endDate") LocalDateTime endDate
    );

    // 查找指定交易员特定类型的交易（分页）
    Page<Trade> findByTrader_TraderIdAndType(Long traderId, TradeActionType type, Pageable pageable);
    List<Trade> findByTrader_TraderIdAndType(Long traderId, TradeActionType type);

    // 查找指定货币对在特定日期范围内的交易（分页）
    @Query("SELECT t FROM Trade t WHERE t.currency = :currency AND t.dateOpen BETWEEN :startDate AND :endDate")
    Page<Trade> findTradesByCurrencyAndDateRange(
        @Param("currency") String currency,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        Pageable pageable
    );
    @Query("SELECT t FROM Trade t WHERE t.currency = :currency AND t.dateOpen BETWEEN :startDate AND :endDate")
    List<Trade> findTradesByCurrencyAndDateRange(
        @Param("currency") String currency,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    // 查找某交易员所有未平仓的交易（分页）
    Page<Trade> findByTrader_TraderIdAndIsOpenTrue(Long traderId, Pageable pageable);
    List<Trade> findByTrader_TraderIdAndIsOpenTrue(Long traderId);

    // 查找某交易账户所有未平仓的交易（分页）
    Page<Trade> findByTraderAccount_AccountIdAndIsOpenTrue(Long accountId, Pageable pageable);
    List<Trade> findByTraderAccount_AccountIdAndIsOpenTrue(Long accountId);

    // 获取某个时间段内所有未平仓交易（分页）
    @Query("SELECT t FROM Trade t WHERE t.isOpen = true AND t.dateOpen BETWEEN :startDate AND :endDate")
    Page<Trade> findOpenTradesWithinDateRange(
        @Param("startDate") LocalDateTime startDate, 
        @Param("endDate") LocalDateTime endDate, 
        Pageable pageable
    );
    @Query("SELECT t FROM Trade t WHERE t.isOpen = true AND t.dateOpen BETWEEN :startDate AND :endDate")
    List<Trade> findOpenTradesWithinDateRange(
        @Param("startDate") LocalDateTime startDate, 
        @Param("endDate") LocalDateTime endDate
    );

    // 查找在特定杠杆范围内的所有交易（分页）
    Page<Trade> findByLeverageBetween(BigDecimal minLeverage, BigDecimal maxLeverage, Pageable pageable);
    List<Trade> findByLeverageBetween(BigDecimal minLeverage, BigDecimal maxLeverage);

    // 查找特定交易账户中已平仓的交易（分页）
    Page<Trade> findByTraderAccount_AccountIdAndIsOpenFalse(Long accountId, Pageable pageable);
    List<Trade> findByTraderAccount_AccountIdAndIsOpenFalse(Long accountId);
}
