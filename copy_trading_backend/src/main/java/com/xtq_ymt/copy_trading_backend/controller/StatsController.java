package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.model.TraderStats;
import com.xtq_ymt.copy_trading_backend.model.FollowerStats;
import com.xtq_ymt.copy_trading_backend.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // 标记该类为Spring的RESTful控制器，表示它处理HTTP请求并返回响应
@RequestMapping("/stats")  // 设置基础路径，所有关于统计的API请求都会以"/stats"开头
@Tag(name = "Statistics Management", description = "APIs for managing statistics")  // Swagger注解，用于生成API文档，描述该控制器是关于统计管理的
public class StatsController {

    private final StatsService statsService;  // 声明StatsService服务类，负责业务逻辑处理

    // 构造器注入StatsService类，Spring会自动注入对应的实例
    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    /**
     * 获取交易员的统计信息
     * @param traderId 交易员的ID
     * @return 返回交易员的统计数据
     */
    @GetMapping("/trader/{traderId}")  // 处理GET请求，路径为"/stats/trader/{traderId}"
    @Operation(summary = "Get trader statistics", description = "Calculates and fetches statistics for a trader.")  // Swagger注解，用于生成API文档，描述该接口功能
    public ResponseEntity<TraderStats> getTraderStats(@PathVariable Long traderId) {
        // 调用StatsService服务中的方法，计算并获取指定交易员的统计信息
        TraderStats stats = statsService.calculateTraderStats(traderId);
        // 返回HTTP响应，状态码200 OK，并返回交易员统计信息
        return ResponseEntity.ok(stats);
    }

    /**
     * 获取跟随者的统计信息
     * @param followerId 跟随者的ID
     * @return 返回跟随者的统计数据
     */
    @GetMapping("/follower/{followerId}")  // 处理GET请求，路径为"/stats/follower/{followerId}"
    @Operation(summary = "Get follower statistics", description = "Calculates and fetches statistics for a follower.")  // Swagger注解，用于生成API文档，描述该接口功能
    public ResponseEntity<FollowerStats> getFollowerStats(@PathVariable Long followerId) {
        // 调用StatsService服务中的方法，计算并获取指定跟随者的统计信息
        FollowerStats stats = statsService.calculateFollowerStats(followerId);
        // 返回HTTP响应，状态码200 OK，并返回跟随者统计信息
        return ResponseEntity.ok(stats);
    }

    /**
     * 获取利润最高的前N名交易员
     * @param limit 返回前N名交易员
     * @return 返回前N名交易员的统计数据
     */
    @GetMapping("/top-traders")  // 处理GET请求，路径为"/stats/top-traders"
    @Operation(summary = "Get top traders", description = "Fetches top traders by profit.")  // Swagger注解，用于生成API文档，描述该接口功能
    public ResponseEntity<List<TraderStats>> getTopTraders(@RequestParam int limit) {
        // 调用StatsService服务中的方法，获取利润最高的前limit名交易员
        List<TraderStats> topTraders = statsService.getTopTradersByProfit(limit);
        // 返回HTTP响应，状态码200 OK，并返回前limit名交易员的统计信息列表
        return ResponseEntity.ok(topTraders);
    }

    /**
     * 获取利润最高的前N名跟随者
     * @param limit 返回前N名跟随者
     * @return 返回前N名跟随者的统计数据
     */
    @GetMapping("/top-followers")  // 处理GET请求，路径为"/stats/top-followers"
    @Operation(summary = "Get top followers", description = "Fetches top followers by profit.")  // Swagger注解，用于生成API文档，描述该接口功能
    public ResponseEntity<List<FollowerStats>> getTopFollowers(@RequestParam int limit) {
        // 调用StatsService服务中的方法，获取利润最高的前limit名跟随者
        List<FollowerStats> topFollowers = statsService.getTopFollowersByProfit(limit);
        // 返回HTTP响应，状态码200 OK，并返回前limit名跟随者的统计信息列表
        return ResponseEntity.ok(topFollowers);
    }
}
