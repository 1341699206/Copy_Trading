package com.xtq_ymt.copy_trading_backend.interceptor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.xtq_ymt.copy_trading_backend.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        // 获取请求的 URI
        String uri = request.getRequestURI();

        // 放行特定路径
        if (uri.startsWith("/swagger-ui/") || uri.startsWith("/v3/api-docs") || uri.equals("/account/create") || uri.equals("/account/currencies")) {
            return true;
        }

        // 获取相关请求头信息
        String authorizationHeader = request.getHeader("Authorization");
        
        // 检查是否具有 "Bearer " 前缀
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // 获取 token 令牌
            String token = authorizationHeader.substring(7);
            
            // 验证 token
            try {
                JwtUtil.parseToken(token);
                // 放行
                return true;
            } catch (Exception e) {
                // 解析 token 出错，返回具体错误信息
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write("{\"error\":\"Invalid or expired token\"}");
                return false;
            }
        } else {
            // 如果没有携带 token，返回错误信息
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"error\":\"Authorization header missing or malformed\"}");
            return false;
        }
    }
}
