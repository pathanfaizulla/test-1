package com.insignia.serviceImpl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.insignia.constants.CustomerBasicDetailsConstants;
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
import com.insignia.repo.TokensEntityRepo;
import com.insignia.security.EncryptionUtility;
import com.insignia.security.JwtUtil;
import com.insignia.serviceInterface.IJwtService;
import com.insignia.userdetailsservice.CustomUserDetailsService;
import com.insignia.util.Constants;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IJwtServiceImpl implements IJwtService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtDao jwtdao;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Autowired
	private EncryptionUtility encryptionUtility;

	@Autowired
	private CustomerDaoInterface customerDaoImpl;

	@Value("${defaultTokenExpirationTime}")
	private Integer defaultTokenExpirationTime;

	public AuthenticationResponse fetchExistTokenIfPresent(AuthenticationResponse authRes,
			AuthenticationRequest authReq) throws ParseException {
		Optional<List<TokensEntity>> tokenDetails = jwtdao.fetchTokendetails(authRes.getCustomerSeqNumber());

		if (tokenDetails.isPresent()) {
			Optional<TokensEntity> isLongLivedToken = tokenDetails.get().stream()
					.filter(p -> p.getIsLongLivedToken() && p.getTokenRevokedAt() == null).findFirst();

			if (isLongLivedToken.isPresent()) {

				longLivedToken(isLongLivedToken.get(), authRes);

				return authRes;

			} else {

				Optional<TokensEntity> activeJwtToken = tokenDetails.get().stream().filter(p -> !p.getIsTokenExpired()
						&& p.getTokenRevokedAt() == null && p.getTokenExpiresAt().compareTo(new Date()) == 1)
						.findFirst();

				activeOrExpiredToken(activeJwtToken, authRes, authReq);

				return authRes;

			}
		} else {
			createTokenDetails(authReq.getExpirationTime(), authReq.isRememberMeSelected(),
					authRes.getCustomerSeqNumber());
			authRes.setTokenStatus(Constants.statusNewToken);
			return authRes;
		}
	}

	private void activeOrExpiredToken(Optional<TokensEntity> activeJwtToken, AuthenticationResponse authRes,
			AuthenticationRequest authReq) {
		if (activeJwtToken.isPresent()) {
			authRes.setExpirationTime(activeJwtToken.get().getTokenExpiresAt());
			authRes.setType(activeJwtToken.get().getTokenType());
			authRes.setToken(activeJwtToken.get().getTokenDetails());
			authRes.setTokenStatus(Constants.statusTokenValid);
		} else {
			deleteTokenDetails(authRes.getCustomerSeqNumber());
			createTokenDetails(authReq.getExpirationTime(), authReq.isRememberMeSelected(),
					authRes.getCustomerSeqNumber());
			authRes.setTokenStatus(TokensEntityRepo.createToken);
		}
	}

	private void longLivedToken(TokensEntity tokensEntity, AuthenticationResponse authRes) {
		authRes.setExpirationTime(tokensEntity.getTokenExpiresAt());
		authRes.setType(tokensEntity.getTokenType());
		authRes.setToken(tokensEntity.getTokenDetails());
		authRes.setTokenStatus(Constants.statusTokenLongLived);
	}

	public void updateTokenDetails(Integer expirationTime, Boolean isRememberMeSelected, String jwt,
			Long customerSeqNumber) {
		jwtdao.updateTokenDetails(expirationTime, isRememberMeSelected, jwt, customerSeqNumber);

	}

	public void deleteTokenDetails(Long seqNo) {
		jwtdao.deleteTokenDetails(seqNo);
	}

	public void createTokenDetails(Integer expirationTime, Boolean isRememberMeSelected, Long customerSeqNumber) {
		jwtdao.createTokenDetails(expirationTime, isRememberMeSelected, customerSeqNumber);
	}

	@Transactional
	@Modifying
	@Override
	public void updatePassword(AuthenticationRequest authenticationRequest)
			throws InvalidInputParametersException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, TokenExpiredException {

		String oldPassword = authenticationRequest.getPassword();

		if (authenticationRequest.getIsOTPAuthentication()) {

			if (authenticationRequest.getoTP() == null || authenticationRequest.getoTP().trim().isEmpty()) {
				throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validateOTPErrorCode,
						CustomerBasicDetailsConstants.validateOTP);
			} else {
				List<Object[]> getOtpFromDatabase = jwtdao.getOtp(authenticationRequest.getApplicationId(),
						authenticationRequest.getTenantId(), authenticationRequest.getUserId());

				if (!authenticationRequest.getoTP().equalsIgnoreCase((String) getOtpFromDatabase.get(0)[0])) {

					throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validateInvalidOtpErrorCode,
							CustomerBasicDetailsConstants.validateInvalidOtp);
				}

				if ((getOtpFromDatabase.get(0)[1]) == null
						|| (((Date) getOtpFromDatabase.get(0)[1])).compareTo(new Date()) < 0) {

					throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validateOTPExpiredErrorCode,
							CustomerBasicDetailsConstants.validateOTPExpired);
				}
			}

		} else {

			tokenDao.checkTokenValidity(authenticationRequest.getCustomerSequenceNumber(),
					authenticationRequest.getExpirationDuration());

			if (oldPassword == null || oldPassword.trim().isEmpty()) {
				throw new InvalidInputParametersException(CustomerBasicDetailsConstants.oldPasswordErrorCode,
						CustomerBasicDetailsConstants.oldPasswordErrorMessage);
			} else {
				Object[] password = jwtdao.getPassword(authenticationRequest.getApplicationId(),
						authenticationRequest.getTenantId(), authenticationRequest.getUserId());

				if (!(encryptionUtility.matchBCryptDecryptedPassword(oldPassword, String.valueOf(password[0])))) {
					throw new InvalidInputParametersException(CustomerBasicDetailsConstants.oldPasswordErrorCode,
							CustomerBasicDetailsConstants.oldPasswordErrorMessage);
				}
			}

		}

		jwtdao.updatePassword(encryptionUtility.getBCryptEncryptedPassword(authenticationRequest.getNewPassword()),
				authenticationRequest.getUserId());
	}

	@Override
	public AuthenticationResponse checkTokenValidity(Long customerSequenceNumber) throws TokenExpiredException {
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();

		try {

			tokenDao.checkTokenValidity(customerSequenceNumber, 0);

			authenticationResponse.setTokenStatus(Constants.statusTokenValid);
		} catch (TokenExpiredException e) {
			authenticationResponse.setTokenStatus(Constants.statusExpired);
		}
		return authenticationResponse;
	}

	@Override
	@Transactional
	public void updateOtpPostValidation(Long customerSequenceNumber) throws InvalidInputParametersException {
		customerDaoImpl.updateOTPPostValidation(customerSequenceNumber);
	}

	@Override
	@Transactional
	@Modifying
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(AuthenticationRequest authenticationRequest)
			throws InvalidInputParametersException, ParseException {

		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		UserDetails userDetails = null;
		String customUserName = null;
		if (authenticationRequest.getIsToValidatePassword()) {

			userDetails = getUserDetailsForUserId(authenticationRequest);

			customUserName = authenticationRequest.getEmailId();

		} else if (authenticationRequest.getIsOTPAuthentication()) {

			userDetails = getUserDetailsForCustomUserName(authenticationRequest);

			customUserName = authenticationRequest.getCustomUserName();

		} else {
			authenticationRequest.setExpirationTime(
					authenticationRequest.getExpirationTime() != null ? authenticationRequest.getExpirationTime()
							: defaultTokenExpirationTime);
			userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getCustomUserName());
			customUserName = authenticationRequest.getCustomUserName();
		}

		CustomerBasicDetailsEntity customerBasicData = userDetailsService.getCustomerBasicDetailsEntity();
		validateCredentials(authenticationRequest, userDetails, customerBasicData);

		if (customUserName == null)
			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.badCredentialsErrorCode,
					CustomerBasicDetailsConstants.badCredentialsErrorMessage);

		if (customerBasicData != null && customerBasicData.getCustomerSequenceNumber() != null) {
			authenticationResponse.setCustomerSeqNumber(customerBasicData.getCustomerSequenceNumber());
			fetchExistTokenIfPresent(authenticationResponse, authenticationRequest);
		}

		if (authenticationResponse != null && authenticationResponse.getTokenStatus() != null
				&& authenticationResponse.getTokenStatus().equalsIgnoreCase(Constants.errorCode403)) {

			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.badCredentialsErrorCode,
					CustomerBasicDetailsConstants.badCredentialsErrorMessage);
		} else if (authenticationResponse != null && authenticationResponse.getTokenStatus() != null
				&& (authenticationResponse.getTokenStatus().equalsIgnoreCase(Constants.statusTokenLongLived)
						|| authenticationResponse.getTokenStatus().equalsIgnoreCase(Constants.statusTokenValid))) {
			authenticationResponse.setRolesAndPermissions(customerBasicData.getRolesAndPermissions().get(0));
			return ResponseEntity.ok(authenticationResponse);
		}

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customUserName,
				authenticationRequest.getPassword(), Collections.emptyList()));

		final String jwt = jwtTokenUtil.generateToken(userDetails.getUsername(),
				authenticationRequest.getExpirationTime());
		authenticationResponse.setToken(jwt);
		authenticationResponse.setTokenStatus(Constants.statusTokenValid);
		updateTokenDetails(authenticationRequest.getExpirationTime(), authenticationRequest.isRememberMeSelected(), jwt,
				authenticationResponse.getCustomerSeqNumber());
		RolesAndPermissions rolesAndPermissions = null;
		if (customerBasicData != null && !customerBasicData.getRolesAndPermissions().isEmpty()
				&& customerBasicData.getRolesAndPermissions().get(0) != null) {
			rolesAndPermissions = customerBasicData.getRolesAndPermissions().get(0);
		}

		return ResponseEntity.ok(new AuthenticationResponse(jwt, Constants.tokenType,
				jwtTokenUtil.extractExpiration(jwt), rolesAndPermissions, Constants.statusTokenValid));
	}

	private UserDetails getUserDetailsForUserId(AuthenticationRequest authenticationRequest) {
		UserDetails userDetails;
		userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmailId());
		String userPassword = userDetailsService.getCustomerBasicDetailsEntity() != null
				? userDetailsService.getCustomerBasicDetailsEntity().getCustomerPassword()
				: "";
		authenticationRequest.setPassword(userPassword);
		authenticationRequest.setExpirationTime(defaultTokenExpirationTime != null ? defaultTokenExpirationTime : 15);
		return userDetails;
	}

	private UserDetails getUserDetailsForCustomUserName(AuthenticationRequest authenticationRequest)
			throws InvalidInputParametersException {
		UserDetails userDetails;
		userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getCustomUserName());
		CustomerBasicDetailsEntity detailsLoadedFromDataBase = userDetailsService.getCustomerBasicDetailsEntity();

		validateOTPDetails(detailsLoadedFromDataBase, authenticationRequest);
		updateOtpPostValidation(detailsLoadedFromDataBase.getCustomerSequenceNumber());
		authenticationRequest.setPassword(detailsLoadedFromDataBase.getCustomerPassword());
		authenticationRequest.setExpirationTime(defaultTokenExpirationTime != null ? defaultTokenExpirationTime : 15);
		return userDetails;
	}

	private void validateOTPDetails(CustomerBasicDetailsEntity customerBasicDetailsEntity,
			AuthenticationRequest authenticationRequest) throws InvalidInputParametersException {

		if (customerBasicDetailsEntity.getOtp() != null
				&& !customerBasicDetailsEntity.getOtp().equalsIgnoreCase(authenticationRequest.getoTP())) {

			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validateInvalidOtpErrorCode,
					CustomerBasicDetailsConstants.validateInvalidOtp);

		}

		if (customerBasicDetailsEntity.getOtp() != null && customerBasicDetailsEntity.getOtpExpiryAt() != null
				&& customerBasicDetailsEntity.getOtpExpiryAt().compareTo(new Date()) < 0) {

			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validateOTPExpiredErrorCode,
					CustomerBasicDetailsConstants.validateOTPExpired);

		}

	}

	private void validateCredentials(AuthenticationRequest authenticationRequest, UserDetails userDetails,
			CustomerBasicDetailsEntity customerBasicData) throws InvalidInputParametersException {
		if (userDetails != null && !authenticationRequest.getIsToValidatePassword() && customerBasicData != null
				&& !(encryptionUtility.matchBCryptDecryptedPassword(authenticationRequest.getPassword(),
						userDetails.getPassword()))
				&& !(authenticationRequest.getIsOTPAuthentication() && authenticationRequest.getoTP() != null)) {
			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.badCredentialsErrorCode,
					CustomerBasicDetailsConstants.badCredentialsErrorMessage);

		}
	}
}
