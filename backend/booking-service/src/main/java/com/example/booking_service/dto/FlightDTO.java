package com.example.booking_service.dto;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FlightDTO {

    @NotNull(message="Flight Number is Mandatory")
    private String flightNumber;
    @NotBlank(message="Flight Name should be Mandatory")
    private String flightName;
    @NotEmpty(message="Please Provide Departure City")
    private String departureCity;
    @NotEmpty(message="Please Provide Arrivale City")
    private String arrivalCity;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    @Min(value=0,message="Seats should not be less than 0")
    @Max(value=200,message="Seats should not be greater than 200")
    private int  totalSeats;
    private int  availableSeats;
    @NotBlank(message = "Status Should not be Blank")
    private String status;// Scheduled, Cancelled, Delayed

    @ElementCollection
    private int bookedSeat=0;

    public @NotNull(message = "Flight Number is Mandatory") String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(@NotNull(message = "Flight Number is Mandatory") String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public @NotBlank(message = "Flight Name should be Mandatory") String getFlightName() {
        return flightName;
    }

    public void setFlightName(@NotBlank(message = "Flight Name should be Mandatory") String flightName) {
        this.flightName = flightName;
    }

    public @NotEmpty(message = "Please Provide Departure City") String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(@NotEmpty(message = "Please Provide Departure City") String departureCity) {
        this.departureCity = departureCity;
    }

    public @NotEmpty(message = "Please Provide Arrivale City") String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(@NotEmpty(message = "Please Provide Arrivale City") String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public @Min(value = 0, message = "Seats should not be less than 0") @Max(value = 200, message = "Seats should not be greater than 200") int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(@Min(value = 0, message = "Seats should not be less than 0") @Max(value = 200, message = "Seats should not be greater than 200") int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public @NotBlank(message = "Status Should not be Blank") String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank(message = "Status Should not be Blank") String status) {
        this.status = status;
    }

    public int getBookedSeat() {
        return bookedSeat;
    }

    public void setBookedSeat(int bookedSeat) {
        this.bookedSeat = bookedSeat;
    }

    public FlightDTO(String flightNumber, String flightName, String departureCity, String arrivalCity, LocalDate departureDate, LocalTime departureTime, LocalDate arrivalDate, LocalTime arrivalTime, int totalSeats, int availableSeats, String status,int bookedSeat) {
        this.flightNumber = flightNumber;
        this.flightName = flightName;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.status = status;
        this.bookedSeat = bookedSeat;
    }

    public FlightDTO() {
    }
}

