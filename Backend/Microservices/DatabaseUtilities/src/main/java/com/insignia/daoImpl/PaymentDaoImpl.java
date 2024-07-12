package com.insignia.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.insignia.constants.CustomerPaymentMethodDetailsConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.daoInterface.PaymentDaoInterface;
import com.insignia.entity.CustomerPaymentMethodDetails;
import com.insignia.repo.CustomerPaymentMethodRepository;

@Repository
public class PaymentDaoImpl implements PaymentDaoInterface {

	@Autowired
	private CustomerPaymentMethodRepository customerPaymentMethodRepository;

	@Override
	public CustomerPaymentMethodDetails addPaymentMethod(CustomerPaymentMethodDetails customerPaymentMethodDetails)
			throws InvalidInputParametersException {
		try {
			return customerPaymentMethodRepository.save(customerPaymentMethodDetails);
		} catch (DataIntegrityViolationException e) {
			throw new InvalidInputParametersException(
					CustomerPaymentMethodDetailsConstant.validateApplicationDetailsErrorCode,
					CustomerPaymentMethodDetailsConstant.validateApplicationDetailsMessage);
		}
	}

	@Override
	public List<CustomerPaymentMethodDetails> getListOfPaymentMethodDetails(Long customerSequenceNumber) {

		return customerPaymentMethodRepository.getListOfPaymentMethodDetails(customerSequenceNumber);
	}

	@Override
	public void removePaymentMethod(Long paymentMethodSequenceNumber) {

		customerPaymentMethodRepository.deleteById(paymentMethodSequenceNumber);
	}

}
