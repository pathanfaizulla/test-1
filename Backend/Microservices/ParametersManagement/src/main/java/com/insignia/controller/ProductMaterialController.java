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
import com.insignia.model.ProductMaterialRequest;
import com.insignia.model.ProductMaterialResponse;
import com.insignia.serviceInterface.ProductMaterialServiceInterface;
import com.insignia.validations.CommonValidation;
import com.insignia.validations.ParametersManagementValidations;

@CrossOrigin
@RestController
@RequestMapping("/productMaterial")
public class ProductMaterialController {

	@Autowired
	private ProductMaterialServiceInterface productMaterialServiceInterface;

	@PostMapping("/saveProductMaterial")
	public ResponseEntity<?> saveProductMaterial(@RequestBody ProductMaterialRequest productMaterialRequest) {
		try {
			ParametersManagementValidations.validateMaterialName(productMaterialRequest.getMaterialName(),
					ParametersManagementConstant.materialNameLength, true);
			validationsForProductMaterial(productMaterialRequest);

			return ResponseEntity.ok(productMaterialServiceInterface.saveProductMaterial(productMaterialRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductMaterialResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductMaterialResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductMaterialResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PutMapping("/updateProductMaterial")
	public ResponseEntity<?> updateProductMaterial(@RequestBody ProductMaterialRequest productMaterialRequest) {
		try {
			ParametersManagementValidations.validateMaterialName(productMaterialRequest.getMaterialName(),
					ParametersManagementConstant.materialNameLength, false);
			validationsForProductMaterial(productMaterialRequest);
			return ResponseEntity.ok(productMaterialServiceInterface.updateProductMaterial(productMaterialRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductMaterialResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductMaterialResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductMaterialResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PostMapping("/deleteProductMaterial")
	public ResponseEntity<?> deleteProductMaterial(@RequestBody ProductMaterialRequest productMaterialRequest) {
		try {
			productMaterialServiceInterface.deleteProductMaterial(productMaterialRequest);
			return ResponseEntity.ok(ParametersManagementConstant.successMessageForProductMaterialDeleteMethod);
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductMaterialResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductMaterialResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductMaterialResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@PostMapping("/getAllProductMaterials")
	public ResponseEntity<?> getAllProductMaterials(@RequestBody ProductMaterialRequest productMaterialRequest) {
		try {
			return ResponseEntity.ok(productMaterialServiceInterface.getAllProductMaterials(productMaterialRequest));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ProductMaterialResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductMaterialResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	private void validationsForProductMaterial(ProductMaterialRequest productMaterialRequest)
			throws InvalidInputParametersException {
		CommonValidation.validateApplicatinIdAndTenantId(productMaterialRequest.getApplicationId(),
				productMaterialRequest.getTenantId());

		ParametersManagementValidations.validateMaterialDescription(productMaterialRequest.getMaterialDescription(),
				ParametersManagementConstant.materialDescriptionLength);
	}
}
