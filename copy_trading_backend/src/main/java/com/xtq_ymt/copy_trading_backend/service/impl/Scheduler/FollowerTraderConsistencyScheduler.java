package com.xtq_ymt.copy_trading_backend.service.impl.Scheduler;

import com.xtq_ymt.copy_trading_backend.model.Follower;
import com.xtq_ymt.copy_trading_backend.model.Trader;
import com.xtq_ymt.copy_trading_backend.repository.FollowerRepository;
import com.xtq_ymt.copy_trading_backend.repository.TraderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;

import java.util.List;

@Component
public class FollowerTraderConsistencyScheduler {

    private static final Logger log = LoggerFactory.getLogger(FollowerTraderConsistencyScheduler.class);

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private TraderRepository traderRepository;

    /**
     * 定时任务：每隔10秒检测所有 Follower 的 followingTraders 列表，并确保 Trader 的 followersWhoFollowed 列表中包含该 Follower
     */
    @Scheduled(fixedRate = 10000) // 每隔 10 秒执行一次
    @Transactional
    public void checkFollowerTraderConsistency() {
        log.info("Starting Follower-Trader consistency check...");

        // 获取所有活跃的 Follower
        List<Follower> followers = followerRepository.findByIsActiveTrue();

        for (Follower follower : followers) {
            List<Trader> followingTraders = follower.getFollowingTraders();

            // 遍历每个 Follower 的 followingTraders 列表
            for (Trader trader : followingTraders) {
                if (!trader.getFollowersWhoFollowed().contains(follower)) {
                    // 如果 Trader 的 followersWhoFollowed 列表中不包含该 Follower，则添加
                    trader.getFollowersWhoFollowed().add(follower);
                    log.info("Added Follower {} to Trader {}'s followersWhoFollowed list.", follower.getFollowerId(), trader.getTraderId());

                    // 保存更新后的 Trader
                    traderRepository.save(trader);
                }
            }
        }

        log.info("Follower-Trader consistency check completed.");
    }
}
