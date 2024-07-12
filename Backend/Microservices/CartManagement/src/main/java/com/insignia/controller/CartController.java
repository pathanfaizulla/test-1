package com.insignia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insignia.constant.CartConstants;
import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.CartRequest;
import com.insignia.model.CartResponce;
import com.insignia.model.CustomerCartRequest;
import com.insignia.model.CustomerCartResponse;
import com.insignia.service.CartServiceInterface;
import com.insignia.validations.CartValidator;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartServiceInterface cartServiceInterface;

	@PostMapping("/saveCart")
	public ResponseEntity<?> saveCart(@RequestBody CartRequest cartRequest) {
		try {

			return ResponseEntity.ok(cartServiceInterface.saveCart(cartRequest));
		} catch (InvalidInputParametersException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CartResponce(e.getErrorCode(), e.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CartResponce(e.getErrorCode(), e.getStrMsg()));

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CartResponce(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));

		}
	}

	@DeleteMapping("/deleteCartForCustomer/{cartSequenceNumber}/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> deleteCartForCustomer(@PathVariable Long cartSequenceNumber,
			@PathVariable Long customerSequenceNumber, @PathVariable Integer expirationDuration) {
		try {
			cartServiceInterface.deleteCartForCustomer(cartSequenceNumber, customerSequenceNumber, expirationDuration);
			return ResponseEntity.ok(CartConstants.successMessage);
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CartResponce(e.getErrorCode(), e.getStrMsg()));

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CartResponce(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));

		}
	}

	@GetMapping("/getCustomerCartInformation/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> getCustomerCartInformation(@PathVariable Long customerSequenceNumber,
			@PathVariable Integer expirationDuration) {
		try {
			return ResponseEntity
					.ok(cartServiceInterface.getCustomerCartInformation(customerSequenceNumber, expirationDuration));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CartResponce(e.getErrorCode(), e.getStrMsg()));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CartResponce(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));

		}
	}

//------------	

	@PostMapping("/addProductToCart")
	public ResponseEntity<?> addProductToCart(@RequestBody CustomerCartRequest customerCartRequest) {
		try {

			validationForCustomerCartRequest(customerCartRequest);

			return ResponseEntity.ok(cartServiceInterface.addProductToCart(customerCartRequest));
		} catch (InvalidInputParametersException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CustomerCartResponse(e.getErrorCode(), e.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CartResponce(e.getErrorCode(), e.getStrMsg()));

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CartResponce(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));

		}
	}

	@PutMapping("/updateProductToCart")
	public ResponseEntity<?> updateProductToCart(@RequestBody CustomerCartRequest customerCartRequest) {
		try {

			validationForCustomerCartRequest(customerCartRequest);

			return ResponseEntity.ok(cartServiceInterface.updateCustomerCartProduct(customerCartRequest));
		} catch (InvalidInputParametersException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CustomerCartResponse(e.getErrorCode(), e.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CartResponce(e.getErrorCode(), e.getStrMsg()));

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CartResponce(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));

		}
	}

	@DeleteMapping("/deleteProductToCart/{customerCartSequenceNumber}/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> deleteProductToCart(@PathVariable Long customerCartSequenceNumber,
			@PathVariable Long customerSequenceNumber, @PathVariable Integer expirationDuration) {
		try {
			cartServiceInterface.deleteProductToCart(customerCartSequenceNumber, customerSequenceNumber,
					expirationDuration);
			return ResponseEntity.ok(CartConstants.successMessage);
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CartResponce(e.getErrorCode(), e.getStrMsg()));

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CartResponce(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));

		}
	}

	@GetMapping("/getProductByCustomer/{cartSequenceNumber}/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> getProductByCustomer(@PathVariable Long cartSequenceNumber,
			@PathVariable Long customerSequenceNumber, @PathVariable Integer expirationDuration) {
		try {
			return ResponseEntity.ok(cartServiceInterface.getProductByCustomer(cartSequenceNumber,
					customerSequenceNumber, expirationDuration));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CartResponce(e.getErrorCode(), e.getStrMsg()));

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CartResponce(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));

		}
	}

	private void validationForCustomerCartRequest(CustomerCartRequest customerCartRequest)
			throws InvalidInputParametersException {
		CartValidator.validateCartSequenceNumber(customerCartRequest.getCartSequenceNumber(),
				CartConstants.cartSequenceNumber);
		CartValidator.validateProductSequenceNumber(customerCartRequest.getProductSequenceNumber(),
				CartConstants.productSequenceNumber);
		CartValidator.validateproductQuantity(customerCartRequest.getProductQuantity(),
				CartConstants.cartProductQuantity);
	}

}
