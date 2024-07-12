package com.insignia.serviceInterface;

import java.util.List;

import org.springframework.stereotype.Service;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.PaymentMethodRequest;
import com.insignia.model.PaymentMethodResponse;

@Service
public interface PaymentServiceInterface {
	
	public PaymentMethodResponse addPaymentMethod(PaymentMethodRequest paymentMethodRequest) throws InvalidInputParametersException, TokenExpiredException;
	
	public List<PaymentMethodResponse> getListOfPaymentMethodDetails(Long customerSequenceNumber, Integer expirationDuration) throws TokenExpiredException; 
	
	public void removePaymentMethod(Long paymentMethodSequenceNumber,Long customerSequenceNumber, Integer expirationDuration) throws TokenExpiredException, InvalidInputParametersException;
}
