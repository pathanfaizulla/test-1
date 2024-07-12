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
import java.util.Arrays;
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
import com.insignia.entity.ProductSubscriptionPlanLink;
import com.insignia.model.ProductSubscriptionLinkRequest;
import com.insignia.model.SubscriptionResponse;
import com.insignia.model.SubscriptonLinkRequest;
import com.insignia.model.SubscriptonLinkResponse;
import com.insignia.service.ProductAndSubscriptonLinkServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestProductSubscriptionLinkPlanController {

	@InjectMocks
	private ProductAndSubscriptonLinkController productAndSubscriptonLinkController;

	@Mock
	private ProductAndSubscriptonLinkServiceInterface productServiceInterface;

	SubscriptonLinkRequest subscriptonLinkRequest = new SubscriptonLinkRequest();
	SubscriptonLinkResponse subscriptonLinkResponse = new SubscriptonLinkResponse();
	ProductSubscriptionPlanLink productSubscriptionPlanLink = new ProductSubscriptionPlanLink();

	ProductSubscriptionLinkRequest productSubscriptionLinkRequest = new ProductSubscriptionLinkRequest();
	List<SubscriptonLinkResponse> subscriptonLinkResponseList = new ArrayList<>();
	SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
	List<SubscriptionResponse> subscriptionResponseList = new ArrayList<>();
	Optional<List<SubscriptonLinkResponse>> optionalProductAndSubscriptionLinkResponse = Optional
			.ofNullable(subscriptonLinkResponseList);

	public void dataInitilization() throws ParseException {
		subscriptonLinkRequest.setCustomerSequenceNumber(5L);
		subscriptonLinkRequest.setExpirationDuration(15);
		subscriptonLinkRequest.setSubscriptionPlanSequenceNumber(5L);

		subscriptonLinkResponse.setSequenceNumber(5L);
		subscriptonLinkResponse.setSubscriptionPlanSequenceNumber(5L);

		subscriptionResponse.setSequenceNumber(5L);
		subscriptionResponse.setPlanId("1");
		subscriptionResponse.setPlanName("Health");
		subscriptionResponse.setPlanDescription("Its Kids");
		subscriptionResponse.setPlanDuration(3);
		subscriptionResponse.setPlanPricing(BigDecimal.valueOf(400.25));
		subscriptionResponse.setPlanActivationStatus("Active");
		subscriptionResponse.setPercentageDiscount(BigDecimal.valueOf(3));

		subscriptionResponseList.add(subscriptionResponse);

		subscriptonLinkResponseList.add(subscriptonLinkResponse);
		productSubscriptionPlanLink.setProductSequenceNumber(5L);
		productSubscriptionPlanLink.setSequenceNumber(5L);
		
		productSubscriptionLinkRequest.setProductSequenceNumber(5L);
		productSubscriptionLinkRequest.setSubscriptionPlanSequenceNumber(5L);
		List<Long> subscriptionPlanSequenceNumber = Arrays.asList(1L);
		productSubscriptionLinkRequest.setSubscriptionSequenceNumberList(subscriptionPlanSequenceNumber);

	}

	@Test
	public void testAssociateSubscriptionPlanWithCustomer()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();
		when(productServiceInterface.associateSubscriptionPlanWithProduct(productSubscriptionLinkRequest))
				.thenReturn(subscriptonLinkResponse);

		ResponseEntity<?> associateSubscriptionPlanWithProduct = productAndSubscriptonLinkController
				.associateSubscriptionPlanWithProduct(productSubscriptionLinkRequest);
		assertEquals(HttpStatus.OK, associateSubscriptionPlanWithProduct.getStatusCode());
	}

	@Test
	public void testDeleteSubscriptionPlanWithCustomer()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		doNothing().when(productServiceInterface).removeSubscriptionPlanForProduct(productSubscriptionLinkRequest);
		productAndSubscriptonLinkController.removeSubscriptionPlanForProduct(productSubscriptionLinkRequest);
		verify(productServiceInterface, times(1)).removeSubscriptionPlanForProduct(productSubscriptionLinkRequest);
	}

	@Test
	public void testGetSubscriptionPlanWithCustomer()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();
		when(productServiceInterface.getAllSubscriptionPlanForProduct(productSubscriptionLinkRequest))
				.thenReturn(optionalProductAndSubscriptionLinkResponse);

		ResponseEntity<?> getSubscriptionPlanWithProduct = productAndSubscriptonLinkController
				.getSubscriptionPlanWithProduct(productSubscriptionLinkRequest);
		assertEquals(HttpStatus.OK, getSubscriptionPlanWithProduct.getStatusCode());
	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException, ParseException {
		dataInitilization();

		when(productServiceInterface.associateSubscriptionPlanWithProduct(productSubscriptionLinkRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> associateSubscriptionPlanWithCustomer = productAndSubscriptonLinkController
				.associateSubscriptionPlanWithProduct(productSubscriptionLinkRequest);

		assertEquals(HttpStatus.BAD_REQUEST, associateSubscriptionPlanWithCustomer.getStatusCode());

		when(productServiceInterface.getAllSubscriptionPlanForProduct(productSubscriptionLinkRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getSubscriptionPlanWithCustomer = productAndSubscriptonLinkController
				.getSubscriptionPlanWithProduct(productSubscriptionLinkRequest);
		assertEquals(HttpStatus.BAD_REQUEST, getSubscriptionPlanWithCustomer.getStatusCode());

		doThrow(new TokenExpiredException("")).when(productServiceInterface)
				.removeSubscriptionPlanForProduct(productSubscriptionLinkRequest);
		ResponseEntity<?> deleteSubscriptionPlanWithCustomer = productAndSubscriptonLinkController
				.removeSubscriptionPlanForProduct(productSubscriptionLinkRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deleteSubscriptionPlanWithCustomer.getStatusCode());

	}

	@Test
	public void testForException() throws TokenExpiredException, InvalidInputParametersException, ParseException {
		dataInitilization();

		when(productServiceInterface.associateSubscriptionPlanWithProduct(productSubscriptionLinkRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> associateSubscriptionPlanWithCustomer = productAndSubscriptonLinkController
				.associateSubscriptionPlanWithProduct(productSubscriptionLinkRequest);

		assertEquals(HttpStatus.BAD_REQUEST, associateSubscriptionPlanWithCustomer.getStatusCode());

		when(productServiceInterface.getAllSubscriptionPlanForProduct(productSubscriptionLinkRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getSubscriptionPlanWithCustomer = productAndSubscriptonLinkController
				.getSubscriptionPlanWithProduct(productSubscriptionLinkRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getSubscriptionPlanWithCustomer.getStatusCode());

		doThrow(new NullPointerException("")).when(productServiceInterface)
				.removeSubscriptionPlanForProduct(productSubscriptionLinkRequest);
		ResponseEntity<?> deleteSubscriptionPlanWithCustomer = productAndSubscriptonLinkController
				.removeSubscriptionPlanForProduct(productSubscriptionLinkRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deleteSubscriptionPlanWithCustomer.getStatusCode());

	}

	@Test
	public void testForInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productServiceInterface)
				.associateSubscriptionPlanWithProduct(productSubscriptionLinkRequest);

		ResponseEntity<?> associateSubscriptionPlanWithCustomer = productAndSubscriptonLinkController
				.associateSubscriptionPlanWithProduct(productSubscriptionLinkRequest);

		verify(productServiceInterface).associateSubscriptionPlanWithProduct(productSubscriptionLinkRequest);

		assertEquals(HttpStatus.BAD_REQUEST, associateSubscriptionPlanWithCustomer.getStatusCode());

		// getAllSubscriptionPlanForCustomer
		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productServiceInterface)
				.getAllSubscriptionPlanForProduct(productSubscriptionLinkRequest);

		ResponseEntity<?> getAllSubscriptionPlanForCustomer = productAndSubscriptonLinkController
				.getSubscriptionPlanWithProduct(productSubscriptionLinkRequest);

		verify(productServiceInterface).getAllSubscriptionPlanForProduct(productSubscriptionLinkRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllSubscriptionPlanForCustomer.getStatusCode());

		// deleteSubscriptionPlanWithCustomer
		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(productServiceInterface)
				.removeSubscriptionPlanForProduct(productSubscriptionLinkRequest);

		ResponseEntity<?> removeSubscriptionPlanForCustomer = productAndSubscriptonLinkController
				.removeSubscriptionPlanForProduct(productSubscriptionLinkRequest);

		verify(productServiceInterface).removeSubscriptionPlanForProduct(productSubscriptionLinkRequest);

		assertEquals(HttpStatus.BAD_REQUEST, removeSubscriptionPlanForCustomer.getStatusCode());

	}

}
