package com.insignia.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insignia.constant.WishlistDetailsConstants;
import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.ProductDetailsRequest;
import com.insignia.model.WishlistDetailsRequest;
import com.insignia.model.WishlistManagementResponse;
import com.insignia.serviceInterface.WishlistServiceInterface;
import com.insignia.validations.WishlistDetailsValidation;

@CrossOrigin
@RestController
public class WishlistController {

	@Autowired
	private WishlistServiceInterface wishlistServiceInterface;

	@PostMapping("/saveWishlistForCustomer")
	public ResponseEntity<?> saveWishlistForCustomer(@RequestBody WishlistDetailsRequest wishlistDetailsRequest)
			throws InvalidInputParametersException {

		try {
			ValidationForWishlistDetails(wishlistDetailsRequest);
			return ResponseEntity.ok(wishlistServiceInterface.saveWishlistForCustomer(wishlistDetailsRequest));

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new WishlistManagementResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new WishlistManagementResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new WishlistManagementResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@CrossOrigin
	@PostMapping("/removeProductFromWishlist")
	public ResponseEntity<?> removeProductFromWishlist(@RequestBody WishlistDetailsRequest wishlistDetailsRequest) {
		try {

			List<ProductDetailsRequest> productDetailsRequestList = wishlistDetailsRequest
					.getProductDetailsRequestList();
			List<Long> productSequenceNumbers = new ArrayList<>();

			for (ProductDetailsRequest productDetailsRequest : productDetailsRequestList) {
				productSequenceNumbers.add(productDetailsRequest.getProductSequenceNumber());
			}
			wishlistServiceInterface.removeProductFromWishlist(wishlistDetailsRequest.getCustomerSequenceNumber(),
					productSequenceNumbers, wishlistDetailsRequest.getExpirationDuration());

			return ResponseEntity.ok(WishlistDetailsConstants.SuccessMessageForRemoveCustomerDetais);
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new WishlistManagementResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new WishlistManagementResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@DeleteMapping("/deleteCustomerWishList/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> deleteCustomerWishList(@PathVariable Long customerSequenceNumber,
			@PathVariable Integer expirationDuration) {

		try {
			wishlistServiceInterface.deleteCustomerWishList(customerSequenceNumber, expirationDuration);
			return ResponseEntity.ok(WishlistDetailsConstants.SuccessMessageForDeleteCustomerDetais);
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new WishlistManagementResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new WishlistManagementResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@GetMapping("/getCustomerWishlist/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> getCustomerWishlist(@PathVariable Long customerSequenceNumber,
			Integer expirationDuration) {
		try {
			return ResponseEntity
					.ok(wishlistServiceInterface.getCustomerWishlist(customerSequenceNumber, expirationDuration));

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new WishlistManagementResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new WishlistManagementResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	private void ValidationForWishlistDetails(WishlistDetailsRequest wishlistDetailsRequest)
			throws InvalidInputParametersException {

		if (wishlistDetailsRequest != null) {

			WishlistDetailsValidation.validateCustomerSequenceNumber(wishlistDetailsRequest.getCustomerSequenceNumber(),
					WishlistDetailsConstants.CustomerSequenceNumberLength);

			List<ProductDetailsRequest> productDetailsRequestList = wishlistDetailsRequest
					.getProductDetailsRequestList();
			for (ProductDetailsRequest productDetailsRequest : productDetailsRequestList) {
				WishlistDetailsValidation.validateProductSequenceNumber(
						productDetailsRequest.getProductSequenceNumber(), WishlistDetailsConstants.ProductIdLength);
				WishlistDetailsValidation.validateQuantity(productDetailsRequest.getQuantity(),
						WishlistDetailsConstants.QuantityLength);
			}
		}
	}
}
