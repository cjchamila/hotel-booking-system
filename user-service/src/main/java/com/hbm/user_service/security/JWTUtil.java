package com.hbm.user_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Component
public class JWTUtil {

    private final PrivateKeyLoader privateKeyLoader;

    public JWTUtil(PrivateKeyLoader privateKeyLoader) {
        this.privateKeyLoader = privateKeyLoader;
    }

    public String generateToken(String email, List<String> roles) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        return Jwts.builder()
                .setSubject(email)
                .claim("role",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ Duration.ofHours(24).toMillis()))
                .signWith(privateKeyLoader.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    public Claims extractClaims(String token) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
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
