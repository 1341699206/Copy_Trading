package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.TraderStats;
import com.xtq_ymt.copy_trading_backend.model.FollowerStats;
import com.xtq_ymt.copy_trading_backend.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {

    private final TradeRepository tradeRepository;

    @Autowired
    public StatsServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public TraderStats calculateTraderStats(Long traderId) {
        double totalProfit = tradeRepository.getTotalProfitByTraderId(traderId);
        int totalTrades = tradeRepository.getTotalTradesByTraderId(traderId);
        int winningTrades = tradeRepository.getWinningTradesByTraderId(traderId);
        double maxDrawdown = tradeRepository.getMaxDrawdownByTraderId(traderId);

        TraderStats stats = new TraderStats();
        stats.setTraderId(traderId);
        stats.setTotalProfit(totalProfit);
        stats.setTotalTrades(totalTrades);
        stats.setWinningTrades(winningTrades);
        stats.setWinRate(totalTrades > 0 ? (double) winningTrades / totalTrades * 100 : 0);
        stats.setMaxDrawdown(maxDrawdown);
        return stats;
    }

    @Override
    public FollowerStats calculateFollowerStats(Long followerId) {
        double totalProfit = tradeRepository.getTotalProfitByFollowerId(followerId);
        int totalTrades = tradeRepository.getTotalTradesByFollowerId(followerId);
        double maxDrawdown = tradeRepository.getMaxDrawdownByFollowerId(followerId);

        FollowerStats stats = new FollowerStats();
        stats.setFollowerId(followerId);
        stats.setTotalProfit(totalProfit);
        stats.setTotalTrades(totalTrades);
        stats.setMaxDrawdown(maxDrawdown);
        stats.setTotalFollowedTraders(tradeRepository.getTotalFollowedTraders(followerId));
        return stats;
    }

    @Override
    public List<TraderStats> getTopTradersByProfit(int limit) {
        Pageable pageable = PageRequest.of(0, limit); // 页码为 0（第一页），每页大小为 limit
        return tradeRepository.getTopTradersByProfit(pageable)
                .stream()
                .map(result -> {
                    TraderStats stats = new TraderStats();
                    stats.setTraderId((Long) result[0]);
                    stats.setTotalProfit((Double) result[1]);
                    return stats;
                })
                .toList();
    }

    @Override
    public List<FollowerStats> getTopFollowersByProfit(int limit) {
        Pageable pageable = PageRequest.of(0, limit); // 页码为 0（第一页），每页大小为 limit
        return tradeRepository.getTopFollowersByProfit(pageable)
                .stream()
                .map(result -> {
                    FollowerStats stats = new FollowerStats();
                    stats.setFollowerId((Long) result[0]);
                    stats.setTotalProfit((Double) result[1]);
                    return stats;
                })
                .toList();

    }
}
