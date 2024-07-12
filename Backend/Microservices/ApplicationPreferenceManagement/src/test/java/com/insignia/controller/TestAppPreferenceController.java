package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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
import com.insignia.model.AppPreferenceRequest;
import com.insignia.model.AppPreferenceResponse;
import com.insignia.model.ApplicationPreferenceRequest;
import com.insignia.model.ApplicationPreferenceResponse;
import com.insignia.serviceInterface.AppPreferenceServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestAppPreferenceController {

	@InjectMocks
	private AppPreferenceController appPreferenceController;

	@Mock
	private AppPreferenceServiceInterface serviceRepo;

	ApplicationPreferenceRequest applicationPreferenceRequest = new ApplicationPreferenceRequest();
	ApplicationPreferenceResponse applicationPreferenceResponse = new ApplicationPreferenceResponse();
	AppPreferenceRequest appPreferenceRequest = new AppPreferenceRequest();
	AppPreferenceResponse appPreferenceResponse = new AppPreferenceResponse();

	public void dataInitilization() {
		applicationPreferenceRequest.setApplicationId(5);
		applicationPreferenceRequest.setCustomerSequenceNumber(8L);
		applicationPreferenceRequest.setExpirationDuration(15);

		List<AppPreferenceRequest> appPreferenceRequestList = new ArrayList<>();
		appPreferenceRequest.setPreferenceType("Backend");
		appPreferenceRequest.setPreferenceValue("Hi");
		appPreferenceRequestList.add(appPreferenceRequest);

		applicationPreferenceRequest.setAppPreferenceRequestList(appPreferenceRequestList);

		applicationPreferenceResponse.setApplicationName("moneybridge");

		List<AppPreferenceResponse> appPreferenceResponseList = new ArrayList<>();
		appPreferenceResponse.setPreferenceType("Backend");
		appPreferenceResponse.setPreferenceValue("Hi");

		appPreferenceResponseList.add(appPreferenceResponse);
		applicationPreferenceResponse.setAppPreferenceResponseList(appPreferenceResponseList);

	}

	@Test
	public void testSaveApplicationDetails() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();
		when(serviceRepo.saveApplicationPreferenceDetails(applicationPreferenceRequest))
				.thenReturn(applicationPreferenceResponse);
		ResponseEntity<?> saveApplicationDetails = appPreferenceController
				.saveApplicationDetails(applicationPreferenceRequest);

		assertEquals(HttpStatus.OK, saveApplicationDetails.getStatusCode());
	}

	@Test
	public void testGetApplicationDetails() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();
		when(serviceRepo.getApplicationPreferenceDetails(applicationPreferenceRequest))
				.thenReturn(applicationPreferenceResponse);
		ResponseEntity<?> getApplicationDetails = appPreferenceController
				.getApplicationPreferenceDetails(applicationPreferenceRequest);

		assertEquals(HttpStatus.OK, getApplicationDetails.getStatusCode());
	}

	@Test
	public void testForInvalidInputParametersException() throws InvalidInputParametersException, TokenExpiredException {
		applicationPreferenceRequest
				.setApplicationName("moneybridgemoneybridgemoneybridgemoneybridgemoneybridgemoneybridgemoneybridge");

		ResponseEntity<?> saveApplicationDetails = appPreferenceController
				.saveApplicationDetails(applicationPreferenceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, saveApplicationDetails.getStatusCode());

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(serviceRepo)
				.getApplicationPreferenceDetails(applicationPreferenceRequest);

		ResponseEntity<?> response = appPreferenceController
				.getApplicationPreferenceDetails(applicationPreferenceRequest);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(serviceRepo.saveApplicationPreferenceDetails(applicationPreferenceRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> saveApplicationDetails = appPreferenceController
				.saveApplicationDetails(applicationPreferenceRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveApplicationDetails.getStatusCode());

		when(serviceRepo.getApplicationPreferenceDetails(applicationPreferenceRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getApplicationDetails = appPreferenceController
				.getApplicationPreferenceDetails(applicationPreferenceRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getApplicationDetails.getStatusCode());
	}

	@Test
	public void testForException() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(serviceRepo.saveApplicationPreferenceDetails(applicationPreferenceRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> saveApplicationDetails = appPreferenceController
				.saveApplicationDetails(applicationPreferenceRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveApplicationDetails.getStatusCode());

		when(serviceRepo.getApplicationPreferenceDetails(applicationPreferenceRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getApplicationDetails = appPreferenceController
				.getApplicationPreferenceDetails(applicationPreferenceRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getApplicationDetails.getStatusCode());

	}

}
