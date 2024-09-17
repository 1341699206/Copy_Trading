package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.PaymentAndCommission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@Repository
public interface PaymentAndCommissionRepository extends JpaRepository<PaymentAndCommission, Long> {

    // 根据跟随者ID查找支付记录（分页）
    Page<PaymentAndCommission> findByFollower_FollowerId(Long followerId, Pageable pageable);

    // 根据交易者ID查找支付记录（分页）
    Page<PaymentAndCommission> findByTrader_TraderId(Long traderId, Pageable pageable);

    // 根据交易状态查找支付记录
    List<PaymentAndCommission> findByStatus(String status);

    // 根据支付日期范围查找支付记录（分页）
    Page<PaymentAndCommission> findByPaymentDateBetween(Date startDate, Date endDate, Pageable pageable);

    // 查找特定交易ID的支付记录，使用Optional来处理可能的null值
    Optional<PaymentAndCommission> findByTransactionId(String transactionId);

    // 根据支付金额范围查找支付记录（分页）
    Page<PaymentAndCommission> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable);

    // 根据支付状态和佣金类型查找支付记录（分页）
    Page<PaymentAndCommission> findByStatusAndCommissionType(String status, String commissionType, Pageable pageable);

    // 自定义查询：查找在特定时间范围内某跟随者的支付记录（分页）
    @Query("SELECT p FROM PaymentAndCommission p WHERE p.follower.followerId = :followerId AND p.paymentDate BETWEEN :startDate AND :endDate")
    Page<PaymentAndCommission> findPaymentsWithinDateRangeForFollower(@Param("followerId") Long followerId,
                                                                      @Param("startDate") Date startDate,
                                                                      @Param("endDate") Date endDate,
                                                                      Pageable pageable);

    // 自定义查询：查找在特定时间范围内某交易者的支付记录（分页）
    @Query("SELECT p FROM PaymentAndCommission p WHERE p.trader.traderId = :traderId AND p.paymentDate BETWEEN :startDate AND :endDate")
    Page<PaymentAndCommission> findPaymentsWithinDateRangeForTrader(@Param("traderId") Long traderId,
                                                                    @Param("startDate") Date startDate,
                                                                    @Param("endDate") Date endDate,
                                                                    Pageable pageable);

    // 批量删除指定状态的支付记录
    void deleteByStatus(String status);
}
