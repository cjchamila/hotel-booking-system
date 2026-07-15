package com.hbm.booking_service.repository;

import com.hbm.booking_service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query("""
            SELECT COUNT(b) > 0
            FROM Booking b
            WHERE b.roomId = :roomID
            AND b.startDate < :reqEndDate
            AND b.endDate > :reqStartDate
            """)
    boolean existsOverlap(
            @Param("roomID") Long roomID,
            @Param("reqStartDate") LocalDateTime startDate,
            @Param("reqEndDate") LocalDateTime endDate
    ) ;


}