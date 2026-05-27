package com.hbm.payment_service.service;

import com.hbm.payment_service.dto.BookingCreatedEvent;
import com.hbm.payment_service.entity.Payment;
import com.hbm.payment_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void processPayment(BookingCreatedEvent event) {

        Payment payment = new Payment();

        payment.setBookingId(event.getBookingId());
        payment.setUserId(event.getUserId());
        payment.setAmount(event.getAmount());

        payment.setStatus("SUCCESS");
        payment.setCreatedAt(LocalDateTime.now());

        paymentRepository.save(payment);
    }
}
