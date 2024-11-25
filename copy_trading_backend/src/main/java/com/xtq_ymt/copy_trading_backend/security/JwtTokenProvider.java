package com.xtq_ymt.copy_trading_backend.security;

import com.xtq_ymt.copy_trading_backend.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * JWT令牌提供器
 * 用于生成和管理JWT令牌
 */
@Component
public class JwtTokenProvider {

    // 用于签名的JWT密钥
    private final SecretKey jwtSecretKey;

    // JWT的过期时间（单位：毫秒）
    private final long jwtExpirationMs;

    /**
     * 构造方法，通过 @Value 注解加载密钥和过期时间
     *
     * @param jwtSecret      JWT密钥（Base64编码格式）
     * @param jwtExpirationMs JWT过期时间（单位：毫秒）
     */
    public JwtTokenProvider(@Value("${jwt.secret}") String jwtSecret, @Value("${jwt.expiration-ms}") long jwtExpirationMs) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret)); // 解码并生成密钥
        this.jwtExpirationMs = jwtExpirationMs;
    }

    /**
     * 生成JWT令牌
     *
     * @param user 用户对象
     * @return 生成的JWT令牌字符串
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername()) // 设置用户名为令牌主体
                .setIssuedAt(new Date()) // 设置令牌签发时间
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // 设置令牌过期时间
                .signWith(jwtSecretKey) // 使用密钥对令牌签名
                .compact(); // 生成完整的令牌
    }
}
