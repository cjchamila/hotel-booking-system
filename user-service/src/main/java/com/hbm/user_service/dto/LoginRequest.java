package com.hbm.user_service.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank(message = "Email is required!")String userName,
                           @NotBlank(message = "Password is required!")String password ) {

}
