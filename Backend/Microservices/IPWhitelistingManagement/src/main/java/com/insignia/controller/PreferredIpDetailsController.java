package com.insignia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insignia.constants.CommonConstant;
import com.insignia.constants.PreferredIpDetailsContants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.PreferredIpDetailsRequest;
import com.insignia.model.PreferredIpDetailsResponse;
import com.insignia.serviceInterface.PreferredIpDetailsServiceInterface;
import com.insignia.validations.PreferredIpDetailsValidation;

@CrossOrigin
@RestController
@RequestMapping("/PreferredIpDetails")
public class PreferredIpDetailsController {

	@Autowired
	private PreferredIpDetailsServiceInterface preferredIpDetailsServiceInterface;

	@PostMapping("/savePreferredIpDetails")
	public ResponseEntity<?> savePreferredIpDetails(@RequestBody PreferredIpDetailsRequest preferredIpDetailsRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		try {

			validationForIpDetails(preferredIpDetailsRequest);

			return ResponseEntity
					.ok(preferredIpDetailsServiceInterface.savePreferredIpDetails(preferredIpDetailsRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new PreferredIpDetailsResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PreferredIpDetailsResponse(
					CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PreferredIpDetailsResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PostMapping("/deletePreferredIpDetails")
	public ResponseEntity<?> deletePreferredIpDetails(@RequestBody PreferredIpDetailsRequest preferredIpDetailsRequest)
			throws TokenExpiredException {
		try {
			preferredIpDetailsServiceInterface.deletePreferredIpDetails(preferredIpDetailsRequest);
			return ResponseEntity.ok(PreferredIpDetailsContants.successMessageForDelete);
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new PreferredIpDetailsResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PreferredIpDetailsResponse(
					CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PreferredIpDetailsResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PostMapping("/getAllPreferredIpDetails")
	public ResponseEntity<?> getAllPreferredIpDetails(@RequestBody PreferredIpDetailsRequest preferredIpDetailsRequest)
			throws TokenExpiredException {
		try {
			return ResponseEntity
					.ok(preferredIpDetailsServiceInterface.getAllPreferredIpDetails(preferredIpDetailsRequest));

		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PreferredIpDetailsResponse(
					CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PreferredIpDetailsResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	private void validationForIpDetails(PreferredIpDetailsRequest preferredIpDetailsRequest)
			throws InvalidInputParametersException {
		preferredIpDetailsRequest
				.setIpType(PreferredIpDetailsValidation.validateIpDetails(preferredIpDetailsRequest.getIpDetails()));
	}
}