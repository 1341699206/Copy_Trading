package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.TradingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TradingAccountRepository extends JpaRepository<TradingAccount, Long> {

    /**
     * 根据账户状态查找交易账户
     * 
     * @param status 账户的状态（例如 "Active", "Closed"）
     * @return 匹配状态的交易账户列表
     */
    List<TradingAccount> findByStatus(String status);

    /**
     * 根据账户类型查找交易账户
     * 
     * @param accountType 账户的类型（例如 "Demo", "Real"）
     * @return 匹配类型的交易账户列表
     */
    List<TradingAccount> findByAccountType(String accountType);

    /**
     * 根据 Trader ID 查找关联的交易账户
     * 
     * @param traderId 交易员的 ID
     * @return 关联到该 Trader ID 的交易账户列表
     */
    List<TradingAccount> findByTrader_TraderId(Long traderId); // 使用属性导航符号 "_"

    /**
     * 根据 Follower ID 查找关联的交易账户
     * 
     * @param followerId 跟随者的 ID
     * @return 关联到该 Follower ID 的交易账户列表
     */
    List<TradingAccount> findByFollower_FollowerId(Long followerId); // 使用属性导航符号 "_"

    /**
     * 根据 API Key 查找唯一的交易账户
     * 
     * @param apiKey 交易账户的 API 密钥
     * @return 与给定 API Key 对应的交易账户
     */
    Optional<TradingAccount> findByApiKey(String apiKey);

    /**
     * 根据账户编号查找交易账户
     * 
     * @param accountNumber 交易账户编号
     * @return 与给定账户编号对应的交易账户
     */
    Optional<TradingAccount> findByAccountNumber(String accountNumber);
}
