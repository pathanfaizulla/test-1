package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.entity.ApplicationEntity;
import com.insignia.model.ApplicationRequest;
import com.insignia.model.ApplicationResponse;
import com.insignia.serviceInterface.ApplicationServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestApplicationController {

	
	@InjectMocks
	private ApplicationController applicationController;
	
	@Mock
	private ApplicationServiceInterface serviceRepo;
	
	ApplicationEntity applicationEntity = new ApplicationEntity();
	ApplicationRequest applicationRequest = new ApplicationRequest();
	ApplicationResponse applicationResponse = new ApplicationResponse();
	
	public void dataInitilization() {
		applicationRequest.setApplicationId(5);
		applicationRequest.setApplicationName("moneybridge");
		applicationRequest.setApplicationDescription("I am Java Developer in Insignia");
		applicationRequest.setCustomerSequenceNumber(8L);
		applicationRequest.setExpirationDuration(15);
		
		applicationResponse.setApplicationId(5);
		applicationResponse.setApplicationName("moneybridge");
		applicationResponse.setApplicationDescription("I am Java Developer in Insignia");
	}
	
	@Test
	public void testSaveApplicationDetails() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();
		when(serviceRepo.saveApplicationDetails(applicationRequest)).thenReturn(applicationResponse);
		ResponseEntity<?> saveApplicationDetails = applicationController.saveApplicationDetails(applicationRequest);
		
		assertEquals(HttpStatus.OK, saveApplicationDetails.getStatusCode());
	}
	@Test
	public void testUpdateApplicationDetails() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();
		when(serviceRepo.updateApplicationDetails(applicationRequest)).thenReturn(applicationResponse);
		ResponseEntity<?> updateApplication = applicationController.updateApplicationDetails(applicationRequest);
		
		assertEquals(HttpStatus.OK, updateApplication.getStatusCode());
	}
	@Test
	public void testDeleteApplicationDetails() throws InvalidInputParametersException, TokenExpiredException {
		Integer applicationId = 5;
		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;

		doNothing().when(serviceRepo).deleteByApplicationId(applicationId, customerSequenceNumber, expirationDuration);
		
		ResponseEntity<?> deleteByApplicationId = applicationController.deleteByApplicationId(applicationId, customerSequenceNumber, expirationDuration);
		verify(serviceRepo, times(1)).deleteByApplicationId(applicationId, customerSequenceNumber,expirationDuration);
		
		
		assertEquals(HttpStatus.OK, deleteByApplicationId.getStatusCode());
	}
	
	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		Integer applicationId = 5;
		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 15;

		when(serviceRepo.saveApplicationDetails(applicationRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> saveApplicationDetails = applicationController.saveApplicationDetails(applicationRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveApplicationDetails.getStatusCode());
		
		when(serviceRepo.updateApplicationDetails(applicationRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> updateApplication = applicationController.updateApplicationDetails(applicationRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateApplication.getStatusCode()); 

		when(serviceRepo.getApplicationDetails(applicationId, customerSequenceNumber, expirationDuration)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> applicationDetails = applicationController.getApplicationDetails(applicationRequest);

		assertEquals(HttpStatus.BAD_REQUEST, applicationDetails.getStatusCode());
		
		doThrow(new TokenExpiredException("")).when(serviceRepo).deleteByApplicationId(applicationId,
				customerSequenceNumber,expirationDuration);
		ResponseEntity<?> deleteByApplicationId = applicationController.deleteByApplicationId(applicationId,
				customerSequenceNumber,expirationDuration);
		assertEquals(HttpStatus.BAD_REQUEST, deleteByApplicationId.getStatusCode());
	}
	
	@Test
	public void testForInvalidInputParametersException() throws InvalidInputParametersException, TokenExpiredException {
		applicationRequest.setApplicationName("moneybridgemoneybridgemoneybridgemoneybridgemoneybridgemoneybridgemoneybridge");
		
		ResponseEntity<?> saveApplicationDetails = applicationController.saveApplicationDetails(applicationRequest);
		assertEquals(HttpStatus.BAD_REQUEST, saveApplicationDetails.getStatusCode());
		
		ResponseEntity<?> updateApplicationDetails = applicationController.updateApplicationDetails(applicationRequest);
		assertEquals(HttpStatus.BAD_REQUEST, updateApplicationDetails.getStatusCode());

	}
	
	@Test
	public void testForException() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();
		Integer applicationId = 1;
		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;

		when(serviceRepo.saveApplicationDetails(applicationRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> saveApplication= applicationController.saveApplicationDetails(applicationRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveApplication.getStatusCode());
		
		when(serviceRepo.updateApplicationDetails(applicationRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> updateApplication = applicationController.updateApplicationDetails(applicationRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateApplication.getStatusCode());

		when(serviceRepo.getApplicationDetails(applicationId, customerSequenceNumber, expirationDuration)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> applicationDetails = applicationController.getApplicationDetails(applicationRequest);

		assertEquals(HttpStatus.BAD_REQUEST, applicationDetails.getStatusCode());
		
		doThrow(new NullPointerException("")).when(serviceRepo).deleteByApplicationId(applicationId,
				customerSequenceNumber,expirationDuration);
		ResponseEntity<?> deleteByApplicationId = applicationController.deleteByApplicationId(applicationId,
				customerSequenceNumber,expirationDuration);
		assertEquals(HttpStatus.BAD_REQUEST, deleteByApplicationId.getStatusCode());

	}	
	
	
}
