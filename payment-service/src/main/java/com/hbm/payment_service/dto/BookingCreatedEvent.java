package com.hbm.payment_service.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookingCreatedEvent {

    private Long bookingId;

    private  Long userId;

    private Long roomId;

    private BigDecimal amount;

    private String correlationId;

}
