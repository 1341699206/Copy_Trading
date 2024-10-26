package com.xtq_ymt.copy_trading_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xtq_ymt.copy_trading_backend.Result.Result;
import com.xtq_ymt.copy_trading_backend.service.FollowerService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/follower")
@CrossOrigin(origins = "http://localhost:8081")  
public class FollowerController {

    @Autowired
    private FollowerService followerService;

    //获取traders数据
    @GetMapping("/traders?quantity={quantity}&timePeriod={timePeriod}")
    public ResponseEntity<Result> getMethodName(@PathVariable Integer quantity,@PathVariable Integer timePeriod) {
        
        Result result=followerService.getTopTradersData(quantity,timePeriod);
        
        return ResponseEntity.ok(result);
    }
    
}
