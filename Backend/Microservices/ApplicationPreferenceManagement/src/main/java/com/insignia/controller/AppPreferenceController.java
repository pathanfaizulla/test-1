package com.insignia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insignia.constant.AppPreferenceConstant;
import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.AppPreferenceRequest;
import com.insignia.model.ApplicationPreferenceRequest;
import com.insignia.model.ApplicationPreferenceResponse;
import com.insignia.serviceInterface.AppPreferenceServiceInterface;
import com.insignia.validations.AppPreferenceDetailsValidation;

@RestController
public class AppPreferenceController {

	@Autowired
	private AppPreferenceServiceInterface serviceRepo;

	@PostMapping("/saveApplicationAndAppPreference")
	public ResponseEntity<?> saveApplicationDetails(
			@RequestBody ApplicationPreferenceRequest applicationPreferenceRequest) {

		try {
			validationForRequestParameters(applicationPreferenceRequest);
			return ResponseEntity.ok(serviceRepo.saveApplicationPreferenceDetails(applicationPreferenceRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApplicationPreferenceResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApplicationPreferenceResponse(
					ex.getErrorCode(), ex.getStrMsg()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApplicationPreferenceResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@GetMapping("/getApplicationAndAppPreferenceDetails")

	public ResponseEntity<?> getApplicationPreferenceDetails(
			@RequestBody ApplicationPreferenceRequest applicationAndAppPreferenceRequest) {
		try {
			return ResponseEntity.ok(serviceRepo.getApplicationPreferenceDetails(applicationAndAppPreferenceRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApplicationPreferenceResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApplicationPreferenceResponse(
					ex.getErrorCode(), ex.getStrMsg()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApplicationPreferenceResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	private void validationForRequestParameters(ApplicationPreferenceRequest applicationPreferenceRequest)
			throws InvalidInputParametersException {
		AppPreferenceDetailsValidation.validateApplicationName(applicationPreferenceRequest.getApplicationName(),
				AppPreferenceConstant.ApplicationNameLength);

		if (applicationPreferenceRequest.getAppPreferenceRequestList() != null) {

			for (AppPreferenceRequest appPreferenceRequest : applicationPreferenceRequest
					.getAppPreferenceRequestList()) {

				AppPreferenceDetailsValidation.validatePreferenceType(appPreferenceRequest.getPreferenceType(),
						AppPreferenceConstant.PreferenceTypeLength);
				AppPreferenceDetailsValidation.validatePreferenceValue(appPreferenceRequest.getPreferenceValue(),
						AppPreferenceConstant.PreferenceValueLength);
			}
		}
	}
}
