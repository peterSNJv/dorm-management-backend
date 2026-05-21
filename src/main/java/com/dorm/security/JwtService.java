package com.dorm.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * JWT 签发与校验（与 SSO 集成时可改为校验外部 token 并签发本系统 token）。
 */
@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMs;

    public JwtService(
            @Value("${dorm.jwt.secret:your-256-bit-secret-key-here-change-in-production!!!}") String secret,
            @Value("${dorm.jwt.expiration-ms:86400000}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    public String createToken(String subject, String role) {
        return Jwts.builder()
            .subject(subject)
            .claim("role", role)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expirationMs))
            .signWith(key)
            .compact();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getSubject(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public String getRole(String token) {
        Object r = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("role");
        return r != null ? r.toString() : null;
    }
}
