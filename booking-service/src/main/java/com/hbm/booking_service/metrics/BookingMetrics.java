package com.hbm.booking_service.metrics;

public interface BookingMetrics {

    void bookingCreated();

    void bookingFailed();

    void bookingDuplicate();

    void bookingEventPublished();

    void bookingEventPublishFailed();
}
