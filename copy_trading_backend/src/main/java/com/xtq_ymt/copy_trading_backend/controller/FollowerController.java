package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.Result.Result;
import com.xtq_ymt.copy_trading_backend.dto.TradeHistoryDTO;
import com.xtq_ymt.copy_trading_backend.service.FollowerService;
import com.xtq_ymt.copy_trading_backend.service.TradeHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follower")
@CrossOrigin(origins = "http://localhost:8081")
public class FollowerController {

    @Autowired
    private FollowerService followerService;

    @Autowired
    private TradeHistoryService tradeHistoryService;

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

        // 直接将分页对象包装到 Result 中
        Result result = Result.success("Successfully retrieved trade history", tradeHistoryPage);

        return ResponseEntity.ok(result);
    }
}
