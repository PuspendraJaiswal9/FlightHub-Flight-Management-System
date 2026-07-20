package com.example.booking_service.feingClient;

import com.example.booking_service.dto.FlightDTO;
import com.example.booking_service.exception.FlightNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "flight-service")
public interface FlightClient {

    @GetMapping("/flight/number/{flightNumber}")
    FlightDTO getFlightByFlightNumber(
            @PathVariable("flightNumber") String flightNumber
    ) throws FlightNotFoundException;

    @PutMapping("/flight/update-seats/{flightNumber}/{seatsToreduce}")
    String updateAvailableSeats(
            @PathVariable("flightNumber") String flightNumber,
            @PathVariable("seatsToreduce") int seatsToreduce
    );
}