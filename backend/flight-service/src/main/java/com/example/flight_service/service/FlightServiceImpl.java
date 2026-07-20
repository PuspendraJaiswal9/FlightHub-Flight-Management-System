package com.example.flight_service.service;

import com.example.flight_service.exception.FlightNotFoundException;
import com.example.flight_service.model.Flight;
import com.example.flight_service.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService{
    @Autowired
    public FlightRepository flightRepository;

    @Override
    public Flight addFlight(Flight flight){
        flight.setAvailableSeats(flight.getTotalSeats());
        return flightRepository.save(flight);
    }

    @Override
    public Flight updateFlight(Flight newFlight, Long flightId) throws FlightNotFoundException {
        Flight oldFlight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with Id " + flightId));
        oldFlight.setFlightNumber(newFlight.getFlightNumber());
        oldFlight.setFlightName(newFlight.getFlightName());
        oldFlight.setDepartureCity(newFlight.getDepartureCity());
        oldFlight.setArrivalCity(newFlight.getArrivalCity());
        oldFlight.setArrivalDate(newFlight.getArrivalDate());
        oldFlight.setArrivalTime(newFlight.getArrivalTime());
        oldFlight.setDepartureDate(newFlight.getDepartureDate());
        oldFlight.setDepartureTime(newFlight.getDepartureTime());
        return flightRepository.save(oldFlight);
    }


    @Override
    public List<Flight> getAllFlight(){
        return flightRepository.findAll();
    }

    @Override
    public Optional<Flight>getFlightById(Long flightId){
        return flightRepository.findById(flightId);
    }

    @Override
    public String deleteFlightById(Long flightId){
        Flight getFlight=flightRepository.findById(flightId).orElse(null);
        if(getFlight!=null){
            flightRepository.deleteById(flightId);
            return "Flight Deleted SuccessFully";
        }
        return "Flight Not Found";
    }

    @Override
    public Flight getFlightByFlightNumber(String flightNumber)throws FlightNotFoundException{
        Flight flight = flightRepository.findByFlightNumber(flightNumber);
        if(flight!=null){
            return flight;
        }else{
            throw new FlightNotFoundException("Flight Not Found");
        }
    }

//    @Override
//    public String bookSeat(String flightNumber) throws FlightNotFoundException{
//        Flight flight=flightRepository.findByFlightNumber(flightNumber);
//        if(flight!=null){
//            if(flight.getAvailableSeats()==0){
//                return "Seat Full";
//            }
//
//            for(int i=0;i<= flight.getTotalSeats();i++){
//                String seat=String.valueOf(i);
//                if(!flight.getBookedSeat().contains(seat)){
//                    flight.getBookedSeat().add(seat);
//                    flight.setAvailableSeats(flight.getAvailableSeats()-1);
//                    flightRepository.save(flight);
//                    return seat;
//                }
//            }
//            return "Seat Full";
//        }else{
//            throw new FlightNotFoundException("Flight Not Found");
//        }
//    }

//    @Override
//    public String canselSeat(String flightNumber,String seat) throws FlightNotFoundException {
//        Flight flight = flightRepository.findByFlightNumber(flightNumber);
//        if (flight != null) {
//            if (flight.getBookedSeat().contains(seat)) {
//                flight.getBookedSeat().remove(seat);
//                flight.setAvailableSeats(flight.getAvailableSeats() + 1);
//                flightRepository.save(flight);
//                return "Seat Cansel SuccessFully";
//            }else{
//                return "Seat Not Booked";
//            }
//        } else {
//            throw new FlightNotFoundException("Flight Not Found");
//        }
//    }

    @Override
    public String updateAvailableSeats(String flightNumber,int seatsToreduce){
        Flight flight=flightRepository.findByFlightNumber(flightNumber);
        if(flight==null){
            return "Flight Not Found";
        }else{
            int available=flight.getAvailableSeats()-seatsToreduce;
            flight.setAvailableSeats(available);
            flightRepository.save(flight);
            return "Seats Updated Successfully";
        }
    }
}
