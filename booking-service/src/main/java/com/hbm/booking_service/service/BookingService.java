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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

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

        //Check if the requested booking request has no overlapping times for the room
        if (bookingRepository.existsOverlap(
                bookingRequest.getRoomId(),
                bookingRequest.getStartDate(),
                bookingRequest.getEndDate()
        )) {
            throw new RuntimeException("Booking already exists!");
        }

        //Generate unique booking reference for business use-eg: for clients, support staff
        String ref=generateBookingReference();

        // Update booking
        booking.setBookingReference(ref);

        bookingRepository.save(booking);

        BookingCreatedEvent bookingCreatedEvent = new BookingCreatedEvent();
        bookingCreatedEvent.setBookingId(booking.getId());
        bookingCreatedEvent.setUserId(booking.getUserId());
        bookingCreatedEvent.setRoomId(booking.getRoomId());
        bookingCreatedEvent.setAmount(booking.getAmount());
        bookingCreatedEvent.setStatus(BookingStatus.PENDING);
        kafkaTemplate.send("booking-created",bookingCreatedEvent);
    }

    public String generateBookingReference() {
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        String random = UUID.randomUUID()
                .toString()
                .substring(0, 6)
                .toUpperCase();

        return "BK-" + date + "-" + random;
    }
}
