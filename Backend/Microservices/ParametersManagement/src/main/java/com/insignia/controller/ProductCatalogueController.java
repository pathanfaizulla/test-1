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

import com.insignia.model.ProductCatalogueRequest;
import com.insignia.model.ProductCatalogueResponse;
import com.insignia.serviceInterface.ProductCatalogueServiceInterface;
import com.insignia.validations.CommonValidation;
import com.insignia.validations.ParametersManagementValidations;

@CrossOrigin
@RestController
@RequestMapping("/productCatalogue")
public class ProductCatalogueController {

	@Autowired
	private ProductCatalogueServiceInterface productCatalogueServiceInterface;

	@PostMapping("/saveProductCatalogue")
	public ResponseEntity<?> saveProductCatalogue(@RequestBody ProductCatalogueRequest productCatalogueRequest) {
		try {
			ParametersManagementValidations.validateCatalogueName(productCatalogueRequest.getCatalogueName(),
					ParametersManagementConstant.catalogueNameLength, true);
			validationsForProductCatalogue(productCatalogueRequest);

			return ResponseEntity.ok(productCatalogueServiceInterface.saveProductCatalogue(productCatalogueRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductCatalogueResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductCatalogueResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductCatalogueResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PutMapping("/updateProductCatalogue")
	public ResponseEntity<?> updateProductCatalogue(@RequestBody ProductCatalogueRequest productCatalogueRequest) {
		try {
			ParametersManagementValidations.validateCatalogueName(productCatalogueRequest.getCatalogueName(),
					ParametersManagementConstant.catalogueNameLength, false);
			validationsForProductCatalogue(productCatalogueRequest);
			return ResponseEntity.ok(productCatalogueServiceInterface.updateProductCatalogue(productCatalogueRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductCatalogueResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductCatalogueResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductCatalogueResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PostMapping("/deleteProductCatalogue")
	public ResponseEntity<?> deleteProductCatalogue(@RequestBody ProductCatalogueRequest productCatalogueRequest) {
		try {
			productCatalogueServiceInterface.deleteProductCatalogue(productCatalogueRequest);
			return ResponseEntity.ok(ParametersManagementConstant.successMessageForProductCatalogueDeleteMethod);
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductCatalogueResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductCatalogueResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductCatalogueResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@PostMapping("/getAllProductCatalogue")
	public ResponseEntity<?> getAllProductCatalogue(@RequestBody ProductCatalogueRequest productCatalogueRequest) {
		try {
			return ResponseEntity.ok(productCatalogueServiceInterface.getAllProductCatalogue(productCatalogueRequest));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductCatalogueResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductCatalogueResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	private void validationsForProductCatalogue(ProductCatalogueRequest productCatalogueRequest)
			throws InvalidInputParametersException {
		CommonValidation.validateApplicatinIdAndTenantId(productCatalogueRequest.getApplicationId(),
				productCatalogueRequest.getTenantId());

		ParametersManagementValidations.validateCatalogueDescription(productCatalogueRequest.getCatalogueDescription(),
				ParametersManagementConstant.catalogueDescriptionLength);
	}
}
