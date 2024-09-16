package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.TradingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TradingAccountRepository extends JpaRepository<TradingAccount, Long> {
    
    // 根据账户状态查找交易账户
    List<TradingAccount> findByStatus(String status);
    
    // 根据账户类型查找交易账户
    List<TradingAccount> findByAccountType(String accountType);
    
    // 根据关联的Trader查找交易账户
    List<TradingAccount> findByTraderId(Long traderId);
    
    // 根据关联的Follower查找交易账户
    List<TradingAccount> findByFollowerId(Long followerId);
    
    // 根据API Key查找交易账户
    Optional<TradingAccount> findByApiKey(String apiKey);
}
