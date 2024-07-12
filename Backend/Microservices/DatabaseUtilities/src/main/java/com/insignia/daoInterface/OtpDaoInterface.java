package com.insignia.daoInterface;

import java.util.Date;

import com.insignia.entity.CustomerBasicDetailsEntity;

public interface OtpDaoInterface {

	public CustomerBasicDetailsEntity saveCustomerData(CustomerBasicDetailsEntity customerBasicDetailsEntity);

	public CustomerBasicDetailsEntity fetchOtpDetails(CustomerBasicDetailsEntity customerBasicDetailsEntity);

	public CustomerBasicDetailsEntity updateCustomerDetails(CustomerBasicDetailsEntity customerBasicDetailsEntity);

	public Long getCountFromCustomerBasicDetails();
	
	public void updateOtp(String applicationId, String tenantId, String userId, String otp, Date otpGeneratedAt,
			Date otpExpiryAt);
}
