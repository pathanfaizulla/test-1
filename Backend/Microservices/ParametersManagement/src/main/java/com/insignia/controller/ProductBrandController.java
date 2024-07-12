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

import com.insignia.model.ProductBrandRequest;
import com.insignia.model.ProductBrandResponse;
import com.insignia.serviceInterface.ProductBrandServiceInterface;
import com.insignia.validations.CommonValidation;
import com.insignia.validations.ParametersManagementValidations;

@CrossOrigin
@RestController
@RequestMapping("/productBrand")
public class ProductBrandController {

	@Autowired
	private ProductBrandServiceInterface productBrandServiceInterface;

	@PostMapping("/saveProductBrand")
	public ResponseEntity<?> saveProductBrand(@RequestBody ProductBrandRequest productBrandRequest) {
		try {
			ParametersManagementValidations.validateBrandName(productBrandRequest.getBrandName(),
					ParametersManagementConstant.brandNameLength, true);
			validationsForProductBrand(productBrandRequest);

			return ResponseEntity.ok(productBrandServiceInterface.saveProductBrand(productBrandRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductBrandResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductBrandResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductBrandResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PutMapping("/updateProductBrand")
	public ResponseEntity<?> updateProductBrand(@RequestBody ProductBrandRequest productBrandRequest) {
		try {
			ParametersManagementValidations.validateBrandName(productBrandRequest.getBrandName(),
					ParametersManagementConstant.brandNameLength, false);
			validationsForProductBrand(productBrandRequest);
			return ResponseEntity.ok(productBrandServiceInterface.updateProductBrand(productBrandRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductBrandResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductBrandResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductBrandResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PostMapping("/deleteProductBrand")
	public ResponseEntity<?> deleteProductBrand(@RequestBody ProductBrandRequest productBrandRequest) {
		try {
			productBrandServiceInterface.deleteProductBrand(productBrandRequest);
			return ResponseEntity.ok(ParametersManagementConstant.successMessageForProductBrandDeleteMethod);
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductBrandResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductBrandResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductBrandResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@PostMapping("/getAllProductBrands")
	public ResponseEntity<?> getAllProductBrands(@RequestBody ProductBrandRequest productBrandRequest) {
		try {
			return ResponseEntity.ok(productBrandServiceInterface.getAllProductBrand(productBrandRequest));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductBrandResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductBrandResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	private void validationsForProductBrand(ProductBrandRequest productBrandRequest)
			throws InvalidInputParametersException {
		CommonValidation.validateApplicatinIdAndTenantId(productBrandRequest.getApplicationId(),
				productBrandRequest.getTenantId());
		ParametersManagementValidations.validateBrandDescription(productBrandRequest.getBrandDescription(),
				ParametersManagementConstant.brandDescriptionLength);
	}
}
