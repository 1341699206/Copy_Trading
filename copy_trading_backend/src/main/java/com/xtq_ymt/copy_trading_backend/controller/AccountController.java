package com.xtq_ymt.copy_trading_backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.xtq_ymt.copy_trading_backend.Result.Result;
import com.xtq_ymt.copy_trading_backend.dto.CreateAccountRequest;
import com.xtq_ymt.copy_trading_backend.service.AccountService;


@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:8081")  // 允许从前端地址访问
public class AccountController {

    @Autowired
    private AccountService accountService;
    
    // 获取支持的币种
    @GetMapping("/currencies")
    public Result getCurrencies() {
            List<String> currencies = new ArrayList<>();
            currencies.add("CNY");
            currencies.add("USD");
            return Result.success("success",currencies);
    }

    // 创建账户
    @PostMapping("/create")
    public ResponseEntity<Result> createAccount(@RequestBody CreateAccountRequest accountRequest){
        Result result=accountService.createAccount(accountRequest);
        if(result.getCode()==0){
            return ResponseEntity.status(400).body(result);
        }else return ResponseEntity.ok(result);
    }
}
