package com.xtq_ymt.copy_trading_backend.service.impl;

import com.xtq_ymt.copy_trading_backend.model.*;
import com.xtq_ymt.copy_trading_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class TradeSimulationService {

    @Autowired
    private TraderRepository traderRepository;

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private CopyTradingRepository copyTradingRepository;

    @Autowired
    private MarketDataRepository marketDataRepository;  // 添加 MarketDataRepository 依赖

    private static final String[] INSTRUMENTS = {
        "EURJPY", "EURCHF", "CADJPY", "EURUSD", "USDCAD", "GBPUSD", "USDJPY", "EURGBP", "USDCHF", "GBPJPY",
        "EURCAD", "GBPCHF", "CADCHF"
    };

    private static final Random random = new Random();

    /**
     * 为每个Trader生成交易记录并更新CopyTrading跟单数据
     */
    public void generateTradesAndCopyTrading() {
        List<Trader> traders = traderRepository.findAll();
        List<Follower> followers = followerRepository.findAll();

        for (Trader trader : traders) {
            // Trader交易账户
            TradingAccount traderAccount = trader.getCurrentAccount();
            if (traderAccount == null) continue;

            // 生成Trader的交易记录（带有合理的开仓和平仓时间间隔）
            Trade traderTrade = createTrade(trader, traderAccount, true);
            tradeRepository.save(traderTrade);

            // 为每个follower生成跟单记录
            for (Follower follower : followers) {
                // 检查该follower是否在跟随该trader
                CopyTrading copyTrading = getOrCreateCopyTrading(follower, trader, follower.getCurrentAccount());
                if (copyTrading == null) continue;

                // Follower的交易账户
                TradingAccount followerAccount = follower.getCurrentAccount();
                if (followerAccount == null) continue;

                // 生成跟单的Trade记录
                Trade followerTrade = createTrade(trader, followerAccount, false);
                followerTrade.setCopyTrading(copyTrading);
                followerTrade.setFollower(follower);
                tradeRepository.save(followerTrade);

                // 更新CopyTrading记录中的收益和损失
                updateCopyTradingResults(copyTrading, followerTrade);
            }
        }
    }

    /**
     * 创建交易记录
     */
    private Trade createTrade(Trader trader, TradingAccount account, boolean isOriginalTrade) {
        Trade trade = new Trade();
        trade.setTraderAccount(account);  // Trader的账户
        trade.setTrader(trader);

        // 获取市场数据
        MarketData marketData = marketDataRepository.findById(INSTRUMENTS[random.nextInt(INSTRUMENTS.length)]).orElse(null);
        if (marketData != null) {
            trade.setInstrument(marketData.getInstrument());
            trade.setOpenPrice(marketData.getOpenPrice());
            trade.setClosePrice(marketData.getClosePrice());
        } else {
            // 如果没有市场数据，随机生成价格
            trade.setInstrument(INSTRUMENTS[random.nextInt(INSTRUMENTS.length)]);
            trade.setOpenPrice(generateRandomPrice());
            trade.setClosePrice(generateRandomPrice());
        }

        trade.setVolume(new BigDecimal("1.00"));
        trade.setProfitLoss(calculateProfitLoss(trade.getOpenPrice(), trade.getClosePrice(), trade.getVolume()));
        trade.setCommission(new BigDecimal("2.00"));
        trade.setTradeType(isOriginalTrade ? "ORIGINAL" : "COPY");
        trade.setIsOpen(false); // 设置交易已关闭状态

        // 设置开仓和平仓时间，增加合理的间隔
        Date openTime = new Date();
        trade.setOpenTime(openTime);
        Date closeTime = new Date(openTime.getTime() + TimeUnit.HOURS.toMillis(random.nextInt(24) + 1)); // 随机生成1到24小时的间隔
        trade.setCloseTime(closeTime);

        return trade;
    }

    /**
     * 获取或创建CopyTrading跟单记录
     */
    private CopyTrading getOrCreateCopyTrading(Follower follower, Trader trader, TradingAccount followerAccount) {
        Optional<CopyTrading> optionalCopyTrading = copyTradingRepository.findByFollowerFollowerIdAndTraderTraderIdAndFollowerAccountAccountId(
            follower.getFollowerId(), trader.getTraderId(), followerAccount.getAccountId());

        if (optionalCopyTrading.isPresent()) {
            return optionalCopyTrading.get();
        }

        CopyTrading copyTrading = CopyTrading.builder()
            .follower(follower)
            .trader(trader)
            .followerAccount(followerAccount) // Bind follower account
            .allocation(new BigDecimal("5000.00"))
            .startDate(new Date())
            .isActive(true)
            .build();

        copyTradingRepository.save(copyTrading);
        return copyTrading;
    }

    /**
     * 计算收益或损失
     */
    private BigDecimal calculateProfitLoss(BigDecimal openPrice, BigDecimal closePrice, BigDecimal volume) {
        return closePrice.subtract(openPrice).multiply(volume);
    }

    /**
     * 更新CopyTrading的收益和损失信息
     */
    private void updateCopyTradingResults(CopyTrading copyTrading, Trade trade) {
        BigDecimal newProfit = copyTrading.getTotalProfit().add(trade.getProfitLoss());
        copyTrading.setTotalProfit(newProfit);

        if (trade.getProfitLoss().compareTo(BigDecimal.ZERO) < 0) {
            BigDecimal newLoss = copyTrading.getTotalLoss().add(trade.getProfitLoss().abs());
            copyTrading.setTotalLoss(newLoss);
        }

        copyTradingRepository.save(copyTrading);
    }

    /**
     * 生成随机价格
     */
    private BigDecimal generateRandomPrice() {
        return BigDecimal.valueOf(1.10 + (1.30 - 1.10) * random.nextDouble()).setScale(5, RoundingMode.HALF_UP);
    }
}
