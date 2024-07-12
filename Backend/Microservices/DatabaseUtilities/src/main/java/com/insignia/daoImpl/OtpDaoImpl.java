package com.insignia.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insignia.daoInterface.OtpDaoInterface;
import com.insignia.entity.CustomerBasicDetailsEntity;
import com.insignia.repo.CustomerBasicDetailsRepository;

@Repository
public class OtpDaoImpl implements OtpDaoInterface {

	@Autowired
	private CustomerBasicDetailsRepository customerBasicDetailsRepository;

	@Autowired
	private EntityManager entityManager;

	public OtpDaoImpl(CustomerBasicDetailsRepository customeRepo, EntityManager entityManager) {
		super();
		this.customerBasicDetailsRepository = customeRepo;
		this.entityManager = entityManager;
	}

	@Override
	public CustomerBasicDetailsEntity saveCustomerData(CustomerBasicDetailsEntity customerBasicDetailsEntity) {
		return customerBasicDetailsRepository.save(customerBasicDetailsEntity);
	}

	@Transactional
	public CustomerBasicDetailsEntity updateCustomerDetails(CustomerBasicDetailsEntity entity) {
		return entityManager.merge(entity);
	}

	@Override
	public CustomerBasicDetailsEntity fetchOtpDetails(CustomerBasicDetailsEntity customerBasicDetailsEntity) {
		return customerBasicDetailsRepository.fetchOtpDetails(customerBasicDetailsEntity.getApplicationId(),
				customerBasicDetailsEntity.getTenantId(), customerBasicDetailsEntity.getUserId());
	}

	@Override
	public Long getCountFromCustomerBasicDetails() {

		return customerBasicDetailsRepository.getCountFromCustomerBasicDetails();
	}
	@Override
	public void updateOtp(String applicationId, String tenantId, String userId, String otp, Date otpGeneratedAt,
			Date otpExpiryAt) {
		customerBasicDetailsRepository.updateQueryForOtp(applicationId, tenantId, userId, otp, otpGeneratedAt, otpExpiryAt);

	}

}
