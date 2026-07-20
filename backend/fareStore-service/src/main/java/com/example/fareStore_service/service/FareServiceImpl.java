package com.example.fareStore_service.service;

import com.example.fareStore_service.dto.FareRequest;
import com.example.fareStore_service.dto.FareResponse;
import com.example.fareStore_service.exception.FareNotFoundException;
import com.example.fareStore_service.model.Fare;
import com.example.fareStore_service.repository.FareRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FareServiceImpl implements FareService{
    @Autowired
    public FareRepository fareRepository;

    @Override
    public Fare addFare(FareRequest fareRequest){
        Fare fare=new Fare();
        fare.setFlightNumber(fareRequest.getFlightNumber());
        fare.setBaseFare(fareRequest.getBaseFare());
        fare.setFareTax(fareRequest.getFareTax());
        fare.setDiscount(fareRequest.getDiscount());
        fare.setTotalFare((fareRequest.getBaseFare()+fareRequest.getFareTax())-fareRequest.getDiscount());
        return fareRepository.save(fare);
    }
    @Override
    public Fare updateFare(FareRequest nfareRequest, String flightNumber) throws FareNotFoundException {
        Fare findFare = fareRepository.findByFlightNumber(flightNumber);
        if (findFare != null) {
            findFare.setBaseFare(nfareRequest.getBaseFare());
            findFare.setFareTax(nfareRequest.getFareTax());
            findFare.setDiscount(nfareRequest.getDiscount());
            findFare.setTotalFare((nfareRequest.getBaseFare() + nfareRequest.getFareTax()) - nfareRequest.getDiscount());
            findFare.setFlightNumber(flightNumber); // ✅ important: ensure flight number not null

            return fareRepository.save(findFare);
        } else {
            throw new FareNotFoundException("Fare Not Found");
        }
    }

    @Override
    public FareResponse getFareByFlightNumnber(String flightNumber)throws FareNotFoundException{
        Fare fare=fareRepository.findByFlightNumber(flightNumber);
        if(fare!=null){
            FareResponse fareResponse=new FareResponse();
            fareResponse.setFlightNumber(fare.getFlightNumber());
            fareResponse.setTotalFare(fare.getTotalFare());
            return fareResponse;
        }else{
            throw new FareNotFoundException("Fare Not Found");
        }
    }
    @Transactional
    public String deleteFareByFlightNumber(String flightNumber) throws FareNotFoundException {
        Fare fare = fareRepository.findByFlightNumber(flightNumber);
        if (fare != null) {
            fareRepository.delete(fare);
            return "Fare Deleted Successfully";
        } else {
            throw new FareNotFoundException("Fare Not Found");
        }
    }
}
