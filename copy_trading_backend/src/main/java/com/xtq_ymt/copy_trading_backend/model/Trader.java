package com.xtq_ymt.copy_trading_backend.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trader {

    // 基本信息字段
    private Long traderId; // 交易者的唯一标识符
    private String name; // 交易者的用户名或显示名称
    private String countryName; // 交易者所在的国家名称
    private String countryIsoCode; // 国家ISO代码
    private Long zuluAccountId; // ZuluTrade平台内的账户ID
    private Double traderRate; // 交易者的评分
    private Integer rateCount; // 评分次数
    private Long brokerAccountId; // 关联的经纪商账户ID

    // 状态标志字段
    private Boolean strategyDescApproved; // 策略描述是否已批准
    private Boolean publicTradeHistory; // 交易历史是否公开
    private Boolean addressVerified; // 地址是否已验证
    private Boolean phoneVerified; // 电话是否已验证
    private Boolean identificationVerified; // 身份是否已验证
    private Boolean hasLiveAccount; // 是否有真实账户
    private Boolean photoApproved; // 头像是否已批准
    private Boolean active; // 是否处于活跃状态
    private Boolean loggedInRecently; // 最近是否登录过
    private Boolean videoApproved; // 视频认证是否已批准
    private Boolean profitSharingTraderOnly; // 是否仅参与利润分享模式

    // 交易表现数据
    private Double leverage; // 杠杆倍数
    private Date lastOpenTradeDate; // 最后开仓交易的时间
    private Date lastUpdatedDate; // 账户的最后更新日期
    private Integer zuluRank; // 交易者在平台上的排名
    private Double amountFollowing; // 跟随该交易者的总金额（美元）
    private Double ROI; // 投资回报率
    private Double totalProfit; // 总利润
    private Double totalProfitMoney; // 总盈利或亏损金额
    private Integer weeks; // 活跃周数
    private Double maximumDrawdown; // 最大回撤金额
    private Double maximumDrawdownPercent; // 最大回撤百分比
    private Integer followers; // 跟随者数量
    private Integer viewed; // 被浏览的总次数
    private Integer trades; // 交易总数
    private Integer maxOpenTrades; // 最大未平仓交易数
    private Integer winTradesCount; // 赢得的交易次数
    private Integer winTradesCountInMoney; // 以金额计算的获胜交易次数
    private Double avgTradeSeconds; // 每笔交易的平均持仓时间（秒）
    private Double avgPipsPerTrade; // 每笔交易的平均点数
    private Double profitPercentage; // 利润百分比
    private Integer nme; // 可能表示交易者的某个统计数据
    private Double uninterruptedLiveFollowerProfit; // 未中断的跟随者利润
    private Double interruptedLiveFollowerProfit; // 中断的跟随者利润
    private String performance; // 表现历史数据的JSON字符串

    // 构造函数
    public Trader() {
    }

    // 带所有字段的构造函数
    public Trader(Long traderId, String name, String countryName, String countryIsoCode, Long zuluAccountId, 
                  Double traderRate, Integer rateCount, Long brokerAccountId, Boolean strategyDescApproved, 
                  Boolean publicTradeHistory, Boolean addressVerified, Boolean phoneVerified, 
                  Boolean identificationVerified, Boolean hasLiveAccount, Boolean photoApproved, Boolean active, 
                  Boolean loggedInRecently, Boolean videoApproved, Boolean profitSharingTraderOnly, Double leverage, 
                  Date lastOpenTradeDate, Date lastUpdatedDate, Integer zuluRank, Double amountFollowing, Double ROI, 
                  Double totalProfit, Double totalProfitMoney, Integer weeks, Double maximumDrawdown, 
                  Double maximumDrawdownPercent, Integer followers, Integer viewed, Integer trades, 
                  Integer maxOpenTrades, Integer winTradesCount, Integer winTradesCountInMoney, Double avgTradeSeconds, 
                  Double avgPipsPerTrade, Double profitPercentage, Integer nme, Double uninterruptedLiveFollowerProfit, 
                  Double interruptedLiveFollowerProfit, String performance) {
        this.traderId = traderId;
        this.name = name;
        this.countryName = countryName;
        this.countryIsoCode = countryIsoCode;
        this.zuluAccountId = zuluAccountId;
        this.traderRate = traderRate;
        this.rateCount = rateCount;
        this.brokerAccountId = brokerAccountId;
        this.strategyDescApproved = strategyDescApproved;
        this.publicTradeHistory = publicTradeHistory;
        this.addressVerified = addressVerified;
        this.phoneVerified = phoneVerified;
        this.identificationVerified = identificationVerified;
        this.hasLiveAccount = hasLiveAccount;
        this.photoApproved = photoApproved;
        this.active = active;
        this.loggedInRecently = loggedInRecently;
        this.videoApproved = videoApproved;
        this.profitSharingTraderOnly = profitSharingTraderOnly;
        this.leverage = leverage;
        this.lastOpenTradeDate = lastOpenTradeDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.zuluRank = zuluRank;
        this.amountFollowing = amountFollowing;
        this.ROI = ROI;
        this.totalProfit = totalProfit;
        this.totalProfitMoney = totalProfitMoney;
        this.weeks = weeks;
        this.maximumDrawdown = maximumDrawdown;
        this.maximumDrawdownPercent = maximumDrawdownPercent;
        this.followers = followers;
        this.viewed = viewed;
        this.trades = trades;
        this.maxOpenTrades = maxOpenTrades;
        this.winTradesCount = winTradesCount;
        this.winTradesCountInMoney = winTradesCountInMoney;
        this.avgTradeSeconds = avgTradeSeconds;
        this.avgPipsPerTrade = avgPipsPerTrade;
        this.profitPercentage = profitPercentage;
        this.nme = nme;
        this.uninterruptedLiveFollowerProfit = uninterruptedLiveFollowerProfit;
        this.interruptedLiveFollowerProfit = interruptedLiveFollowerProfit;
        this.performance = performance;
    }

    // Getter 和 Setter 方法...

    // 为每个字段生成相应的 Getter 和 Setter 方法，可以使用 IDE 自动生成。

}
