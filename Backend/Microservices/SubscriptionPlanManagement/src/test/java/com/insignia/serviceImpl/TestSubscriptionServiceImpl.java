package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.SubscriptionDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.SubscriptionDetails;
import com.insignia.entity.PlanStatus;
import com.insignia.model.SubscriptionRequest;
import com.insignia.model.SubscriptionResponse;

@ExtendWith(MockitoExtension.class)
public class TestSubscriptionServiceImpl {

	@InjectMocks
	private SubscriptionServiceImpl serviceImpl;

	@Mock
	private SubscriptionDaoInterface daoRepo;

	@Mock
	private TokenDaoInterface tokenRepo;

	SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
	SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
	List<SubscriptionResponse> subscriptionResponseList = new ArrayList<>();
	SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
	Optional<List<SubscriptionResponse>> optionalSubscriptionDetails = Optional.ofNullable(subscriptionResponseList);
	List<SubscriptionDetails> subscriptionDetailsList = new ArrayList<>();

	public void dataInitilization() throws ParseException {
		subscriptionRequest.setCustomerSequenceNumber(5L);
		subscriptionRequest.setExpirationDuration(15);

		subscriptionRequest.setSequenceNumber(1L);
		subscriptionRequest.setPlanId("1");
		subscriptionRequest.setPlanName("Health");
		subscriptionRequest.setPlanDescription("Its Kids");
		subscriptionRequest.setPlanDuration(3);
		subscriptionRequest.setPlanPricing(BigDecimal.valueOf(400.25));
		subscriptionRequest.setPlanActivationDate("25-03-2023 45:00:23");
		subscriptionRequest.setPlanActivationStatus("Hi");
		subscriptionRequest.setPlanDeactivationDate("25-03-2023 45:00:23");
		subscriptionRequest.setPercentageDiscount(BigDecimal.valueOf(3));
		subscriptionRequest.setAutoRenewal(true);
		subscriptionRequest.setMinOrderValue(BigDecimal.valueOf(100.25));
		subscriptionRequest.setApplicationId("insignia");
		subscriptionRequest.setTenantId("LU008");

		subscriptionResponse.setSequenceNumber(5L);
		subscriptionResponse.setPlanId("1");
		subscriptionResponse.setPlanName("Health");
		subscriptionResponse.setPlanDescription("Its Kids");
		subscriptionResponse.setPlanDuration(3);
		subscriptionResponse.setPlanPricing(BigDecimal.valueOf(400.25));
		subscriptionResponse.setPlanActivationStatus("Hi");
		subscriptionResponse.setPercentageDiscount(BigDecimal.valueOf(3));

		subscriptionResponseList.add(subscriptionResponse);

		subscriptionDetails.setSequenceNumber(1L);
		subscriptionDetails.setPlanId("1");
		subscriptionDetails.setPlanName("Health");
		subscriptionDetails.setPlanDescription("Its Kids");
		subscriptionDetails.setPlanDuration(3);
		subscriptionDetails.setPlanPricing(BigDecimal.valueOf(400.25));
		subscriptionDetails.setAutoRenewal(true);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		Date activationDate = dateFormat.parse("25-03-2023 45:00:23");
		subscriptionDetails.setPlanActivationDate(activationDate);

		subscriptionDetails.setPlanActivationStatus(PlanStatus.NEW.name());
		subscriptionDetails.setPlanDeactivationDate(activationDate);
		subscriptionDetails.setPercentageDiscount(BigDecimal.valueOf(3.25));
		subscriptionDetails.setApplicationId("insignia");
		subscriptionDetails.setTenantId("LU008");
		subscriptionDetailsList.add(subscriptionDetails);
	}

	@Test
	public void testSaveSubscriptionDetails()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {

		subscriptionRequest.setPlanId("1");
		subscriptionRequest.setPlanName("Health");
		subscriptionRequest.setPlanDescription("Its Kids");
		subscriptionRequest.setMinOrderValue(BigDecimal.valueOf(100.25));
		subscriptionRequest.setApplicationId("insignia");
		subscriptionRequest.setTenantId("LU008");

		subscriptionResponse.setSequenceNumber(5L);
		subscriptionResponse.setPlanId("1");
		subscriptionResponse.setPlanName("Health");
		subscriptionResponse.setPlanDescription("Its Kids");

		subscriptionDetails.setPlanId("1");
		subscriptionDetails.setPlanName("Health");
		subscriptionDetails.setPlanDescription("Its Kids");
		subscriptionDetails.setAutoRenewal(true);
		subscriptionDetails.setMinOrderValue(BigDecimal.valueOf(100.25));
		subscriptionDetails.setApplicationId("insignia");
		subscriptionDetails.setTenantId("LU008");

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(daoRepo.findByPlanIdAndPlanName(any(), any(), any(), any())).thenReturn(Optional.empty());
		when(daoRepo.saveSubscriptionPlan(any())).thenReturn(subscriptionDetails);

		SubscriptionResponse response = serviceImpl.saveSubscriptionPlan(subscriptionRequest);

		assertNotNull(response);
	}

	@Test
	public void testDuplicateDataWithNEWSubscriptionDetailsException()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {

		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setPlanActivationStatus(PlanStatus.NEW.name());

		when(daoRepo.findByPlanIdAndPlanName(any(), any(), any(), any())).thenReturn(Optional.of(subscriptionDetails));

		InvalidInputParametersException exception = assertThrows(InvalidInputParametersException.class, () -> {
			serviceImpl.saveSubscriptionPlan(subscriptionRequest);
		});
		assertNotNull(exception);

	}

	@Test
	public void testDuplicateDataWithActiveSubscriptionDetailsException()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {

		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setPlanActivationStatus(PlanStatus.ACTIVE.name());

		when(daoRepo.findByPlanIdAndPlanName(any(), any(), any(), any())).thenReturn(Optional.of(subscriptionDetails));

		InvalidInputParametersException exception = assertThrows(InvalidInputParametersException.class, () -> {
			serviceImpl.saveSubscriptionPlan(subscriptionRequest);
		});
		assertNotNull(exception);

	}

	@Test
	public void testDuplicateDataWithDeActiveSubscriptionDetailsException()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {

		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setPlanActivationStatus(PlanStatus.DEACTIVE.name());

		when(daoRepo.findByPlanIdAndPlanName(any(), any(), any(), any())).thenReturn(Optional.of(subscriptionDetails));

		InvalidInputParametersException exception = assertThrows(InvalidInputParametersException.class, () -> {
			serviceImpl.saveSubscriptionPlan(subscriptionRequest);
		});
		assertNotNull(exception);

	}

	@Test
	public void testUpdateSubscriptionDetails()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {

		Long sequenceNumber = 1L;
		String applicationId = "insignia";
		String tenantId = "LU008";
		subscriptionRequest.setCustomerSequenceNumber(5L);
		subscriptionRequest.setSequenceNumber(1L);
		subscriptionRequest.setPlanId("1");
		subscriptionRequest.setPlanName("Medicare");
		subscriptionRequest.setPlanPricing(BigDecimal.valueOf(400.25));
		subscriptionRequest.setPlanDescription("Its is very flexible");
		subscriptionRequest.setPlanDuration(4);
		subscriptionRequest.setPercentageDiscount(BigDecimal.valueOf(5.25));
		subscriptionRequest.setAutoRenewal(true);
		subscriptionRequest.setMinOrderValue(BigDecimal.valueOf(100.25));
		subscriptionRequest.setApplicationId(applicationId);
		subscriptionRequest.setTenantId(tenantId);

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());
		when(daoRepo.findBySequenceNumber(sequenceNumber, applicationId, tenantId)).thenReturn(Optional.of(subscriptionDetails));
		when(daoRepo.updateSubscriptionPlan(any())).thenReturn(subscriptionDetails);

		SubscriptionResponse response = serviceImpl.updateSubscriptionPlan(subscriptionRequest);

		assertNotNull(response);
	}

	@Test
	public void testUpdateSubscriptionDetailsNotFound()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {
		SubscriptionRequest invalidRequest = new SubscriptionRequest();
		invalidRequest.setCustomerSequenceNumber(1000L);

		when(daoRepo.findBySequenceNumber(any(), any(), any())).thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class, () -> {

			serviceImpl.updateSubscriptionPlan(invalidRequest);
		});
		verify(daoRepo, times(1)).findBySequenceNumber(any(), any(), any());

	}

	@Test
	public void testUpdateActiveStateException()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {
		dataInitilization();
		Long sequenceNumber = 1L;
		String applicationId = "insignia";
		String tenantId = "LU008";
		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setPlanActivationStatus(PlanStatus.ACTIVE.name());

		when(daoRepo.findBySequenceNumber(sequenceNumber, applicationId, tenantId)).thenReturn(Optional.of(subscriptionDetails));

		InvalidInputParametersException exception = assertThrows(InvalidInputParametersException.class, () -> {
			serviceImpl.updateSubscriptionPlan(subscriptionRequest);
		});
		assertNotNull(exception);

	}

	@Test
	public void testUpdateDeActiveStateException()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {
		dataInitilization();
		Long sequenceNumber = 1L;
		String applicationId = "insignia";
		String tenantId = "LU008";
		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setPlanActivationStatus(PlanStatus.DEACTIVE.name());

		when(daoRepo.findBySequenceNumber(sequenceNumber, applicationId, tenantId)).thenReturn(Optional.of(subscriptionDetails));

		InvalidInputParametersException exception = assertThrows(InvalidInputParametersException.class, () -> {
			serviceImpl.updateSubscriptionPlan(subscriptionRequest);
		});
		assertNotNull(exception);

	}

	@Test
	public void testActiveSubscriptionPlan()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {

		subscriptionResponse = new SubscriptionResponse();

		Optional<SubscriptionDetails> optional = Optional.of(subscriptionDetails);

		subscriptionRequest.setCustomerSequenceNumber(5L);

		subscriptionRequest.setPlanActivationDate("25-03-2023 06:00:23");
		subscriptionRequest.setPlanActivationStatus(PlanStatus.ACTIVE.name());
		subscriptionRequest.setPlanActiveTill("25-03-2023 08:00:23");
		subscriptionRequest.setPlanId("1");
		subscriptionRequest.setPlanName("Health");
		subscriptionRequest.setPlanDeactivationDate(null);
		subscriptionRequest.setAutoRenewal(true);
		subscriptionRequest.setApplicationId("insignia");
		subscriptionRequest.setTenantId("LU008");

		subscriptionResponse.setSequenceNumber(1L);
		subscriptionResponse.setPlanId("1");
		subscriptionResponse.setPlanName("Health");
		subscriptionResponse.setPlanDescription("Its Kids");

		subscriptionDetails.setSequenceNumber(1L);
		subscriptionDetails.setPlanId("1");
		subscriptionDetails.setPlanName("Health");
		subscriptionDetails.setPlanDuration(3);
		subscriptionDetails.setAutoRenewal(true);
		subscriptionDetails.setApplicationId("insignia");
		subscriptionDetails.setTenantId("LU008");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		Date activationDate = dateFormat.parse("25-03-2023 00:00:23");
		subscriptionDetails.setPlanActivationDate(activationDate);

		subscriptionDetails.setPlanActivationStatus(PlanStatus.NEW.name());
		subscriptionDetails.setPlanActiveTill(activationDate);

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());
		when(daoRepo.findByPlanIdAndPlanName(any(), any(), any(), any())).thenReturn(optional);
		when(daoRepo.activateSubscriptionPlan(subscriptionDetails)).thenReturn(subscriptionDetails);
		SubscriptionResponse activateSubscriptionPlan = serviceImpl.activateSubscriptionPlan(subscriptionRequest);

		assertNotNull(activateSubscriptionPlan);
	}

	@Test
	public void testActiveAlreadyExistSubscriptionPlanException()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {

		Optional<SubscriptionDetails> optional = Optional.of(subscriptionDetails);
		subscriptionDetails.setPlanActivationStatus(PlanStatus.ACTIVE.name());

		when(daoRepo.findByPlanIdAndPlanName(any(), any(), any(), any())).thenReturn(optional);

		InvalidInputParametersException exception = assertThrows(InvalidInputParametersException.class, () -> {
			serviceImpl.activateSubscriptionPlan(subscriptionRequest);
		});
		assertNotNull(exception);
	}

	@Test
	public void testActiveSubscriptionDetailsNotFoundException()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {
		SubscriptionRequest invalidRequest = new SubscriptionRequest();
		invalidRequest.setCustomerSequenceNumber(1000L);

		when(daoRepo.findByPlanIdAndPlanName(any(), any(), any(), any())).thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class, () -> {

			serviceImpl.activateSubscriptionPlan(invalidRequest);
		});
		verify(daoRepo, times(1)).findByPlanIdAndPlanName(any(), any(), any(), any());

	}

	@Test
	public void testActivateSubscriptionPlanWithInvalidActiveTillException() {
		SubscriptionRequest subscriptionRequest = new SubscriptionRequest();

		subscriptionRequest.setPlanActivationDate("2022-01-01 12:00:00");
		subscriptionRequest.setPlanActiveTill("2022-01-01 10:00:00");

		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setPlanId("1");
		subscriptionDetails.setPlanName("Health");

		when(daoRepo.findByPlanIdAndPlanName(any(), any(), any(), any())).thenReturn(Optional.of(subscriptionDetails));

		assertThrows(InvalidInputParametersException.class, () -> {

			serviceImpl.activateSubscriptionPlan(subscriptionRequest);
		});
		verify(daoRepo, times(1)).findByPlanIdAndPlanName(any(), any(), any(), any());

	}

	@Test
	public void testDeActiveSubscriptionPlan()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {

		subscriptionResponse = new SubscriptionResponse();

		Optional<SubscriptionDetails> optional = Optional.of(subscriptionDetails);

		subscriptionRequest.setCustomerSequenceNumber(5L);
		subscriptionRequest.setPlanActivationStatus(PlanStatus.DEACTIVE.name());
		subscriptionRequest.setPlanDeactivationDate("28-03-2023 08:00:23");
		subscriptionRequest.setPlanId("1");
		subscriptionRequest.setPlanName("Health");
		subscriptionRequest.setApplicationId("insignia");
		subscriptionRequest.setTenantId("LU008");

		subscriptionResponse.setSequenceNumber(1L);
		subscriptionResponse.setPlanId("1");
		subscriptionResponse.setPlanName("Health");
		subscriptionResponse.setPlanDescription("Its Kids");

		subscriptionDetails.setSequenceNumber(1L);
		subscriptionDetails.setPlanId("1");
		subscriptionDetails.setPlanName("Health");
		subscriptionDetails.setPlanDuration(3);
		subscriptionDetails.setApplicationId("insignia");
		subscriptionDetails.setTenantId("LU008");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		Date activationDate = dateFormat.parse("25-03-2023 00:00:23");
		subscriptionDetails.setPlanActivationDate(activationDate);
		Date activeTill = dateFormat.parse("26-03-2023 00:00:23");
		subscriptionDetails.setPlanActivationStatus(PlanStatus.ACTIVE.name());
		subscriptionDetails.setPlanActiveTill(activeTill);
		subscriptionDetails.setAutoRenewal(true);

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());
		when(daoRepo.findByPlanIdAndPlanName(any(), any(), any(), any())).thenReturn(optional);
		when(daoRepo.deactivateSubscriptionPlan(subscriptionDetails)).thenReturn(subscriptionDetails);
		SubscriptionResponse deactivateSubscriptionPlan = serviceImpl.deactivateSubscriptionPlan(subscriptionRequest);

		assertNotNull(deactivateSubscriptionPlan);
	}

	@Test
	public void testDeActiveSubscriptionDetailsNotFoundException()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {
		SubscriptionRequest invalidRequest = new SubscriptionRequest();
		invalidRequest.setCustomerSequenceNumber(1000L);

		when(daoRepo.findByPlanIdAndPlanName(any(), any(), any(), any())).thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class, () -> {

			serviceImpl.deactivateSubscriptionPlan(invalidRequest);
		});
		verify(daoRepo, times(1)).findByPlanIdAndPlanName(any(), any(), any(), any());

	}

	@Test
	public void testDeActiveAlreadyExistSubscriptionPlanException()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {

		subscriptionDetails.setPlanId("1");
		subscriptionDetails.setPlanName("Health");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		Date activationDate = dateFormat.parse("25-03-2023 00:00:23");
		subscriptionDetails.setPlanActivationDate(activationDate);
		Date activeTill = dateFormat.parse("26-03-2023 00:00:23");
		subscriptionDetails.setPlanActiveTill(activeTill);

		Optional<SubscriptionDetails> optional = Optional.of(subscriptionDetails);
		subscriptionDetails.setPlanActivationStatus(PlanStatus.DEACTIVE.name());

		when(daoRepo.findByPlanIdAndPlanName(any(), any(), any(), any())).thenReturn(optional);

		InvalidInputParametersException exception = assertThrows(InvalidInputParametersException.class, () -> {
			serviceImpl.deactivateSubscriptionPlan(subscriptionRequest);
		});
		assertNotNull(exception);
	}

	@Test
	public void testDeActiveSubscriptionPlanCantbeDeactivatedException()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {

		subscriptionDetails.setPlanId("1");
		subscriptionDetails.setPlanName("Health");

		subscriptionDetails.setPlanActivationDate(null);

		Optional<SubscriptionDetails> optional = Optional.of(subscriptionDetails);
		subscriptionDetails.setPlanActivationStatus(PlanStatus.NEW.name());

		when(daoRepo.findByPlanIdAndPlanName(any(), any(), any(), any())).thenReturn(optional);

		InvalidInputParametersException exception = assertThrows(InvalidInputParametersException.class, () -> {
			serviceImpl.deactivateSubscriptionPlan(subscriptionRequest);
		});
		assertNotNull(exception);
	}

	@Test
	public void testDeactivationDateMustBeAfterTheActivationDateException() throws ParseException {
		SubscriptionRequest subscriptionRequest = new SubscriptionRequest();

		subscriptionRequest.setCustomerSequenceNumber(5L);
		subscriptionRequest.setPlanDeactivationDate("24-03-2023 08:00:23");
		subscriptionRequest.setPlanId("1");
		subscriptionRequest.setPlanName("Health");

		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setPlanId("1");
		subscriptionDetails.setPlanName("Health");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		Date activationDate = dateFormat.parse("25-03-2023 00:00:23");
		subscriptionDetails.setPlanActivationDate(activationDate);
		Date activeTill = dateFormat.parse("26-03-2023 00:00:23");
		subscriptionDetails.setPlanActivationStatus(PlanStatus.ACTIVE.name());
		subscriptionDetails.setPlanActiveTill(activeTill);

		when(daoRepo.findByPlanIdAndPlanName(any(), any(), any(), any())).thenReturn(Optional.of(subscriptionDetails));

		assertThrows(InvalidInputParametersException.class, () -> {

			serviceImpl.deactivateSubscriptionPlan(subscriptionRequest);
		});
		verify(daoRepo, times(1)).findByPlanIdAndPlanName(any(), any(), any(), any());

	}

	@Test
	public void testDeactivationDateMustBeAfterTheActiveTillDateException() throws ParseException {
		SubscriptionRequest subscriptionRequest = new SubscriptionRequest();

		subscriptionRequest.setCustomerSequenceNumber(5L);
		subscriptionRequest.setPlanDeactivationDate("25-03-2023 08:00:23");
		subscriptionRequest.setPlanId("1");
		subscriptionRequest.setPlanName("Health");

		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setPlanId("1");
		subscriptionDetails.setPlanName("Health");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		Date activationDate = dateFormat.parse("24-03-2023 00:00:23");
		subscriptionDetails.setPlanActivationDate(activationDate);
		Date activeTill = dateFormat.parse("26-03-2023 00:00:23");
		subscriptionDetails.setPlanActivationStatus(PlanStatus.ACTIVE.name());
		subscriptionDetails.setPlanActiveTill(activeTill);

		when(daoRepo.findByPlanIdAndPlanName(any(), any(), any(), any())).thenReturn(Optional.of(subscriptionDetails));

		assertThrows(InvalidInputParametersException.class, () -> {

			serviceImpl.deactivateSubscriptionPlan(subscriptionRequest);
		});
		verify(daoRepo, times(1)).findByPlanIdAndPlanName(any(), any(), any(), any());

	}

	@Test
	public void testGetSubscriptionDetails()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());
		when(daoRepo.findBySequenceNumber(subscriptionRequest.getSequenceNumber(), subscriptionRequest.getApplicationId(), subscriptionRequest.getTenantId()))
				.thenReturn(Optional.of(subscriptionDetails));

		Optional<List<SubscriptionResponse>> subscriptionDetailsList = serviceImpl
				.getSubscriptionDetails(subscriptionRequest);

		assertNotNull(subscriptionDetailsList);
	}

	@Test
	public void testGetSubscriptionDetailsByPlanIdAndPlanName()
			throws TokenExpiredException, InvalidInputParametersException, ParseException {
		dataInitilization();
		String applicationId = "insignia";
		String tenantId = "LU008";
		SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
		subscriptionRequest.setPlanId("1");
		subscriptionRequest.setPlanName("Health");
		subscriptionRequest.setApplicationId("insignia");
		subscriptionRequest.setTenantId("LU008");

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(daoRepo.findAllSubscriptionDetails(applicationId, tenantId)).thenReturn(subscriptionDetailsList);
		Optional<List<SubscriptionResponse>> subscriptionDetailsList = serviceImpl
				.getSubscriptionDetails(subscriptionRequest);

		assertNotNull(subscriptionDetailsList);
	}

	@Test
	public void testDeleteSubscriptionDetailsWithPlanIdAndPlanName()
			throws ParseException, TokenExpiredException, InvalidInputParametersException {
		SubscriptionRequest subscriptionRequest = new SubscriptionRequest();

		subscriptionRequest.setCustomerSequenceNumber(5L);
		subscriptionRequest.setPlanId("1");
		subscriptionRequest.setPlanName("Health");

		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setPlanId("1");
		subscriptionDetails.setPlanName("Health");

		subscriptionDetails.setPlanActivationStatus(PlanStatus.DEACTIVE.name());

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());
		when(daoRepo.findByPlanIdAndPlanName(any(), any(), any(), any())).thenReturn(Optional.of(subscriptionDetails));

		doNothing().when(daoRepo).deleteSubscriptionPlanByName(any(), any(), any(), any());
		serviceImpl.deleteSubscriptionPlan(subscriptionRequest);
		verify(daoRepo, times(1)).findByPlanIdAndPlanName(any(), any(), any(), any());

	}

	@Test
	public void testDeleteSubscriptionDetails()
			throws ParseException, TokenExpiredException, InvalidInputParametersException {
		SubscriptionRequest subscriptionRequest = new SubscriptionRequest();

		subscriptionRequest.setSequenceNumber(1L);
		subscriptionRequest.setCustomerSequenceNumber(5L);

		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setPlanId("1");
		subscriptionDetails.setPlanName("Health");

		subscriptionDetails.setPlanActivationStatus(PlanStatus.DEACTIVE.name());

		when(daoRepo.findBySequenceNumber(any(), any(), any())).thenReturn(Optional.of(subscriptionDetails));
		doNothing().when(daoRepo).deleteSubscriptionPlan(any(), any(), any());

		serviceImpl.deleteSubscriptionPlan(subscriptionRequest);
		verify(daoRepo, times(1)).findBySequenceNumber(any(), any(), any());

	}

	@Test
	public void testDeleteSubscriptionDetailsPlanIsActiveCannotbeDeletedBySequenceNumberException()
			throws ParseException, TokenExpiredException, InvalidInputParametersException {
		SubscriptionRequest subscriptionRequest = new SubscriptionRequest();

		subscriptionRequest.setSequenceNumber(1L);
		subscriptionRequest.setCustomerSequenceNumber(5L);
		subscriptionRequest.setPlanActivationStatus(PlanStatus.ACTIVE.name());

		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setPlanId("1");
		subscriptionDetails.setPlanName("Health");

		subscriptionDetails.setPlanActivationStatus(PlanStatus.ACTIVE.name());

		when(daoRepo.findBySequenceNumber(any(), any(), any())).thenReturn(Optional.of(subscriptionDetails));

		assertThrows(InvalidInputParametersException.class, () -> {

			serviceImpl.deleteSubscriptionPlan(subscriptionRequest);
		});
		verify(daoRepo, times(1)).findBySequenceNumber(any(), any(), any());

	}

	@Test
	public void testDeleteSubscriptionDetailsNotFoundOnTheSystemException()
			throws ParseException, TokenExpiredException, InvalidInputParametersException {
		SubscriptionRequest subscriptionRequest = new SubscriptionRequest();

		subscriptionRequest.setSequenceNumber(1L);
		subscriptionRequest.setCustomerSequenceNumber(5L);
		subscriptionRequest.setPlanActivationStatus(PlanStatus.ACTIVE.name());
		subscriptionRequest.setPlanId("1");
		subscriptionRequest.setPlanName("Health");

		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setPlanId("1");
		subscriptionDetails.setPlanName("Health");

		subscriptionDetails.setPlanActivationStatus(PlanStatus.ACTIVE.name());

		assertThrows(InvalidInputParametersException.class, () -> {

			serviceImpl.deleteSubscriptionPlan(subscriptionRequest);
		});
	}

	@Test
	public void testDeleteSubscriptionDetailsCannotBeDeletedWithPlanIdAndPlanNameItsIsInActiveState()
			throws ParseException, TokenExpiredException, InvalidInputParametersException {
		SubscriptionRequest subscriptionRequest = new SubscriptionRequest();

		subscriptionRequest.setCustomerSequenceNumber(5L);
		subscriptionRequest.setPlanId("1");
		subscriptionRequest.setPlanName("Health");
		subscriptionRequest.setPlanActivationStatus(PlanStatus.ACTIVE.name());

		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setPlanId("1");
		subscriptionDetails.setPlanName("Health");

		subscriptionDetails.setPlanActivationStatus(PlanStatus.ACTIVE.name());

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());
		when(daoRepo.findByPlanIdAndPlanName(any(), any(), any(), any())).thenReturn(Optional.of(subscriptionDetails));

		assertThrows(InvalidInputParametersException.class, () -> {

			serviceImpl.deleteSubscriptionPlan(subscriptionRequest);
		});
		verify(daoRepo, times(1)).findByPlanIdAndPlanName(any(), any(), any(), any());

	}

	@Test
	public void testDeleteSubscriptionDetailsNotFoundOnTheSystem()
			throws ParseException, TokenExpiredException, InvalidInputParametersException {
		SubscriptionRequest subscriptionRequest = new SubscriptionRequest();

		subscriptionRequest.setCustomerSequenceNumber(5L);

		subscriptionRequest.setPlanActivationStatus(PlanStatus.ACTIVE.name());

		assertThrows(InvalidInputParametersException.class, () -> {

			serviceImpl.deleteSubscriptionPlan(subscriptionRequest);
		});

	}

	@Test
	public void testDeleteSubscriptionDetailsNotFoundOnTheSystemWithPlanIdAndPlanName()
			throws ParseException, TokenExpiredException, InvalidInputParametersException {
		SubscriptionRequest subscriptionRequest = new SubscriptionRequest();

		subscriptionRequest.setCustomerSequenceNumber(5L);
		subscriptionRequest.setPlanId("1");
		subscriptionRequest.setPlanName("Medicare");

		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setPlanId("1");
		subscriptionDetails.setPlanName("Health");

		assertThrows(InvalidInputParametersException.class, () -> {

			serviceImpl.deleteSubscriptionPlan(subscriptionRequest);
		});

	}

}
