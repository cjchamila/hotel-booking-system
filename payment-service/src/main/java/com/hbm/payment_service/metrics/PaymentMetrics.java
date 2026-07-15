package com.hbm.payment_service.metrics;

public interface PaymentMetrics {

    void paymentProcessed();

    void paymentFailed();

    void paymentDuplicate();

    void bookingEventConsumed();

    void bookingEventProcessingFailed();
}