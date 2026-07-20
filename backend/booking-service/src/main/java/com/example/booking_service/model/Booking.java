package com.example.booking_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

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

    @Positive(message = "Seat Count Should be Positive Not 0")
    private int seatCount;

    @NotBlank(message = "Booking status must not be blank")
    private String status; // BOOKED / CANCELLED

    private LocalDateTime bookingDate;

    @PositiveOrZero(message = "Fare amount must be 0 or greater")
    private double fareAmount;

    private String sessionId;
    @Column(length = 1000)
    private String sessionUrl;

    // ✅ Correct @PrePersist method (auto-generate reference)
    @PrePersist
    public void generateBookingReference() {
        if (this.bookingReference == null || this.bookingReference.isBlank()) {
            this.bookingReference = "BOOK-" + UUID.randomUUID().toString().substring(0, 8);
        }
    }

    // Getters & Setters

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getFareAmount() {
        return fareAmount;
    }

    public void setFareAmount(double fareAmount) {
        this.fareAmount = fareAmount;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionUrl() {
        return sessionUrl;
    }

    public void setSessionUrl(String sessionUrl) {
        this.sessionUrl = sessionUrl;
    }

    // Constructors

    public Booking(Long bookingId, String bookingReference, String flightNumber, String passengerName, String email, String phone, int seatCount, String status, LocalDateTime bookingDate, double fareAmount, String sessionId, String sessionUrl) {
        this.bookingId = bookingId;
        this.bookingReference = bookingReference;
        this.flightNumber = flightNumber;
        this.passengerName = passengerName;
        this.email = email;
        this.phone = phone;
        this.seatCount = seatCount;
        this.status = status;
        this.bookingDate = bookingDate;
        this.fareAmount = fareAmount;
        this.sessionId = sessionId;
        this.sessionUrl = sessionUrl;
    }

    public Booking() {
    }
}
