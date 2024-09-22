package com.xtq_ymt.copy_trading_backend.model;

import jakarta.persistence.*; // 导入 JPA 注解
import java.util.List;
import java.util.Date;
import java.math.BigDecimal; // 导入 BigDecimal
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor  // 自动生成包含所有字段的构造函数
@NoArgsConstructor   // 自动生成无参构造函数
@Entity // 标识该类为JPA实体
@Table(name = "trader") // 指定表名
public class Trader {

    // 基本信息字段
    @Id // 标识主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略
    @Column(name = "trader_id") // 指定列名
    private Long traderId; // 交易者的唯一标识符

    @OneToMany(mappedBy = "trader", cascade = CascadeType.ALL)
    private List<TradingAccount> tradingAccounts;

    @Column(name = "name", nullable = false) // 指定列名，并设置为非空
    private String name; // 交易者的用户名或显示名称

    @Column(name = "password", nullable = false)  // 新增密码字段
    private String password; // 加密后的密码

    @Column(name = "email", nullable = false, unique = true) // 新增 email 字段，确保唯一性和非空约束
    private String email; // 交易者的邮箱地址

    @Column(name = "country_name") // 指定列名
    private String countryName; // 交易者所在的国家名称

    @Column(name = "country_iso_code") // 指定列名
    private String countryIsoCode; // 国家ISO代码

    @Column(name = "zulu_account_id", unique = true) // 指定列名，并设置唯一约束
    private Long zuluAccountId; // ZuluTrade平台内的账户ID

    // 修改为 BigDecimal，并使用 columnDefinition
    @Column(name = "trader_rate", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal traderRate; // 交易者的评分

    @Column(name = "rate_count") // 指定列名
    private Integer rateCount; // 评分次数

    @Column(name = "broker_account_id") // 指定列名
    private Long brokerAccountId; // 关联的经纪商账户ID

    // 状态标志字段
    @Column(name = "strategy_desc_approved")
    private Boolean strategyDescApproved; // 策略描述是否已批准

    @Column(name = "public_trade_history")
    private Boolean publicTradeHistory; // 交易历史是否公开

    @Column(name = "address_verified")
    private Boolean addressVerified; // 地址是否已验证

    @Column(name = "phone_verified")
    private Boolean phoneVerified; // 电话是否已验证

    @Column(name = "identification_verified")
    private Boolean identificationVerified; // 身份是否已验证

    @Column(name = "has_live_account")
    private Boolean hasLiveAccount; // 是否有真实账户

    @Column(name = "photo_approved")
    private Boolean photoApproved; // 头像是否已批准

    @Column(name = "active")
    private Boolean active; // 是否处于活跃状态

    @Column(name = "logged_in_recently")
    private Boolean loggedInRecently; // 最近是否登录过

    @Column(name = "video_approved")
    private Boolean videoApproved; // 视频认证是否已批准

    @Column(name = "profit_sharing_trader_only")
    private Boolean profitSharingTraderOnly; // 是否仅参与利润分享模式

    // 交易表现数据
    @Column(name = "leverage", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal leverage; // 杠杆倍数

    @Column(name = "last_open_trade_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastOpenTradeDate; // 最后开仓交易的时间

    @Column(name = "last_updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDate; // 账户的最后更新日期

    @Column(name = "zulu_rank")
    private Integer zuluRank; // 交易者在平台上的排名

    @Column(name = "amount_following", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal amountFollowing; // 跟随该交易者的总金额（美元）

    @Column(name = "roi", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal ROI; // 投资回报率

    @Column(name = "total_profit", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal totalProfit; // 总利润

    @Column(name = "total_profit_money", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal totalProfitMoney; // 总盈利或亏损金额

    @Column(name = "weeks")
    private Integer weeks; // 活跃周数

    @Column(name = "maximum_drawdown", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal maximumDrawdown; // 最大回撤金额

    @Column(name = "maximum_drawdown_percent", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal maximumDrawdownPercent; // 最大回撤百分比

    @Column(name = "followers")
    private Integer followers; // 跟随者数量

    @Column(name = "viewed")
    private Integer viewed; // 被浏览的总次数

    @Column(name = "trades")
    private Integer trades; // 交易总数

    @Column(name = "max_open_trades")
    private Integer maxOpenTrades; // 最大未平仓交易数

    @Column(name = "win_trades_count")
    private Integer winTradesCount; // 赢得的交易次数

    @Column(name = "win_trades_count_in_money")
    private Integer winTradesCountInMoney; // 以金额计算的获胜交易次数

    @Column(name = "avg_trade_seconds", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal avgTradeSeconds; // 每笔交易的平均持仓时间（秒）

    @Column(name = "avg_pips_per_trade", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal avgPipsPerTrade; // 每笔交易的平均点数

    @Column(name = "profit_percentage", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal profitPercentage; // 利润百分比

    @Column(name = "nme")
    private Integer nme; // 可能表示交易者的某个统计数据

    @Column(name = "uninterrupted_live_follower_profit", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal uninterruptedLiveFollowerProfit; // 未中断的跟随者利润

    @Column(name = "interrupted_live_follower_profit", columnDefinition = "DECIMAL(18,8)")
    private BigDecimal interruptedLiveFollowerProfit; // 中断的跟随者利润

    @Column(name = "performance", columnDefinition = "TEXT")
    private String performance; // 表现历史数据的JSON字符串
}
