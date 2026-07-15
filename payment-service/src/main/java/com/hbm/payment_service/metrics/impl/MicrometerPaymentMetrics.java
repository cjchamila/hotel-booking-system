package com.hbm.payment_service.metrics.impl;

import com.hbm.payment_service.metrics.PaymentMetrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MicrometerPaymentMetrics implements PaymentMetrics {

    private final Counter paymentProcessedCounter;

    private final Counter paymentFailedCounter;

    private final Counter paymentDuplicateCounter;

    private final Counter bookingEventConsumedCounter;

    private final Counter bookingEventProcessingFailedCounter;

    public MicrometerPaymentMetrics(MeterRegistry meterRegistry){
        this.paymentProcessedCounter=Counter.builder("payment.processed")
                .description("Successful payments")
                .register(meterRegistry);

        this.paymentFailedCounter=Counter.builder("payment.failed")
                .description("Failed payments")
                .register(meterRegistry);

        this.paymentDuplicateCounter=Counter.builder("payment.duplicate")
                .description("Duplicate payments")
                .register(meterRegistry);

        this.bookingEventConsumedCounter=Counter.builder("booking.event.consumed")
                .description("Consumed booking events")
                .register(meterRegistry);

        this.bookingEventProcessingFailedCounter=Counter.builder("booking.event.processing.failed")
                .description("Failed booking processing events")
                .register(meterRegistry);
    }

    @Override
    public void paymentProcessed() {
        paymentProcessedCounter.increment();
    }

    @Override
    public void paymentFailed() {
        paymentFailedCounter.increment();
    }

    @Override
    public void paymentDuplicate() {
        paymentDuplicateCounter.increment();
    }

    @Override
    public void bookingEventConsumed() {
        bookingEventConsumedCounter.increment();
    }

    @Override
    public void bookingEventProcessingFailed() {
        bookingEventProcessingFailedCounter.increment();
    }
}
