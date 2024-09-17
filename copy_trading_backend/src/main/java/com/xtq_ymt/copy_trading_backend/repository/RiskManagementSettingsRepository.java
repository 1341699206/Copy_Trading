package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.RiskManagementSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.math.BigDecimal; // 导入 BigDecimal

@Repository
public interface RiskManagementSettingsRepository extends JpaRepository<RiskManagementSettings, Long> {

    // 查找启用了特定止损百分比的风险管理设置（支持分页）
    Page<RiskManagementSettings> findByIsStopLossPercentageTrue(Pageable pageable);

    // 查找启用了止损限额的风险管理设置（支持分页）
    Page<RiskManagementSettings> findByIsStopLossThresholdTrue(Pageable pageable);

    // 查找启用了移动止损的风险管理设置（支持分页）
    Page<RiskManagementSettings> findByEnableTrailingStopTrue(Pageable pageable);

    // 查找指定的止损百分比范围内的风险管理设置（支持分页）
    Page<RiskManagementSettings> findByStopLossPercentageBetween(BigDecimal minPercentage, BigDecimal maxPercentage, Pageable pageable);

    // 查找指定的止损阈值范围内的风险管理设置（支持分页）
    Page<RiskManagementSettings> findByStopLossThresholdBetween(BigDecimal minThreshold, BigDecimal maxThreshold, Pageable pageable);

    // 根据不参与交易的锁定资金来查找风险管理设置（支持分页）
    Page<RiskManagementSettings> findByCapitalProtectionGreaterThan(BigDecimal capitalProtection, Pageable pageable);

    // 查找启用了特定止损百分比的风险管理设置
    List<RiskManagementSettings> findByIsStopLossPercentageTrue();

    // 查找启用了止损限额的风险管理设置
    List<RiskManagementSettings> findByIsStopLossThresholdTrue();

    // 查找启用了移动止损的风险管理设置
    List<RiskManagementSettings> findByEnableTrailingStopTrue();

    // 查找指定的止损百分比范围内的风险管理设置
    List<RiskManagementSettings> findByStopLossPercentageBetween(BigDecimal minPercentage, BigDecimal maxPercentage);

    // 查找指定的止损阈值范围内的风险管理设置
    List<RiskManagementSettings> findByStopLossThresholdBetween(BigDecimal minThreshold, BigDecimal maxThreshold);

    // 根据不参与交易的锁定资金来查找风险管理设置
    List<RiskManagementSettings> findByCapitalProtectionGreaterThan(BigDecimal capitalProtection);

    // 自定义查询：查找止损百分比大于某个值且锁定资金大于某个值的风险管理设置
    @Query("SELECT r FROM RiskManagementSettings r WHERE r.stopLossPercentage > :percentage AND r.capitalProtection > :protection")
    List<RiskManagementSettings> findByComplexCondition(@Param("percentage") BigDecimal percentage, @Param("protection") BigDecimal protection);
}
