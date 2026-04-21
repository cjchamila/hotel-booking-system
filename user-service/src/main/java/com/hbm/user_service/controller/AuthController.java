package com.hbm.user_service.controller;

import com.hbm.user_service.model.User;
import com.hbm.user_service.repository.UserRepository;
import com.hbm.user_service.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestBody User user){
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered!";
    }

    @PostMapping("/login")
    public String login(@RequestBody User request) throws Throwable {
       User user = userRepository.findByEmail(request.getEmail())
               .orElseThrow(()-> new RuntimeException("Invalid user!"));
       String password=user.getPassword();
       if(!passwordEncoder.matches(password,passwordEncoder.encode(password))){
        throw new RuntimeException("Invalid credentials!");
       }
       return jwtUtil.generateToken(user.getEmail(),user.getRole());
    }
}
