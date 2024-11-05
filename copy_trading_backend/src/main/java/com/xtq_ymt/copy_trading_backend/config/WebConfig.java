package com.xtq_ymt.copy_trading_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.xtq_ymt.copy_trading_backend.interceptor.LoginInterceptor;

import org.springframework.lang.NonNull;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
            .excludePathPatterns(
                "/api/register", 
                "/api/login", 
                "/api/register/countries", 
                "/api/market-data/available", 
                "/api/market-data/market", 
                "/follower/traders", 
                "/account/currencies", 
                "/account/create", // 确保未拦截 /account/create
                "/swagger-ui/**", 
                "/v3/api-docs/**", 
                "/v3/api-docs"
            ); // 放行 Swagger 相关路径
    }

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOriginPatterns("http://localhost:8081", "http://example.com", "http://anotherdomain.com") // 添加 localhost 允许跨域访问
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*") // 允许所有请求头
            .allowCredentials(true); // 允许携带凭证信息（如cookie）
    }
}
