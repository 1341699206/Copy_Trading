package com.xtq_ymt.copy_trading_backend.repository;

import java.time.LocalDate;

import com.xtq_ymt.copy_trading_backend.model.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Long> {

    // 根据id查找Trader
    Optional<Trader> findByTraderId(Long traderId);

    // 根据交易者名称查找（支持分页）
    Page<Trader> findByName(String name, Pageable pageable);

    // 根据国家ISO代码查找交易者（支持分页）
    Page<Trader> findByCountryIsoCode(String countryIsoCode, Pageable pageable);

    // 根据活跃状态查找交易者（支持分页）
    Page<Trader> findByActive(Boolean active, Pageable pageable);

    // 查找评分前10的交易者
    List<Trader> findTop10ByOrderByTraderRateDesc();

    // 自定义查询：查找特定国家中按总利润降序排序的交易者
    @Query("SELECT t FROM Trader t WHERE t.countryIsoCode = :countryIsoCode ORDER BY t.totalProfit DESC")
    List<Trader> findTopByCountrySortedByProfit(@Param("countryIsoCode") String countryIsoCode);

    // 查找所有策略已批准的交易者
    List<Trader> findByStrategyDescApprovedTrue();

    // 查找所有交易历史公开的交易者
    List<Trader> findByPublicTradeHistoryTrue();

    // 批量更新特定交易者的活跃状态
    @Modifying
    @Query("UPDATE Trader t SET t.active = :active WHERE t.traderId IN :ids")
    void updateActiveStatusByIds(@Param("active") Boolean active, @Param("ids") List<Long> ids);

    // 批量删除特定交易者
    void deleteByTraderIdIn(List<Long> ids);

    // 根据ZuluTrade平台账户ID查找交易者
    Trader findByZuluAccountId(Long zuluAccountId);

    // 查找最近登录的交易者（支持分页）
    List<Trader> findByLoggedInRecentlyTrue(Pageable pageable);

    // 查询历史回报率排序前 n 的交易者（无时间限制）
    @Query("SELECT t FROM Trader t ORDER BY t.ROI DESC")
    List<Trader> findTopByOrderByROIDesc(Pageable pageable);

    //暂时未处理的待定逻辑

    // // 查找特定时间段内回报率（ROI）排序前 n 的交易者
    // @Query("SELECT t FROM Trader t WHERE t.date >= :startDate AND t.date <= :endDate ORDER BY t.roi DESC")
    // List<Trader> findTopByOrderByROIDesc(
    //         @Param("startDate") LocalDate startDate,
    //         @Param("endDate") LocalDate endDate,
    //         Pageable pageable);

    // 查找在特定日期范围内活跃的交易者
    @Query("SELECT t FROM Trader t WHERE t.lastUpdatedDate BETWEEN :startDate AND :endDate")
    List<Trader> findActiveTradersWithinDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // 根据最近更新日期查找（支持分页）
    Page<Trader> findByLastUpdatedDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // 根据交易者排名查找前N名（支持分页）
    Page<Trader> findByZuluRankLessThanEqual(Integer rank, Pageable pageable);

    // 查找跟随者数量大于指定数量的交易者（支持分页）
    Page<Trader> findByFollowersGreaterThan(Integer followers, Pageable pageable);

    // 查找最近一段时间内没有交易的交易者（支持分页）
    @Query("SELECT t FROM Trader t WHERE t.lastOpenTradeDate < :cutoffDate")
    Page<Trader> findTradersInactiveSince(@Param("cutoffDate") LocalDateTime cutoffDate, Pageable pageable);

    // 根据 email 查找 Trader
    Optional<Trader> findByEmail(String email); // 添加这一行
}
