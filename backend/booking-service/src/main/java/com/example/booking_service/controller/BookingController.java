package com.example.booking_service.controller;

import com.example.booking_service.dto.BookingRequest;
import com.example.booking_service.dto.BookingResponse;
import com.example.booking_service.exception.FareNotFoundException;
import com.example.booking_service.exception.FlightNotFoundException;
import com.example.booking_service.model.Booking;
import com.example.booking_service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<Booking> addBooking(@RequestBody BookingRequest bookingRequest)
            throws FlightNotFoundException, FareNotFoundException {
        Booking booking = bookingService.addBooking(bookingRequest);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/{bookingReference}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable String bookingReference) {
        BookingResponse bookingResponse = bookingService.getBookingDetails(bookingReference);
        if (bookingResponse != null) {
            return ResponseEntity.ok(bookingResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{bookingReference}")
    public ResponseEntity<String> cancelBooking(@PathVariable String bookingReference) {
        String result = bookingService.cancelBooking(bookingReference);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @PutMapping("/confirm/{bookingReference}")
    public ResponseEntity<String> confirmBooking(@PathVariable String bookingReference) {
        String result = bookingService.confirmBooking(bookingReference);
        return ResponseEntity.ok(result);
    }
}
