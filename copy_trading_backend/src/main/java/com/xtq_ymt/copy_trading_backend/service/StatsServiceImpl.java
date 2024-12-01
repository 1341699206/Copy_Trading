package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.TraderStats;
import com.xtq_ymt.copy_trading_backend.model.FollowerStats;
import com.xtq_ymt.copy_trading_backend.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service  // 标记该类为Spring的服务层（业务逻辑层）组件，Spring会自动扫描并注册为Bean
public class StatsServiceImpl implements StatsService {

    private final TradeRepository tradeRepository;
    private final TradeService tradeService;  // 声明 tradeService 字段

    @Autowired
    public StatsServiceImpl(TradeRepository tradeRepository, TradeService tradeService) {
        this.tradeRepository = tradeRepository;
        this.tradeService = tradeService;
    }


    

    /**
     * 计算交易者的统计数据
     * @param traderId 交易者ID
     * @return 返回包含交易者统计信息的TraderStats对象
     */
    @Override
    public TraderStats calculateTraderStats(Long traderId) {
        // 获取交易者的总利润
        double totalProfit = tradeRepository.getTotalProfitByTraderId(traderId);
        // 获取交易者的总交易次数
        int totalTrades = tradeRepository.getTotalTradesByTraderId(traderId);
        // 获取交易者的获胜交易次数
        int winningTrades = tradeRepository.getWinningTradesByTraderId(traderId);
        // 获取交易者的最大回撤
        double maxDrawdown = tradeRepository.getMaxDrawdownByTraderId(traderId);

        // 创建一个TraderStats对象并设置相关数据
        TraderStats stats = new TraderStats();
        stats.setTraderId(traderId);  // 设置交易者ID
        stats.setTotalProfit(totalProfit);  // 设置总利润
        stats.setTotalTrades(totalTrades);  // 设置总交易次数
        stats.setWinningTrades(winningTrades);  // 设置获胜交易次数
        // 计算并设置获胜率，若总交易次数大于0，则计算胜率；否则为0
        stats.setWinRate(totalTrades > 0 ? (double) winningTrades / totalTrades * 100 : 0);
        stats.setMaxDrawdown(maxDrawdown);  // 设置最大回撤
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
        double totalProfit = tradeRepository.getTotalProfitByFollowerId(followerId);
        // 获取跟随者的总交易次数
        int totalTrades = tradeRepository.getTotalTradesByFollowerId(followerId);
        // 获取跟随者的最大回撤
        double maxDrawdown = tradeRepository.getMaxDrawdownByFollowerId(followerId);

        // 创建一个FollowerStats对象并设置相关数据
        FollowerStats stats = new FollowerStats();
        stats.setFollowerId(followerId);  // 设置跟随者ID
        stats.setTotalProfit(totalProfit);  // 设置总利润
        stats.setTotalTrades(totalTrades);  // 设置总交易次数
        stats.setMaxDrawdown(maxDrawdown);  // 设置最大回撤
        // 获取该跟随者关注的交易者数量
        stats.setTotalFollowedTraders(tradeRepository.getTotalFollowedTraders(followerId));
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
        // 获取利润最高的前limit名交易者，使用流式处理将结果转换为TraderStats对象
        return tradeRepository.getTopTradersByProfit(pageable)
                .stream()
                .map(result -> {
                    TraderStats stats = new TraderStats();
                    stats.setTraderId((Long) result[0]);  // 设置交易者ID
                    stats.setTotalProfit((Double) result[1]);  // 设置总利润
                    return stats;  // 返回转换后的TraderStats对象
                })
                .toList();  // 返回结果列表
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
        // 获取利润最高的前limit名跟随者，使用流式处理将结果转换为FollowerStats对象
        return tradeRepository.getTopFollowersByProfit(pageable)
                .stream()
                .map(result -> {
                    FollowerStats stats = new FollowerStats();
                    stats.setFollowerId((Long) result[0]); // 设置跟随者ID
                    stats.setTotalProfit((Double) result[1]); // 设置总利润
                    return stats; // 返回转换后的FollowerStats对象
                })
                .toList(); // 返回结果列表
    }
    

    @Override
    public void updateTraderAndFollowerStats(Long tradeId) {
        // 调用 TradeService 中的方法
        tradeService.updateTraderAndFollowerStats(tradeId);
    }

}
