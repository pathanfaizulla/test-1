package com.insignia.daoImpl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.insignia.constants.CustomerBasicDetailsConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.daoInterface.CustomerDaoInterface;
import com.insignia.entity.CustomerBasicDetailsEntity;
import com.insignia.repo.CustomerBasicDetailsRepository;

@Repository
public class CustomerDaoImpl implements CustomerDaoInterface {

	@Autowired
	private CustomerBasicDetailsRepository customerBasicDetailsRepo;

	@Autowired
	private EntityManager entityManager;

	public CustomerDaoImpl(CustomerBasicDetailsRepository customerBasicDetailsRepo, EntityManager entityManager) {
		super();
		this.customerBasicDetailsRepo = customerBasicDetailsRepo;
		this.entityManager = entityManager;

	}

	@Override
	public CustomerBasicDetailsEntity saveAllCustomerDetails(CustomerBasicDetailsEntity cbde)
			throws InvalidInputParametersException {
		try {

			return customerBasicDetailsRepo.save(cbde);

		} catch (DataIntegrityViolationException e) {
			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validateCustomerDetailsErrorCode,
					CustomerBasicDetailsConstants.validateCustomerDetailsMessage);
		}

	}

	@Override
	public CustomerBasicDetailsEntity updateAllCustomerDetails(CustomerBasicDetailsEntity customerBasicDetailsEntity)
			throws InvalidInputParametersException {
		try {
			return entityManager.merge(customerBasicDetailsEntity);

		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				throw new InvalidInputParametersException(
						CustomerBasicDetailsConstants.validateCustomerDetailsErrorCode,
						CustomerBasicDetailsConstants.validateCustomerDetailsMessage);
			}
			throw e;
		}
	}

	@Transactional
	@Override
	public void deleteCustomerAssociatedDetails(Long customerSequenceNumber, String applicationId, String tenantId) {
		customerBasicDetailsRepo.deleteCustomerDetails(customerSequenceNumber, applicationId, tenantId);

	}

	@Override
	public Optional<CustomerBasicDetailsEntity> getCustomerDetails(Long customerSequenceNumber,String applicationId, String tenantId) {
		return customerBasicDetailsRepo.getCustomerDetails(customerSequenceNumber, applicationId, tenantId);

	}

	@Override
	public List<CustomerBasicDetailsEntity> getAllCustomerData(String applicationId, String tenantId) {

		return customerBasicDetailsRepo.getAllCustomerDetails(applicationId, tenantId);
	}

	@Override
	public List<Object[]> getCustomerAndStoreInformation() {

		return customerBasicDetailsRepo.getCustomerAndStoreInformation();
	}

	@Override
	public void updateOTPPostValidation(Long customerSequenceNumber) {
		customerBasicDetailsRepo.updateOTPPostValidation(customerSequenceNumber);
	}
}
