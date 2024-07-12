package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.PaymentDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.CustomerPaymentMethodDetails;
import com.insignia.model.PaymentMethodRequest;
import com.insignia.model.PaymentMethodResponse;

@ExtendWith(MockitoExtension.class)
public class TestPaymentMethodServiceImpl {

	@InjectMocks
	private PaymentServiceImpl paymentServiceImpl;

	@Mock
	private PaymentDaoInterface paymentDaoRepo;

	@Mock
	private TokenDaoInterface tokenRepo;

	List<PaymentMethodResponse> paymentMethodResponseList = new ArrayList<>();
	List<CustomerPaymentMethodDetails> customerPaymentMethodDetailsList = new ArrayList<>();
	PaymentMethodRequest paymentMethodRequest = new PaymentMethodRequest();
	PaymentMethodResponse paymentMethodResponse = new PaymentMethodResponse();
	CustomerPaymentMethodDetails customerPaymentMethodDetails = new CustomerPaymentMethodDetails();

	public void dataInitilization() {
		paymentMethodRequest.setCustomerSequenceNumber(8L);
		// paymentMethodRequest.setPaymentMethodSequenceNumber(8L);
		paymentMethodRequest.setValidFrom("Now");
		paymentMethodRequest.setValidUpto("Upto");

		paymentMethodRequest.setPaymentMethodType("Card");
		paymentMethodRequest.setPaymentMethodDetails("Its easy");
		paymentMethodRequest.setIsDefaultPaymentMethod(true);
		paymentMethodRequest.setExpirationDuration(15);

		// paymentMethodResponse.setPaymentMethodSequenceNumber(8L);
		paymentMethodResponse.setPaymentMethodType("Card");
		paymentMethodResponse.setPaymentMethodDetails("Its easy");
		paymentMethodResponse.setIsDefaultPaymentMethod(true);

		paymentMethodResponse.setValidFrom("Now");
		paymentMethodResponse.setValidUpto("Upto");

		paymentMethodResponseList.add(paymentMethodResponse);

		customerPaymentMethodDetails.setCustomerSequenceNumber(8L);
		customerPaymentMethodDetails.setValidFrom("Now");
		customerPaymentMethodDetails.setValidUpto("Upto");
		customerPaymentMethodDetails.setPaymentMethodType("Card");
		customerPaymentMethodDetails.setPaymentMethodDetails("Its easy");
		customerPaymentMethodDetails.setIsDefaultPaymentMethod(true);
		customerPaymentMethodDetailsList.add(customerPaymentMethodDetails);

	}

	@Test
	public void testAddPaymentMethod() throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();

		when(paymentDaoRepo.addPaymentMethod(any())).thenReturn(customerPaymentMethodDetails);

		PaymentMethodResponse addPaymentMethod = paymentServiceImpl.addPaymentMethod(paymentMethodRequest);

		assertNotNull(addPaymentMethod);
	}

	@Test
	public void testRemovePaymentMethod() throws TokenExpiredException, InvalidInputParametersException {

		Long paymentMethodSequenceNumber = 5L;
		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;

		paymentServiceImpl.removePaymentMethod(paymentMethodSequenceNumber, customerSequenceNumber, expirationDuration);

		verify(paymentDaoRepo, times(1)).removePaymentMethod(paymentMethodSequenceNumber);

	}

	@Test
	public void testListOfPaymentMethodDetails() throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();

		Long customerSequenceNumber = 5L;
		Integer expirationDuration = 15;
		when(paymentDaoRepo.getListOfPaymentMethodDetails(customerSequenceNumber))
				.thenReturn(customerPaymentMethodDetailsList);

		List<PaymentMethodResponse> listOfPaymentMethodDetails = paymentServiceImpl
				.getListOfPaymentMethodDetails(customerSequenceNumber, expirationDuration);

		assertNotNull(listOfPaymentMethodDetails);
	}

}
