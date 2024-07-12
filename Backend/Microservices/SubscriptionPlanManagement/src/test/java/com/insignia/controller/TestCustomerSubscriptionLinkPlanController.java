package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.entity.CustomerSubscriptionPlanLink;
import com.insignia.model.CustomerSubscriptonLinkRequest;
import com.insignia.model.SubscriptionResponse;
import com.insignia.model.SubscriptonLinkResponse;
import com.insignia.service.CustomerAndSubscriptonLinkServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestCustomerSubscriptionLinkPlanController {

	@InjectMocks
	private CustomerAndSubscriptonLinkController customerAndSubscriptonLinkController;

	@Mock
	private CustomerAndSubscriptonLinkServiceInterface serviceRepo;

	SubscriptonLinkResponse customerAndSubscriptonLinkResponse = new SubscriptonLinkResponse();
	CustomerSubscriptionPlanLink customerSubscriptionPlanLink = new CustomerSubscriptionPlanLink();
	
	CustomerSubscriptonLinkRequest customerSubscriptonLinkRequest = new CustomerSubscriptonLinkRequest();

	List<SubscriptonLinkResponse> customerAndSubscriptonLinkResponseList = new ArrayList<>();
	SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
	List<SubscriptionResponse> subscriptionResponseList = new ArrayList<>();
	Optional<List<SubscriptonLinkResponse>> optionalCustomerAndSubscriptionLinkResponse = Optional
			.ofNullable(customerAndSubscriptonLinkResponseList);

	public void dataInitilization() throws ParseException {
		customerSubscriptonLinkRequest.setCustomerSequenceNumber(5L);
		customerSubscriptonLinkRequest.setExpirationDuration(15);
		customerSubscriptonLinkRequest.setSubscriptionPlanSequenceNumber(5L);
		customerSubscriptonLinkRequest.setAutoRenewal(true);
		
		customerAndSubscriptonLinkResponse.setSequenceNumber(5L);
		customerAndSubscriptonLinkResponse.setSubscriptionPlanSequenceNumber(5L);

		subscriptionResponse.setSequenceNumber(5L);
		subscriptionResponse.setPlanId("1");
		subscriptionResponse.setPlanName("Health");
		subscriptionResponse.setPlanDescription("Its Kids");
		subscriptionResponse.setPlanDuration(3);
		subscriptionResponse.setPlanPricing(BigDecimal.valueOf(300.25));
		subscriptionResponse.setPlanActivationStatus("Active");
		subscriptionResponse.setPercentageDiscount(BigDecimal.valueOf(3));

		subscriptionResponseList.add(subscriptionResponse);

		customerAndSubscriptonLinkResponseList.add(customerAndSubscriptonLinkResponse);
		customerSubscriptionPlanLink.setCustomerSequenceNumber(5L);
		customerSubscriptionPlanLink.setSequenceNumber(5L);

	}

	@Test
	public void testAssociateSubscriptionPlanWithCustomer()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();
		when(serviceRepo.associateSubscriptionPlanWithCustomer(customerSubscriptonLinkRequest))
				.thenReturn(customerAndSubscriptonLinkResponse);

		ResponseEntity<?> associateSubscriptionPlanWithCustomer = customerAndSubscriptonLinkController
				.associateSubscriptionPlanWithCustomer(customerSubscriptonLinkRequest);
		assertEquals(HttpStatus.OK, associateSubscriptionPlanWithCustomer.getStatusCode());
	}

	@Test
	public void testDeleteSubscriptionPlanWithCustomer()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		doNothing().when(serviceRepo).removeSubscriptionPlanForCustomer(customerSubscriptonLinkRequest);
		customerAndSubscriptonLinkController.removeSubscriptionPlanForCustomer(customerSubscriptonLinkRequest);
		verify(serviceRepo, times(1)).removeSubscriptionPlanForCustomer(customerSubscriptonLinkRequest);
	}

	@Test
	public void testGetSubscriptionPlanWithCustomer()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();
		when(serviceRepo.getAllSubscriptionPlanForCustomer(customerSubscriptonLinkRequest))
				.thenReturn(optionalCustomerAndSubscriptionLinkResponse);

		ResponseEntity<?> getSubscriptionPlanWithCustomer = customerAndSubscriptonLinkController
				.getSubscriptionPlanWithCustomer(customerSubscriptonLinkRequest);
		assertEquals(HttpStatus.OK, getSubscriptionPlanWithCustomer.getStatusCode());
	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException, ParseException {
		dataInitilization();

		when(serviceRepo.associateSubscriptionPlanWithCustomer(customerSubscriptonLinkRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> associateSubscriptionPlanWithCustomer = customerAndSubscriptonLinkController
				.associateSubscriptionPlanWithCustomer(customerSubscriptonLinkRequest);

		assertEquals(HttpStatus.BAD_REQUEST, associateSubscriptionPlanWithCustomer.getStatusCode());

		when(serviceRepo.getAllSubscriptionPlanForCustomer(customerSubscriptonLinkRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getSubscriptionPlanWithCustomer = customerAndSubscriptonLinkController
				.getSubscriptionPlanWithCustomer(customerSubscriptonLinkRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getSubscriptionPlanWithCustomer.getStatusCode());

		doThrow(new TokenExpiredException("")).when(serviceRepo)
				.removeSubscriptionPlanForCustomer(customerSubscriptonLinkRequest);
		ResponseEntity<?> deleteSubscriptionPlanWithCustomer = customerAndSubscriptonLinkController
				.removeSubscriptionPlanForCustomer(customerSubscriptonLinkRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deleteSubscriptionPlanWithCustomer.getStatusCode());

	}

	@Test
	public void testForException() throws TokenExpiredException, InvalidInputParametersException, ParseException {
		dataInitilization();

		when(serviceRepo.associateSubscriptionPlanWithCustomer(customerSubscriptonLinkRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> associateSubscriptionPlanWithCustomer = customerAndSubscriptonLinkController
				.associateSubscriptionPlanWithCustomer(customerSubscriptonLinkRequest);

		assertEquals(HttpStatus.BAD_REQUEST, associateSubscriptionPlanWithCustomer.getStatusCode());

		when(serviceRepo.getAllSubscriptionPlanForCustomer(customerSubscriptonLinkRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getSubscriptionPlanWithCustomer = customerAndSubscriptonLinkController
				.getSubscriptionPlanWithCustomer(customerSubscriptonLinkRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getSubscriptionPlanWithCustomer.getStatusCode());

		doThrow(new NullPointerException("")).when(serviceRepo)
				.removeSubscriptionPlanForCustomer(customerSubscriptonLinkRequest);
		ResponseEntity<?> deleteSubscriptionPlanWithCustomer = customerAndSubscriptonLinkController
				.removeSubscriptionPlanForCustomer(customerSubscriptonLinkRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deleteSubscriptionPlanWithCustomer.getStatusCode());

	}

	@Test
	public void testForInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(serviceRepo)
				.associateSubscriptionPlanWithCustomer(customerSubscriptonLinkRequest);

		ResponseEntity<?> associateSubscriptionPlanWithCustomer = customerAndSubscriptonLinkController
				.associateSubscriptionPlanWithCustomer(customerSubscriptonLinkRequest);

		verify(serviceRepo).associateSubscriptionPlanWithCustomer(customerSubscriptonLinkRequest);

		assertEquals(HttpStatus.BAD_REQUEST, associateSubscriptionPlanWithCustomer.getStatusCode());

		// getAllSubscriptionPlanForCustomer
		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(serviceRepo)
				.getAllSubscriptionPlanForCustomer(customerSubscriptonLinkRequest);

		ResponseEntity<?> getAllSubscriptionPlanForCustomer = customerAndSubscriptonLinkController
				.getSubscriptionPlanWithCustomer(customerSubscriptonLinkRequest);

		verify(serviceRepo).getAllSubscriptionPlanForCustomer(customerSubscriptonLinkRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllSubscriptionPlanForCustomer.getStatusCode());

		// deleteSubscriptionPlanWithCustomer
		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(serviceRepo)
				.removeSubscriptionPlanForCustomer(customerSubscriptonLinkRequest);
		

		ResponseEntity<?> removeSubscriptionPlanForCustomer = customerAndSubscriptonLinkController
				.removeSubscriptionPlanForCustomer(customerSubscriptonLinkRequest);

		verify(serviceRepo).removeSubscriptionPlanForCustomer(customerSubscriptonLinkRequest);
		

		assertEquals(HttpStatus.BAD_REQUEST, removeSubscriptionPlanForCustomer.getStatusCode());

	}

}
