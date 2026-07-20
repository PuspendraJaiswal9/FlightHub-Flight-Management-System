package com.example.booking_service.repository;

import com.example.booking_service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    Booking findByBookingReference(String bookingReference);
    void deleteByBookingReference(String bookingReference);
}
