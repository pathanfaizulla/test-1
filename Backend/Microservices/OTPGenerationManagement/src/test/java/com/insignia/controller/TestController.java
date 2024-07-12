package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import jakarta.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.entity.CustomerBasicDetailsEntity;
import com.insignia.model.AuthenticationRequest;
import com.insignia.model.AuthenticationResponse;
import com.insignia.serviceInterface.OtpServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestController {

	@InjectMocks
	private OtpController otpController;

	@Mock
	private OtpServiceInterface otpServiceInterface;

	AuthenticationRequest authenticationRequest = new AuthenticationRequest();
	CustomerBasicDetailsEntity customerBasicDetailsEntity = new CustomerBasicDetailsEntity();
	AuthenticationResponse authenticationResponse = new AuthenticationResponse();

	public void dataInitilization() {

		authenticationRequest.setApplicationId("112");
		authenticationRequest.setTenantId("1124");
		authenticationRequest.setUserId("lakshminagu54@gmail.com");
		authenticationRequest.setEmail("lakshminagu54@gmail.com");
		authenticationRequest.setOtpExpiryDuration(5);
		authenticationRequest.setOtp("345876");

		customerBasicDetailsEntity.setApplicationId("112");
		customerBasicDetailsEntity.setTenantId("1124");
		customerBasicDetailsEntity.setUserId("lakshminagu54@gmail.com");
		customerBasicDetailsEntity.setCustomerPassword("5485");
		customerBasicDetailsEntity.setCustomerSequenceNumber(8L);
		customerBasicDetailsEntity.setCustomerEmail("lakshminagu54@gmail.com");
		customerBasicDetailsEntity.setUserName("Lakshmi");
		customerBasicDetailsEntity.setOtp("098877");

		authenticationResponse.setOtp("348756");

	}
	
	@Test
	public void testGenerateAndSaveOtpForInvalidInputParameters() throws InvalidInputParametersException, MessagingException {
		dataInitilization();
		authenticationRequest.setApplicationId(null);
		authenticationRequest.setTenantId(null);
		
		ResponseEntity<?> saveAddress = otpController.generateAndSaveOtp(authenticationRequest);
		assertEquals(HttpStatus.BAD_REQUEST, saveAddress.getStatusCode());
	}

	@Test
	public void testGenerateAndSaveOtp() throws InvalidInputParametersException, MessagingException {
		dataInitilization();
		when(otpServiceInterface.saveAndUpdateCustomer(authenticationRequest)).thenReturn(authenticationResponse);
		ResponseEntity<?> response = otpController.generateAndSaveOtp(authenticationRequest);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	@Test
	public void testForException() throws InvalidInputParametersException, MessagingException {
		dataInitilization();
	
		when(otpServiceInterface.saveAndUpdateCustomer(authenticationRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> saveAddress = otpController.generateAndSaveOtp(authenticationRequest);
		assertEquals(HttpStatus.BAD_REQUEST, saveAddress.getStatusCode());
	}

}
