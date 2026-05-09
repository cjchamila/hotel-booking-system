package com.hbm.booking_service.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Component
public class JWTValidator {

    private final PublicKeyLoader publicKeyLoader;

    public JWTValidator(PublicKeyLoader publicKeyLoader) {
        this.publicKeyLoader = publicKeyLoader;
    }

    public Claims validateToken(String token) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
            return Jwts.parserBuilder()
                    .setSigningKey(publicKeyLoader.loadPublicKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }

    public Long extractUserId(String token) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Claims claims = validateToken(token);
        return  claims.get("userId",Long.class);
    }
}
