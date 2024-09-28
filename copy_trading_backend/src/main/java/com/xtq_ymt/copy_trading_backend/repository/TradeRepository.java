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
import java.math.BigDecimal;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    // 根据交易账户ID查找所有交易（分页）
    Page<Trade> findByTradingAccount_AccountId(Long accountId, Pageable pageable);

    // 根据交易账户ID查找未平仓的所有交易
    List<Trade> findByTradingAccount_AccountIdAndIsOpenTrue(Long accountId);

    // 查找特定金融工具在特定交易账户的所有交易
    List<Trade> findByTradingAccount_AccountIdAndInstrument(Long accountId, String instrument);

    // 根据交易账户和时间范围查找交易
    List<Trade> findByTradingAccount_AccountIdAndOpenTimeBetween(Long accountId, Date startDate, Date endDate);

    // 自定义查询：查找在某个价格范围内的所有交易
    @Query("SELECT t FROM Trade t WHERE t.openPrice BETWEEN :minPrice AND :maxPrice")
    List<Trade> findByOpenPriceBetween(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    // 批量关闭交易
    @Modifying
    @Query("UPDATE Trade t SET t.isOpen = false, t.closeTime = CURRENT_TIMESTAMP WHERE t.tradeId IN :tradeIds")
    void closeTradesByIds(@Param("tradeIds") List<Long> tradeIds);

    // 批量删除交易
    void deleteByTradeIdIn(List<Long> tradeIds);

    // 根据时间范围查找所有未平仓交易
    @Query("SELECT t FROM Trade t WHERE t.isOpen = true AND t.openTime BETWEEN :startDate AND :endDate")
    List<Trade> findOpenTradesWithinDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
