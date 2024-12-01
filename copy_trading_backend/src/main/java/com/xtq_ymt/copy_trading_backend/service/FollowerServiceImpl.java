package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.FollowerTrader;
import com.xtq_ymt.copy_trading_backend.repository.FollowerTraderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // 标记该类为Spring的Service层，业务逻辑的实现类
public class FollowerServiceImpl implements FollowerService {

    private final FollowerTraderRepository followerTraderRepository;

    // 构造器注入FollowerTraderRepository，用于与数据库交互
    @Autowired
    public FollowerServiceImpl(FollowerTraderRepository followerTraderRepository) {
        this.followerTraderRepository = followerTraderRepository;
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
            throw new IllegalArgumentException("Already following this trader.");  // 如果已经关注，抛出异常
        }
        // 创建一个新的FollowerTrader对象，表示一个关注记录
        FollowerTrader followerTrader = new FollowerTrader();
        followerTrader.setFollowerAccountId(followerAccountId);  // 设置跟随者账户ID
        followerTrader.setTraderAccountId(traderAccountId);  // 设置交易者账户ID
        // 保存并返回新创建的FollowerTrader记录
        return followerTraderRepository.save(followerTrader);
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
                .filter(record -> record.getTraderAccountId().equals(traderAccountId))  // 过滤出匹配的交易者账户ID
                .findFirst()  // 找到第一个匹配的记录
                .ifPresent(followerTraderRepository::delete);  // 如果存在，则删除该记录
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
}
