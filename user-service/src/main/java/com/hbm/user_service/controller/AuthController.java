package com.hbm.user_service.controller;

import com.hbm.user_service.dto.ApiResponse;
import com.hbm.user_service.dto.AuthResponse;
import com.hbm.user_service.dto.LoginRequest;
import com.hbm.user_service.dto.RegistrationRequest;
import com.hbm.user_service.dto.common.UserResponse;
import com.hbm.user_service.model.User;
import com.hbm.user_service.repository.UserRepository;
import com.hbm.user_service.security.JWTUtil;
import com.hbm.user_service.service.AuthenticationService;
import com.hbm.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.function.Supplier;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegistrationRequest request){
       UserResponse response=userService.registerUser(request);
        URI location = MvcUriComponentsBuilder
                .fromMethodCall(on(UserController.class).profile(response.publicId()))
                .build()
                .toUri();

        return ResponseEntity.created(location).body(response);

    }



    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        AuthResponse authResponse=authenticationService.authenticate(request);
        ApiResponse<AuthResponse> response=new ApiResponse<AuthResponse>(
                true,
                "Login successful!",
                authResponse
        );
        return new ResponseEntity<>(response,HttpStatus.OK);

    }
}
