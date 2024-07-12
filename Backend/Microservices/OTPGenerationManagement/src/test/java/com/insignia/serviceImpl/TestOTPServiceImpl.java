package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.insignia.constants.SecretKeyConstant;
import com.insignia.customExceptions.InvalidInputParametersException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

import com.insignia.daoInterface.OtpDaoInterface;
import com.insignia.entity.CustomerBasicDetailsEntity;
import com.insignia.entity.RolesAndPermissions;
import com.insignia.model.AuthenticationRequest;
import com.insignia.model.AuthenticationResponse;

@ExtendWith(MockitoExtension.class)
public class TestOTPServiceImpl {

	@Value("${spring.mail.username}")
	private String username;

	@Mock
	private OtpDaoInterface otpDao;

	@Mock
	private JavaMailSender javaMailSender;

	@InjectMocks
	private OtpServiceImpl otpService;

	@Mock
	private MimeMessage mimeMessage;

	AuthenticationRequest authenticationRequest = new AuthenticationRequest();
	CustomerBasicDetailsEntity customerBasicDetailsEntity = new CustomerBasicDetailsEntity();
	AuthenticationResponse authenticationResponse = new AuthenticationResponse();
	CustomerBasicDetailsEntity initializeCustmerDetailsEntity = new CustomerBasicDetailsEntity();
	RolesAndPermissions rolesAndPermissions = new RolesAndPermissions();
	List<RolesAndPermissions> rolesAndPermissionsList = new ArrayList<>();

	public void dataInitilization() throws MessagingException {
		rolesAndPermissions.setRoleName("temp-user");
		rolesAndPermissionsList.add(rolesAndPermissions);
		authenticationRequest.setApplicationId("112");
		authenticationRequest.setTenantId("1124");
		authenticationRequest.setUserId("lakshminagu54@gmail.com");
		authenticationRequest.setEmail("lakshminagu54@gmail.com");
		authenticationRequest.setOtpExpiryDuration(5);
		// authenticationRequest.setOtp("345698");
		authenticationRequest.setCustomerId("1111");

		customerBasicDetailsEntity.setApplicationId("112");
		customerBasicDetailsEntity.setTenantId("1124");
		customerBasicDetailsEntity.setUserId("2545");
		customerBasicDetailsEntity.setCustomerPassword("5485");
		customerBasicDetailsEntity.setCustomerSequenceNumber(8L);
		customerBasicDetailsEntity.setCustomerEmail("lakshminagu54@gmail.com");
		customerBasicDetailsEntity.setUserName("Lakshmi");
		customerBasicDetailsEntity.setOtp("096587");
		customerBasicDetailsEntity.setOtpExpiryAt(new Date(System.currentTimeMillis() + SecretKeyConstant.EXPIRY_DURATION * 60));

		authenticationResponse.setOtp("345698");
		
		customerBasicDetailsEntity.setRolesAndPermissions(rolesAndPermissionsList);

	}

//	@Test
//	public void testSaveAndUpdateCustomer() throws MessagingException, InvalidInputParametersException {
//		dataInitilization();
//
//		when(otpDao.fetchOtpDetails(any(CustomerBasicDetailsEntity.class))).thenReturn(customerBasicDetailsEntity);
//
//		AuthenticationResponse response = otpService.saveAndUpdateCustomer(authenticationRequest);
//		assertNotNull(response);
//
//	}

//	@Test
//	public void generateOtpAndCreateCustomerDataToSave() throws MessagingException, InvalidInputParametersException {
//		dataInitilization();
//
//		when(otpDao.fetchOtpDetails(any(CustomerBasicDetailsEntity.class))).thenReturn(null);
//
//		when(otpDao.saveCustomerData(customerBasicDetailsEntity)).thenReturn(customerBasicDetailsEntity);
//
//		AuthenticationResponse response = otpService.saveAndUpdateCustomer(authenticationRequest);
//		assertNotNull(response);
//
//	}

	@Test
	public void testCreateResponse() throws Exception {

		dataInitilization();

		OtpDaoInterface otpDao = Mockito.mock(OtpDaoInterface.class);
		OtpServiceImpl otpService = new OtpServiceImpl(otpDao);
		when(otpDao.fetchOtpDetails(any(CustomerBasicDetailsEntity.class))).thenReturn(customerBasicDetailsEntity);
		Method method = OtpServiceImpl.class.getDeclaredMethod("fetchOtpDetails", AuthenticationRequest.class);
		method.setAccessible(true);
		Object invoke = method.invoke(otpService, authenticationRequest);

		assertNotNull(invoke);
	}

	@Test
	public void testMailSender() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

		OtpDaoInterface otpDao = Mockito.mock(OtpDaoInterface.class);
		authenticationRequest.setOtp("789765");
		authenticationRequest.setUserId("lakshminagu54@gmail.com");
		authenticationRequest.setEmail("exampletest@gmail.com");

		OtpServiceImpl otpService = new OtpServiceImpl(otpDao);

		org.springframework.test.util.ReflectionTestUtils.setField(otpService, "username", "125254");

		mimeMessage = Mockito.mock(MimeMessage.class);
		javaMailSender = mock(JavaMailSender.class);
		when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
		otpService = new OtpServiceImpl(javaMailSender);

		Method method = OtpServiceImpl.class.getDeclaredMethod("mailSender", AuthenticationRequest.class);
		method.setAccessible(true);

		assertNull(method.invoke(otpService, authenticationRequest));
	}

}
