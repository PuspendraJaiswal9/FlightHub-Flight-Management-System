package com.example.flight_service.service;

import com.example.flight_service.exception.FlightNotFoundException;
import com.example.flight_service.model.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightService {
    public Flight addFlight(Flight flight);
    public Flight updateFlight(Flight newFlight,Long flightId) throws FlightNotFoundException;
    public List<Flight> getAllFlight();
    public Optional<Flight> getFlightById(Long flightId);
    public String deleteFlightById(Long flightId);
   // public String bookSeat(String flightNumber)throws FlightNotFoundException;
   // public String canselSeat(String flightNumber,String seat)throws FlightNotFoundException;
    public Flight getFlightByFlightNumber(String flightNumber)throws FlightNotFoundException;
    public String updateAvailableSeats(String flightNumber,int seatsToreduce);
}
