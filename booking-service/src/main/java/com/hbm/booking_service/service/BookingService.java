package com.hbm.booking_service.service;

import com.hbm.booking_service.dto.BookingCreatedEvent;
import com.hbm.booking_service.dto.BookingRequest;
import com.hbm.booking_service.model.Booking;
import com.hbm.booking_service.model.BookingStatus;
import com.hbm.booking_service.repository.BookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private KafkaTemplate<String,Object>kafkaTemplate;

    @Autowired
    BookingRepository bookingRepository;

    public void createBooking(BookingRequest bookingRequest){
        Booking booking = modelMapper.map(bookingRequest,Booking.class);

        // Save to get id
        Booking saved = bookingRepository.save(booking);

        // Generate reference
        String ref=generateReference(saved.getId());

        // Update booking
        saved.setBookingReference(ref);

        bookingRepository.save(saved);

        BookingCreatedEvent bookingCreatedEvent = new BookingCreatedEvent();
        bookingCreatedEvent.setBookingId(booking.getId());
        bookingCreatedEvent.setUserId(booking.getUserId());
        bookingCreatedEvent.setRoomId(booking.getRoomId());
        bookingCreatedEvent.setAmount(booking.getAmount());

        kafkaTemplate.send("booking-created",bookingCreatedEvent);
    }

    private String generateReference(Long id){
        String prefix = "HBS";
        int year = LocalDateTime.now().getYear();

        String sequence = String.format("%04d", id);

        return prefix+"-"+year+"-"+sequence;
    }
}
