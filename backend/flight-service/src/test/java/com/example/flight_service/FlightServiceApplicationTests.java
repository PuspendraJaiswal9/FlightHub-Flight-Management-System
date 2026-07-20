//package com.example.flight_service;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class FlightServiceApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//
//}


package com.example.flight_service;

import com.example.flight_service.controller.FlightController;
import com.example.flight_service.exception.FlightNotFoundException;
import com.example.flight_service.model.Flight;
import com.example.flight_service.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightControllerTest {

	@InjectMocks
	private FlightController flightController;

	@Mock
	private FlightService flightService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddFlight() {
		Flight flight = new Flight();
		when(flightService.addFlight(flight)).thenReturn(flight);

		ResponseEntity<Flight> response = flightController.addFlight(flight);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(flight, response.getBody());
		verify(flightService, times(1)).addFlight(flight);
	}

	@Test
	void testUpdateFlight() throws FlightNotFoundException {
		Long id = 1L;
		Flight flight = new Flight();
		when(flightService.updateFlight(flight, id)).thenReturn(flight);

		ResponseEntity<Flight> response = flightController.updateFlight(flight, id);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(flight, response.getBody());
		verify(flightService, times(1)).updateFlight(flight, id);
	}

	@Test
	void testGetAllFlights() {
		List<Flight> flights = Arrays.asList(new Flight(), new Flight());
		when(flightService.getAllFlight()).thenReturn(flights);

		ResponseEntity<List<Flight>> response = flightController.getAllFlights();

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(flights, response.getBody());
		verify(flightService, times(1)).getAllFlight();
	}

	@Test
	void testGetFlightById_Found() {
		Long id = 1L;
		Flight flight = new Flight();
		when(flightService.getFlightById(id)).thenReturn(Optional.of(flight));

		ResponseEntity<Flight> response = flightController.getFlightById(id);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(flight, response.getBody());
		verify(flightService, times(1)).getFlightById(id);
	}

	@Test
	void testGetFlightById_NotFound() {
		Long id = 99L;
		when(flightService.getFlightById(id)).thenReturn(Optional.empty());

		ResponseEntity<Flight> response = flightController.getFlightById(id);

		assertEquals(404, response.getStatusCodeValue());
		assertNull(response.getBody());
		verify(flightService, times(1)).getFlightById(id);
	}

	@Test
	void testGetFlightByFlightNumber() throws FlightNotFoundException {
		String flightNumber = "AI101";
		Flight flight = new Flight();
		when(flightService.getFlightByFlightNumber(flightNumber)).thenReturn(flight);

		ResponseEntity<Flight> response = flightController.getFlightByFlightNumber(flightNumber);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(flight, response.getBody());
		verify(flightService, times(1)).getFlightByFlightNumber(flightNumber);
	}

	@Test
	void testDeleteFlightById() {
		Long id = 1L;
		String result = "Flight deleted successfully";
		when(flightService.deleteFlightById(id)).thenReturn(result);

		ResponseEntity<String> response = flightController.deleteFlightById(id);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(result, response.getBody());
		verify(flightService, times(1)).deleteFlightById(id);
	}

	@Test
	void testBookSeat() throws FlightNotFoundException {
		// bookSeat method removed - test skipped
	}

	@Test
	void testCancelSeat() throws FlightNotFoundException {
		// canselSeat method removed - test skipped
	}

	@Test
	void testUpdateAvailableSeats() {
		String flightNumber = "AI101";
		int seatsToReduce = 5;
		String result = "Seats updated successfully";
		when(flightService.updateAvailableSeats(flightNumber, seatsToReduce)).thenReturn(result);

		ResponseEntity<String> response = flightController.updateAvailableSeats(flightNumber, seatsToReduce);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(result, response.getBody());
		verify(flightService, times(1)).updateAvailableSeats(flightNumber, seatsToReduce);
	}
}
