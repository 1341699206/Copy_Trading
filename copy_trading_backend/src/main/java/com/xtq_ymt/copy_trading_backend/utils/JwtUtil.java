package com.xtq_ymt.copy_trading_backend.utils;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

//jwt工具类
public class JwtUtil {
    
    //加密密匙
    private static final String key="co_trade";

    //接收业务数据，生成token并返回
    public static String genToken(Map<String,Object> claims){
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24))
                .sign(Algorithm.HMAC256(key));
    }

    //接收token,验证token,并返回业务数据
    public static Map<String,Object> parseToken(String token){
        return JWT.require(Algorithm.HMAC256(key))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();

    }
}
