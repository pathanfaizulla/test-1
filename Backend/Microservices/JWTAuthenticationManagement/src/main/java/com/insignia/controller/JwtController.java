package com.insignia.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.insignia.constants.CommonConstant;
import com.insignia.constants.CustomerBasicDetailsConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.RoleNotFoundException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.AuthenticationRequest;
import com.insignia.model.AuthenticationResponse;
import com.insignia.security.EncryptionUtility;
import com.insignia.serviceInterface.IJwtService;
import com.insignia.util.Constants;
import com.insignia.validations.CommonValidation;
import com.insignia.validations.CustomerBasicDetailsValidation;

@RestController
@CrossOrigin
public class JwtController {

	@Autowired
	private IJwtService service;

	@Value("${errorCodes.500}")
	private String internalServerError;

	@Value("${errorCodes.403}")
	private String badCredentials;

	@Value("${errorCodes.406}")
	private String decryptionError;

	@Value("${errorCodes.407}")
	private String unexpectedError;

	@Autowired
	public RestTemplate restTemplate;

	@Autowired
	public EncryptionUtility encryptionUtility;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
		

		try {

			validateLoginCredentialSyntax(authenticationRequest);

			return service.createAuthenticationToken(authenticationRequest);

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AuthenticationResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (BadCredentialsException | UsernameNotFoundException e) {

			if (e.getCause() != null && e.getCause() instanceof RoleNotFoundException) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new AuthenticationResponse(CustomerBasicDetailsConstants.validateRoleErrorCode,
								CustomerBasicDetailsConstants.validateRole));
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new AuthenticationResponse(Constants.errorCode403, badCredentials));

		} catch (Exception e) {

			if (e.getClass() != null && (e.getClass().equals(InvalidKeyException.class)
					|| e.getClass().equals(NoSuchAlgorithmException.class)
					|| e.getClass().equals(NoSuchPaddingException.class)
					|| e.getClass().equals(IllegalBlockSizeException.class)
					|| e.getClass().equals(BadPaddingException.class))) {

				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new AuthenticationResponse(Constants.errorCode406, decryptionError));
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new AuthenticationResponse(Constants.errorCode407, unexpectedError));
		}

	}

	@PutMapping("/updatePassword")
	public ResponseEntity<?> updatePassword(@RequestBody AuthenticationRequest authenticationRequest) {
		try {

			CustomerBasicDetailsValidation.validateNewPassword(authenticationRequest.getNewPassword(),
					Constants.passwordMaxlength, Constants.passwordMinlength);

			service.updatePassword(authenticationRequest);

			return ResponseEntity.ok(Constants.successMessageForUpdate);

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AuthenticationResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AuthenticationResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthenticationResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@GetMapping("/checkTokenValidity/{customerSequenceNumber}")
	public ResponseEntity<?> checkTokenValidity(@PathVariable Long customerSequenceNumber) {
		try {

			return ResponseEntity.ok(service.checkTokenValidity(customerSequenceNumber));

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AuthenticationResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthenticationResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	private void validateLoginCredentialSyntax(AuthenticationRequest authenticationRequest)
			throws InvalidInputParametersException {

		if (authenticationRequest.getIsToValidatePassword()) {

			CustomerBasicDetailsValidation.validateCustomerEmail(authenticationRequest.getEmailId(),
					Constants.customerEmailLength);

		} else if (authenticationRequest.getIsOTPAuthentication()) {

			if (authenticationRequest.getoTP() == null) {
				throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validateOTPErrorCode,  CustomerBasicDetailsConstants.validateOTP);

			} else if (String.valueOf(authenticationRequest.getoTP()).length() != Constants.otpLength) {
				throw new InvalidInputParametersException(CustomerBasicDetailsConstants.validateOTPLengthErrorCode, CustomerBasicDetailsConstants.validateOTPLength);

			}

		} else {
			CustomerBasicDetailsValidation.validateUserId(authenticationRequest.getUserId(), Constants.userIdlength);

			CommonValidation.validateApplicatinIdAndTenantId(authenticationRequest.getApplicationId(),
					authenticationRequest.getTenantId());
			CustomerBasicDetailsValidation.validatePassword(authenticationRequest.getPassword(),
					Constants.passwordMaxlength);

		}
	}

}
