package com.insignia.controller;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insignia.conistant.OtpConstants;
import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.model.AuthenticationRequest;
import com.insignia.model.AuthenticationResponse;
import com.insignia.serviceInterface.OtpServiceInterface;
import com.insignia.validations.CommonValidation;
import com.insignia.validations.CustomerBasicDetailsValidation;

@RestController
@CrossOrigin
public class OtpController {

	@Autowired
	private OtpServiceInterface otpServiceInterface;

	@PostMapping("/otpService")
	public ResponseEntity<?> generateAndSaveOtp(@RequestBody AuthenticationRequest authenticationRequest)
			throws MessagingException {
		try {
			validationForOtpGeneration(authenticationRequest);

			return ResponseEntity.ok(otpServiceInterface.saveAndUpdateCustomer(authenticationRequest));

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AuthenticationResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthenticationResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	private void validationForOtpGeneration(AuthenticationRequest authenticationRequest)
			throws InvalidInputParametersException {
		// Validating user id same as email because in otp case, email id will be used
		// as used id too.
		CustomerBasicDetailsValidation.validateCustomerEmail(authenticationRequest.getUserId(),
				OtpConstants.emaillength);
		CommonValidation.validateApplicatinIdAndTenantId(authenticationRequest.getApplicationId(),
				authenticationRequest.getTenantId());
		
		CustomerBasicDetailsValidation.validateCustomerEmail(authenticationRequest.getEmail(),
				OtpConstants.emaillength);
		CustomerBasicDetailsValidation.validateCustomerId(authenticationRequest.getCustomerId(),
				OtpConstants.customerIdLength, false);
	}

}
