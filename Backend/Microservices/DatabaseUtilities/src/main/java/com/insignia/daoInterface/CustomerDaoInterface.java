package com.insignia.daoInterface;

import java.util.List;
import java.util.Optional;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.entity.CustomerBasicDetailsEntity;

public interface CustomerDaoInterface {

	public CustomerBasicDetailsEntity saveAllCustomerDetails(CustomerBasicDetailsEntity cbde)
			throws InvalidInputParametersException;

	public Optional<CustomerBasicDetailsEntity> getCustomerDetails(Long customerSequenceNumber, String applicationId, String tenantId);

	public CustomerBasicDetailsEntity updateAllCustomerDetails(CustomerBasicDetailsEntity customerBasicDetailsEntity)
			throws InvalidInputParametersException;

	public void deleteCustomerAssociatedDetails(Long customerSequenceNumber, String applicationId, String tenantId);
	
	public List<CustomerBasicDetailsEntity> getAllCustomerData(String applicationId, String tenantId);

	public List<Object[]> getCustomerAndStoreInformation();

    void updateOTPPostValidation (Long customerSequenceNumber);
}
