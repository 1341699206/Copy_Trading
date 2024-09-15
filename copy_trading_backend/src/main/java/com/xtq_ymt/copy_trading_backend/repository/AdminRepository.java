package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.Admin;
import com.xtq_ymt.copy_trading_backend.model.AdminRole;
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

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // 根据用户名查找管理员
    Optional<Admin> findByUsername(String username);

    // 根据电子邮件查找管理员
    Optional<Admin> findByEmail(String email);

    // 根据角色查找管理员（支持分页）
    Page<Admin> findByRole(AdminRole role, Pageable pageable);

    // 查找所有活跃的管理员（支持分页）
    Page<Admin> findByIsActiveTrue(Pageable pageable);

    // 查找所有不活跃的管理员（支持分页）
    Page<Admin> findByIsActiveFalse(Pageable pageable);

    // 自定义查询：查找特定角色的所有管理员
    @Query("SELECT a FROM Admin a WHERE a.role = :role")
    List<Admin> findAdminsByRole(@Param("role") AdminRole role);

    // 批量更新管理员的激活状态
    @Modifying
    @Query("UPDATE Admin a SET a.isActive = :status WHERE a.adminId IN :ids")
    void updateAdminStatusByIds(@Param("status") Boolean status, @Param("ids") List<Long> ids);

    // 批量删除指定的管理员
    void deleteByAdminIdIn(List<Long> ids);

    // 查找最近登录的管理员（支持分页）
    Page<Admin> findByLastLoginAfter(Date date, Pageable pageable);

    // 根据角色和激活状态查找管理员（支持分页）
    Page<Admin> findByRoleAndIsActiveTrue(AdminRole role, Pageable pageable);

    // 自定义查询：查找某个时间之后所有登录的管理员
    @Query("SELECT a FROM Admin a WHERE a.lastLogin > :lastLoginDate")
    List<Admin> findAdminsLoggedInAfter(@Param("lastLoginDate") Date lastLoginDate);

    // 批量激活管理员
    @Modifying
    @Query("UPDATE Admin a SET a.isActive = true WHERE a.adminId IN :ids")
    void activateAdmins(@Param("ids") List<Long> ids);

    // 批量停用管理员
    @Modifying
    @Query("UPDATE Admin a SET a.isActive = false WHERE a.adminId IN :ids")
    void deactivateAdmins(@Param("ids") List<Long> ids);
}
