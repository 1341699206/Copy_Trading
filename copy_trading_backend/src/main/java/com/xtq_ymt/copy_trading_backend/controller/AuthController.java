package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.dto.UserRegisterDTO;
import com.xtq_ymt.copy_trading_backend.dto.UserResponseDTO;
import com.xtq_ymt.copy_trading_backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 用户认证控制器类，提供用户注册和登录的 API 接口。
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "APIs for user authentication")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 用户注册接口。
     *
     * @param userRegisterDTO 包含用户注册信息的 DTO
     * @return 包含用户基本信息的响应 DTO 和 HTTP 状态码
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "This API registers a new user in the system.")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            // 调用服务层的注册方法，返回用户响应 DTO
            UserResponseDTO responseDTO = authService.register(userRegisterDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // 捕获异常并返回错误信息
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage()); // 返回详细的错误信息
        }
    }

    /**
     * 用户登录接口。
     *
     * @param username 用户名
     * @param password 密码
     * @return 包含 JWT 令牌或错误信息和 HTTP 状态码
     */
    @PostMapping("/login")
    @Operation(summary = "User login", description = "This API authenticates a user and returns a JWT token.")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
            // 调用服务层的登录方法获取 JWT 令牌
            String token = authService.login(username, password);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            // 捕获异常并返回错误信息
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Error: " + e.getMessage());
        }
    }
}
