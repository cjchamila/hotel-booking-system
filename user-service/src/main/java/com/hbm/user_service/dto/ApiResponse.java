package com.hbm.user_service.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        boolean success,
        String message,
        Instant timestamp,
        T data,
        String errorCode
) {
    // Canonical Success Constructor
    public ApiResponse(boolean success, String message, T data) {
        this(success, message, Instant.now(), data, null);
    }

    // Canonical Error Constructor
    public ApiResponse(boolean success, String message, String errorCode) {
        this(success, message, Instant.now(), null, errorCode);
    }
}
