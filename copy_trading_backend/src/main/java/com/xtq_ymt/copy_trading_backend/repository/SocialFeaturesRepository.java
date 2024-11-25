package com.xtq_ymt.copy_trading_backend.repository;

import com.xtq_ymt.copy_trading_backend.model.SocialFeatures;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface SocialFeaturesRepository extends JpaRepository<SocialFeatures, Long> {

    // 根据Trader查找评论（支持分页），修改方法名称
    Page<SocialFeatures> findByTrader_TraderId(Long traderId, Pageable pageable);

    // 根据Follower查找评论（支持分页）
    Page<SocialFeatures> findByFollower_FollowerId(Long followerId, Pageable pageable);

    // 根据Trader查找评论，按点赞数升序排序（支持分页），修改方法名称
    Page<SocialFeatures> findByTrader_TraderIdOrderByLikeNumAsc(Long traderId, Pageable pageable);

    // 根据Follower查找评论，按点赞数降序排序（支持分页）
    Page<SocialFeatures> findByFollower_FollowerIdOrderByLikeNumDesc(Long followerId, Pageable pageable);

    // 根据Trader查找评论，按时间降序排序（支持分页），修改方法名称
    Page<SocialFeatures> findByTrader_TraderIdOrderByCommentTimestampDesc(Long traderId, Pageable pageable);

    // 根据Follower查找评论，按时间升序排序（支持分页）
    Page<SocialFeatures> findByFollower_FollowerIdOrderByCommentTimestampAsc(Long followerId, Pageable pageable);

    // 模糊搜索评论内容
    @Query("SELECT s FROM SocialFeatures s WHERE s.content LIKE %:keyword%")
    List<SocialFeatures> searchByContent(@Param("keyword") String keyword);

    // 批量更新点赞数
    @Modifying
    @Query("UPDATE SocialFeatures s SET s.likeNum = s.likeNum + :increment WHERE s.commentId IN :ids")
    void incrementLikeNumByIds(@Param("increment") Integer increment, @Param("ids") List<Long> ids);

    // 批量删除某Trader的所有评论，修改方法名称
    void deleteByTrader_TraderId(Long traderId);

    // 批量删除某Follower的所有评论
    void deleteByFollower_FollowerId(Long followerId);
}
