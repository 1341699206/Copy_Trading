package com.xtq_ymt.copy_trading_backend.controller;

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
    public ResponseEntity<Result> login(@RequestBody LoginRequest loginRequest) {
        // 调用 AuthService 中的 authenticate 方法
        Result authResult = authService.authenticate(
            loginRequest.getEmail(),
            loginRequest.getPassword(),
            loginRequest.getRole()
        );

        // 返回带有 Result 结构的响应
        return ResponseEntity.ok(authResult);
    }

    // 注册端点
    @PostMapping("/register")
    public ResponseEntity<Result> register(@RequestBody RegisterRequest registerRequest) {
        try {
            // 添加密码长度验证
            if (registerRequest.getPassword().length() < 6 || registerRequest.getPassword().length() > 14) {
                return ResponseEntity.status(400).body(Result.error("Password length must be between 6 and 14 characters."));
            }

            Result registrationResult = authService.register(
                registerRequest.getName(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getRole(),
                registerRequest.getCountry()
            );
            return ResponseEntity.ok(registrationResult);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(Result.error(e.getMessage()));
        }
    }


    // 获取国家列表
    @GetMapping("/register/countries")
    public Result getCountries() {
        List<Result.Country> countries = new ArrayList<>();
        countries.add(new Result.Country("CN", "China"));
        countries.add(new Result.Country("US", "United States"));
        return Result.success(countries);
    }
}
