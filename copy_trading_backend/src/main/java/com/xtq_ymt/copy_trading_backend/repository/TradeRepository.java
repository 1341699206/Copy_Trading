package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.math.BigDecimal; // 导入 BigDecimal

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    // 根据 Trader ID 查找所有交易（分页）
    Page<Trade> findByTrader_TraderId(Long traderId, Pageable pageable);

    // 根据 Follower ID 查找所有交易（分页）
    Page<Trade> findByFollower_FollowerId(Long followerId, Pageable pageable);

    // 根据是否开仓查找交易（分页）
    Page<Trade> findByIsOpen(Boolean isOpen, Pageable pageable);

    // 查找特定交易类型的交易（例如：买入/卖出）
    List<Trade> findByTradeType(String tradeType);

    // 查找在某个时间范围内的所有交易（分页）
    Page<Trade> findByOpenTimeBetween(Date startDate, Date endDate, Pageable pageable);

    // 自定义查询：查找在某个价格范围内的所有交易
    @Query("SELECT t FROM Trade t WHERE t.openPrice BETWEEN :minPrice AND :maxPrice")
    List<Trade> findByOpenPriceBetween(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    // 查找特定金融工具的所有交易
    List<Trade> findByInstrument(String instrument);

    // 批量关闭特定交易（修改 isOpen 字段）
    @Modifying
    @Query("UPDATE Trade t SET t.isOpen = false, t.closeTime = CURRENT_TIMESTAMP WHERE t.tradeId IN :tradeIds")
    void closeTradesByIds(@Param("tradeIds") List<Long> tradeIds);

    // 批量删除交易
    void deleteByTradeIdIn(List<Long> tradeIds);

    // 查找某交易员的所有已平仓交易（分页）
    Page<Trade> findByTrader_TraderIdAndIsOpenFalse(Long traderId, Pageable pageable);

    // 查找特定交易员在某个时间范围内的所有交易
    @Query("SELECT t FROM Trade t WHERE t.trader.traderId = :traderId AND t.openTime BETWEEN :startDate AND :endDate")
    List<Trade> findByTrader_TraderIdAndOpenTimeBetween(@Param("traderId") Long traderId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // 查找指定交易员特定类型的交易（分页）
    Page<Trade> findByTrader_TraderIdAndTradeType(Long traderId, String tradeType, Pageable pageable);

    // 查找指定金融工具在特定日期范围内的交易
    @Query("SELECT t FROM Trade t WHERE t.instrument = :instrument AND t.openTime BETWEEN :startDate AND :endDate")
    List<Trade> findTradesByInstrumentAndDateRange(
        @Param("instrument") String instrument,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate
    );

    // 查找某交易员所有未平仓的交易
    List<Trade> findByTrader_TraderIdAndIsOpenTrue(Long traderId);

    // 获取某个时间段内所有未平仓交易
    @Query("SELECT t FROM Trade t WHERE t.isOpen = true AND t.openTime BETWEEN :startDate AND :endDate")
    List<Trade> findOpenTradesWithinDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
