package com.hbm.user_service.service;

import com.hbm.user_service.dto.AuthResponse;
import com.hbm.user_service.dto.LoginRequest;
import com.hbm.user_service.model.User;
import com.hbm.user_service.repository.UserRepository;
import com.hbm.user_service.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public AuthResponse authenticate(LoginRequest request) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
       Authentication authentication= authenticationManager
               .authenticate(
                       new UsernamePasswordAuthenticationToken(request.userName(),request.password()));
   List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        User user = (User) authentication.getPrincipal();
       String token = jwtUtil.generateToken(request.userName(), roles);
       long expiry=jwtUtil.extractClaims(token).getExpiration().getTime()-System.currentTimeMillis();
        return new AuthResponse(user.getPublicId().toString(),token,expiry);
    }


}
