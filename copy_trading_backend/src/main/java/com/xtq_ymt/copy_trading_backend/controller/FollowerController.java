package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.Result.Result;
import com.xtq_ymt.copy_trading_backend.dto.TradeHistoryDTO;
import com.xtq_ymt.copy_trading_backend.dto.TradeOpenPositionDTO;
import com.xtq_ymt.copy_trading_backend.dto.CopiersDTO;
import com.xtq_ymt.copy_trading_backend.dto.FollowerCopyDTO;
import com.xtq_ymt.copy_trading_backend.service.FollowerChartService;
import com.xtq_ymt.copy_trading_backend.service.FollowerService;
import com.xtq_ymt.copy_trading_backend.service.TradeHistoryService;
import com.xtq_ymt.copy_trading_backend.service.TradePositionService;
import com.xtq_ymt.copy_trading_backend.service.TraderService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/follower")
@CrossOrigin(origins = "http://localhost:8081")
public class FollowerController {

    @Autowired
    private FollowerService followerService;

    @Autowired
    private TradeHistoryService tradeHistoryService;

    @Autowired
    private TradePositionService tradePositionService;

    @Autowired
    private TraderService traderService;

    @Autowired
    private FollowerChartService followerChartService;

    // 获取 traders 数据
    @GetMapping("/traders")
    public ResponseEntity<Result> getTradersData(
            @RequestParam Integer quantity,
            @RequestParam(required = false) Integer timePeriod) { // timePeriod 为可选参数
        
        Result result = followerService.getTopTradersData(quantity, timePeriod);
        if (result.getCode() == 1) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }

    // 获取指定 trader 的基本信息或详细信息
    @GetMapping("/trader/{id}")
    public ResponseEntity<Result> getTraderInfo(
            @PathVariable Long id,
            @RequestParam(required = false) Integer timePeriod) { // timePeriod 可选
        Result result;
        if (timePeriod != null) {
            result = followerService.getTraderDetailedInfo(id, timePeriod); // 获取详细信息
        } else {
            result = followerService.getTraderBasicInfo(id); // 获取基本信息
        }

        if (result.getCode() == 1) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }

    // 查询指定 trader 已经完成的历史交易数据
    @GetMapping("/trader/{id}/tradesHistory")
    public ResponseEntity<Result> getTradesHistory(
            @PathVariable("id") Long traderId,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("currentPage") int currentPage) {

        Page<TradeHistoryDTO> tradeHistoryPage = tradeHistoryService.getTradeHistory(traderId, pageSize, currentPage);
        Result result = Result.success("Successfully retrieved trade history", tradeHistoryPage);
        return ResponseEntity.ok(result);
    }

    // 查询指定 trader 已经持仓的交易数据
    @GetMapping("/trader/{id}/openPosition")
    public ResponseEntity<Result> getOpenPositions(
            @PathVariable("id") Long traderId,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("currentPage") int currentPage) {

        Page<TradeOpenPositionDTO> openPositionsPage = tradePositionService.getOpenPositions(traderId, pageSize, currentPage);
        Result result = Result.success("Successfully retrieved open positions", openPositionsPage);
        return ResponseEntity.ok(result);
    }

    // 查询指定 trader 的所有 copier（跟随者），按收益降序排列
    @GetMapping("/trader/{id}/copiers")
    public ResponseEntity<Result> getTraderCopiers(
            @PathVariable("id") Long traderId,
            @RequestParam("currentPage") int currentPage,
            @RequestParam("pageSize") int pageSize) {

        Page<CopiersDTO> copiersPage = traderService.getTraderCopiers(traderId, currentPage, pageSize);

        Result result = Result.success("Successfully retrieved copiers", copiersPage);
        return ResponseEntity.ok(result);
    }

    // 新增 API: 获取 trader 和 follower 之间的跟单历史数据（用于绘图）
    @GetMapping("/trader/{traderId}/follower/{followerId}/chartData")
    public ResponseEntity<Result> getFollowerChartData(
            @PathVariable("traderId") Long traderId,
            @PathVariable("followerId") Long followerId) {

        try {
            String chartData = followerChartService.getFollowedChartData(traderId, followerId);
            Result result = Result.success("Successfully retrieved chart data", chartData);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Result result = Result.error("Error retrieving chart data: " + e.getMessage());
            return ResponseEntity.status(500).body(result);
        }
    }

    // 新增 API: 查询 copier copy trader 的总体收益情况
    @GetMapping("/trader/{traderId}/copier/{followerId}")
    public ResponseEntity<Result> getFollowerCopyInfo(
            @PathVariable("traderId") Long traderId,
            @PathVariable("followerId") Long followerId) {

        try {
            FollowerCopyDTO followerCopyInfo = followerChartService.getFollowerCopyInfo(traderId, followerId);
            Result result = Result.success("Successfully retrieved follower copy info", followerCopyInfo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Result result = Result.error("Error retrieving follower copy info: " + e.getMessage());
            return ResponseEntity.status(500).body(result);
        }
    }
}
