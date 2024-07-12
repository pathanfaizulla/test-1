package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.insignia.entity.SubscriptionDetails;
import com.insignia.model.SubscriptionRequest;
import com.insignia.model.SubscriptionResponse;
import com.insignia.service.SubscriptionServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestSubscriptionController {

	@InjectMocks
	private SubscriptionSystemController subscriptionController;

	@Mock
	private SubscriptionServiceInterface serviceRepo;

	SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
	SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
	List<SubscriptionResponse> subscriptionResponseList = new ArrayList<>();
	SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
	Optional<List<SubscriptionResponse>> optionalSubscriptionDetails = Optional.ofNullable(subscriptionResponseList);

	public void dataInitilization() throws ParseException {
		subscriptionRequest.setCustomerSequenceNumber(5L);
		subscriptionRequest.setExpirationDuration(15);

		subscriptionRequest.setSequenceNumber(5L);
		subscriptionRequest.setPlanId("1");
		subscriptionRequest.setPlanName("Health");
		subscriptionRequest.setPlanDescription("Its Kids");
		subscriptionRequest.setPlanDuration(3);
		subscriptionRequest.setPlanPricing(BigDecimal.valueOf(400.25));
		subscriptionRequest.setPlanActivationDate("24-03-2023 45:00:23");
		subscriptionRequest.setPlanActivationStatus("Active");
		subscriptionRequest.setPlanDeactivationDate("24-03-2023 45:00:23");
		subscriptionRequest.setPercentageDiscount(BigDecimal.valueOf(3));
		subscriptionRequest.setPlanActiveTill("26-03-2023 45:00:23");
		subscriptionRequest.setApplicationId("insignia");
		subscriptionRequest.setTenantId("LU008");

		subscriptionResponse.setSequenceNumber(5L);
		subscriptionResponse.setPlanId("1");
		subscriptionResponse.setPlanName("Health");
		subscriptionResponse.setPlanDescription("Its Kids");
		subscriptionResponse.setPlanDuration(3);
		subscriptionResponse.setPlanPricing(BigDecimal.valueOf(400.25));
		subscriptionResponse.setPlanActivationStatus("Active");
		subscriptionResponse.setPercentageDiscount(BigDecimal.valueOf(3));

		subscriptionResponseList.add(subscriptionResponse);

		subscriptionDetails.setSequenceNumber(5L);
		subscriptionDetails.setPlanId("1");
		subscriptionDetails.setPlanName("Health");
		subscriptionDetails.setPlanDescription("Its Kids");
		subscriptionDetails.setPlanDuration(3);
		subscriptionDetails.setPlanPricing(BigDecimal.valueOf(400.25));
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		Date activationDate = dateFormat.parse("25-03-2023 45:00:23");
		subscriptionDetails.setPlanActivationDate(activationDate);

		subscriptionDetails.setPlanActivationStatus("Active");
		subscriptionDetails.setPlanDeactivationDate(activationDate);
		subscriptionDetails.setPercentageDiscount(BigDecimal.valueOf(3));

	}

	@Test
	public void testSaveSubscriptionValidator() throws InvalidInputParametersException, ParseException {
		dataInitilization();
		subscriptionRequest.setPlanId(null);
		subscriptionRequest.setPlanName(null);
		subscriptionRequest.setSequenceNumber(null);

		ResponseEntity<?> createSubscriptionPlan = subscriptionController.saveSubscriptionPlan(subscriptionRequest);
		assertEquals(HttpStatus.BAD_REQUEST, createSubscriptionPlan.getStatusCode());

		ResponseEntity<?> updateSubscriptionPlan = subscriptionController.modifySubscriptionPlan(subscriptionRequest);
		assertEquals(HttpStatus.BAD_REQUEST, updateSubscriptionPlan.getStatusCode());

		ResponseEntity<?> activationSubscriptionDetails = subscriptionController
				.activateSubscriptionPlan(subscriptionRequest);
		assertEquals(HttpStatus.BAD_REQUEST, activationSubscriptionDetails.getStatusCode());

		ResponseEntity<?> deactivationSubscriptionDetails = subscriptionController
				.deactivateSubscriptionPlan(subscriptionRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deactivationSubscriptionDetails.getStatusCode());

	}

	@Test
	public void testSaveSubscriptionDetails()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		when(serviceRepo.saveSubscriptionPlan(subscriptionRequest)).thenReturn(subscriptionResponse);
		ResponseEntity<?> response = subscriptionController.saveSubscriptionPlan(subscriptionRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateSubscriptionDetails()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		when(serviceRepo.updateSubscriptionPlan(subscriptionRequest)).thenReturn(subscriptionResponse);
		ResponseEntity<?> response = subscriptionController.modifySubscriptionPlan(subscriptionRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetSubscriptionDetails()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		when(serviceRepo.getSubscriptionDetails(subscriptionRequest)).thenReturn(optionalSubscriptionDetails);
		ResponseEntity<?> response = subscriptionController.getSubscriptionDetails(subscriptionRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testActivationSubscriptionDetails()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		when(serviceRepo.activateSubscriptionPlan(subscriptionRequest)).thenReturn(subscriptionResponse);
		ResponseEntity<?> response = subscriptionController.activateSubscriptionPlan(subscriptionRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeActivationSubscriptionDetails()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		when(serviceRepo.deactivateSubscriptionPlan(subscriptionRequest)).thenReturn(subscriptionResponse);
		ResponseEntity<?> response = subscriptionController.deactivateSubscriptionPlan(subscriptionRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteSubscriptionDetails() throws InvalidInputParametersException, Exception {

		subscriptionRequest.setApplicationId("insginia");
		subscriptionRequest.setTenantId("LU008");
		doNothing().when(serviceRepo).deleteSubscriptionPlan(subscriptionRequest);

		subscriptionController.deleteSubscriptionPlanByPlanName(subscriptionRequest);
		verify(serviceRepo, times(1)).deleteSubscriptionPlan(subscriptionRequest);

	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException, ParseException {
		dataInitilization();

		when(serviceRepo.saveSubscriptionPlan(subscriptionRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> createSubscriptionPlan = subscriptionController.saveSubscriptionPlan(subscriptionRequest);

		assertEquals(HttpStatus.BAD_REQUEST, createSubscriptionPlan.getStatusCode());

		when(serviceRepo.updateSubscriptionPlan(subscriptionRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> modifySubscriptionPlan = subscriptionController.modifySubscriptionPlan(subscriptionRequest);

		assertEquals(HttpStatus.BAD_REQUEST, modifySubscriptionPlan.getStatusCode());

		when(serviceRepo.getSubscriptionDetails(subscriptionRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getSubscriptionDetails = subscriptionController.getSubscriptionDetails(subscriptionRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getSubscriptionDetails.getStatusCode());

		when(serviceRepo.activateSubscriptionPlan(subscriptionRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> activateSubscriptionPlan = subscriptionController
				.activateSubscriptionPlan(subscriptionRequest);

		assertEquals(HttpStatus.BAD_REQUEST, activateSubscriptionPlan.getStatusCode());

		when(serviceRepo.deactivateSubscriptionPlan(subscriptionRequest)).thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> deactivateSubscriptionPlan = subscriptionController
				.deactivateSubscriptionPlan(subscriptionRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deactivateSubscriptionPlan.getStatusCode());

		doThrow(new TokenExpiredException("")).when(serviceRepo).deleteSubscriptionPlan(subscriptionRequest);
		ResponseEntity<?> deleteSubscriptionPlan = subscriptionController
				.deleteSubscriptionPlanByPlanName(subscriptionRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deleteSubscriptionPlan.getStatusCode());

	}

	@Test
	public void testForExpired() throws TokenExpiredException, InvalidInputParametersException, ParseException {
		dataInitilization();

		when(serviceRepo.saveSubscriptionPlan(subscriptionRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> createSubscriptionPlan = subscriptionController.saveSubscriptionPlan(subscriptionRequest);

		assertEquals(HttpStatus.BAD_REQUEST, createSubscriptionPlan.getStatusCode());

		when(serviceRepo.updateSubscriptionPlan(subscriptionRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> modifySubscriptionPlan = subscriptionController.modifySubscriptionPlan(subscriptionRequest);

		assertEquals(HttpStatus.BAD_REQUEST, modifySubscriptionPlan.getStatusCode());

		when(serviceRepo.getSubscriptionDetails(subscriptionRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> getSubscriptionDetails = subscriptionController.getSubscriptionDetails(subscriptionRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getSubscriptionDetails.getStatusCode());

		when(serviceRepo.deactivateSubscriptionPlan(subscriptionRequest)).thenThrow(new NullPointerException(""));
		ResponseEntity<?> deactivateSubscriptionPlan = subscriptionController
				.deactivateSubscriptionPlan(subscriptionRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deactivateSubscriptionPlan.getStatusCode());

		doThrow(new NullPointerException("")).when(serviceRepo).deleteSubscriptionPlan(subscriptionRequest);
		ResponseEntity<?> deleteSubscriptionPlan = subscriptionController
				.deleteSubscriptionPlanByPlanName(subscriptionRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deleteSubscriptionPlan.getStatusCode());

	}

	@Test
	public void testExceptionForActivationPlan()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {

		subscriptionRequest.setPlanId(null);

		ResponseEntity<?> activateSubscriptionPlan = subscriptionController
				.activateSubscriptionPlan(subscriptionRequest);

		assertEquals(HttpStatus.BAD_REQUEST, activateSubscriptionPlan.getStatusCode());

	}

	@Test
	public void testInvalidExceptionForDeleteSubscriptionPlan()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {
		SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
		subscriptionRequest.setCustomerSequenceNumber(100L);
		subscriptionRequest.setApplicationId("insignia");
		subscriptionRequest.setTenantId("LU008");

		doThrow(new InvalidInputParametersException("Cant be deleted")).when(serviceRepo)
				.deleteSubscriptionPlan(subscriptionRequest);
		subscriptionController.deleteSubscriptionPlanByPlanName(subscriptionRequest);

		verify(serviceRepo, times(1)).deleteSubscriptionPlan(subscriptionRequest);
	}

	@Test
	public void testForInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(serviceRepo)
				.getSubscriptionDetails(subscriptionRequest);

		ResponseEntity<?> getSubscriptionDetails = subscriptionController.getSubscriptionDetails(subscriptionRequest);

		verify(serviceRepo).getSubscriptionDetails(subscriptionRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getSubscriptionDetails.getStatusCode());
	}

	@Test
	public void testForActivatePlanForException() throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new NullPointerException("255")).when(serviceRepo).activateSubscriptionPlan(subscriptionRequest);

		ResponseEntity<?> activateSubscriptionPlan = subscriptionController
				.activateSubscriptionPlan(subscriptionRequest);

		verify(serviceRepo).activateSubscriptionPlan(subscriptionRequest);

		assertEquals(HttpStatus.BAD_REQUEST, activateSubscriptionPlan.getStatusCode());
	}

}
