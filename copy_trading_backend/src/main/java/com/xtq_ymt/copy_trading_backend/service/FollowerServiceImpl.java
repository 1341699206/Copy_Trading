package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.FollowerTrader;
import com.xtq_ymt.copy_trading_backend.repository.FollowerTraderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowerServiceImpl implements FollowerService {

    private final FollowerTraderRepository followerTraderRepository;

    @Autowired
    public FollowerServiceImpl(FollowerTraderRepository followerTraderRepository) {
        this.followerTraderRepository = followerTraderRepository;
    }

    @Override
    public FollowerTrader followTrader(Long followerAccountId, Long traderAccountId) {
        if (followerTraderRepository.existsByFollowerAccountIdAndTraderAccountId(followerAccountId, traderAccountId)) {
            throw new IllegalArgumentException("Already following this trader.");
        }
        FollowerTrader followerTrader = new FollowerTrader();
        followerTrader.setFollowerAccountId(followerAccountId);
        followerTrader.setTraderAccountId(traderAccountId);
        return followerTraderRepository.save(followerTrader);
    }

    @Override
    public void unfollowTrader(Long followerAccountId, Long traderAccountId) {
        List<FollowerTrader> records = followerTraderRepository.findByFollowerAccountId(followerAccountId);
        records.stream()
                .filter(record -> record.getTraderAccountId().equals(traderAccountId))
                .findFirst()
                .ifPresent(followerTraderRepository::delete);
    }

    @Override
    public List<FollowerTrader> getFollowedTraders(Long followerAccountId) {
        return followerTraderRepository.findByFollowerAccountId(followerAccountId);
    }
}
