package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.TraderStats;
import com.xtq_ymt.copy_trading_backend.model.FollowerStats;
import com.xtq_ymt.copy_trading_backend.model.User;
import com.xtq_ymt.copy_trading_backend.repository.TradeRepository;
import com.xtq_ymt.copy_trading_backend.repository.UserRepository;
import com.xtq_ymt.copy_trading_backend.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service  // 标记该类为Spring的服务层（业务逻辑层）组件，Spring会自动扫描并注册为Bean
public class StatsServiceImpl implements StatsService {

    private final TradeRepository tradeRepository;
    private final TradeService tradeService;  // 声明 tradeService 字段
    private final UserRepository userRepository;

    @Autowired
    public StatsServiceImpl(TradeRepository tradeRepository, TradeService tradeService, UserRepository userRepository) {
        this.tradeRepository = tradeRepository;
        this.tradeService = tradeService;
        this.userRepository = userRepository;
    }

    /**
     * 计算交易者的统计数据
     * @param traderId 交易者ID
     * @return 返回包含交易者统计信息的TraderStats对象
     */
    @Override
    public TraderStats calculateTraderStats(Long traderId) {
        // 获取交易者的总利润
        Double totalProfit = tradeRepository.getTotalProfitByTraderId(traderId);
        // 获取交易者的总交易次数
        Integer totalTrades = tradeRepository.getTotalTradesByTraderId(traderId);
        // 获取交易者的盈利交易次数
        Integer winningTrades = tradeRepository.getWinningTradesByTraderId(traderId);
        // 获取交易者的最大回撤
        Double maxDrawdown = tradeRepository.getMaxDrawdownByTraderId(traderId);

        // 创建一个TraderStats对象并设置相关数据
        TraderStats stats = new TraderStats();
        stats.setTraderId(traderId);  // 设置交易者ID
        stats.setTotalProfit(totalProfit != null ? totalProfit : 0.0);  // 设置总利润
        stats.setTotalTrades(totalTrades != null ? totalTrades : 0);  // 设置总交易次数
        stats.setWinningTrades(winningTrades != null ? winningTrades : 0);  // 设置获胜交易次数
        // 计算并设置获胜率，若总交易次数大于0，则计算胜率；否则为0
        stats.setWinRate(stats.getTotalTrades() > 0 ? (double) stats.getWinningTrades() / stats.getTotalTrades() * 100 : 0.0);
        stats.setMaxDrawdown(maxDrawdown != null ? maxDrawdown : 0.0);  // 设置最大回撤

        // 获取关联的 User 实体
        Optional<User> userOpt = userRepository.findById(traderId);
        if (userOpt.isPresent()) {
            stats.setUser(userOpt.get());
        } else {
            throw new UserNotFoundException("User not found for traderId: " + traderId);
        }

        return stats;  // 返回包含统计数据的TraderStats对象
    }

    /**
     * 计算跟随者的统计数据
     * @param followerId 跟随者ID
     * @return 返回包含跟随者统计信息的FollowerStats对象
     */
    @Override
    public FollowerStats calculateFollowerStats(Long followerId) {
        // 获取跟随者的总利润
        Double totalProfit = tradeRepository.getTotalProfitByFollowerId(followerId);
        // 获取跟随者的总交易次数
        Integer totalTrades = tradeRepository.getTotalTradesByFollowerId(followerId);
        // 获取跟随者的最大回撤
        Double maxDrawdown = tradeRepository.getMaxDrawdownByFollowerId(followerId);
        // 获取跟随者总共跟随的交易员数量
        Integer totalFollowedTraders = tradeRepository.getTotalFollowedTraders(followerId);

        // 创建一个FollowerStats对象并设置相关数据
        FollowerStats stats = new FollowerStats();
        stats.setFollowerId(followerId);  // 设置跟随者ID
        stats.setTotalProfit(totalProfit != null ? totalProfit : 0.0);  // 设置总利润
        stats.setTotalTrades(totalTrades != null ? totalTrades : 0);  // 设置总交易次数
        stats.setMaxDrawdown(maxDrawdown != null ? maxDrawdown : 0.0);  // 设置最大回撤
        stats.setTotalFollowedTraders(totalFollowedTraders != null ? totalFollowedTraders : 0);  // 设置跟随的交易员数量

        // 获取关联的 User 实体
        Optional<User> userOpt = userRepository.findById(followerId);
        if (userOpt.isPresent()) {
            stats.setUser(userOpt.get());
        } else {
            throw new UserNotFoundException("User not found for followerId: " + followerId);
        }

        return stats;  // 返回包含统计数据的FollowerStats对象
    }

    /**
     * 获取利润最高的前N名交易者
     * @param limit 限制返回的数量
     * @return 返回前N名交易者的统计数据
     */
    @Override
    public List<TraderStats> getTopTradersByProfit(int limit) {
        // 创建Pageable对象，指定查询的页码为0（第一页），每页返回limit条数据
        Pageable pageable = PageRequest.of(0, limit);
        // 获取利润最高的前limit名交易者
        List<Object[]> results = tradeRepository.getTopTradersByProfit(pageable);

        return results.stream()
                .map(result -> {
                    Long traderId = (Long) result[0];
                    Double totalProfit = (Double) result[1];

                    TraderStats stats = new TraderStats();
                    stats.setTraderId(traderId);
                    stats.setTotalProfit(totalProfit != null ? totalProfit : 0.0);

                    // 获取关联的 User 实体
                    Optional<User> userOpt = userRepository.findById(traderId);
                    if (userOpt.isPresent()) {
                        stats.setUser(userOpt.get());
                    } else {
                        throw new UserNotFoundException("User not found for traderId: " + traderId);
                    }

                    return stats;
                })
                .toList();
    }

    /**
     * 获取利润最高的前N名跟随者
     * @param limit 限制返回的数量
     * @return 返回前N名跟随者的统计数据
     */
    @Override
    public List<FollowerStats> getTopFollowersByProfit(int limit) {
        // 创建Pageable对象，指定查询的页码为0（第一页），每页返回limit条数据
        Pageable pageable = PageRequest.of(0, limit);
        // 获取利润最高的前limit名跟随者
        List<Object[]> results = tradeRepository.getTopFollowersByProfit(pageable);

        return results.stream()
                .map(result -> {
                    Long followerId = (Long) result[0];
                    Double totalProfit = (Double) result[1];

                    FollowerStats stats = new FollowerStats();
                    stats.setFollowerId(followerId);
                    stats.setTotalProfit(totalProfit != null ? totalProfit : 0.0);

                    // 获取关联的 User 实体
                    Optional<User> userOpt = userRepository.findById(followerId);
                    if (userOpt.isPresent()) {
                        stats.setUser(userOpt.get());
                    } else {
                        throw new UserNotFoundException("User not found for followerId: " + followerId);
                    }

                    return stats;
                })
                .toList();
    }

    /**
     * 更新交易和跟随相关的统计数据
     * @param tradeId 交易ID
     */
    @Override
    public void updateTraderAndFollowerStats(Long tradeId) {
        // 调用 TradeService 中的方法
        tradeService.updateTraderAndFollowerStats(tradeId);
    }
}
