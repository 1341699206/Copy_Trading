package com.xtq_ymt.copy_trading_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 禁用 CSRF（如果需要）
            .cors(cors -> cors.configure(http)) // 启用 CORS
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/register", "/api/login", "/api/register/countries", "/api/market-data/available").permitAll() 
                        // 允许未认证的访问
                .requestMatchers(request -> request.getHeader("Authorization") != null).permitAll() //允许所有携带请求头的访问
                .requestMatchers("/ws/**").permitAll() // 允许 WebSocket 连接
                .anyRequest().authenticated() // 其他请求需要认证
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 使用 BCrypt 进行密码加密
    }
}
