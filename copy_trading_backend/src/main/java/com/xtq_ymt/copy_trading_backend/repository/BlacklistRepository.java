package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {

    // 根据 userId 查找黑名单记录
    Blacklist findByUserId(Long userId);

    // 根据 userId 删除黑名单记录
    void deleteByUserId(Long userId);
}
