package com.hbm.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/profile/{publicId}")
    public ResponseEntity<?> profile(@PathVariable UUID publicId){
        return null;
    }


}
