package com.example.booking_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public class BookingRequest {

    @NotBlank(message = "Flight number must not be blank")
    private String flightNumber;

    @NotBlank(message = "Passenger name must not be blank")
    private String passengerName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;

    @Positive(message="Seat Count Should be Positive Not 0")
    private int seatCount;

    private LocalDateTime bookingDate;

    public @NotBlank(message = "Flight number must not be blank") String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(@NotBlank(message = "Flight number must not be blank") String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public @NotBlank(message = "Passenger name must not be blank") String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(@NotBlank(message = "Passenger name must not be blank") String passengerName) {
        this.passengerName = passengerName;
    }

    public @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    public @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits") String getPhone() {
        return phone;
    }

    public void setPhone(@Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits") String phone) {
        this.phone = phone;
    }

    public @Positive(message = "Seat Count Should be Positive Not 0") int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(@Positive(message = "Seat Count Should be Positive Not 0") int seatCount) {
        this.seatCount = seatCount;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public BookingRequest(String flightNumber, String passengerName, String email, String phone, int seatCount, LocalDateTime bookingDate) {
        this.flightNumber = flightNumber;
        this.passengerName = passengerName;
        this.email = email;
        this.phone = phone;
        this.seatCount = seatCount;
        this.bookingDate = bookingDate;
    }

    public BookingRequest() {
    }
}
