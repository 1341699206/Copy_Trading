package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.TraderScripts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Date;

@Repository
public interface TraderScriptsRepository extends JpaRepository<TraderScripts, Long> {

    // 根据交易者ID查找所有脚本（支持分页）
    Page<TraderScripts> findByTraderTraderId(Long traderId, Pageable pageable);

    // 根据脚本名称查找特定交易者的脚本（支持分页）
    Page<TraderScripts> findByTraderTraderIdAndScriptNameContaining(Long traderId, String scriptName, Pageable pageable);

    // 查找所有激活的脚本（支持分页）
    Page<TraderScripts> findByIsActiveTrue(Pageable pageable);

    // 自定义查询：查找特定交易者在指定时间范围内的脚本
    @Query("SELECT ts FROM TraderScripts ts WHERE ts.trader.traderId = :traderId AND ts.createdAt BETWEEN :startDate AND :endDate")
    List<TraderScripts> findScriptsByTraderAndDateRange(
        @Param("traderId") Long traderId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate
    );

    // 批量更新脚本的激活状态
    @Modifying
    @Query("UPDATE TraderScripts ts SET ts.isActive = :status WHERE ts.scriptId IN :ids")
    void updateScriptStatusByIds(@Param("status") Boolean status, @Param("ids") List<Long> ids);

    // 批量删除指定的脚本
    void deleteByScriptIdIn(List<Long> ids);

    // 查找最近更新的脚本（支持分页）
    Page<TraderScripts> findByUpdatedAtAfter(Date date, Pageable pageable);

    // 查找指定交易者的激活脚本（支持分页）
    Page<TraderScripts> findByTraderTraderIdAndIsActiveTrue(Long traderId, Pageable pageable);

    // 查找指定交易者的脚本，并根据创建时间降序排序（支持分页）
    Page<TraderScripts> findByTraderTraderIdOrderByCreatedAtDesc(Long traderId, Pageable pageable);

    // 根据交易者ID和语言查找脚本（支持分页）
    Page<TraderScripts> findByTraderTraderIdAndLanguage(Long traderId, String language, Pageable pageable);

    // 自定义查询：查找最近一个月更新的Python脚本（支持分页）
    @Query("SELECT ts FROM TraderScripts ts WHERE ts.language = 'Python' AND ts.updatedAt > :lastMonth")
    Page<TraderScripts> findRecentPythonScripts(@Param("lastMonth") Date lastMonth, Pageable pageable);
}
