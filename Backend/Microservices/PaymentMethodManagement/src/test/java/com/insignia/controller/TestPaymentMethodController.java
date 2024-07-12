package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import com.insignia.model.PaymentMethodRequest;
import com.insignia.model.PaymentMethodResponse;
import com.insignia.serviceInterface.PaymentServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestPaymentMethodController {

	@InjectMocks
	private PaymentMethodController paymentMethodController;

	@Mock
	private PaymentServiceInterface paymentServiceRepo;

	
	List<PaymentMethodResponse> paymentMethodResponseList = new ArrayList<>();
	PaymentMethodRequest paymentMethodRequest = new PaymentMethodRequest();
	PaymentMethodResponse paymentMethodResponse = new PaymentMethodResponse();

	
	public void dataInitilization() {
		paymentMethodRequest.setCustomerSequenceNumber(8L);
		paymentMethodRequest.setPaymentMethodSequenceNumber(8L);
		paymentMethodRequest.setPaymentMethodType("Card");
		paymentMethodRequest.setPaymentMethodDetails("Its easy");
		paymentMethodRequest.setIsDefaultPaymentMethod(false);
		paymentMethodRequest.setExpirationDuration(15);
		
	
		
		paymentMethodResponse.setPaymentMethodSequenceNumber(8L);
		paymentMethodResponse.setPaymentMethodType("Card");
		paymentMethodResponse.setPaymentMethodDetails("Its easy");
		paymentMethodResponse.setIsDefaultPaymentMethod(false);
		
		paymentMethodResponseList.add(paymentMethodResponse);
		
	}
	

	@Test
	public void testAddPaymentMethod() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(paymentServiceRepo.addPaymentMethod(paymentMethodRequest)).thenReturn(paymentMethodResponse);
		ResponseEntity<?> response = paymentMethodController.addPaymentMethod(paymentMethodRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	

	@Test
	public void testRemovePaymentMethod() throws Exception {

		Long paymentMethodSequenceNumber = 5L;
		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		doNothing().when(paymentServiceRepo).removePaymentMethod(paymentMethodSequenceNumber, customerSequenceNumber, expirationDuration);

		paymentMethodController.removePaymentMethod(paymentMethodSequenceNumber, customerSequenceNumber, expirationDuration);
		verify(paymentServiceRepo, times(1)).removePaymentMethod(paymentMethodSequenceNumber, customerSequenceNumber, expirationDuration);

	}

	@Test
	public void testListOfPaymentMethodDetails() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
	
		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		when(paymentServiceRepo.getListOfPaymentMethodDetails(customerSequenceNumber, expirationDuration)).thenReturn(paymentMethodResponseList);
		ResponseEntity<?> response = paymentMethodController.listPaymentMethod(customerSequenceNumber, expirationDuration);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	
	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		Long customerSequenceNumber = 5L;
		Long paymentMethodSequenceNumber = 5L;
		Integer expirationDuration = 15;

		when(paymentServiceRepo.addPaymentMethod(paymentMethodRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> addPaymentMethod = paymentMethodController.addPaymentMethod(paymentMethodRequest);

		assertEquals(HttpStatus.BAD_REQUEST, addPaymentMethod.getStatusCode());


		doThrow(new TokenExpiredException("")).when(paymentServiceRepo).removePaymentMethod(paymentMethodSequenceNumber, customerSequenceNumber, expirationDuration);
		ResponseEntity<?> removePaymentMethod = paymentMethodController.removePaymentMethod(paymentMethodSequenceNumber, customerSequenceNumber, expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, removePaymentMethod.getStatusCode());

		when(paymentServiceRepo.getListOfPaymentMethodDetails(customerSequenceNumber, expirationDuration))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getListOfPaymentMethodDetails = paymentMethodController.listPaymentMethod(customerSequenceNumber, expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, getListOfPaymentMethodDetails.getStatusCode());

	}

	@Test
	public void testForException() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		Long customerSequenceNumber = 5L;
		Long paymentMethodSequenceNumber = 5L;
		Integer expirationDuration = 15;

		when(paymentServiceRepo.addPaymentMethod(paymentMethodRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> addPaymentMethod = paymentMethodController.addPaymentMethod(paymentMethodRequest);

		assertEquals(HttpStatus.BAD_REQUEST, addPaymentMethod.getStatusCode());


		doThrow(new NullPointerException("")).when(paymentServiceRepo).removePaymentMethod(paymentMethodSequenceNumber, customerSequenceNumber, expirationDuration);
		ResponseEntity<?> removePaymentMethod = paymentMethodController.removePaymentMethod(paymentMethodSequenceNumber, customerSequenceNumber, expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, removePaymentMethod.getStatusCode());

		when(paymentServiceRepo.getListOfPaymentMethodDetails(customerSequenceNumber, expirationDuration))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getListOfPaymentMethodDetails = paymentMethodController.listPaymentMethod(customerSequenceNumber, expirationDuration);

		assertEquals(HttpStatus.BAD_REQUEST, getListOfPaymentMethodDetails.getStatusCode());

	}

}
