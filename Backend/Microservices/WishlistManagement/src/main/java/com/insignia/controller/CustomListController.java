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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insignia.constant.WishlistDetailsConstants;
import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.CustomListDetailsRequest;
import com.insignia.model.CustomListManagementRequest;
import com.insignia.model.CustomListManagementResponse;
import com.insignia.model.ProductDetailsRequest;
import com.insignia.serviceInterface.CustomListServiceInterface;
import com.insignia.validations.WishlistDetailsValidation;

@CrossOrigin
@RestController
@RequestMapping("/customList")
public class CustomListController {

	@Autowired
	private CustomListServiceInterface customListServiceInterface;

	@PostMapping("/saveCustomList")
	public ResponseEntity<?> saveCustomList(@RequestBody CustomListManagementRequest customListManagementRequest) {
		try {
			ValidationForCustomListDetails(customListManagementRequest);

			return ResponseEntity.ok(customListServiceInterface.saveCustomList(customListManagementRequest));

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CustomListManagementResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CustomListManagementResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));
		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomListManagementResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@PostMapping("/createCustomList")
	public ResponseEntity<?> createCustomList(@RequestBody CustomListManagementRequest customListManagementRequest) {
		try {

			ValidationForCustomListDetails(customListManagementRequest);

			return ResponseEntity.ok(customListServiceInterface.createCustomList(customListManagementRequest));

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CustomListManagementResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CustomListManagementResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));
		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomListManagementResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@PutMapping("/updateCustomListName")
	public ResponseEntity<?> updateCustomListName(
			@RequestBody CustomListManagementRequest customListManagementRequest) {
		try {

			List<CustomListDetailsRequest> customListDetailsRequestList = customListManagementRequest
					.getCustomListDetailsRequestList();

			if (customListDetailsRequestList == null) {
				throw new InvalidInputParametersException(
						WishlistDetailsConstants.validateCustomListRequestDetailsMessage,
						WishlistDetailsConstants.validateCustomListSequenceNumberMessage);
			}

			List<String> customListNameList = new ArrayList<>();

			for (CustomListDetailsRequest customListDetailsRequest : customListDetailsRequestList) {
				customListNameList.add(customListDetailsRequest.getCustomListName());
			}

			ValidationForCustomListDetails(customListManagementRequest);

			return ResponseEntity.ok(customListServiceInterface.updateCustomListName(customListManagementRequest));

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CustomListManagementResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CustomListManagementResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomListManagementResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@GetMapping("/getCustomListForCustomer/{customerSequenceNumber}/{tokenExpirationDuration}")
	public ResponseEntity<?> getCustomListForCustomer(@PathVariable Long customerSequenceNumber,
			Integer tokenExpirationDuration) {
		try {
			return ResponseEntity.ok(customListServiceInterface.getCustomListForCustomer(customerSequenceNumber,
					tokenExpirationDuration));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomListManagementResponse(
					CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomListManagementResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@DeleteMapping("/deleteAllCustomListForCustomer/{customerSequenceNumber}/{tokenExpirationDuration}")
	public ResponseEntity<?> deleteAllCustomListForCustomer(@PathVariable Long customerSequenceNumber,
			Integer tokenExpirationDuration) {
		try {
			customListServiceInterface.deleteAllCustomListForCustomer(customerSequenceNumber, tokenExpirationDuration);
			return ResponseEntity.ok(WishlistDetailsConstants.SuccessMessageForDeleteCustomList);
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomListManagementResponse(
					CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomListManagementResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@CrossOrigin
	@PostMapping("/deleteCustomList")
	public ResponseEntity<?> deleteCustomList(@RequestBody CustomListManagementRequest customListManagementRequest) {
		try {

			List<CustomListDetailsRequest> customListDetailsRequestList = customListManagementRequest
					.getCustomListDetailsRequestList();

			List<String> customListNameList = new ArrayList<>();

			for (CustomListDetailsRequest customListDetailsRequest : customListDetailsRequestList) {
				customListNameList.add(customListDetailsRequest.getCustomListName());
			}

			customListServiceInterface.deleteCustomList(customListManagementRequest.getCustomerSequenceNumber(),
					customListNameList, customListManagementRequest.getTokenExpirationDuration());

			return ResponseEntity.ok(WishlistDetailsConstants.SuccessMessageForRemoveProductFromCustomList);
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CustomListManagementResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomListManagementResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	private void ValidationForCustomListDetails(CustomListManagementRequest customListManagementRequest)
			throws InvalidInputParametersException {

		if (customListManagementRequest != null) {
			WishlistDetailsValidation.validateCustomerSequenceNumber(
					customListManagementRequest.getCustomerSequenceNumber(),
					WishlistDetailsConstants.CustomerSequenceNumberLength);
			List<CustomListDetailsRequest> customListDetailsRequestList = customListManagementRequest
					.getCustomListDetailsRequestList();
			if (customListDetailsRequestList != null) {
				for (CustomListDetailsRequest customListDetailsRequest : customListDetailsRequestList) {
					WishlistDetailsValidation.validateCustomListName(customListDetailsRequest.getCustomListName(),
							WishlistDetailsConstants.CustomListNameLength);
					List<ProductDetailsRequest> productDetailsRequestList = customListDetailsRequest
							.getProductDetailsRequestList();
					if (productDetailsRequestList != null) {
						for (ProductDetailsRequest productDetailsRequest : productDetailsRequestList) {
							WishlistDetailsValidation.validateProductSequenceNumber(
									productDetailsRequest.getProductSequenceNumber(),
									WishlistDetailsConstants.ProductIdLength);
							WishlistDetailsValidation.validateQuantity(productDetailsRequest.getQuantity(),
									WishlistDetailsConstants.CustomListProductQuantityLength);
						}
					}
				}
			}
		}
	}
}
