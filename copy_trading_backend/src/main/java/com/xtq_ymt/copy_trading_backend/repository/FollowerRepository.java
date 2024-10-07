package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.Follower;
import com.xtq_ymt.copy_trading_backend.model.RiskManagementSettings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.math.BigDecimal; // 导入 BigDecimal

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {

    //根据id查找Follower
    Optional<Follower> findByFollowerId(Long followerId);

    // 根据name查找Follower（支持分页）
    Page<Follower> findByName(String name, Pageable pageable);

    // 根据isActive查找活跃的Follower（支持分页）
    Page<Follower> findByIsActive(Boolean isActive, Pageable pageable);

    // 根据email查找唯一的Follower，使用Optional处理可能的null值
    Optional<Follower> findByEmail(String email);

    // 根据totalInvestment大于某个值查找Follower（支持分页）
    Page<Follower> findByTotalInvestmentGreaterThan(BigDecimal amount, Pageable pageable);

    // 根据name查找Follower，按注册日期升序排序
    List<Follower> findByNameOrderByRegistrationDateAsc(String name);

    // 根据isActive查找活跃的Follower，按总投资降序排序
    List<Follower> findByIsActiveOrderByTotalInvestmentDesc(Boolean isActive);

    // 根据国家查找Follower
    List<Follower> findByCountry(String country);

    // 查找在某个时间范围内注册的Follower
    List<Follower> findByRegistrationDateBetween(Date startDate, Date endDate);

    // 查找所有活跃的Follower
    List<Follower> findByIsActiveTrue();

    // 查找所有不活跃的Follower
    List<Follower> findByIsActiveFalse();

    // 批量删除所有不活跃的Follower
    void deleteByIsActiveFalse();

    // 

    // 批量激活或停用Follower
    @Modifying
    @Query("UPDATE Follower f SET f.isActive = :status WHERE f.followerId IN :ids")
    void updateFollowerStatusByIds(@Param("status") Boolean status, @Param("ids") List<Long> ids);

    // 批量更新总投资
    @Modifying
    @Query("UPDATE Follower f SET f.totalInvestment = f.totalInvestment + :adjustment WHERE f.followerId IN :ids")
    void adjustTotalInvestmentByIds(@Param("adjustment") BigDecimal adjustment, @Param("ids") List<Long> ids);

    // 批量删除Follower
    void deleteAllByFollowerIdIn(List<Long> ids);

    // 批量查找Follower
    List<Follower> findByFollowerIdIn(List<Long> ids);

    // 批量更新风险管理设置
    @Modifying
    @Query("UPDATE Follower f SET f.riskSettings = :newSettings WHERE f.followerId IN :ids")
    void updateRiskSettingsByIds(@Param("newSettings") RiskManagementSettings newSettings, @Param("ids") List<Long> ids);
}
