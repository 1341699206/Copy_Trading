package com.xtq_ymt.copy_trading_backend.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Follower {

    // 基本信息字段
    private Long followerId; // 跟随者的唯一标识符
    private String name; // 跟随者的用户名或显示名称
    private String email; // 跟随者的电子邮件
    private String country; // 跟随者所在的国家
    private Date registrationDate; // 注册日期
    private Boolean isActive; // 是否活跃

    // 跟随交易相关字段
    private List<Trader> followingTraders; // 正在跟随的交易员列表
    private Double totalInvestment; // 跟随交易的总金额
    private Double currentBalance; // 当前账户余额
    private Double profitLoss; // 当前的总利润或损失
    private RiskManagementSettings riskSettings; // 风险管理设置（如最大可接受亏损）

    // 策略参数
    private  HashMap<Trader,Double> allocationPercentage; // 资金分配比例
    private Boolean autoAdjust; // 是否自动调整策略

    // 构造函数
    public Follower() {
    }

    // 带所有字段的构造函数
    public Follower(Long followerId, String name, String email, String country, Date registrationDate, Boolean isActive,
                    List<Trader> followingTraders, Double totalInvestment, Double currentBalance, Double profitLoss,
                    RiskManagementSettings riskSettings, HashMap<Trader,Double> allocationPercentage, Boolean autoAdjust) {
        this.followerId = followerId;
        this.name = name;
        this.email = email;
        this.country = country;
        this.registrationDate = registrationDate;
        this.isActive = isActive;
        this.followingTraders = followingTraders;
        this.totalInvestment = totalInvestment;
        this.currentBalance = currentBalance;
        this.profitLoss = profitLoss;
        this.riskSettings = riskSettings;
        this.allocationPercentage = allocationPercentage;
        this.autoAdjust = autoAdjust;
    }

    // Getter 和 Setter 方法将由 Lombok 自动生成
}
