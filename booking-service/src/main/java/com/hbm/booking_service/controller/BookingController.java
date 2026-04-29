package com.hbm.booking_service.controller;

import com.hbm.booking_service.dto.BookingRequest;
import com.hbm.booking_service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping
    public String reserve(@RequestBody BookingRequest bookingRequest){
        bookingService.createBooking(bookingRequest);
        return "booking created!!";
    }
}
