package com.xtq_ymt.copy_trading_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.xtq_ymt.copy_trading_backend.Result.Result;
import com.xtq_ymt.copy_trading_backend.service.FollowerService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/follower")
@CrossOrigin(origins = "http://localhost:8081")  
public class FollowerController {

    @Autowired
    private FollowerService followerService;

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
}
