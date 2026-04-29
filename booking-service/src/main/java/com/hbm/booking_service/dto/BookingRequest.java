package com.hbm.booking_service.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Getter
@Setter
public class BookingRequest {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double amount;

}
