package com.example.flight_service.controller;

import com.example.flight_service.exception.FlightNotFoundException;
import com.example.flight_service.model.Flight;
import com.example.flight_service.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 🔥 ADD THESE IMPORTS
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/flight")
public class FlightController {

    @Autowired
    private FlightService flightService;

    // 🔥 LOGGER OBJECT
    private static final Logger log = LoggerFactory.getLogger(FlightController.class);

    @PostMapping("/add")
    public ResponseEntity<Flight> addFlight(@Valid @RequestBody Flight flight) {
        log.info("Add flight API called");
        Flight savedFlight = flightService.addFlight(flight);
        log.info("Flight added successfully: {}", savedFlight.getFlightNumber());
        return ResponseEntity.ok(savedFlight);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@Valid @RequestBody Flight flight, @PathVariable("id") Long id) throws FlightNotFoundException {
        log.info("Update flight API called for id: {}", id);
        Flight updated = flightService.updateFlight(flight, id);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        log.info("Get all flights API called");
        return ResponseEntity.ok(flightService.getAllFlight());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable("id") Long id) {
        log.info("Get flight by id: {}", id);
        return flightService.getFlightById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/number/{flightNumber}")
    public ResponseEntity<Flight> getFlightByFlightNumber(@PathVariable("flightNumber") String flightNumber) throws FlightNotFoundException {
        log.info("Get flight by flightNumber: {}", flightNumber);
        return ResponseEntity.ok(flightService.getFlightByFlightNumber(flightNumber));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlightById(@PathVariable("id") Long id) {
        log.info("Delete flight by id: {}", id);
        return ResponseEntity.ok(flightService.deleteFlightById(id));
    }

    @PutMapping("/update-seats/{flightNumber}/{seatsToreduce}")
    public ResponseEntity<String> updateAvailableSeats(@PathVariable("flightNumber") String flightNumber, @PathVariable("seatsToreduce") int seatsToreduce) {
        log.info("Update seats for flight: {}, seats reduce: {}", flightNumber, seatsToreduce);
        return ResponseEntity.ok(flightService.updateAvailableSeats(flightNumber, seatsToreduce));
    }
}