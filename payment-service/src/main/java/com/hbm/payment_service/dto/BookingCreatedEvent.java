package com.hbm.payment_service.dto;


import java.math.BigDecimal;

public class BookingCreatedEvent {

    private Long bookingId;

    private  Long userId;

    private Long roomId;

    private BigDecimal amount;

    public Long getBookingId() {
        return bookingId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
