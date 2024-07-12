package com.insignia.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.JwtDao;
import com.insignia.entity.CustomerBasicDetailsEntity;
import com.insignia.entity.RolesAndPermissions;
import com.insignia.model.AuthenticationRequest;
import com.insignia.model.AuthenticationResponse;
import com.insignia.security.JwtUtil;
import com.insignia.serviceInterface.IJwtService;
import com.insignia.userdetailsservice.CustomUserDetailsService;

@ExtendWith(MockitoExtension.class)
public class JwtControllerTest {

	@InjectMocks
	private JwtController jwtController;

	@Mock
	private JwtDao jwtdao;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtUtil jwtTokenUtil;

	@Mock
	private CustomUserDetailsService userDetailsService;

	@Mock
	private UserDetailsService userDetails;

	@Mock
	private IJwtService serviceImpl;

	@Value("${errorCodes.500}")
	private String internalServerError;

	@Value("${errorCodes.403}")
	private String UNAUTHORIZED;

	@Value("${errorCodes.400}")
	private String badRequest;

	AuthenticationResponse authResp = new AuthenticationResponse();
	AuthenticationRequest authenticationRequest = new AuthenticationRequest();
	CustomerBasicDetailsEntity customerDetails = new CustomerBasicDetailsEntity();
	ResponseEntity<AuthenticationResponse> res = null;
	List<RolesAndPermissions> rolsList = new ArrayList<>();
	RolesAndPermissions role = new RolesAndPermissions();

	public void dataInitilization() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		authResp.setExpirationTime(new Date());
		authResp.setType("JWT");
		authResp.setToken("1252ddf52drf25");
		authResp.setTokenStatus("update");
		res = ResponseEntity.status(HttpStatus.OK).body(authResp);

		authenticationRequest.setApplicationId("112");
		authenticationRequest.setExpirationTime(30);
		authenticationRequest.setTenantId("1124");
		authenticationRequest.setUserId("insignia@gmail.com");
		authenticationRequest.setEmailId("insignia@gmail.com");
		authenticationRequest.setPassword("5485678987654567");

		customerDetails.setUserName(authenticationRequest.getCustomUserName());
		customerDetails.setCustomerPassword(authenticationRequest.getPassword());
		customerDetails.setCustomerSequenceNumber(authResp.getCustomerSeqNumber());

		role.setPermission1(true);
		role.setRoleId(1);
		rolsList.add(role);
		customerDetails.setRolesAndPermissions(rolsList);
	}

	@Test
	public void testCreateAuthenticationToken_SuccessfulAuthentication()
			throws InvalidInputParametersException, Exception {
		dataInitilization();
		authenticationRequest.setApplicationId("112");
		authenticationRequest.setExpirationTime(30);
		authenticationRequest.setTenantId("1124");
		authenticationRequest.setPassword("5485678987654567");

		Mockito.when(serviceImpl.createAuthenticationToken(authenticationRequest)).thenReturn(res);

		ResponseEntity<AuthenticationResponse> createAuthenticationToken = jwtController
				.createAuthenticationToken(authenticationRequest);
		assertEquals(HttpStatus.OK, createAuthenticationToken.getStatusCode());
	}

	@Test
	void testCreateAuthentication_InvalidInputParametersException() {

		AuthenticationRequest authenticationRequest = new AuthenticationRequest();

		ResponseEntity<AuthenticationResponse> createAuthenticationToken = jwtController
				.createAuthenticationToken(authenticationRequest);

		assertEquals(HttpStatus.BAD_REQUEST, createAuthenticationToken.getStatusCode());

	}

	@Test
	void testCreateAuthenticationToken_WithBadCredentialsException() throws InvalidInputParametersException, Exception {
		dataInitilization();
		authenticationRequest.setApplicationId("112");
		authenticationRequest.setExpirationTime(30);
		authenticationRequest.setTenantId("1124");
		authenticationRequest.setPassword("5485678987654567");
		role.setRoleId(4);

		when(serviceImpl.createAuthenticationToken(authenticationRequest))
				.thenThrow(new BadCredentialsException("Bad credentials"));

		ResponseEntity<AuthenticationResponse> responseEntity = jwtController
				.createAuthenticationToken(authenticationRequest);

		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());

	}

	@Test
	void testCreateAuthenticationToken_Exception() throws InvalidInputParametersException, Exception {
		dataInitilization();
		authenticationRequest.setApplicationId("112");
		authenticationRequest.setExpirationTime(30);
		authenticationRequest.setTenantId("1124");
		authenticationRequest.setPassword("5485678987654567");
		role.setRoleName(null);

		when(serviceImpl.createAuthenticationToken(authenticationRequest))
				.thenThrow(new NullPointerException("Bad credentials"));

		ResponseEntity<AuthenticationResponse> responseEntity = jwtController
				.createAuthenticationToken(authenticationRequest);
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());

	}

	@Test
	public void testValidateOauthLoginCredentials() throws Exception {

		dataInitilization();
		authenticationRequest.setIsToValidatePassword(true);

		authenticationRequest.setEmailId(
				"insigniainsigniainsigniainsigniainsigniainsigniainsigniainsigniainsigniainsigniainsignia@gmail.com");

		authenticationRequest.setApplicationId("hinges-design");
		authenticationRequest.setTenantId("LU008");

		ResponseEntity<?> createAuthenticationToken = jwtController.createAuthenticationToken(authenticationRequest);

		assertEquals(createAuthenticationToken.getStatusCode(), HttpStatus.BAD_REQUEST);

	}

	@Test
	public void testValidateOTPLoginCredentialsForOTP() throws Exception {

		dataInitilization();

		authenticationRequest.setIsOTPAuthentication(true);

		authenticationRequest.setUserId("insignia@gmail.com");
		authenticationRequest.setApplicationId("hinges-design");
		authenticationRequest.setTenantId("LU008");

		ResponseEntity<?> createAuthenticationToken = jwtController.createAuthenticationToken(authenticationRequest);

		assertEquals(createAuthenticationToken.getStatusCode(), HttpStatus.BAD_REQUEST);

	}

	@Test
	public void testValidateOTPLoginCredentialsForOTPLength() throws Exception {

		dataInitilization();

		authenticationRequest.setIsOTPAuthentication(true);
		authenticationRequest.setUserId("insignia@gmail.com");
		authenticationRequest.setApplicationId("hinges-design");
		authenticationRequest.setTenantId("LU008");
		authenticationRequest.setoTP("9087656");

		ResponseEntity<?> createAuthenticationToken = jwtController.createAuthenticationToken(authenticationRequest);

		assertEquals(createAuthenticationToken.getStatusCode(), HttpStatus.BAD_REQUEST);

	}

	@Test
	public void testUpdatePassword_SuccessScenario() throws InvalidInputParametersException, Exception {
		dataInitilization();
		authenticationRequest.setApplicationId("112");
		authenticationRequest.setExpirationTime(30);
		authenticationRequest.setTenantId("1124");
		authenticationRequest.setPassword("1234567894567890");
		authenticationRequest.setNewPassword("123456789");

		doNothing().when(serviceImpl).updatePassword(authenticationRequest);

		ResponseEntity<?> updatePassword = jwtController.updatePassword(authenticationRequest);
		assertEquals(HttpStatus.OK, updatePassword.getStatusCode());
	}

	@Test
	public void testUpdatePassword_InvalidInputParameters_Exception()
			throws InvalidInputParametersException, Exception {
		dataInitilization();
		authenticationRequest.setApplicationId("112");
		authenticationRequest.setExpirationTime(30);
		authenticationRequest.setTenantId("1124");
		authenticationRequest.setPassword("1234567894567890");

		ResponseEntity<?> updatePassword = jwtController.updatePassword(authenticationRequest);
		assertEquals(HttpStatus.BAD_REQUEST, updatePassword.getStatusCode());
	}

	@Test
	void testUpdatePassword_TokenExpiredException()
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, InvalidInputParametersException, TokenExpiredException {

		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		authenticationRequest.setPassword("1234567894567890");
		authenticationRequest.setNewPassword("123456789");

		doThrow(new TokenExpiredException("Unexpected exception")).when(serviceImpl)
				.updatePassword(authenticationRequest);

		ResponseEntity<?> responseEntity = jwtController.updatePassword(authenticationRequest);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());

	}

	@Test
	void testUpdatePassword_Exception() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidInputParametersException, TokenExpiredException {

		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		authenticationRequest.setPassword("1234567894567890");
		authenticationRequest.setNewPassword("123456789");

		doThrow(new RuntimeException("Unexpected exception")).when(serviceImpl).updatePassword(authenticationRequest);

		ResponseEntity<?> responseEntity = jwtController.updatePassword(authenticationRequest);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());

	}

	@Test
	public void testCheckTokenPassword_SuccessScenario() throws InvalidInputParametersException, Exception {
		dataInitilization();
		Long customerSequenceNumber = 5L;

		when(serviceImpl.checkTokenValidity(customerSequenceNumber)).thenReturn(authResp);

		ResponseEntity<?> checkTokenValidity = jwtController.checkTokenValidity(customerSequenceNumber);
		assertEquals(HttpStatus.OK, checkTokenValidity.getStatusCode());
	}

	@Test
	void testCheckTokenPassword_TokenExpiredException() throws TokenExpiredException {
		Long customerSequenceNumber = 5L;

		when(serviceImpl.checkTokenValidity(customerSequenceNumber))
				.thenThrow(new TokenExpiredException("Token is not valid"));

		ResponseEntity<?> responseEntity = jwtController.checkTokenValidity(customerSequenceNumber);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());

	}

	@Test
	void testCheckTokenPassword_Exception() throws TokenExpiredException {
		Long customerSequenceNumber = 5L;

		when(serviceImpl.checkTokenValidity(customerSequenceNumber))
				.thenThrow(new RuntimeException("Unexpected error occured"));

		ResponseEntity<?> responseEntity = jwtController.checkTokenValidity(customerSequenceNumber);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());

	}


}
