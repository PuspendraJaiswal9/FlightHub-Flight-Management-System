package com.example.fareStore_service.repository;

import com.example.fareStore_service.model.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FareRepository extends JpaRepository<Fare,Long> {
    Fare findByFlightNumber(String flightNumber);
    void deleteByFlightNumber(String flightNumber);
}