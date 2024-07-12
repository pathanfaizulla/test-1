package com.insignia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insignia.constant.ParametersManagementConstant;
import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.CurrencyDetailsRequest;
import com.insignia.model.CurrencyDetailsResponse;
import com.insignia.serviceInterface.CurrencyDetailsServiceInterface;
import com.insignia.validations.CommonValidation;
import com.insignia.validations.ParametersManagementValidations;

@CrossOrigin
@RestController
@RequestMapping("/currencyDetails")
public class CurencyDetailsController {

	@Autowired
	private CurrencyDetailsServiceInterface curencyDetailsServiceInterface;

	@PostMapping("/saveCurrencyDetails")
	public ResponseEntity<?> saveCurrencyDetails(@RequestBody CurrencyDetailsRequest currencyDetailsRequest) {
		try {

			ParametersManagementValidations.validateCurrencyName(currencyDetailsRequest.getCurrencyName(),
					ParametersManagementConstant.currencytNameLength, true);
			 validationsForCurrencyDetails(currencyDetailsRequest);

			return ResponseEntity.ok(curencyDetailsServiceInterface.saveCurrencyDetails(currencyDetailsRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CurrencyDetailsResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new CurrencyDetailsResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CurrencyDetailsResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PutMapping("/updateCurrencyDetails")
	public ResponseEntity<?> updateCurrencyDetails(@RequestBody CurrencyDetailsRequest currencyDetailsRequest) {
		try {
			 ParametersManagementValidations.validateCurrencyName(currencyDetailsRequest.getCurrencyName(),
			 ParametersManagementConstant.currencytNameLength, false);
			 validationsForCurrencyDetails(currencyDetailsRequest);
			return ResponseEntity.ok(curencyDetailsServiceInterface.updateCurrencyDetails(currencyDetailsRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CurrencyDetailsResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new CurrencyDetailsResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CurrencyDetailsResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PostMapping("/deleteCurrencyDetails")
	public ResponseEntity<?> deleteCurrencyDetails(@RequestBody CurrencyDetailsRequest currencyDetailsRequest) {
		try {
			curencyDetailsServiceInterface.deleteCurrencyDetails(currencyDetailsRequest);
			return ResponseEntity.ok(ParametersManagementConstant.successMessageForCurrencyDetailsDeleteMethod);
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CurrencyDetailsResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new CurrencyDetailsResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CurrencyDetailsResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@PostMapping("/getAllCurrencyDetails")
	public ResponseEntity<?> getAllCurrencyDetails(@RequestBody CurrencyDetailsRequest currencyDetailsRequest) {
		try {
			return ResponseEntity.ok(curencyDetailsServiceInterface.getAllCurrencyDetails(currencyDetailsRequest));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new CurrencyDetailsResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CurrencyDetailsResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	private void validationsForCurrencyDetails(CurrencyDetailsRequest currencyDetailsRequest)
			throws InvalidInputParametersException {
		CommonValidation.validateApplicatinIdAndTenantId(currencyDetailsRequest.getApplicationId(), currencyDetailsRequest.getTenantId());
		
		ParametersManagementValidations.validateCurrencyDescription(currencyDetailsRequest.getCurrencyDescription(),
				ParametersManagementConstant.currencyDescriptionLength);
		ParametersManagementValidations.validateCurrencyCode(currencyDetailsRequest.getCurrencyCode(),
				ParametersManagementConstant.currencyCodeLength);
		ParametersManagementValidations.validateBaseRateCurrency(currencyDetailsRequest.getBaseRateCurrency(),
				ParametersManagementConstant.baseRateCurrencyLength);
		ParametersManagementValidations.validateBaseRate(currencyDetailsRequest.getBaseRate(),
				ParametersManagementConstant.baseRateLength);
		
	}
}
