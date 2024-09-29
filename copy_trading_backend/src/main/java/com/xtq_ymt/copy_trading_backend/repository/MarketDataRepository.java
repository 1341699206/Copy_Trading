package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.MarketData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Date;
import java.util.List;
import java.math.BigDecimal; // 确保导入 BigDecimal

@Repository
public interface MarketDataRepository extends JpaRepository<MarketData, Long> {

    // 根据市场工具名称查找MarketData（支持分页）
    Page<MarketData> findByInstrument(String instrument, Pageable pageable);

    // 根据symbol查找MarketData（支持分页）
    Page<MarketData> findBySymbol(String symbol, Pageable pageable);

    // 根据时间戳查找在某个时间范围内的MarketData（支持分页）
    Page<MarketData> findByTimestampBetween(Date startDate, Date endDate, Pageable pageable);

    // 查找指定市场工具名称和在某个时间范围内的MarketData（支持分页）
    Page<MarketData> findByInstrumentAndTimestampBetween(String instrument, Date startDate, Date endDate, Pageable pageable);

    // 根据symbol和时间范围查找MarketData（支持分页）
    Page<MarketData> findBySymbolAndTimestampBetween(String symbol, Date startDate, Date endDate, Pageable pageable);

    // 查找最高价格大于指定值的MarketData
    List<MarketData> findByHighPriceGreaterThan(BigDecimal highPrice);

    // 查找交易量小于指定值的MarketData
    List<MarketData> findByVolumeLessThan(BigDecimal volume);

    // 查找市场波动率大于指定值的MarketData
    List<MarketData> findByVolatilityGreaterThan(BigDecimal volatility); // 修改参数类型为 BigDecimal

    // 按时间戳降序排序，查找最近的MarketData（分页）
    Page<MarketData> findAllByOrderByTimestampDesc(Pageable pageable);

    // 自定义查询：查找在特定时间范围内的特定金融工具的历史记录
    @Query("SELECT m FROM MarketData m WHERE m.instrument = :instrument AND m.timestamp BETWEEN :startDate AND :endDate")
    Page<MarketData> findHistoryByInstrumentAndDateRange(
        @Param("instrument") String instrument,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate,
        Pageable pageable
    );

    // 自定义查询：根据symbol查找MarketData
    @Query("SELECT m FROM MarketData m WHERE m.symbol = :symbol AND m.timestamp BETWEEN :startDate AND :endDate")
    Page<MarketData> findHistoryBySymbolAndDateRange(
        @Param("symbol") String symbol,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate,
        Pageable pageable
    );

    // 批量删除在指定时间范围内的MarketData
    @Modifying
    @Query("DELETE FROM MarketData m WHERE m.timestamp BETWEEN :startDate AND :endDate")
    void deleteByTimestampBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
