package com.xtq_ymt.copy_trading_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;  // 添加此行导入 @NonNull 注解

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {  // 添加 @NonNull 注解
        registry.addMapping("/**")  // 允许所有路径
                .allowedOrigins("http://localhost:8081")  // 允许来自前端的跨域请求
                .allowedMethods("GET", "POST", "PUT", "DELETE");  // 允许的请求方法
    }
}
