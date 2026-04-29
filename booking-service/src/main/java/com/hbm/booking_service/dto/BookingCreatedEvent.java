package com.hbm.booking_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingCreatedEvent {
    private Long bookingId;
    private Long userId;
    private Long roomId;
    private double amount;
    private String status;
}