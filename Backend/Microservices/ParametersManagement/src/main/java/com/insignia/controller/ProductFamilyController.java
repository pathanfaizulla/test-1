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
import com.insignia.model.ProductFamilyRequest;
import com.insignia.model.ProductFamilyResponse;
import com.insignia.serviceInterface.ProductFamilyServiceInterface;
import com.insignia.validations.CommonValidation;
import com.insignia.validations.ParametersManagementValidations;

@CrossOrigin
@RestController
@RequestMapping("/productFamily")
public class ProductFamilyController {

	@Autowired
	private ProductFamilyServiceInterface productFamilyServiceInterface;

	@PostMapping("/saveProductFamily")
	public ResponseEntity<?> saveProductFamily(@RequestBody ProductFamilyRequest productFamilyRequest) {
		try {

			ParametersManagementValidations.validateFamilyName(productFamilyRequest.getFamilyName(),
					ParametersManagementConstant.familyNameLength, true);
			validationsForProductFamily(productFamilyRequest);

			return ResponseEntity.ok(productFamilyServiceInterface.saveProductFamily(productFamilyRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductFamilyResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductFamilyResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductFamilyResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PutMapping("/updateProductFamily")
	public ResponseEntity<?> updateProductFamily(@RequestBody ProductFamilyRequest productFamilyRequest) {
		try {
			ParametersManagementValidations.validateFamilyName(productFamilyRequest.getFamilyName(),
					ParametersManagementConstant.familyNameLength, false);
			validationsForProductFamily(productFamilyRequest);
			return ResponseEntity.ok(productFamilyServiceInterface.updateProductFamily(productFamilyRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductFamilyResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductFamilyResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductFamilyResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PostMapping("/deleteProductFamily")
	public ResponseEntity<?> deleteProductFamily(@RequestBody ProductFamilyRequest productFamilyRequest) {
		try {
			productFamilyServiceInterface.deleteProductFamily(productFamilyRequest);
			return ResponseEntity.ok(ParametersManagementConstant.successMessageForProductFamilyDeleteMethod);
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductFamilyResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductFamilyResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductFamilyResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@PostMapping("/getAllProductFamily")
	public ResponseEntity<?> getAllProductFamily(@RequestBody ProductFamilyRequest productFamilyRequest) {
		try {
			return ResponseEntity.ok(productFamilyServiceInterface.getAllProductFamily(productFamilyRequest));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductFamilyResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductFamilyResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	private void validationsForProductFamily(ProductFamilyRequest productFamilyRequest)
			throws InvalidInputParametersException {
		CommonValidation.validateApplicatinIdAndTenantId(productFamilyRequest.getApplicationId(),
				productFamilyRequest.getTenantId());

		ParametersManagementValidations.validateFamilyDescription(productFamilyRequest.getFamilyDescription(),
				ParametersManagementConstant.familyDescriptionLength);
	}
}
