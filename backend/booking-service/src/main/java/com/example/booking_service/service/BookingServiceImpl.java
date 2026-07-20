package com.example.booking_service.service;

import com.example.booking_service.dto.*;
import com.example.booking_service.exception.FareNotFoundException;
import com.example.booking_service.exception.FlightNotFoundException;
import com.example.booking_service.feingClient.FareClient;
import com.example.booking_service.feingClient.FlightClient;
import com.example.booking_service.feingClient.PayClient;
import com.example.booking_service.model.Booking;
import com.example.booking_service.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{
    @Autowired
    public BookingRepository bookingRepository;

    @Autowired
    public FareClient fareClient;

    @Autowired
    public FlightClient flightClient;

    @Autowired
    public PayClient payClient;

    @Override
    public Booking addBooking(BookingRequest req) throws FlightNotFoundException, FareNotFoundException {

        FlightDTO flightDTO = flightClient.getFlightByFlightNumber(req.getFlightNumber());
        if (flightDTO == null) throw new FlightNotFoundException("Flight Not Found");

        FareResponse fareResponse = fareClient.getFare(req.getFlightNumber());
        if (fareResponse == null) throw new FareNotFoundException("Fare Not Found");

        if(flightDTO.getAvailableSeats() < req.getSeatCount()) throw new FlightNotFoundException("Seats Not Available in this Flight");

        PaymentRequest paymentRequest = new PaymentRequest();
        long amount=(long)fareResponse.getTotalFare();
        paymentRequest.setAmount(amount);
        paymentRequest.setQuantity(req.getSeatCount());
        paymentRequest.setCurrency("INR");

        StripeResponse paymentResponse = payClient.makePayment(paymentRequest);

        if (!"SUCCESS".equalsIgnoreCase(paymentResponse.getStatus())) {
            throw new RuntimeException("Payment Failed: " + paymentResponse.getMessage());
        }
        Booking booking = new Booking();
        booking.setFlightNumber(req.getFlightNumber());
        booking.setPassengerName(req.getPassengerName());
        booking.setEmail(req.getEmail());
        booking.setPhone(req.getPhone());
        booking.setSeatCount(req.getSeatCount());
        booking.setBookingDate(req.getBookingDate());
        booking.setStatus("PENDING");
        booking.setFareAmount(fareResponse.getTotalFare()*req.getSeatCount());
        booking.setSessionId(paymentResponse.getSessionId());
        booking.setSessionUrl(paymentResponse.getSessionUrl());
        return bookingRepository.save(booking);
    }

    @Transactional
    @Override
    public String cancelBooking(String bookingReference){
        Booking booking=bookingRepository.findByBookingReference(bookingReference);
        if(booking == null){
            return "Booking Reference Not Found";
        }else{
            bookingRepository.deleteByBookingReference(bookingReference);
           // flightClient.updateAvailableSeatsInc(booking.getFlightNumber(),booking.getSeatCount());
            return "Booking Deleted SuccessFully";
        }
    }

    @Override
    public BookingResponse getBookingDetails(String bookingReference){
        Booking booking=bookingRepository.findByBookingReference(bookingReference);
        if(booking == null){
            return null;
        }
        BookingResponse bookingResponse=new BookingResponse();
        bookingResponse.setBookingReference(booking.getBookingReference());
        bookingResponse.setFlightNumber(booking.getFlightNumber());
        bookingResponse.setPassengerName(booking.getPassengerName());
        bookingResponse.setEmail(booking.getEmail());
        bookingResponse.setPhone(booking.getPhone());
        bookingResponse.setStatus(booking.getStatus());
        bookingResponse.setBookingDate(booking.getBookingDate());
        bookingResponse.setFareAmount(booking.getFareAmount());
        bookingResponse.setSessionUrl(booking.getSessionUrl());
        return bookingResponse;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Transactional
    @Override
    public String confirmBooking(String bookingReference) {
        Booking booking = bookingRepository.findByBookingReference(bookingReference);
        if (booking == null) {
            return "Booking Reference Not Found";
        }
        if ("CONFIRMED".equals(booking.getStatus())) {
            return "Booking Already Confirmed";
        }
        booking.setStatus("CONFIRMED");
        bookingRepository.save(booking);
        flightClient.updateAvailableSeats(booking.getFlightNumber(), booking.getSeatCount());
        return "Booking Confirmed Successfully";
    }

}
