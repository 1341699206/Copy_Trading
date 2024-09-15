package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.RiskManagementSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface RiskManagementSettingsRepository extends JpaRepository<RiskManagementSettings, Long> {

    // 查找启用了特定止损百分比的风险管理设置（支持分页）
    Page<RiskManagementSettings> findByIsStopLossPercentageTrue(Pageable pageable);

    // 查找启用了止损限额的风险管理设置（支持分页）
    Page<RiskManagementSettings> findByIsStopLossThresholdTrue(Pageable pageable);

    // 查找启用了移动止损的风险管理设置（支持分页）
    Page<RiskManagementSettings> findByEnableTrailingStopTrue(Pageable pageable);

    // 查找指定的止损百分比范围内的风险管理设置（支持分页）
    Page<RiskManagementSettings> findByStopLossPercentageBetween(Double minPercentage, Double maxPercentage, Pageable pageable);

    // 查找指定的止损阈值范围内的风险管理设置（支持分页）
    Page<RiskManagementSettings> findByStopLossThresholdBetween(Double minThreshold, Double maxThreshold, Pageable pageable);

    // 根据不参与交易的锁定资金来查找风险管理设置（支持分页）
    Page<RiskManagementSettings> findByCapitalProtectionGreaterThan(Double capitalProtection, Pageable pageable);

    // 查找启用了特定止损百分比的风险管理设置
    List<RiskManagementSettings> findByIsStopLossPercentageTrue();

    // 查找启用了止损限额的风险管理设置
    List<RiskManagementSettings> findByIsStopLossThresholdTrue();

    // 查找启用了移动止损的风险管理设置
    List<RiskManagementSettings> findByEnableTrailingStopTrue();

    // 查找指定的止损百分比范围内的风险管理设置
    List<RiskManagementSettings> findByStopLossPercentageBetween(Double minPercentage, Double maxPercentage);

    // 查找指定的止损阈值范围内的风险管理设置
    List<RiskManagementSettings> findByStopLossThresholdBetween(Double minThreshold, Double maxThreshold);

    // 根据不参与交易的锁定资金来查找风险管理设置
    List<RiskManagementSettings> findByCapitalProtectionGreaterThan(Double capitalProtection);


    // 自定义查询：查找止损百分比大于某个值且锁定资金大于某个值的风险管理设置
    @Query("SELECT r FROM RiskManagementSettings r WHERE r.stopLossPercentage > :percentage AND r.capitalProtection > :protection")
    List<RiskManagementSettings> findByComplexCondition(@Param("percentage") Double percentage, @Param("protection") Double protection);
}
