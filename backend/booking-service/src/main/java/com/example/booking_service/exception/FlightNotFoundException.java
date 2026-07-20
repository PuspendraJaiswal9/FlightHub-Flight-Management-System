package com.example.booking_service.exception;

public class FlightNotFoundException extends Exception{
    public FlightNotFoundException(String message){
        super(message);
    }
}
