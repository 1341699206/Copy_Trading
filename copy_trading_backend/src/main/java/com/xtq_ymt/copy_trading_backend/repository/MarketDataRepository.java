package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.MarketData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketDataRepository extends JpaRepository<MarketData, Long> {

    /**
     * 获取指定 symbol 的最新市场数据
     *
     * @param symbol 交易品种
     * @return 最新的 MarketData 对象
     */
    MarketData findTopBySymbolOrderByTimestampDesc(String symbol);

    /**
     * 获取所有 symbol 的最新市场数据
     * 
     * 使用原生 SQL 查询每个 symbol 的最新记录
     *
     * @return 每个 symbol 的最新 MarketData 列表
     */
    @Query("SELECT m FROM MarketData m WHERE m.timestamp IN (SELECT MAX(m2.timestamp) FROM MarketData m2 GROUP BY m2.symbol)")
    List<MarketData> findLatestForAllSymbols();

    /**
     * 获取指定 symbol 的历史市场数据
     * 
     * 按 timestamp 倒序排序
     *
     * @param symbol 交易品种
     * @return 指定 symbol 的 MarketData 列表
     */
    List<MarketData> findBySymbolOrderByTimestampDesc(String symbol);

    /**
     * 分页查询指定 symbol 的历史市场数据
     * 
     * 按 timestamp 排序（默认降序）
     *
     * @param symbol 交易品种
     * @param pageable 分页参数
     * @return 分页的 MarketData 列表
     */
    Page<MarketData> findBySymbol(String symbol, Pageable pageable);
}
