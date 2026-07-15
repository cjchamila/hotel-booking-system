package com.hbm.booking_service.metrics.impl;

import com.hbm.booking_service.metrics.BookingMetrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MicrometerBookingMetrics implements BookingMetrics {

    private final Counter bookingCreatedCounter;
    private final Counter bookingFailedCounter;
    private final Counter bookingDuplicateCounter;
    private final Counter bookingEventPublishedCounter;
    private final Counter bookingEventPublishedFailedCounter;

    public MicrometerBookingMetrics(MeterRegistry meterRegistry) {

        this.bookingCreatedCounter =
                Counter.builder("booking.created")
                        .description("Successful bookings")
                        .register(meterRegistry);

        this.bookingFailedCounter =
                Counter.builder("booking.failed")
                        .description("Failed bookings")
                        .register(meterRegistry);

        this.bookingDuplicateCounter =
                Counter.builder("booking.duplicate")
                        .description("Duplicate booking attempts")
                        .register(meterRegistry);

        this.bookingEventPublishedCounter=Counter.builder("booking.event.published")
                .description("Booking event published")
                .register(meterRegistry);

        this.bookingEventPublishedFailedCounter=Counter.builder("booking.event.published.failed")
                .description("Booking event published failed")
                .register(meterRegistry);
    }


    @Override
    public void bookingCreated() {
        bookingCreatedCounter.increment();
    }

    @Override
    public void bookingFailed() {
        bookingFailedCounter.increment();
    }

    @Override
    public void bookingDuplicate() {
        bookingDuplicateCounter.increment();
    }

    @Override
    public void bookingEventPublished() {
        bookingEventPublishedCounter.increment();
    }

    @Override
    public void bookingEventPublishFailed() {
        bookingEventPublishedFailedCounter.increment();
    }
}
