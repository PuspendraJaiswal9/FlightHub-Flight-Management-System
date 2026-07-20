package com.example.fareStore_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class FareRequest {

    @NotBlank(message="Flight Number should Not be Blank")
    private String flightNumber;
    @Positive(message="Base Fare Should be Posititve")
    private double baseFare;
    @PositiveOrZero(message="Fare Tax should be Posititve or Zero")
    private int fareTax;
    @PositiveOrZero(message="Fare Discount should be Positive or Zero")
    private double discount;

    public @NotBlank(message = "Flight Number should Not be Blank") String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(@NotBlank(message = "Flight Number should Not be Blank") String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public @Positive(message = "Base Fare Should be Posititve") double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(@Positive(message = "Base Fare Should be Posititve") double baseFare) {
        this.baseFare = baseFare;
    }

    public @PositiveOrZero(message = "Fare Tax should be Posititve or Zero") int getFareTax() {
        return fareTax;
    }

    public void setFareTax(@PositiveOrZero(message = "Fare Tax should be Posititve or Zero") int fareTax) {
        this.fareTax = fareTax;
    }

    public @PositiveOrZero(message = "Fare Discount should be Positive or Zero") double getDiscount() {
        return discount;
    }

    public void setDiscount(@PositiveOrZero(message = "Fare Discount should be Positive or Zero") double discount) {
        this.discount = discount;
    }

    public FareRequest() {
    }

    public FareRequest(String flightNumber, double baseFare, int fareTax, double discount) {
        this.flightNumber = flightNumber;
        this.baseFare = baseFare;
        this.fareTax = fareTax;
        this.discount = discount;
    }
}

