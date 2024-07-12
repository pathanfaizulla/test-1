
package com.insignia.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.PaymentDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.CustomerPaymentMethodDetails;
import com.insignia.model.PaymentMethodRequest;
import com.insignia.model.PaymentMethodResponse;
import com.insignia.serviceInterface.PaymentServiceInterface;

@Service
public class PaymentServiceImpl implements PaymentServiceInterface {

	@Autowired
	private PaymentDaoInterface paymentDaoInterface;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Override
	public PaymentMethodResponse addPaymentMethod(PaymentMethodRequest paymentMethodRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(paymentMethodRequest.getCustomerSequenceNumber(),
				paymentMethodRequest.getExpirationDuration());

		CustomerPaymentMethodDetails customerPaymentMethodDetails = new CustomerPaymentMethodDetails();

		if (paymentMethodRequest != null) {

			customerPaymentMethodDetails.setCustomerSequenceNumber(paymentMethodRequest.getCustomerSequenceNumber());
			customerPaymentMethodDetails.setPaymentMethodType(paymentMethodRequest.getPaymentMethodType());
			customerPaymentMethodDetails.setPaymentMethodDetails(paymentMethodRequest.getPaymentMethodDetails());
			customerPaymentMethodDetails.setIsDefaultPaymentMethod(paymentMethodRequest.getIsDefaultPaymentMethod());
			customerPaymentMethodDetails.setValidFrom(paymentMethodRequest.getValidFrom());
			customerPaymentMethodDetails.setValidUpto(paymentMethodRequest.getValidUpto());
		}
		CustomerPaymentMethodDetails customerPaymentMethod = paymentDaoInterface
				.addPaymentMethod(customerPaymentMethodDetails);

		return createResponseForCustomerPaymentMethodEntity(customerPaymentMethod);
	}

	@Override
	public List<PaymentMethodResponse> getListOfPaymentMethodDetails(Long customerSequenceNumber,
			Integer expirationDuration) throws TokenExpiredException {

		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);

		List<CustomerPaymentMethodDetails> listOfPaymentMethodDetails = paymentDaoInterface
				.getListOfPaymentMethodDetails(customerSequenceNumber);

		List<PaymentMethodResponse> paymentMethodResponseList = new ArrayList<>();

		if (listOfPaymentMethodDetails != null && !listOfPaymentMethodDetails.isEmpty()) {
			for (CustomerPaymentMethodDetails customerPaymentMethodDetails : listOfPaymentMethodDetails) {
				PaymentMethodResponse paymentMethodResponse = createResponseForCustomerPaymentMethodEntity(
						customerPaymentMethodDetails);
				paymentMethodResponseList.add(paymentMethodResponse);
			}
		}

		return paymentMethodResponseList;
	}

	private PaymentMethodResponse createResponseForCustomerPaymentMethodEntity(
			CustomerPaymentMethodDetails customerPaymentMethodDetails) {

		PaymentMethodResponse paymentMethodResponse = new PaymentMethodResponse();

		paymentMethodResponse
				.setPaymentMethodSequenceNumber(customerPaymentMethodDetails.getPaymentMethodSequenceNumber());
		paymentMethodResponse.setPaymentMethodType(customerPaymentMethodDetails.getPaymentMethodType());
		paymentMethodResponse.setPaymentMethodDetails(customerPaymentMethodDetails.getPaymentMethodDetails());
		paymentMethodResponse.setIsDefaultPaymentMethod(customerPaymentMethodDetails.getIsDefaultPaymentMethod());
		paymentMethodResponse.setValidFrom(customerPaymentMethodDetails.getValidFrom());
		paymentMethodResponse.setValidUpto(customerPaymentMethodDetails.getValidUpto());

		return paymentMethodResponse;
	}

	@Override
	public void removePaymentMethod(Long paymentMethodSequenceNumber, Long customerSequenceNumber,
			Integer expirationDuration) throws TokenExpiredException {

		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);

		paymentDaoInterface.removePaymentMethod(paymentMethodSequenceNumber);

	}

}
