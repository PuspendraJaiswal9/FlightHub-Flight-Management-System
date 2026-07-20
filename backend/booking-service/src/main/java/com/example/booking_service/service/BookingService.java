package com.example.booking_service.service;

import com.example.booking_service.dto.BookingRequest;
import com.example.booking_service.dto.BookingResponse;
import com.example.booking_service.exception.FareNotFoundException;
import com.example.booking_service.exception.FlightNotFoundException;
import com.example.booking_service.model.Booking;

import java.util.List;

public interface BookingService {
    public Booking addBooking(BookingRequest bookingRequest) throws FlightNotFoundException, FareNotFoundException;
    public String cancelBooking(String bookingReference);
    public BookingResponse getBookingDetails(String bookingReference);
    public List<Booking> getAllBookings();
    public String confirmBooking(String bookingReference);
}
