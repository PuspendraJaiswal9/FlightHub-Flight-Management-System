package com.example.booking_service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

public class BookingResponse {
    @Column(unique = true, nullable = false)
    private String bookingReference;

    @NotBlank(message = "Flight number must not be blank")
    private String flightNumber;

    @NotBlank(message = "Passenger name must not be blank")
    private String passengerName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;

    //@NotBlank(message = "Seat number must not be blank")
    //private String seatNumber;

    @NotBlank(message = "Booking status must not be blank")
    private String status; // BOOKED / CANCELLED

    private LocalDateTime bookingDate;

    @PositiveOrZero(message = "Fare amount must be 0 or greater")
    private double fareAmount;

    private String sessionUrl;

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

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

    public @NotBlank(message = "Booking status must not be blank") String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank(message = "Booking status must not be blank") String status) {
        this.status = status;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public @PositiveOrZero(message = "Fare amount must be 0 or greater") double getFareAmount() {
        return fareAmount;
    }

    public void setFareAmount(@PositiveOrZero(message = "Fare amount must be 0 or greater") double fareAmount) {
        this.fareAmount = fareAmount;
    }

    public String getSessionUrl() {
        return sessionUrl;
    }

    public void setSessionUrl(String sessionUrl) {
        this.sessionUrl = sessionUrl;
    }

    public BookingResponse(String bookingReference, String flightNumber, String passengerName, String email, String phone, String status, LocalDateTime bookingDate, double fareAmount, String sessionUrl) {
        this.bookingReference = bookingReference;
        this.flightNumber = flightNumber;
        this.passengerName = passengerName;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.bookingDate = bookingDate;
        this.fareAmount = fareAmount;
        this.sessionUrl = sessionUrl;
    }

    public BookingResponse() {
    }
}
