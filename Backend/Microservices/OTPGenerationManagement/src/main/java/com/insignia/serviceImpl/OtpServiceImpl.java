package com.insignia.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.insignia.constants.SecretKeyConstant;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.insignia.conistant.OtpConstants;
import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.daoInterface.OtpDaoInterface;
import com.insignia.entity.CustomerBasicDetailsEntity;
import com.insignia.entity.RolesAndPermissions;
import com.insignia.model.AuthenticationRequest;
import com.insignia.model.AuthenticationResponse;
import com.insignia.serviceInterface.OtpServiceInterface;

@Service

public class OtpServiceImpl implements OtpServiceInterface {

	@Autowired
	private OtpDaoInterface otpDao;

	@Value("${spring.mail.username}")
	private String username;

	@Autowired
	private JavaMailSender javaMailSender;

	public OtpServiceImpl() {
		super();
	}

	public OtpServiceImpl(JavaMailSender javaMailSender) {
		super();

		this.javaMailSender = javaMailSender;
	}

	public OtpServiceImpl(OtpDaoInterface otpDao) {
		super();

		this.otpDao = otpDao;
	}

	@Transactional
	@Override
	public AuthenticationResponse saveAndUpdateCustomer(AuthenticationRequest authenticationRequest)
			throws MessagingException, InvalidInputParametersException {

		AuthenticationResponse authResponse = new AuthenticationResponse();
		RolesAndPermissions rolesAndPermissions = new RolesAndPermissions();

		CustomerBasicDetailsEntity existingDetails = fetchOtpDetails(authenticationRequest);

		String text = "1234567890";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();

		IntStream.rangeClosed(1, 6).forEach(i -> sb.append(text.charAt(random.nextInt(text.length()))));

		authenticationRequest.setOtp(sb.toString());

		if (existingDetails == null) {

			existingDetails = new CustomerBasicDetailsEntity();
			CustomerBasicDetailsEntity customerBasicDetailsEntity = generateOtpAndCreateCustomerDataToSave(
					authenticationRequest, existingDetails, rolesAndPermissions);
			CustomerBasicDetailsEntity saveCustomerEntity = otpDao.saveCustomerData(customerBasicDetailsEntity);
			authResponse.setOtp(saveCustomerEntity.getOtp());

		} else {
			if (existingDetails.getOtpExpiryAt() != null
					&& existingDetails.getOtpExpiryAt().compareTo(new Date()) > 0) {

				authResponse.setOtp(existingDetails.getOtp());

			} else {
				updateOTPDetails(authenticationRequest);
				authResponse.setOtp(authenticationRequest.getOtp());

			}

		}

		return authResponse;
	}

	private CustomerBasicDetailsEntity generateOtpAndCreateCustomerDataToSave(
			AuthenticationRequest authenticationRequest, CustomerBasicDetailsEntity customerBasicDetailsEntity,
			RolesAndPermissions rolesAndPermissions) throws MessagingException, InvalidInputParametersException {

		mailSender(authenticationRequest);

		if (authenticationRequest.getCustomerId() == null || authenticationRequest.getCustomerId().isEmpty()) {
			customerBasicDetailsEntity.setCustomerId(authenticationRequest.getCustomerId());

			Long totalCustomerCount = otpDao.getCountFromCustomerBasicDetails();

			String customerId = String.format(CommonConstant.paddingLength, totalCustomerCount + 1);

			authenticationRequest.setCustomerId(customerId);

		}

		customerBasicDetailsEntity.setCustomerId(authenticationRequest.getCustomerId());
		customerBasicDetailsEntity.setApplicationId(authenticationRequest.getApplicationId());
		customerBasicDetailsEntity.setTenantId(authenticationRequest.getTenantId());
		customerBasicDetailsEntity.setUserId(authenticationRequest.getUserId());
		customerBasicDetailsEntity.setCustomerEmail(authenticationRequest.getEmail());
		customerBasicDetailsEntity.setOtp(authenticationRequest.getOtp());
		customerBasicDetailsEntity.setOtpGeneratedAt(new Date());
		customerBasicDetailsEntity.setOtpExpiryAt(new Date(System.currentTimeMillis()
				+ SecretKeyConstant.EXPIRY_DURATION * authenticationRequest.getOtpExpiryDuration()));
		List<RolesAndPermissions> rolesAndPermissionsList = new ArrayList<>();
		rolesAndPermissions.setRoleName(OtpConstants.roleName);
		rolesAndPermissionsList.add(rolesAndPermissions);
		customerBasicDetailsEntity.setRolesAndPermissions(rolesAndPermissionsList);
		return customerBasicDetailsEntity;
	}

	private void updateOTPDetails(AuthenticationRequest authenticationRequest) throws MessagingException {

		mailSender(authenticationRequest);

		Date oTPExpiryTime = new Date(System.currentTimeMillis()
				+ SecretKeyConstant.EXPIRY_DURATION * authenticationRequest.getOtpExpiryDuration());

		otpDao.updateOtp(authenticationRequest.getApplicationId(), authenticationRequest.getTenantId(),
				authenticationRequest.getUserId(), authenticationRequest.getOtp(), new Date(), oTPExpiryTime);

	}

	private void mailSender(AuthenticationRequest authenticationRequest) throws MessagingException {
		MimeMessage mailMessage = javaMailSender.createMimeMessage();

		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);

		messageHelper.setTo(authenticationRequest.getUserId());
		messageHelper.setFrom(username != null ? username : "test@gmail.com");

		messageHelper.setSubject("This is otp verification");
		messageHelper.setText(authenticationRequest.getOtp());

		javaMailSender.send(mailMessage);
	}

	private CustomerBasicDetailsEntity fetchOtpDetails(AuthenticationRequest authenticationRequest) {

		CustomerBasicDetailsEntity customerBasicDetailsEntity = new CustomerBasicDetailsEntity();
		customerBasicDetailsEntity.setUserId(authenticationRequest.getUserId());
		customerBasicDetailsEntity.setApplicationId(authenticationRequest.getApplicationId());
		customerBasicDetailsEntity.setTenantId(authenticationRequest.getTenantId());
		return otpDao.fetchOtpDetails(customerBasicDetailsEntity);
	}

}
