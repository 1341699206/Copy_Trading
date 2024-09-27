package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.Result.AuthResult;
import com.xtq_ymt.copy_trading_backend.Result.Country;
import com.xtq_ymt.copy_trading_backend.Result.Result;
import com.xtq_ymt.copy_trading_backend.dto.LoginRequest;
import com.xtq_ymt.copy_trading_backend.dto.RegisterRequest;
import com.xtq_ymt.copy_trading_backend.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List; 
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")  // 允许从前端地址访问
public class AuthController {

    @Autowired
    private AuthService authService;

    // 登录端点
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // 调用 AuthService 中的 authenticate 方法
        AuthResult authResult = authService.authenticate(
            loginRequest.getEmail(),
            loginRequest.getPassword(),
            loginRequest.getRole()
        );

        // 根据 AuthResult 返回不同的状态码和消息
        if (authResult.isSuccess()) {
            return ResponseEntity.ok(authResult.getMessage()); // 登录成功
        } else {
            return ResponseEntity.status(401).body(authResult.getMessage()); // 登录失败，返回详细错误消息
        }
    }

    // 注册端点
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        try {
            boolean isRegistered = authService.register(
                registerRequest.getName(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getRole(),
                registerRequest.getCountry()
            );

            if (isRegistered) {
                return ResponseEntity.ok("Registration successful");
            } else {
                return ResponseEntity.status(400).body("Registration failed: User already exists or invalid data");
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    //获取国家列表
    @GetMapping("/register/countries")
    public Result getCountries(){
        List<Country> countries=new ArrayList<>();
        countries.add(new Country("CN", "China"));
        return new Result().success(countries);
    }
    
}
