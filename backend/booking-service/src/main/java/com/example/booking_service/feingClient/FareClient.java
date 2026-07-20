package com.example.booking_service.feingClient;

import com.example.booking_service.dto.FareResponse;
import com.example.booking_service.exception.FareNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "fareStore-service")
public interface FareClient {

    @GetMapping("/fare/{flightNumber}")
    FareResponse getFare(@PathVariable("flightNumber") String flightNumber) throws FareNotFoundException;

}