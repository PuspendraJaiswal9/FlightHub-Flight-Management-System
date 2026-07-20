//package com.example.fareStore_service;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class FareStoreServiceApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//
//}


package com.example.fareStore_service;

import com.example.fareStore_service.controller.FareController;
import com.example.fareStore_service.dto.FareRequest;
import com.example.fareStore_service.dto.FareResponse;
import com.example.fareStore_service.exception.FareNotFoundException;
import com.example.fareStore_service.model.Fare;
import com.example.fareStore_service.service.FareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FareControllerTest {

	@InjectMocks
	private FareController fareController;

	@Mock
	private FareService fareService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddFare() {
		FareRequest request = new FareRequest();
		Fare fare = new Fare();
		when(fareService.addFare(request)).thenReturn(fare);

		ResponseEntity<Fare> response = fareController.addFare(request);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(fare, response.getBody());
		verify(fareService, times(1)).addFare(request);
	}

	@Test
	void testUpdateFare() throws FareNotFoundException {
		String flightNumber = "AI101";
		FareRequest request = new FareRequest();
		Fare updatedFare = new Fare();
		when(fareService.updateFare(request, flightNumber)).thenReturn(updatedFare);

		ResponseEntity<Fare> response = fareController.updateFare(request, flightNumber);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(updatedFare, response.getBody());
		verify(fareService, times(1)).updateFare(request, flightNumber);
	}

	@Test
	void testGetFare() throws FareNotFoundException {
		String flightNumber = "AI101";
		FareResponse fareResponse = new FareResponse();
		when(fareService.getFareByFlightNumnber(flightNumber)).thenReturn(fareResponse);

		ResponseEntity<FareResponse> response = fareController.getFare(flightNumber);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(fareResponse, response.getBody());
		verify(fareService, times(1)).getFareByFlightNumnber(flightNumber);
	}

	@Test
	void testDeleteFare() throws FareNotFoundException {
		String flightNumber = "AI101";
		String result = "Fare deleted successfully";
		when(fareService.deleteFareByFlightNumber(flightNumber)).thenReturn(result);

		ResponseEntity<String> response = fareController.deleteFare(flightNumber);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(result, response.getBody());
		verify(fareService, times(1)).deleteFareByFlightNumber(flightNumber);
	}
}

