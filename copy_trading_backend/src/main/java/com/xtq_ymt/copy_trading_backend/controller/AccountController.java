package com.xtq_ymt.copy_trading_backend.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xtq_ymt.copy_trading_backend.Result.Result;


@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:8081")  // 允许从前端地址访问
public class AccountController {

    // 获取支持的币种
    @GetMapping("/currencies")
    public Result getCurrencies(@RequestHeader(name="Authorization") String token) {
            List<String> currencies = new ArrayList<>();
            currencies.add("CNY");
            currencies.add("USD");
            return Result.success("success",currencies);
    }
}