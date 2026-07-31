package com.hbm.user_service.dto;


public record AuthResponse(String publicId,
                           String accessToken,
                           String tokenType,
                           Long expiresIn) {

     public AuthResponse(String publicId,String accessToken,long expiresIn) {
         this(publicId,accessToken,"Bearer",expiresIn);
    }
}
