package com.example.booking_service.dto;

import jakarta.validation.constraints.NotBlank;

public class FareResponse {
    @NotBlank(message="Flight Number should Not be Blank")
    private String flightNumber;
    private double totalFare;

    public @NotBlank(message = "Flight Number should Not be Blank") String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(@NotBlank(message = "Flight Number should Not be Blank") String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(double totalFare) {
        this.totalFare = totalFare;
    }

    public FareResponse(String flightNumber, double totalFare) {
        this.flightNumber = flightNumber;
        this.totalFare = totalFare;
    }

    public FareResponse() {
    }
}