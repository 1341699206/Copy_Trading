package com.xtq_ymt.copy_trading_backend.interceptor;


import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.xtq_ymt.copy_trading_backend.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor{

    @SuppressWarnings({ "null", "unused" })
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
        //获取相关请求头信息
        String authorizationHeader=request.getHeader("Authorization");
        //查看是否具有http规范“Bearer ”
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            //获取token令牌
            String token=authorizationHeader.substring(7);
            //验证token
            try {
                Map<String,Object> claims =JwtUtil.parseToken(token);
                //放行
                return true;
            } catch (Exception e) {
                response.setStatus(401);
                //不放行
                return false;
            }
        }else return false;

    }
}