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

import com.insignia.model.ProductColourRequest;
import com.insignia.model.ProductColourResponse;
import com.insignia.serviceInterface.ProductColourServiceInterface;
import com.insignia.validations.CommonValidation;
import com.insignia.validations.ParametersManagementValidations;

@CrossOrigin
@RestController
@RequestMapping("/productColour")
public class ProductColourController {

	@Autowired
	private ProductColourServiceInterface productColourServiceInterface;

	@PostMapping("/saveProductColour")
	public ResponseEntity<?> saveProductColour(@RequestBody ProductColourRequest productColourRequest) {
		try {
			ParametersManagementValidations.validateColourName(productColourRequest.getColourName(),
					ParametersManagementConstant.colourNameLength, true);
			ParametersManagementValidations.validateHexCode(productColourRequest.getHexCode(),
					ParametersManagementConstant.hexCodeLength);
			validationsForProductColour(productColourRequest);

			return ResponseEntity.ok(productColourServiceInterface.saveProductColour(productColourRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductColourResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductColourResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductColourResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PutMapping("/updateProductColour")
	public ResponseEntity<?> updateProductColour(@RequestBody ProductColourRequest productColourRequest) {
		try {
			ParametersManagementValidations.validateColourName(productColourRequest.getColourName(),
					ParametersManagementConstant.colourNameLength, false);
			ParametersManagementValidations.validateHexCode(productColourRequest.getHexCode(),
					ParametersManagementConstant.hexCodeLength);
			validationsForProductColour(productColourRequest);
			return ResponseEntity.ok(productColourServiceInterface.updateProductColour(productColourRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductColourResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductColourResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductColourResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PostMapping("/deleteProductColour")
	public ResponseEntity<?> deleteProductColour(@RequestBody ProductColourRequest productColourRequest) {
		try {
			productColourServiceInterface.deleteProductColour(productColourRequest);
			return ResponseEntity.ok(ParametersManagementConstant.successMessageForProductColourDeleteMethod);
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductColourResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductColourResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductColourResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@PostMapping("/getAllProductColours")
	public ResponseEntity<?> getAllProductColours(@RequestBody ProductColourRequest productColourRequest) {
		try {
			return ResponseEntity.ok(productColourServiceInterface.getAllProductColour(productColourRequest));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductColourResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductColourResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	private void validationsForProductColour(ProductColourRequest productColourRequest)
			throws InvalidInputParametersException {
		CommonValidation.validateApplicatinIdAndTenantId(productColourRequest.getApplicationId(),
				productColourRequest.getTenantId());
		ParametersManagementValidations.validateColourDescription(productColourRequest.getColourDescription(),
				ParametersManagementConstant.colourDescriptionLength);
	}
}
