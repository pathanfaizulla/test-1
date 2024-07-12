package com.insignia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insignia.constant.ApplicationStringConstant;
import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.ApplicationRequest;
import com.insignia.model.ApplicationResponse;
import com.insignia.serviceInterface.ApplicationServiceInterface;
import com.insignia.validations.ApplicationDetailsValidation;

@RestController
public class ApplicationController {

	@Autowired
	private ApplicationServiceInterface serviceRepo;

	@PostMapping("/saveApplication")
	public ResponseEntity<?> saveApplicationDetails(@RequestBody ApplicationRequest applicationRequest)
			throws InvalidInputParametersException {
		try {

			applicationDetailsValidations(applicationRequest);

			return ResponseEntity.ok(serviceRepo.saveApplicationDetails(applicationRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApplicationResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApplicationResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApplicationResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PutMapping("/updateApplication")
	public ResponseEntity<?> updateApplicationDetails(@RequestBody ApplicationRequest applicationRequest) {
		try {

			applicationDetailsValidations(applicationRequest);

			return ResponseEntity.ok(serviceRepo.updateApplicationDetails(applicationRequest));

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApplicationResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApplicationResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApplicationResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@DeleteMapping("/deleteApplication/{applicationId}/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> deleteByApplicationId(@PathVariable Integer applicationId,
			@PathVariable Long customerSequenceNumber, @PathVariable Integer expirationDuration) {
		try {
			serviceRepo.deleteByApplicationId(applicationId, customerSequenceNumber, expirationDuration);
			return ResponseEntity.ok("Application Successfully deleted");
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApplicationResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApplicationResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@GetMapping("/getApplicationDetails")

	public ResponseEntity<?> getApplicationDetails(@RequestBody ApplicationRequest applicationRequest) {
		try {

			return ResponseEntity.ok(serviceRepo.getApplicationDetails(applicationRequest.getApplicationId(),
					applicationRequest.getCustomerSequenceNumber(), applicationRequest.getExpirationDuration()));
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApplicationResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApplicationResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	private void applicationDetailsValidations(ApplicationRequest applicationRequest)
			throws InvalidInputParametersException {
		ApplicationDetailsValidation.validateApplicationName(applicationRequest.getApplicationName(),
				ApplicationStringConstant.applicationNameLength);
		ApplicationDetailsValidation.validateApplicationDescrption(applicationRequest.getApplicationDescription(),
				ApplicationStringConstant.applicationDescriptionLength);
	}

}
