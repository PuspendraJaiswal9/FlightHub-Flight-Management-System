//package com.example.booking_service;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class BookingServiceApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//
//}


package com.example.booking_service;

import com.example.booking_service.controller.BookingController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.example.booking_service.dto.BookingRequest;
import com.example.booking_service.dto.BookingResponse;
import com.example.booking_service.exception.FareNotFoundException;
import com.example.booking_service.exception.FlightNotFoundException;
import com.example.booking_service.model.Booking;
import com.example.booking_service.service.BookingService;


class BookingControllerTest {

	@InjectMocks
	private BookingController bookingController;

	@Mock
	private BookingService bookingService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddBooking() throws FlightNotFoundException, FareNotFoundException {
		BookingRequest request = new BookingRequest();
		Booking booking = new Booking();
		when(bookingService.addBooking(request)).thenReturn(booking);

		ResponseEntity<Booking> response = bookingController.addBooking(request);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(booking, response.getBody());
		verify(bookingService, times(1)).addBooking(request);
	}

	@Test
	void testGetBooking_Found() {
		String bookingRef = "ABC123";
		BookingResponse bookingResponse = new BookingResponse();
		when(bookingService.getBookingDetails(bookingRef)).thenReturn(bookingResponse);

		ResponseEntity<BookingResponse> response = bookingController.getBooking(bookingRef);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(bookingResponse, response.getBody());
		verify(bookingService, times(1)).getBookingDetails(bookingRef);
	}

	@Test
	void testGetBooking_NotFound() {
		String bookingRef = "XYZ999";
		when(bookingService.getBookingDetails(bookingRef)).thenReturn(null);

		ResponseEntity<BookingResponse> response = bookingController.getBooking(bookingRef);

		assertEquals(404, response.getStatusCodeValue());
		assertNull(response.getBody());
		verify(bookingService, times(1)).getBookingDetails(bookingRef);
	}

	@Test
	void testCancelBooking() {
		String bookingRef = "DEL123";
		String result = "Booking cancelled successfully";
		when(bookingService.cancelBooking(bookingRef)).thenReturn(result);

		ResponseEntity<String> response = bookingController.cancelBooking(bookingRef);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(result, response.getBody());
		verify(bookingService, times(1)).cancelBooking(bookingRef);
	}
}

