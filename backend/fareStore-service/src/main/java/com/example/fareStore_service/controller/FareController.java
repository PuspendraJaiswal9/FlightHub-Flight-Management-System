package com.example.fareStore_service.controller;

import com.example.fareStore_service.dto.FareRequest;
import com.example.fareStore_service.dto.FareResponse;
import com.example.fareStore_service.exception.FareNotFoundException;
import com.example.fareStore_service.model.Fare;
import com.example.fareStore_service.service.FareService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/fare")
public class FareController {

    @Autowired
    private FareService fareService;

    @PostMapping("/add")
    public ResponseEntity<Fare> addFare(@Valid @RequestBody FareRequest fareRequest) {
        Fare savedFare = fareService.addFare(fareRequest);
        return ResponseEntity.ok(savedFare);
    }

    @PutMapping("/number/{flightNumber}")
    public ResponseEntity<Fare> updateFare(@Valid @RequestBody FareRequest fareRequest,
                                           @PathVariable String flightNumber) throws FareNotFoundException {
        Fare updatedFare = fareService.updateFare(fareRequest, flightNumber);
        return ResponseEntity.ok(updatedFare);
    }

    @GetMapping("/{flightNumber}")
    public ResponseEntity<FareResponse> getFare(@PathVariable String flightNumber) throws FareNotFoundException {
        FareResponse fareResponse = fareService.getFareByFlightNumnber(flightNumber);
        return ResponseEntity.ok(fareResponse);
    }

    @DeleteMapping("/{flightNumber}")
    public ResponseEntity<String> deleteFare(@PathVariable String flightNumber) throws FareNotFoundException {
        String message = fareService.deleteFareByFlightNumber(flightNumber);
        return ResponseEntity.ok(message);
    }
}
