package com.insignia.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.CustomerDaoInterface;
import com.insignia.daoInterface.JwtDao;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.CustomerBasicDetailsEntity;
import com.insignia.entity.RolesAndPermissions;
import com.insignia.entity.TokensEntity;
import com.insignia.model.AuthenticationRequest;
import com.insignia.model.AuthenticationResponse;
import com.insignia.security.EncryptionUtility;
import com.insignia.security.JwtUtil;
import com.insignia.serviceImpl.IJwtServiceImpl;
import com.insignia.userdetailsservice.CustomUserDetailsService;
import com.insignia.util.Constants;

@ExtendWith(MockitoExtension.class)
public class IJwtServiceImplTest {

	@InjectMocks
	private IJwtServiceImpl iJwtServiceImpl;

	@Mock
	private JwtDao jwtdao;

	@Mock
	private UserDetails userDetails;

	@Mock
	private JwtUtil jwtTokenUtil;

	@Mock
	private EncryptionUtility encryptionUtility;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private CustomerDaoInterface customerDaoImpl;

	@Mock
	private TokenDaoInterface tokenRepo;

	@Mock
	private CustomUserDetailsService customUserDetailsService;

	List<TokensEntity> listOfEntity = new ArrayList<>();
	AuthenticationResponse authRes = new AuthenticationResponse();
	AuthenticationRequest authReq = new AuthenticationRequest();
	Optional<List<TokensEntity>> tokenDetails = null;
	List<RolesAndPermissions> rolesAndPermissionsList = new ArrayList<>();
	RolesAndPermissions rolesAndPermissions = new RolesAndPermissions();
	CustomerBasicDetailsEntity customerBasicDetailsEntity = new CustomerBasicDetailsEntity();

	@BeforeEach
	public void initialization() {
		TokensEntity tke = new TokensEntity();
		tke.setIsLongLivedToken(false);
		tke.setTokenSequenceNumber(9l);
		tke.setIsLongLivedToken(false);
		tke.setIsTokenExpired(false);
		tke.setTokenCreatedAt(new Date());
		tke.setTokenDetails("H245");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 30);
		Date dateBefore30Days = cal.getTime();
		tke.setTokenExpiresAt(dateBefore30Days);
		tke.setTokenRevokedAt(null);
		tke.setTokenType("jwt");
		listOfEntity.add(tke);
		tokenDetails = Optional.ofNullable(listOfEntity.isEmpty() ? null : listOfEntity);

		authReq.setApplicationId("hinges-design");
		authReq.setPassword("Insignia@123");
		authReq.setRememberMeSelected(false);
		authReq.setTenantId("LU008");
		authReq.setUserId("lakshmiinsignia@gmail.com");
		authReq.setExpirationTime(15);

		authRes.setCustomerSeqNumber(12l);
		authRes.setToken("25452");
		authRes.setType("JWT");

		customerBasicDetailsEntity.setOtp("123456");
		customerBasicDetailsEntity.setOtpExpiryAt(new Date(System.currentTimeMillis() + 1000));

		rolesAndPermissions.setRoleName("admin");

		rolesAndPermissionsList.add(rolesAndPermissions);

		customerBasicDetailsEntity.setRolesAndPermissions(rolesAndPermissionsList);

	}

	@Test
	public void testFetchExistTokenIsPresent() throws Exception {

		when(jwtdao.fetchTokendetails(authRes.getCustomerSeqNumber())).thenReturn(tokenDetails);

		AuthenticationResponse fetchExistTokenIfPresent = iJwtServiceImpl.fetchExistTokenIfPresent(authRes, authReq);

		assertNotNull(fetchExistTokenIfPresent);
	}

	@Test
	public void testFetchExistTokenIsAlived() throws Exception {
		tokenDetails.get().get(0).setIsLongLivedToken(true);
		when(jwtdao.fetchTokendetails(authRes.getCustomerSeqNumber())).thenReturn(tokenDetails);

		AuthenticationResponse fetchExistTokenIfPresent = iJwtServiceImpl.fetchExistTokenIfPresent(authRes, authReq);

		assertNotNull(fetchExistTokenIfPresent);
	}

	@Test
	public void testCreateNewTokenForFetchExistTokenIfPresent() throws Exception {

		tokenDetails.get().get(0).setIsLongLivedToken(false);
		when(jwtdao.fetchTokendetails(authRes.getCustomerSeqNumber())).thenReturn(Optional.ofNullable(null));

		AuthenticationResponse fetchExistTokenIfPresent = iJwtServiceImpl.fetchExistTokenIfPresent(authRes, authReq);

		assertNotNull(fetchExistTokenIfPresent);
	}

	@Test
	public void testActiveJwtTokenIsNotPresent() throws Exception {
		tokenDetails.get().get(0).setIsLongLivedToken(true);
		tokenDetails.get().get(0).setTokenRevokedAt(new Date());
		when(jwtdao.fetchTokendetails(authRes.getCustomerSeqNumber())).thenReturn(tokenDetails);

		AuthenticationResponse fetchExistTokenIfPresent = iJwtServiceImpl.fetchExistTokenIfPresent(authRes, authReq);

		assertNotNull(fetchExistTokenIfPresent);
	}

	@Test
	public void updateTokenDetails() {
		Long customerSequenceNumber = 8L;

		doNothing().when(jwtdao).updateTokenDetails(authReq.getExpirationTime(), authReq.isRememberMeSelected(), "JWT",
				customerSequenceNumber);

		iJwtServiceImpl.updateTokenDetails(authReq.getExpirationTime(), authReq.isRememberMeSelected(), "JWT",
				customerSequenceNumber);

		verify(jwtdao, times(1)).updateTokenDetails(authReq.getExpirationTime(), authReq.isRememberMeSelected(), "JWT",
				customerSequenceNumber);
	}

	@Test
	public void deleteTokenDetails() {

		doNothing().when(jwtdao).deleteTokenDetails(authRes.getCustomerSeqNumber());

		iJwtServiceImpl.deleteTokenDetails(authRes.getCustomerSeqNumber());

		verify(jwtdao, times(1)).deleteTokenDetails(authRes.getCustomerSeqNumber());
	}

	@Test
	public void createTokenDetails() {
		Long customerSequenceNumber = 8L;
		doNothing().when(jwtdao).createTokenDetails(authReq.getExpirationTime(), authReq.isRememberMeSelected(),
				customerSequenceNumber);

		iJwtServiceImpl.createTokenDetails(authReq.getExpirationTime(), authReq.isRememberMeSelected(),
				customerSequenceNumber);

		verify(jwtdao, times(1)).createTokenDetails(authReq.getExpirationTime(), authReq.isRememberMeSelected(),
				customerSequenceNumber);
	}

	@Test
	public void testForUpdateOtpPostValidation() throws InvalidInputParametersException {
		Long customerSequenceNumber = 8L;

		doNothing().when(customerDaoImpl).updateOTPPostValidation(customerSequenceNumber);

		iJwtServiceImpl.updateOtpPostValidation(customerSequenceNumber);

		verify(customerDaoImpl, times(1)).updateOTPPostValidation(customerSequenceNumber);
	}

	@Test
	public void testUpdatePassword_PossitiveCase()
			throws InvalidInputParametersException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, TokenExpiredException {
		initialization();

		String applicationId = "hinges-design";
		String tenantId = "LU008";
		String userId = "lakshmiinsignia@gmail.com";
		Object[] password = new Object[] { "hashedPassword" };
		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(jwtdao.getPassword(applicationId, tenantId, userId)).thenReturn(password);
		when(encryptionUtility.matchBCryptDecryptedPassword("Insignia@123", "hashedPassword")).thenReturn(true);

		iJwtServiceImpl.updatePassword(authReq);

		verify(jwtdao).getPassword(applicationId, tenantId, userId);
		verify(encryptionUtility).matchBCryptDecryptedPassword("Insignia@123", "hashedPassword");
	}

	@Test
	public void testUpdatePassword_ValidateOTP()
			throws InvalidInputParametersException, TokenExpiredException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		initialization();
		authReq.setIsOTPAuthentication(true);
		assertThrows(InvalidInputParametersException.class, () -> iJwtServiceImpl.updatePassword(authReq));

	}

	@Test
	public void testUpdatePassword_ValidateInvalidOTP()
			throws InvalidInputParametersException, TokenExpiredException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		initialization();
		authReq.setIsOTPAuthentication(true);
		authReq.setApplicationId("hinges-design");
		authReq.setTenantId("LU008");
		authReq.setUserId("lakshmiinsignia@gmail.com");
		authReq.setoTP("123456");

		String applicationId = "hinges-design";
		String tenantId = "LU008";
		String userId = "lakshmiinsignia@gmail.com";

		List<Object[]> otpFromDatabase = new ArrayList<>();
		otpFromDatabase.add(new Object[] { "45890", new Date() });
		when(jwtdao.getOtp(applicationId, tenantId, userId)).thenReturn(otpFromDatabase);

		assertThrows(InvalidInputParametersException.class, () -> iJwtServiceImpl.updatePassword(authReq));

	}

	@Test
	public void testUpdatePassword_ValidateOTPExpired()
			throws InvalidInputParametersException, TokenExpiredException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		authReq.setIsOTPAuthentication(true);
		authReq.setApplicationId("hinges-design");
		authReq.setTenantId("LU008");
		authReq.setUserId("lakshmiinsignia@gmail.com");
		authReq.setoTP("123456");
		authReq.setPassword("password");

		String applicationId = "hinges-design";
		String tenantId = "LU008";
		String userId = "lakshmiinsignia@gmail.com";

		List<Object[]> otpFromDatabase = new ArrayList<>();
		otpFromDatabase.add(new Object[] { "123456", new Date() });
		when(jwtdao.getOtp(applicationId, tenantId, userId)).thenReturn(otpFromDatabase);

		assertThrows(InvalidInputParametersException.class, () -> iJwtServiceImpl.updatePassword(authReq));

	}

	@Test
	public void testUpdatePassword_OldPasswordIsIncorrect()
			throws InvalidInputParametersException, TokenExpiredException {
		initialization();
		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		authenticationRequest.setCustomerSequenceNumber(123l);
		authenticationRequest.setExpirationDuration(1000);
		authenticationRequest.setPassword(null);

		assertThrows(InvalidInputParametersException.class,
				() -> iJwtServiceImpl.updatePassword(authenticationRequest));

	}

	@Test
	public void testUpdatePasswordWithInvalidOldPassword()
			throws InvalidInputParametersException, TokenExpiredException {
		initialization();

		String applicationId = "hinges-design";
		String tenantId = "LU008";
		String userId = "lakshmiinsignia@gmail.com";

		Object[] password = new Object[] { "hashedPassword" }; // Mocked password data

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());
		when(jwtdao.getPassword(applicationId, tenantId, userId)).thenReturn(password);
		when(encryptionUtility.matchBCryptDecryptedPassword("Insignia@123", "hashedPassword")).thenReturn(false); // password

		assertThrows(InvalidInputParametersException.class, () -> iJwtServiceImpl.updatePassword(authReq));

		verify(jwtdao).getPassword("hinges-design", "LU008", "lakshmiinsignia@gmail.com");
		verify(encryptionUtility).matchBCryptDecryptedPassword("Insignia@123", "hashedPassword");
	}

	@Test
	public void testCheckTokenValidity_PossitiveScenario() throws TokenExpiredException {

		Long customerSequenceNumber = 5L;
		authRes.setTokenStatus(Constants.statusTokenValid);
		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		AuthenticationResponse checkTokenValidity = iJwtServiceImpl.checkTokenValidity(customerSequenceNumber);

		assertEquals(Constants.statusTokenValid, checkTokenValidity.getTokenStatus());
	}

	@Test
	public void testCheckTokenValidity_ExpiredToken() throws TokenExpiredException {

		Long customerSequenceNumber = 5L;
		authRes.setTokenStatus(Constants.statusExpired);

		doThrow(TokenExpiredException.class).when(tokenRepo).checkTokenValidity(any(), any());

		AuthenticationResponse checkTokenValidity = iJwtServiceImpl.checkTokenValidity(customerSequenceNumber);

		assertEquals(Constants.statusExpired, checkTokenValidity.getTokenStatus());
	}

	@Test
	void testGetUserDetailsForUserId_CreateAuthenticationTokenWithEmailValidation() throws Exception {

		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		authenticationRequest.setIsToValidatePassword(true);
		authenticationRequest.setEmailId("test@example.com");

		org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
				"test@example.com", "password", new ArrayList<>());

		when(customUserDetailsService.loadUserByUsername(authenticationRequest.getEmailId())).thenReturn(userDetails);
		when(customUserDetailsService.getCustomerBasicDetailsEntity()).thenReturn(customerBasicDetailsEntity); // Replace
																												// CustomerBasicDetailsEntity

		ResponseEntity<AuthenticationResponse> response = iJwtServiceImpl
				.createAuthenticationToken(authenticationRequest);

		assertNotNull(response);
	}

	@Test
	void testGetUserDetailsForCustomUserName_CreateAuthenticationToken() throws Exception {
		initialization();
		AuthenticationRequest authenticationRequest = new AuthenticationRequest();

		authenticationRequest.setIsOTPAuthentication(true);

		authenticationRequest.setoTP("123456");

		String customUserName = authenticationRequest.getCustomUserName();

		org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
				"test@example.com", "password", new ArrayList<>());

		when(customUserDetailsService.loadUserByUsername(customUserName)).thenReturn(userDetails);
		when(customUserDetailsService.getCustomerBasicDetailsEntity()).thenReturn(customerBasicDetailsEntity);

		ResponseEntity<AuthenticationResponse> responseEntity = iJwtServiceImpl
				.createAuthenticationToken(authenticationRequest);

		assertNotNull(responseEntity);
	}

	@Test
	void testCreateAuthenticationToken_ValidateInvalidOTP() throws Exception {
		initialization();
		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		authenticationRequest.setIsOTPAuthentication(true);
		authenticationRequest.setoTP("123478");

		String customUserName = authenticationRequest.getCustomUserName();

		org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
				"test@example.com", "password", new ArrayList<>());

		when(customUserDetailsService.loadUserByUsername(customUserName)).thenReturn(userDetails);
		when(customUserDetailsService.getCustomerBasicDetailsEntity()).thenReturn(customerBasicDetailsEntity);

		assertThrows(InvalidInputParametersException.class,
				() -> iJwtServiceImpl.createAuthenticationToken(authenticationRequest));

	}

	@Test
	void testCreateAuthenticationToken_ValidateOTPExpired() throws Exception {
		initialization();
		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		authenticationRequest.setIsOTPAuthentication(true);
		authenticationRequest.setoTP("123456");

		String customUserName = authenticationRequest.getCustomUserName();

		customerBasicDetailsEntity.setOtpExpiryAt(new Date());
		org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
				"test@example.com", "password", new ArrayList<>());

		when(customUserDetailsService.loadUserByUsername(customUserName)).thenReturn(userDetails);
		when(customUserDetailsService.getCustomerBasicDetailsEntity()).thenReturn(customerBasicDetailsEntity);

		assertThrows(InvalidInputParametersException.class,
				() -> iJwtServiceImpl.createAuthenticationToken(authenticationRequest));

	}

	@Test
	void testCreateAuthenticationToken_IsToValidatePassword_BadCredentials() throws Exception {
		initialization();
		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		authenticationRequest.setIsToValidatePassword(true);
		authenticationRequest.setApplicationId("hinges-design");
		authenticationRequest.setTenantId("LU008");
		authenticationRequest.setEmailId(null);

		when(customUserDetailsService.getCustomerBasicDetailsEntity()).thenReturn(customerBasicDetailsEntity);

		assertThrows(InvalidInputParametersException.class,
				() -> iJwtServiceImpl.createAuthenticationToken(authenticationRequest));

	}

	@Test
	void testCreateAuthenticationToken_NormalAuthentication_BadCredentials() throws Exception {

		AuthenticationRequest authenticationRequest = new AuthenticationRequest();

		authenticationRequest.setApplicationId("hinges-design");
		authenticationRequest.setTenantId("LU008");
		authenticationRequest.setUserId("lakshmiinsignia@gmail.com");
		authenticationRequest.setIsOTPAuthentication(false);
		authenticationRequest.setIsToValidatePassword(false);
		authenticationRequest.setoTP("123456");
		authenticationRequest.setPassword("admin@12dddddddd");

		String customUserName = authenticationRequest.getCustomUserName();

		org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
				"test@example.com", "$2a$12$yIKhNy3lueoTmdZy97H.KOtZfghjFTBCfVbGimGUF4UeTtvaR7PLy", new ArrayList<>());

		when(customUserDetailsService.loadUserByUsername(customUserName)).thenReturn(userDetails);
		when(customUserDetailsService.getCustomerBasicDetailsEntity()).thenReturn(customerBasicDetailsEntity);

		assertThrows(InvalidInputParametersException.class,
				() -> iJwtServiceImpl.createAuthenticationToken(authenticationRequest));

	}

}
