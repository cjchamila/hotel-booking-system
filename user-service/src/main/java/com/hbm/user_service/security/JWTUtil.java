package com.hbm.user_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JWTUtil {

    private final PrivateKeyLoader privateKeyLoader;

    public JWTUtil(PrivateKeyLoader privateKeyLoader) {
        this.privateKeyLoader = privateKeyLoader;
    }

    public String generateToken(String email, String role,Long userId) throws Exception {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId",userId)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ Duration.ofHours(24).toMillis()))
                .signWith(privateKeyLoader.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    public Claims extractClaims(String token) throws Exception {
        return  Jwts.parserBuilder()
                .setSigningKey(privateKeyLoader.getPrivateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) throws Exception {
        return extractClaims(token)
                .getSubject();
    }
}
