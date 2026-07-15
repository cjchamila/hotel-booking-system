package com.hbm.payment_service.service;

import com.hbm.payment_service.consumer.BookingCreatedConsumer;
import com.hbm.payment_service.dto.BookingCreatedEvent;
import com.hbm.payment_service.entity.Payment;
import com.hbm.payment_service.metrics.impl.MicrometerPaymentMetrics;
import com.hbm.payment_service.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class PaymentService {

    private static final Logger log= LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;

    private final MicrometerPaymentMetrics metrics;


    public PaymentService(PaymentRepository paymentRepository, MicrometerPaymentMetrics metrics) {
        this.paymentRepository = paymentRepository;
        this.metrics = metrics;

    }

    public void processPayment(BookingCreatedEvent event) {

        log.info(
                "Processing payment bookingId={} amount={}",
                event.getBookingId(),
                event.getAmount()
        );
        Long bookingId= event.getBookingId();
        if(paymentRepository.existsByBookingId(event.getBookingId())) {

                log.warn(
                        "Duplicate payment event ignored for bookingId={}",
                        event.getBookingId()
                );

                return;
            }

            Payment payment = new Payment();

        payment.setBookingId(bookingId);
        payment.setUserId(event.getUserId());
        payment.setAmount(event.getAmount());

        payment.setStatus("SUCCESS");
        payment.setCreatedAt(LocalDateTime.now());


        paymentRepository.save(payment);
        metrics.paymentProcessed();

        log.info(
                "Payment processed bookingId={}",
                event.getBookingId()
        );
    }
}
