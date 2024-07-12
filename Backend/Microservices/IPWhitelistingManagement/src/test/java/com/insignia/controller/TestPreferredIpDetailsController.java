package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.CustomerIpDetails;
import com.insignia.model.IpDetails;
import com.insignia.model.PreferredIpDetailsRequest;
import com.insignia.model.PreferredIpDetailsResponse;
import com.insignia.serviceInterface.PreferredIpDetailsServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestPreferredIpDetailsController {

	@InjectMocks
	private PreferredIpDetailsController preferredIpDetailsController;

	@Mock
	private PreferredIpDetailsServiceInterface preferredIpDetailsServiceInterface;

	List<CustomerIpDetails> customerIpDetailsList = new ArrayList<>();
	List<IpDetails> ipDetailsList = new ArrayList<>();
	List<PreferredIpDetailsResponse> preferredIpDetailsResponseList = new ArrayList<>();

	PreferredIpDetailsRequest preferredIpDetailsRequest = new PreferredIpDetailsRequest();
	PreferredIpDetailsResponse preferredIpDetailsResponse = new PreferredIpDetailsResponse();
	CustomerIpDetails customerIpDetails = new CustomerIpDetails();
	IpDetails ipDetails = new IpDetails();

	public void dataInitilization() {
		preferredIpDetailsRequest.setCustomerSequenceNumber(5L);
		preferredIpDetailsRequest.setExpirationDuration(15);
		preferredIpDetailsRequest.setIpDetails("130.0.0.0");
		preferredIpDetailsRequest.setIpType("IPv4");

		ipDetails.setIpDetails("130.0.0.0");
		ipDetails.setIpType("IPv4");

		ipDetailsList.add(ipDetails);

		customerIpDetails.setCustomerSequenceNumber(5L);
		customerIpDetails.setIpDetailsList(ipDetailsList);
		customerIpDetailsList.add(customerIpDetails);
		preferredIpDetailsResponse.setCustomerIpDetailsList(customerIpDetailsList);
		preferredIpDetailsResponseList.add(preferredIpDetailsResponse);
	}

	@Test
	public void testSavePreferredIpDetails() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(preferredIpDetailsServiceInterface.savePreferredIpDetails(preferredIpDetailsRequest))
				.thenReturn(preferredIpDetailsResponse);
		ResponseEntity<?> response = preferredIpDetailsController.savePreferredIpDetails(preferredIpDetailsRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testSavePreferredIpDetailsInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(preferredIpDetailsServiceInterface)
				.savePreferredIpDetails(preferredIpDetailsRequest);
		ResponseEntity<?> savePreferredIpDetails = preferredIpDetailsController
				.savePreferredIpDetails(preferredIpDetailsRequest);

		verify(preferredIpDetailsServiceInterface).savePreferredIpDetails(preferredIpDetailsRequest);
		assertEquals(HttpStatus.BAD_REQUEST, savePreferredIpDetails.getStatusCode());

	}

	@Test
	public void testGetAllPreferredIpDetails() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(preferredIpDetailsServiceInterface.getAllPreferredIpDetails(preferredIpDetailsRequest))
				.thenReturn(preferredIpDetailsResponseList);
		ResponseEntity<?> response = preferredIpDetailsController.getAllPreferredIpDetails(preferredIpDetailsRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeletePreferredIpDetails() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		doNothing().when(preferredIpDetailsServiceInterface).deletePreferredIpDetails(preferredIpDetailsRequest);
		preferredIpDetailsController.deletePreferredIpDetails(preferredIpDetailsRequest);
		verify(preferredIpDetailsServiceInterface, times(1)).deletePreferredIpDetails(preferredIpDetailsRequest);
	}

	@Test
	public void testDeletePreferredIpDetailsInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(preferredIpDetailsServiceInterface)
				.deletePreferredIpDetails(preferredIpDetailsRequest);
		ResponseEntity<?> deletePreferredIpDetails = preferredIpDetailsController
				.deletePreferredIpDetails(preferredIpDetailsRequest);

		verify(preferredIpDetailsServiceInterface).deletePreferredIpDetails(preferredIpDetailsRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deletePreferredIpDetails.getStatusCode());

	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(preferredIpDetailsServiceInterface.savePreferredIpDetails(preferredIpDetailsRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> savePreferredIpDetails = preferredIpDetailsController
				.savePreferredIpDetails(preferredIpDetailsRequest);

		assertEquals(HttpStatus.BAD_REQUEST, savePreferredIpDetails.getStatusCode());

		doThrow(new TokenExpiredException("")).when(preferredIpDetailsServiceInterface)
				.deletePreferredIpDetails(preferredIpDetailsRequest);
		ResponseEntity<?> deletePreferredIpDetails = preferredIpDetailsController
				.deletePreferredIpDetails(preferredIpDetailsRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deletePreferredIpDetails.getStatusCode());

		when(preferredIpDetailsServiceInterface.getAllPreferredIpDetails(preferredIpDetailsRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getAllPreferredIpDetails = preferredIpDetailsController
				.getAllPreferredIpDetails(preferredIpDetailsRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllPreferredIpDetails.getStatusCode());

	}
	@Test
	public void testForException() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(preferredIpDetailsServiceInterface.savePreferredIpDetails(preferredIpDetailsRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> savePreferredIpDetails = preferredIpDetailsController
				.savePreferredIpDetails(preferredIpDetailsRequest);

		assertEquals(HttpStatus.BAD_REQUEST, savePreferredIpDetails.getStatusCode());

		doThrow(new NullPointerException("")).when(preferredIpDetailsServiceInterface)
				.deletePreferredIpDetails(preferredIpDetailsRequest);
		ResponseEntity<?> deletePreferredIpDetails = preferredIpDetailsController
				.deletePreferredIpDetails(preferredIpDetailsRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deletePreferredIpDetails.getStatusCode());

		when(preferredIpDetailsServiceInterface.getAllPreferredIpDetails(preferredIpDetailsRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getAllPreferredIpDetails = preferredIpDetailsController
				.getAllPreferredIpDetails(preferredIpDetailsRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllPreferredIpDetails.getStatusCode());

	}

}
