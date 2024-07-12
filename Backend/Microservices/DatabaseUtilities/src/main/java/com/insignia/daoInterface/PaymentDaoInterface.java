package com.insignia.daoInterface;

import java.util.List;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.entity.CustomerPaymentMethodDetails;

public interface PaymentDaoInterface {

	public CustomerPaymentMethodDetails addPaymentMethod(CustomerPaymentMethodDetails customerPaymentMethodDetails) throws InvalidInputParametersException;
	
	public List<CustomerPaymentMethodDetails> getListOfPaymentMethodDetails(Long customerSequenceNumber); 
	
	public void removePaymentMethod(Long paymentMethodSequenceNumber);
}
