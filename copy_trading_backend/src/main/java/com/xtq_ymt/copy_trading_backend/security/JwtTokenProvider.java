package com.xtq_ymt.copy_trading_backend.security;

import com.xtq_ymt.copy_trading_backend.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey jwtSecretKey;
    private final long jwtExpirationMs;

    public JwtTokenProvider(@Value("${jwt.secret}") String jwtSecret, @Value("${jwt.expiration-ms}") long jwtExpirationMs) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(jwtSecretKey) // 直接使用 Key 类型
                .compact();
    }
}
