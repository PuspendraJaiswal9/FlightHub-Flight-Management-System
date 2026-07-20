package com.example.flight_service.exception;

public class FlightNotFoundException extends Exception{
    public FlightNotFoundException(String message){
        super(message);
    }
}
