package com.xtq_ymt.copy_trading_backend.controller;

// 导入必要的类和注解
import com.xtq_ymt.copy_trading_backend.model.FollowerTrader;
import com.xtq_ymt.copy_trading_backend.service.FollowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 标识该类为一个 REST 控制器
@RestController
// 设置请求的基础路径为 /followers
@RequestMapping("/followers")
// 使用 Swagger 注解为该控制器添加标签，方便 API 文档生成
@Tag(name = "Follower Management", description = "APIs for managing followers and traders")
public class FollowerController {

    // 注入 FollowerService 服务，用于处理业务逻辑
    private final FollowerService followerService;

    // 通过构造函数注入 FollowerService
    @Autowired
    public FollowerController(FollowerService followerService) {
        this.followerService = followerService;
    }

    /**
     * 追随交易员的接口
     * 处理 POST 请求到 /followers/follow
     *
     * @param followerAccountId 追随者的账户 ID
     * @param traderAccountId   交易员的账户 ID
     * @return 返回创建的 FollowerTrader 对象和 HTTP 201 状态码
     */
    @PostMapping("/follow")
    // 使用 Swagger 注解描述该操作的摘要和详细信息
    @Operation(summary = "Follow a trader", description = "Allows a follower to follow a specific trader.")
    public ResponseEntity<FollowerTrader> followTrader(@RequestParam Long followerAccountId,
                                                        @RequestParam Long traderAccountId) {
        // 调用服务层的 followTrader 方法，创建追随关系
        FollowerTrader followerTrader = followerService.followTrader(followerAccountId, traderAccountId);
        // 返回创建的对象和 HTTP 201 CREATED 状态
        return new ResponseEntity<>(followerTrader, HttpStatus.CREATED);
    }

    /**
     * 取消追随交易员的接口
     * 处理 DELETE 请求到 /followers/unfollow
     *
     * @param followerAccountId 追随者的账户 ID
     * @param traderAccountId   交易员的账户 ID
     * @return 返回成功消息和 HTTP 200 状态码
     */
    @DeleteMapping("/unfollow")
    // 使用 Swagger 注解描述该操作的摘要和详细信息
    @Operation(summary = "Unfollow a trader", description = "Allows a follower to unfollow a specific trader.")
    public ResponseEntity<String> unfollowTrader(@RequestParam Long followerAccountId,
                                                  @RequestParam Long traderAccountId) {
        // 调用服务层的 unfollowTrader 方法，删除追随关系
        followerService.unfollowTrader(followerAccountId, traderAccountId);
        // 返回成功消息和 HTTP 200 OK 状态
        return new ResponseEntity<>("Unfollowed successfully.", HttpStatus.OK);
    }

    /**
     * 获取某个追随者所追随的所有交易员的接口
     * 处理 GET 请求到 /followers/{followerAccountId}/traders
     *
     * @param followerAccountId 追随者的账户 ID，通过路径变量传入
     * @return 返回追随的交易员列表和 HTTP 200 状态码
     */
    @GetMapping("/{followerAccountId}/traders")
    // 使用 Swagger 注解描述该操作的摘要和详细信息
    @Operation(summary = "Get followed traders", description = "Fetches the list of traders followed by a follower.")
    public ResponseEntity<List<FollowerTrader>> getFollowedTraders(@PathVariable Long followerAccountId) {
        // 调用服务层的 getFollowedTraders 方法，获取追随的交易员列表
        List<FollowerTrader> followedTraders = followerService.getFollowedTraders(followerAccountId);
        // 返回交易员列表和 HTTP 200 OK 状态
        return new ResponseEntity<>(followedTraders, HttpStatus.OK);
    }

    
    
    /**
     * 使用follower和trader的accountid判断其是否存在跟随与被跟随关系
     * 
     * @param followerAccountId 追随者的账户 ID
     * @param traderAccountId   交易员的账户 ID
     */
    @GetMapping("/")
    // 使用 Swagger 注解描述该操作的摘要和详细信息
    @Operation(summary = "Check relationship", description = "Check relationship between follower and trader.")
    public ResponseEntity<Boolean> CheckBooleanFollowerBoolean(Long followerAccountId, Long traderAccountId) {
        Boolean relationshipExists = followerService.exBooleanFollowerBoolean(followerAccountId, traderAccountId);
        return new ResponseEntity<>(relationshipExists, HttpStatus.OK);
    }
}
