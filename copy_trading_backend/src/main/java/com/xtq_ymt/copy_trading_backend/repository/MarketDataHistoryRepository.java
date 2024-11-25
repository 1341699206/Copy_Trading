package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.MarketDataHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.math.BigDecimal; // 导入 BigDecimal

@Repository
public interface MarketDataHistoryRepository extends JpaRepository<MarketDataHistory, Long> {

    // 分页查找特定金融工具的所有历史记录
    Page<MarketDataHistory> findByInstrument(String instrument, Pageable pageable);

    // 分页查找特定金融工具在某个时间段内的历史记录
    Page<MarketDataHistory> findByInstrumentAndTimestampBetween(String instrument, Date startDate, Date endDate, Pageable pageable);

    // 分页查找在指定时间段内的所有历史记录
    Page<MarketDataHistory> findByTimestampBetween(Date startDate, Date endDate, Pageable pageable);

    // 分页查找在某个价格范围内的历史记录
    Page<MarketDataHistory> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    // 自定义查询：分页查找在特定时间范围内的特定金融工具的历史记录
    @Query("SELECT m FROM MarketDataHistory m WHERE m.instrument = :instrument AND m.timestamp BETWEEN :startDate AND :endDate")
    Page<MarketDataHistory> findHistoryByInstrumentAndDateRange(
        @Param("instrument") String instrument,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate,
        Pageable pageable
    );
}
