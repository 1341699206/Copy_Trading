package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.FollowerTrader;
import java.util.List;

/**
 * FollowerService 接口定义了管理用户跟随关系的业务逻辑方法。
 * 该接口包含用于创建、删除和查询跟随关系的方法。
 */
public interface FollowerService {

    /**
     * 创建一个新的跟随关系，即某个关注者账户开始关注某个交易者账户。
     *
     * @param followerAccountId 关注者的账户ID
     * @param traderAccountId   交易者的账户ID
     * @return 返回创建的 FollowerTrader 实体对象，表示关注者与交易者之间的跟随关系
     * @throws IllegalArgumentException 如果关注者账户ID或交易者账户ID无效，或跟随关系已存在
     */
    FollowerTrader followTrader(Long followerAccountId, Long traderAccountId);

    /**
     * 删除一个已存在的跟随关系，即某个关注者账户停止关注某个交易者账户。
     *
     * @param followerAccountId 关注者的账户ID
     * @param traderAccountId   交易者的账户ID
     * @throws IllegalArgumentException 如果关注者账户ID或交易者账户ID无效，或跟随关系不存在
     */
    void unfollowTrader(Long followerAccountId, Long traderAccountId);

    /**
     * 获取某个关注者账户所关注的所有交易者账户的跟随关系列表。
     *
     * @param followerAccountId 关注者的账户ID
     * @return 返回一个包含所有与该关注者账户相关的 FollowerTrader 实体对象的列表
     * @throws IllegalArgumentException 如果关注者账户ID无效
     */
    List<FollowerTrader> getFollowedTraders(Long followerAccountId);


    /** 
     * 使用follower和trader的userid来判断是否存在跟随关系。
     * 
     * @param followerId
     * @param traderId 
     * @return Boolean返回一个布尔值，表示是否存在跟随关系。
     * @throws IllegalArgumentException 如果followerId或traderId无效。
     */
    Boolean exBooleanFollowerBoolean(Long followerAccountId, Long traderAccountId);
    
}
