package com.example.fareStore_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Fare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fareId;

    private String flightNumber;

    private double baseFare;

    private int fareTax;

    private double discount;

    private double totalFare;

    public Fare() {
    }

    public Fare(double discount, Long fareId, String flightNumber, double baseFare, int fareTax, double totalFare) {
        this.discount = discount;
        this.fareId = fareId;
        this.flightNumber = flightNumber;
        this.baseFare = baseFare;
        this.fareTax = fareTax;
        this.totalFare = totalFare;
    }

    public Long getFareId() {
        return fareId;
    }

    public void setFareId(Long fareId) {
        this.fareId = fareId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    public int getFareTax() {
        return fareTax;
    }

    public void setFareTax(int fareTax) {
        this.fareTax = fareTax;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(double totalFare) {
        this.totalFare = totalFare;
    }
}
