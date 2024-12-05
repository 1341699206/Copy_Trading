package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.FollowerTrader;
import com.xtq_ymt.copy_trading_backend.model.TraderStats;
import com.xtq_ymt.copy_trading_backend.repository.FollowerTraderRepository;
import com.xtq_ymt.copy_trading_backend.repository.TraderStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 标记该类为Spring的Service层，业务逻辑的实现类
public class FollowerServiceImpl implements FollowerService {

    private final FollowerTraderRepository followerTraderRepository;
    private final TraderStatsRepository traderStatsRepository; // 注入 TraderStatsRepository 用于更新交易员的跟随者数量

    // 构造器注入FollowerTraderRepository和TraderStatsRepository
    @Autowired
    public FollowerServiceImpl(FollowerTraderRepository followerTraderRepository,
            TraderStatsRepository traderStatsRepository) {
        this.followerTraderRepository = followerTraderRepository;
        this.traderStatsRepository = traderStatsRepository;
    }

    /**
     * 关注交易者
     * @param followerAccountId 跟随者账户ID
     * @param traderAccountId 交易者账户ID
     * @return 返回已保存的FollowerTrader对象，表示跟随者和交易者之间的关系
     * @throws IllegalArgumentException 如果已经关注该交易者，则抛出异常
     */
    @Override
    public FollowerTrader followTrader(Long followerAccountId, Long traderAccountId) {
        // 判断当前跟随者是否已经关注了该交易者
        if (followerTraderRepository.existsByFollowerAccountIdAndTraderAccountId(followerAccountId, traderAccountId)) {
            throw new IllegalArgumentException("Already following this trader."); // 如果已经关注，抛出异常
        }
        // 创建一个新的FollowerTrader对象，表示一个关注记录
        FollowerTrader followerTrader = new FollowerTrader();
        followerTrader.setFollowerAccountId(followerAccountId); // 设置跟随者账户ID
        followerTrader.setTraderAccountId(traderAccountId); // 设置交易者账户ID
        // 保存并返回新创建的FollowerTrader记录
        followerTraderRepository.save(followerTrader);

        // 更新交易员的总跟随者数
        updateTotalFollowers(traderAccountId, 1);

        return followerTrader;
    }

    /**
     * 取消关注交易者
     * @param followerAccountId 跟随者账户ID
     * @param traderAccountId 交易者账户ID
     */
    @Override
    public void unfollowTrader(Long followerAccountId, Long traderAccountId) {
        // 查找该跟随者所有的关注记录
        List<FollowerTrader> records = followerTraderRepository.findByFollowerAccountId(followerAccountId);
        // 使用Stream API遍历所有关注记录，找到对应交易者的记录
        records.stream()
                .filter(record -> record.getTraderAccountId().equals(traderAccountId)) // 过滤出匹配的交易者账户ID
                .findFirst() // 找到第一个匹配的记录
                .ifPresent(followerTraderRepository::delete); // 如果存在，则删除该记录

        // 更新交易员的总跟随者数
        updateTotalFollowers(traderAccountId, -1);
    }

    /**
     * 获取跟随者所关注的所有交易者
     * @param followerAccountId 跟随者账户ID
     * @return 返回该跟随者关注的所有交易者的列表
     */
    @Override
    public List<FollowerTrader> getFollowedTraders(Long followerAccountId) {
        // 查询该跟随者所关注的所有交易者
        return followerTraderRepository.findByFollowerAccountId(followerAccountId);
    }

    /**
     * 更新交易员的跟随者数量
     * @param traderAccountId 交易员账户ID
     * @param delta 跟随者变化量（+1表示新增一个跟随者，-1表示取消一个跟随者）
     */
    private void updateTotalFollowers(Long traderAccountId, int delta) {
        // 获取交易员的统计数据
        TraderStats stats = traderStatsRepository.findByTraderId(traderAccountId);
        if (stats == null) {
            // 如果没有找到交易员统计数据，则创建一个新的
            stats = new TraderStats();
            stats.setTraderId(traderAccountId);
            stats.setTotalProfit(0);
            stats.setWinRate(0);
            stats.setMaxDrawdown(0);
            stats.setTotalTrades(0);
            stats.setWinningTrades(0);
            stats.setTotalFollowers(0); // 默认跟随者数量为0
        }

        // 更新跟随者数量
        stats.updateTotalFollowers(delta);
        traderStatsRepository.save(stats); // 保存更新后的统计数据
    }

    /**
     * 检查某个关注者账户是否已关注特定交易者账户。
     *
     * @param followerAccountId 关注者的账户ID
     * @param traderAccountId   交易者的账户ID
     * @return 如果关注者已关注交易者，返回 true；否则，返回 false
     */
    public Boolean exBooleanFollowerBoolean(Long followerAccountId, Long traderAccountId) {
        
        // 调用 FollowerTraderRepository 接口的方法，检查是否存在特定的跟随关系
        // existsByFollowerAccountIdAndTraderAccountId 方法根据关注者账户ID和交易者账户ID
        // 查询数据库，判断是否存在对应的记录
        return followerTraderRepository.existsByFollowerAccountIdAndTraderAccountId(followerAccountId, traderAccountId);
    }

}