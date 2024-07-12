package com.insignia.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.insignia.constant.AddressStringConstant;
import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.AddressRequest;
import com.insignia.model.AddressResponse;
import com.insignia.serviceInterface.AddressServiceInterface;
import com.insignia.validations.AddressDetailsValidation;

@CrossOrigin
@RestController
public class AddressController {

	@Autowired
	private AddressServiceInterface serviceRepo;

	@PostMapping("/saveAddress")
	public ResponseEntity<?> saveAddress(@RequestBody AddressRequest addressReq) {
		try {

			AddressDetailsValidation.validateAddressLine1(addressReq.getAddressLine1(),
					AddressStringConstant.addresLine1length, true);
			AddressDetailsValidation.validateCity(addressReq.getCity(), AddressStringConstant.cityLength, true);
			AddressDetailsValidation.validateState(addressReq.getState(), AddressStringConstant.stateLength, true);
			AddressDetailsValidation.validateCountry(addressReq.getCountry(), AddressStringConstant.countryLength,
					true);
			AddressDetailsValidation.validateZipCode(addressReq.getZipCode(), AddressStringConstant.zipCodeLength,
					true);

			validateAddressFields(addressReq);

			return ResponseEntity.ok(serviceRepo.saveAddressDetails(addressReq));

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AddressResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PutMapping("/updateAddress")
	public ResponseEntity<?> updateAddressDetails(@RequestBody AddressRequest addressReq) {
		try {
			AddressDetailsValidation.validateAddressLine1(addressReq.getAddressLine1(),
					AddressStringConstant.addresLine1length, addressReq.isAddressLine1Updated());
			AddressDetailsValidation.validateCity(addressReq.getCity(), AddressStringConstant.cityLength,
					addressReq.isCityUpdated());
			AddressDetailsValidation.validateState(addressReq.getState(), AddressStringConstant.stateLength,
					addressReq.isStateUpdated());
			AddressDetailsValidation.validateCountry(addressReq.getCountry(), AddressStringConstant.countryLength,
					addressReq.isCountryUpdated());
			AddressDetailsValidation.validateZipCode(addressReq.getZipCode(), AddressStringConstant.zipCodeLength,
					addressReq.isZipCodeUpdated());

			validateAddressFields(addressReq);

			return ResponseEntity.ok(serviceRepo.updateAddressDetails(addressReq));

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AddressResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@DeleteMapping("/deleteAddress/{sequenceNumber}/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> deleteByAddressId(@PathVariable Integer sequenceNumber,
			@PathVariable Long customerSequenceNumber, @PathVariable Integer expirationDuration) {
		try {

			serviceRepo.deleteByAddressId(sequenceNumber, customerSequenceNumber, expirationDuration);
			return ResponseEntity.ok(AddressStringConstant.deleteForAddress);

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AddressResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@DeleteMapping("/deleteAddressForCustomer/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> deleteAddressForCustomer(@PathVariable Long customerSequenceNumber,
			@PathVariable Integer expirationDuration) {
		try {
			serviceRepo.deleteAddressForCustomer(customerSequenceNumber, expirationDuration);
			return ResponseEntity.ok("Record Successfully Deleted");

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AddressResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@GetMapping("/getAddressList/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> getAddressList(@PathVariable Long customerSequenceNumber,
			@PathVariable Integer expirationDuration) {
		try {
			List<AddressResponse> customerDetails = serviceRepo.getAddressList(customerSequenceNumber,
					expirationDuration);
			return ResponseEntity.ok(!customerDetails.isEmpty() ? customerDetails : "User Address Details Not found");

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AddressResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	private void validateAddressFields(AddressRequest addressReq) throws InvalidInputParametersException {

		AddressDetailsValidation.validateAddressLine2(addressReq.getAddressLine2(),
				AddressStringConstant.addresLine2length);
		AddressDetailsValidation.validateEmailId(addressReq.getEmailId(), AddressStringConstant.emailIdLength);
		AddressDetailsValidation.validateLandmark(addressReq.getLandmark(), AddressStringConstant.landMarkLength);
		AddressDetailsValidation.validateLandLineNumber(addressReq.getLandlineNumber(),
				AddressStringConstant.landlineNumberLength);
		AddressDetailsValidation.validateMobileNumber(addressReq.getMobileNumber(),
				AddressStringConstant.mobilenumberLength);
	}
}
