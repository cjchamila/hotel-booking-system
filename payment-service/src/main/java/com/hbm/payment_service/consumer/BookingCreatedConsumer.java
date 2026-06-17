package com.hbm.payment_service.consumer;

import com.hbm.payment_service.dto.BookingCreatedEvent;
import com.hbm.payment_service.service.PaymentService;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookingCreatedConsumer {

    private final PaymentService paymentService;

    public BookingCreatedConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(
            topics = "booking-created",
            groupId = "payment-group"
    )
    public void consume(BookingCreatedEvent event) {

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
