package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.insignia.daoInterface.ProductAndSubscriptonLinkDaoInterface;
import com.insignia.daoInterface.SubscriptionDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductSubscriptionPlanLink;
import com.insignia.entity.SubscriptionDetails;
import com.insignia.entity.PlanStatus;
import com.insignia.model.ProductSubscriptionLinkRequest;
import com.insignia.model.SubscriptionResponse;
import com.insignia.model.SubscriptonLinkRequest;
import com.insignia.model.SubscriptonLinkResponse;

@ExtendWith(MockitoExtension.class)
public class TestProductSubscriptionLinkPlanServiceImpl {

	@InjectMocks
	private ProductAndSubscriptonLinkServiceImpl productServiceImpl;

	@Mock
	private ProductAndSubscriptonLinkDaoInterface productDaoRepo;

	@Mock
	private SubscriptionDaoInterface subscriptionDaoInterface;

	@Mock
	private TokenDaoInterface tokenRepo;

	SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
	SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
	SubscriptonLinkRequest subscriptonLinkRequest = new SubscriptonLinkRequest();
	SubscriptonLinkResponse subscriptonLinkResponse = new SubscriptonLinkResponse();
	ProductSubscriptionPlanLink productSubscriptionPlanLink = new ProductSubscriptionPlanLink();
	ProductSubscriptionPlanLink productSubscriptionPlanLinkMap = new ProductSubscriptionPlanLink();
	ProductSubscriptionLinkRequest productSubscriptionLinkRequest = new ProductSubscriptionLinkRequest();

	List<SubscriptonLinkResponse> subscriptonLinkResponseList = new ArrayList<>();
	List<SubscriptionResponse> subscriptionResponseList = new ArrayList<>();
	Optional<List<SubscriptonLinkResponse>> optionalProductAndSubscriptionLinkResponse = Optional
			.ofNullable(subscriptonLinkResponseList);
	List<ProductSubscriptionPlanLink> productSubscriptionPlanLinkList = new ArrayList<>();

	public void dataInitilization() throws ParseException {
		subscriptonLinkRequest.setCustomerSequenceNumber(6L);
		subscriptonLinkRequest.setExpirationDuration(15);
		subscriptonLinkRequest.setSubscriptionPlanSequenceNumber(5L);
		productSubscriptionLinkRequest.setSubscriptionPlanSequenceNumber(1L);
		productSubscriptionLinkRequest.setProductSequenceNumber(6L);
		List<Long> subscriptionPlanSequenceNumber = Arrays.asList(1L, 2L, 3L);
		productSubscriptionLinkRequest.setSubscriptionSequenceNumberList(subscriptionPlanSequenceNumber);

		subscriptonLinkResponse.setSubscriptionPlanSequenceNumber(1L);

		subscriptionResponse.setSequenceNumber(5L);
		subscriptionResponse.setPlanId("1");
		subscriptionResponse.setPlanName("Health");
		subscriptionResponse.setPlanDescription("Its Kids");
		subscriptionResponse.setPlanDuration(3);
		subscriptionResponse.setPlanPricing(BigDecimal.valueOf(400.25));
		subscriptionResponse.setPlanActivationStatus("Active");
		subscriptionResponse.setPercentageDiscount(BigDecimal.valueOf(3));

		subscriptonLinkResponse.setSequenceNumber(5L);
		subscriptonLinkResponse.setSubscriptionResponse(subscriptionResponse);
		subscriptionResponseList.add(subscriptionResponse);
		subscriptionResponse.setAssociatedProductSequenceNumberList(subscriptionPlanSequenceNumber);

		subscriptonLinkResponseList.add(subscriptonLinkResponse);

		subscriptionDetails.setPlanActivationStatus(PlanStatus.ACTIVE.name());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date planActivationDate = dateFormat.parse("2023-11-29 12:00:00");
		subscriptionDetails.setPlanActivationDate(planActivationDate);

		Date planActiveTill = dateFormat.parse("3030-12-06 12:00:00");
		subscriptionDetails.setPlanActiveTill(planActiveTill);

		productSubscriptionPlanLink.setProductSequenceNumber(6L);
		productSubscriptionPlanLink.setSubscriptionDetails(subscriptionDetails);

		productSubscriptionPlanLinkMap.setProductSequenceNumber(7L);
		productSubscriptionPlanLinkMap.setSubscriptionDetails(subscriptionDetails);

		productSubscriptionPlanLinkList.add(productSubscriptionPlanLinkMap);
		productSubscriptionPlanLinkList.add(productSubscriptionPlanLink);

	}

	@Test
	public void testAssociateSubscriptionPlanWithCustomer()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();
		SubscriptonLinkRequest subscriptonLinkRequest = new SubscriptonLinkRequest();
		subscriptonLinkRequest.setCustomerSequenceNumber(10L);
		subscriptonLinkRequest.setSubscriptionPlanSequenceNumber(15L);

		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();

		subscriptionDetails.setPlanActivationStatus(PlanStatus.ACTIVE.name());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date planActivationDate = dateFormat.parse("2023-11-29 12:00:00");
		subscriptionDetails.setPlanActivationDate(planActivationDate);

		Date planActiveTill = dateFormat.parse("3030-12-06 12:00:00");
		subscriptionDetails.setPlanActiveTill(planActiveTill);

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productDaoRepo.findProductSubscritipionDetails(any(), any())).thenReturn(Optional.empty());
		when(subscriptionDaoInterface.findById(any())).thenReturn(Optional.of(subscriptionDetails));
		when(productDaoRepo.associateSubscriptionPlanWithProduct(productSubscriptionPlanLink))
				.thenReturn(productSubscriptionPlanLink);

		SubscriptonLinkResponse associateSubscriptionPlanWithProduct = productServiceImpl
				.associateSubscriptionPlanWithProduct(productSubscriptionLinkRequest);

		assertNotNull(associateSubscriptionPlanWithProduct);
	}

	@Test
	public void testAssociateSubscriptionPlan_PlanAlreadyExistException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		subscriptionDetails.setPlanActivationStatus(PlanStatus.DEACTIVE.name());

		when(productDaoRepo.findProductSubscritipionDetails(any(), any()))
				.thenReturn(Optional.of(productSubscriptionPlanLink));

		assertThrows(InvalidInputParametersException.class, () -> {

			productServiceImpl.associateSubscriptionPlanWithProduct(productSubscriptionLinkRequest);
		});
		verify(productDaoRepo, times(1)).findProductSubscritipionDetails(any(), any());

	}

	@Test
	public void testAssociateSubscriptionPlan_SubscriptionIdIsNotPresentException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		when(subscriptionDaoInterface.findById(subscriptonLinkRequest.getSubscriptionPlanSequenceNumber()))
				.thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class, () -> {

			productServiceImpl.associateSubscriptionPlanWithProduct(productSubscriptionLinkRequest);
		});
		verify(subscriptionDaoInterface, times(1)).findById(any());

	}

	@Test
	public void testAssociateSubscriptionPlan_IsNotInActiveStateException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		subscriptionDetails.setPlanActivationStatus(PlanStatus.DEACTIVE.name());

		when(subscriptionDaoInterface.findById(any())).thenReturn(Optional.of(subscriptionDetails));

		assertThrows(InvalidInputParametersException.class, () -> {

			productServiceImpl.associateSubscriptionPlanWithProduct(productSubscriptionLinkRequest);
		});
		verify(subscriptionDaoInterface, times(1)).findById(any());

	}
	@Test
	public void testRemoveSubscriptionPlanForCustomer()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		subscriptonLinkRequest.setToForceDelete(true);
		productSubscriptionLinkRequest.setToForceDelete(true);
		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productDaoRepo.findProductSubscritipionDetails(any(), any()))
				.thenReturn(Optional.of(productSubscriptionPlanLink));
		doNothing().when(productDaoRepo).removeSubscriptionPlanForProduct(any(), any());

		productServiceImpl.removeSubscriptionPlanForProduct(productSubscriptionLinkRequest);

	}
	@Test
	public void testRemoveSubscriptionPlan_NotAvailableException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		subscriptionDetails.setPlanActivationStatus(PlanStatus.DEACTIVE.name());
		when(productDaoRepo.findProductSubscritipionDetails(any(), any())).thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class, () -> {

			productServiceImpl.removeSubscriptionPlanForProduct(productSubscriptionLinkRequest);
		});
		verify(productDaoRepo, times(1)).findProductSubscritipionDetails(any(), any());
	}

	@Test
	public void testRemoveSubscriptionPlan_IsCurrentlyActiveException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		subscriptonLinkRequest.setToForceDelete(false);

		subscriptionDetails.setPlanActivationStatus(PlanStatus.ACTIVE.name());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date planActivationDate = dateFormat.parse("2023-11-29 12:00:00");
		subscriptionDetails.setPlanActivationDate(planActivationDate);

		Date planActiveTill = dateFormat.parse("3023-12-06 12:00:00");
		subscriptionDetails.setPlanActiveTill(planActiveTill);

		when(productDaoRepo.findProductSubscritipionDetails(any(), any()))
				.thenReturn(Optional.of(productSubscriptionPlanLink));

		assertThrows(InvalidInputParametersException.class, () -> {

			productServiceImpl.removeSubscriptionPlanForProduct(productSubscriptionLinkRequest);
		});
		verify(productDaoRepo, times(1)).findProductSubscritipionDetails(any(), any());
	}

	

	

	@Test
	public void testGetAllSubscriptionPlanForProductWithSingleRecord()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();
		productSubscriptionLinkRequest.setSubscriptionPlanSequenceNumber(5L);
		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(productDaoRepo.findProductSubscritipionDetails(any(), any()))
				.thenReturn(Optional.of(productSubscriptionPlanLink));

		Optional<List<SubscriptonLinkResponse>> allSubscriptionPlanForCustomer = productServiceImpl
				.getAllSubscriptionPlanForProduct(productSubscriptionLinkRequest);
		assertTrue(allSubscriptionPlanForCustomer.isPresent());

	}

	@Test
	public void testGetAllSubscriptionPlanForProduct_OnlyGivenSubscriptionPlanId() throws Exception {
		dataInitilization();
		productSubscriptionLinkRequest.setProductSequenceNumber(null);
		productSubscriptionPlanLink.setSequenceNumber(47L);
		subscriptonLinkRequest.setSubscriptionPlanSequenceNumber(47L);
		productSubscriptionLinkRequest.setSubscriptionPlanSequenceNumber(47L);

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());
		when(productDaoRepo.findAllProductForGivenSubscription(any())).thenReturn(productSubscriptionPlanLinkList);

		Optional<List<SubscriptonLinkResponse>> result = productServiceImpl
				.getAllSubscriptionPlanForProduct(productSubscriptionLinkRequest);

		assertTrue(result.isPresent());

	}

	@Test
	public void testGetAllSubscriptionPlanForProductWithMultipleInputs() throws Exception {
		dataInitilization();

		productSubscriptionLinkRequest.setProductSequenceNumber(null);

		subscriptionDetails.setSequenceNumber(1L);
		List<Long> subscriptionSequenceNumberList = Arrays.asList(1L, 4L, 5L);

		
		ProductSubscriptionLinkRequest productSubscriptionLinkRequest = new ProductSubscriptionLinkRequest();
		productSubscriptionLinkRequest.setSubscriptionSequenceNumberList(subscriptionSequenceNumberList);

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());
		when(productDaoRepo.findAllProductForGivenSubscription(any())).thenReturn(productSubscriptionPlanLinkList);

		Optional<List<SubscriptonLinkResponse>> result = productServiceImpl
				.getAllSubscriptionPlanForProduct(productSubscriptionLinkRequest);

		assertTrue(result.isPresent());

	}

}