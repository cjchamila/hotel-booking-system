package com.hbm.payment_service.consumer;

import com.hbm.payment_service.dto.BookingCreatedEvent;
import com.hbm.payment_service.metrics.impl.MicrometerPaymentMetrics;
import com.hbm.payment_service.service.PaymentService;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookingCreatedConsumer {

    private final PaymentService paymentService;

    private final MicrometerPaymentMetrics metrics;

    public BookingCreatedConsumer(PaymentService paymentService, MicrometerPaymentMetrics metrics) {
        this.paymentService = paymentService;
        this.metrics = metrics;
    }

    @KafkaListener(
            topics = "booking-created",
            groupId = "payment-group"
    )
    public void consume(BookingCreatedEvent event) {
            metrics.bookingEventConsumed();
        MDC.put(
                "correlationId",
                event.getCorrelationId()
        );

        try{
            paymentService.processPayment(event);

        } finally {
            MDC.clear();
        }

    }
}
