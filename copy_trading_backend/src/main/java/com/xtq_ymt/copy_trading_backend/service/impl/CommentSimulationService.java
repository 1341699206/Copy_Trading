package com.xtq_ymt.copy_trading_backend.service.impl;

import com.xtq_ymt.copy_trading_backend.model.SocialFeatures;
import com.xtq_ymt.copy_trading_backend.model.Trader;
import com.xtq_ymt.copy_trading_backend.model.Follower;
import com.xtq_ymt.copy_trading_backend.repository.SocialFeaturesRepository;
import com.xtq_ymt.copy_trading_backend.repository.TraderRepository;
import com.xtq_ymt.copy_trading_backend.repository.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CommentSimulationService {

    @Autowired
    private SocialFeaturesRepository socialFeaturesRepository;

    @Autowired
    private TraderRepository traderRepository;

    @Autowired
    private FollowerRepository followerRepository;

    private final Random random = new Random();

    /**
     * Generates random comments for traders and their followers.
     */
    public void generateRandomComments() {
        List<Trader> traders = traderRepository.findAll();
        List<Follower> followers = followerRepository.findAll();

        String[] sampleComments = {
            "Great strategy!", "Keep up the good work!", "Nice performance last month!",
            "Looking forward to more updates.", "Can you share more about your approach?"
        };

        for (Trader trader : traders) {
            generateTraderComments(trader, sampleComments);
            generateFollowerComments(trader, followers, sampleComments);
        }
    }

    /**
     * Generates random comments made by traders themselves.
     */
    private void generateTraderComments(Trader trader, String[] sampleComments) {
        for (int i = 0; i < 3; i++) {
            SocialFeatures comment = new SocialFeatures();
            comment.setTrader(trader);
            comment.setContent(sampleComments[random.nextInt(sampleComments.length)]);
            comment.setCommentTimestamp(new Date());
            comment.setLikeNum(random.nextInt(50));
            comment.setAuthorType("Trader");
            socialFeaturesRepository.save(comment);
        }
    }

    /**
     * Generates random comments made by followers on traders.
     */
    private void generateFollowerComments(Trader trader, List<Follower> followers, String[] sampleComments) {
        for (int j = 0; j < 5; j++) {
            Follower follower = followers.get(random.nextInt(followers.size()));

            SocialFeatures comment = new SocialFeatures();
            comment.setTrader(trader);
            comment.setFollower(follower);
            comment.setContent(sampleComments[random.nextInt(sampleComments.length)]);
            comment.setCommentTimestamp(new Date());
            comment.setLikeNum(random.nextInt(50));
            comment.setAuthorType("Follower");
            socialFeaturesRepository.save(comment);
        }
    }
}