package com.insignia.controller;

import com.insignia.constant.ProductConstants;
import com.insignia.constants.CommonConstant;
import com.insignia.constants.ProductValidatorConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.*;
import com.insignia.service.ProductServiceInterface;
import com.insignia.validations.CommonValidation;
import com.insignia.validations.ProductValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

	private final ProductServiceInterface productService;

	@PostMapping("/addProduct")
	public ResponseEntity<?> addProduct(@RequestBody ProductManagementRequest productManagementRequest) {
		try {

			ProductRequest productRequest = productManagementRequest.getProductRequest();
			if (productRequest == null) {
				throw new InvalidInputParametersException(
						ProductValidatorConstants.validateProductRequestDetailsErrorCode,
						ProductValidatorConstants.validateProductRequestDetailsMessage);
			}
			CommonValidation.validateApplicatinIdAndTenantId(productRequest.getApplicationId(), productRequest.getTenantId());
			ProductValidator.validateProductId(productRequest.getProductId(), ProductConstants.productIdLength, false);
			validationForProductRequest(productRequest);

			return ResponseEntity.ok(productService.addProduct(productManagementRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PutMapping("/updateProduct")
	public ResponseEntity<?> updateProduct(@RequestBody ProductManagementRequest productManagementRequest) {
		try {
			ProductRequest productRequest = productManagementRequest.getProductRequest();
			if (productRequest == null) {
				throw new InvalidInputParametersException(
						ProductValidatorConstants.validateProductRequestDetailsErrorCode,
						ProductValidatorConstants.validateProductRequestDetailsMessage);
			}
			CommonValidation.validateApplicatinIdAndTenantId(productRequest.getApplicationId(), productRequest.getTenantId());
			ProductValidator.validateProductId(productRequest.getProductId(), ProductConstants.productIdLength, true);
			validationForProductRequest(productRequest);
			return ResponseEntity.ok(productService.updateProduct(productManagementRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PostMapping("/deleteByProductId")
	public ResponseEntity<?> deleteByProductId(@RequestBody ProductManagementRequest productManagementRequest) {
		try {
			ProductRequest productRequest = productManagementRequest.getProductRequest();
			if (productRequest == null) {
				throw new InvalidInputParametersException(
						ProductValidatorConstants.validateProductRequestDetailsErrorCode,
						ProductValidatorConstants.validateProductRequestDetailsMessage);
			}
			CommonValidation.validateApplicatinIdAndTenantId(productRequest.getApplicationId(), productRequest.getTenantId());
			productService.deleteProductById(productManagementRequest);
			return ResponseEntity.ok(ProductConstants.successMessage);
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@PostMapping("/getProductById")
	public ResponseEntity<?> getProductById(@RequestBody ProductManagementRequest productManagementRequest) {
		try {
			ProductRequest productRequest = productManagementRequest.getProductRequest();
			if (productRequest == null) {
				throw new InvalidInputParametersException(
						ProductValidatorConstants.validateProductRequestDetailsErrorCode,
						ProductValidatorConstants.validateProductRequestDetailsMessage);
			}
			CommonValidation.validateApplicatinIdAndTenantId(productRequest.getApplicationId(), productRequest.getTenantId());
			return ResponseEntity.ok(productService.getProductById(productManagementRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@PostMapping("/getProductDetailsById")
	public ResponseEntity<?> getProductDetailsById(@RequestBody ProductManagementRequest productManagementRequest) {
		try {
			ProductRequest productRequest = productManagementRequest.getProductRequest();
			if (productRequest == null) {
				throw new InvalidInputParametersException(
						ProductValidatorConstants.validateProductRequestDetailsErrorCode,
						ProductValidatorConstants.validateProductRequestDetailsMessage);
			}
			CommonValidation.validateApplicatinIdAndTenantId(productRequest.getApplicationId(), productRequest.getTenantId());
			return ResponseEntity.ok(productService.getProductDetailsById(productManagementRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}
	
	@GetMapping("/getAllProducts/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> getAllProducts(@PathVariable Long customerSequenceNumber,
			@PathVariable Integer expirationDuration) {
		try {
			return ResponseEntity.ok(productService.getAllProducts(customerSequenceNumber, expirationDuration));
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@PostMapping("/filterProduct")
	public ResponseEntity<?> filterProduct(@RequestBody ProductManagementRequest productManagementRequest) {
		log.debug("Inside the filterProduct method");

		try {
			ProductFilterCriteria filterCriteria = productManagementRequest.getFilterCriteria();
			if (filterCriteria == null) {
				throw new InvalidInputParametersException(
						ProductValidatorConstants.validateProductRequestDetailsErrorCode,
						ProductValidatorConstants.validateProductRequestDetailsMessage);
			}
			CommonValidation.validateApplicatinIdAndTenantId(filterCriteria.getApplicationId(), filterCriteria.getTenantId());
			if (null!= productManagementRequest.getSortCriteria())
				validateProductSortCriteria(productManagementRequest.getSortCriteria());

			return ResponseEntity.ok(productService.filterProduct(productManagementRequest));

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProductResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@PostMapping("/getProductFilters")
	public ResponseEntity<?> getProductFilters(@RequestBody ProductManagementRequest productManagementRequest) {
		try {

			ProductRequest productRequest = productManagementRequest.getProductRequest();
			if (productRequest == null) {
				throw new InvalidInputParametersException(
						ProductValidatorConstants.validateProductRequestDetailsErrorCode,
						ProductValidatorConstants.validateProductRequestDetailsMessage);
			}
			CommonValidation.validateApplicatinIdAndTenantId(productRequest.getApplicationId(), productRequest.getTenantId());
			return ResponseEntity.ok(productService.getProductFilters(productManagementRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ProductResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					ProductFilterResponse.builder().errorCode(ex.getErrorCode()).errorMessage(ex.getStrMsg()).build());

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ProductFilterResponse.builder().errorCode(CommonConstant.validateUnexpectedErrorCode)
							.errorMessage(CommonConstant.validateUnexpectedErrorMessage).build());
		}
	}

	private void validationForProductRequest(ProductRequest productRequest) throws InvalidInputParametersException {

		ProductValidator.validateProductName(productRequest.getProductName(), ProductConstants.productNameLength);
		ProductValidator.validateMeasuringQuantity(productRequest.getMeasuringUnit(),
				ProductConstants.productMeasuringQuantity);
		ProductValidator.validateMeasuringUnit(productRequest.getMeasuringQuantity(),
				ProductConstants.productMeasuringUnit);
		ProductValidator.validateDescription(productRequest.getDescription(), ProductConstants.descriptionLength);
		ProductValidator.validateProductPerUnitActualPrice(productRequest.getProductPerUnitActualPrice(),
				ProductConstants.productPerUnitActualPrice);
		ProductValidator.validateProductPerUnitCurrentPrice(productRequest.getProductPerUnitCurrentPrice(),
				ProductConstants.productPerUnitCurrentPrice);
		ProductValidator.validateProductLength(productRequest.getProductLength(), ProductConstants.productLength);
		ProductValidator.validateHeight(productRequest.getHeight(), ProductConstants.heightLength);
		ProductValidator.validateWidth(productRequest.getWidth(), ProductConstants.widthLength);
		ProductValidator.validateDimensionUnit(productRequest.getDimensionUnit(), ProductConstants.dimensionUnitLength);
		ProductValidator.validateMaterials(productRequest.getMaterials(), ProductConstants.materialsLength);
		ProductValidator.validateColours(productRequest.getColours(), ProductConstants.coloursLength);
		CommonValidation.validateHexCode(productRequest.getHexCode(), ProductConstants.hexCodeLength);
		ProductValidator.validateProductFinish(productRequest.getProductFinish(), ProductConstants.productFinish);
		ProductValidator.validateProdWholeTier1Price(productRequest.getProdWholesaleTier1Price(),
				ProductConstants.prodWholeTier1Price);
		ProductValidator.validateProdWholeTier2Price(productRequest.getProdWholesaleTier2Price(),
				ProductConstants.prodWholeTier2Price);
		ProductValidator.validateProdWholeTier1SalePrice(productRequest.getProdRetailTier1SalePrice(),
				ProductConstants.prodWholeTier1SalePrice);
		ProductValidator.validateProdWholeTier2SalePrice(productRequest.getProdRetailTier2SalePrice(),
				ProductConstants.prodWholeTier2SalePrice);
		ProductValidator.validateRetailTier1Price(productRequest.getProdRetailTier1Price(),
				ProductConstants.prodRetailTier1Price);
		ProductValidator.validateRetailTier2Price(productRequest.getProdRetailTier2Price(),
				ProductConstants.prodRetailTier2Price);
		ProductValidator.validateRetailTier1SalePrice(productRequest.getProdRetailTier1SalePrice(),
				ProductConstants.prodRetailTier1SalePrice);
		ProductValidator.validateRetailTier2SalePrice(productRequest.getProdRetailTier2SalePrice(),
				ProductConstants.prodRetailTier2SalePrice);
					ProductValidator.validateEta(productRequest.getEta(), ProductConstants.etaLength);
	}


	private void validateProductSortCriteria(SortCriteria sortCriteria) throws InvalidInputParametersException {
		if (StringUtils.isEmpty(sortCriteria.getSortBy().getSortBy()) || sortCriteria.getSortOrder() == null || StringUtils.isEmpty(sortCriteria.getSortOrder().toString()))
			throw new InvalidInputParametersException(ProductValidatorConstants.validateCriteriaCode, ProductValidatorConstants.validateSortCriteria);
	}

}
