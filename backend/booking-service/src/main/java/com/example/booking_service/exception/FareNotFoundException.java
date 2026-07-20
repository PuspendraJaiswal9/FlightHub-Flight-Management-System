package com.example.booking_service.exception;

public class FareNotFoundException extends Exception{
    public FareNotFoundException(String message){
        super(message);
    }
}
