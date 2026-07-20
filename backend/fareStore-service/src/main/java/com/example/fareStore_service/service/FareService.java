package com.example.fareStore_service.service;

import com.example.fareStore_service.dto.FareRequest;
import com.example.fareStore_service.dto.FareResponse;
import com.example.fareStore_service.exception.FareNotFoundException;
import com.example.fareStore_service.model.Fare;

public interface FareService {
    public Fare addFare(FareRequest fareRequest);
    public Fare updateFare(FareRequest nfareRequest,String flightNumber) throws FareNotFoundException;
    FareResponse getFareByFlightNumnber(String flightNumber) throws FareNotFoundException;
    public String deleteFareByFlightNumber(String flightNumber) throws FareNotFoundException;
}
