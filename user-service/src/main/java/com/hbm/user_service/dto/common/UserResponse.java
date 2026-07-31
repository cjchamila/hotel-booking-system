package com.hbm.user_service.dto.common;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponse(
        String firstName,
        String lastName,
        LocalDate dob,
        String email,
        UUID publicId

        ) {}
