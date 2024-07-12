package com.insignia.controller;

import com.insignia.constant.CustomerManagementConstants;
import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.*;
import com.insignia.serviceInterface.CustomerServiceInterface;
import com.insignia.validations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class CustomerController {

	@Autowired
	private CustomerServiceInterface serviceRepo;

	@PostMapping("/saveCustomerDetails")
	public ResponseEntity<?> saveAllCustomerDetails(
			@RequestBody CustomerManagementServiceRequest customerManagementDetails) {
		try {
			CustomerBasicDetailsValidation.validateCustomerId(
					customerManagementDetails.getCustomerBasicDetailsRequest().getCustomerId(),
					CustomerManagementConstants.customerIdLength, false);

			validateCustomerAllDetails(customerManagementDetails);
			CustomerBasicDetailsValidation.validatePassword(
					customerManagementDetails.getCustomerBasicDetailsRequest().getPassword(),
					CustomerManagementConstants.passwordlength);

			return ResponseEntity.ok(serviceRepo.saveAllCustomerDetails(customerManagementDetails));

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CustomerManagementServiceResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomerManagementServiceResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PutMapping("/updateCustomerDetails")
	public ResponseEntity<?> updateAllCustomerDetails(
			@RequestBody CustomerManagementServiceRequest customerManagementDetails) {

		try {
			CustomerBasicDetailsValidation.validateCustomerId(
					customerManagementDetails.getCustomerBasicDetailsRequest().getCustomerId(),
					CustomerManagementConstants.customerIdLength, true);

			validateCustomerAllDetails(customerManagementDetails);

			return ResponseEntity.ok(serviceRepo.updateAllCustomerDetails(customerManagementDetails));

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CustomerManagementServiceResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomerManagementServiceResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PostMapping("/deleteCustomerDetails")
	public ResponseEntity<?> deleteCustomerAssociatedDetails(
			@RequestBody CustomerManagementServiceRequest customerManagementDetails)
			throws InvalidInputParametersException {
		try {

			serviceRepo.deleteCustomerAssociatedDetails(customerManagementDetails);

			return ResponseEntity.ok("Record Successfully Deleted");
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CustomerManagementServiceResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomerManagementServiceResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@PostMapping("/getCustomerDetails")
	public ResponseEntity<?> getCustomerDetails(
			@RequestBody CustomerManagementServiceRequest customerManagementDetails) {
		try {

			return ResponseEntity.ok(serviceRepo.getCustomerDetails(customerManagementDetails));

		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new CustomerManagementServiceResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomerManagementServiceResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PostMapping("/getAllCustomerData")
	public ResponseEntity<?> getAllCustomerData(
			@RequestBody CustomerManagementServiceRequest customerManagementDetails) {
		try {

			return ResponseEntity.ok(serviceRepo.getAllCustomerData(customerManagementDetails));

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomerManagementServiceResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@GetMapping("/getCustomerAndStoreInformation/{customerSequenceNumber}/{expirationDuration}")
	public ResponseEntity<?> getCustomerAndStoreInformation(
			@PathVariable("customerSequenceNumber") Long customerSequenceNumber,
			@PathVariable("expirationDuration") Integer expirationDuration) {
		try {

			return ResponseEntity
					.ok(serviceRepo.getCustomerAndStoreInformation(customerSequenceNumber, expirationDuration));

		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AddressResponse(ex.getErrorCode(), ex.getStrMsg()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomerManagementServiceResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	private void validateCustomerAllDetails(CustomerManagementServiceRequest customerManagementDetails)
			throws InvalidInputParametersException {

		validateCustomerBasicDetails(customerManagementDetails);

		validateAddressDetails(customerManagementDetails);

		validateCustomerPersonalDetails(customerManagementDetails);

		validateRolesAndPermissions(customerManagementDetails);

		validateCustomerStoreDetails(customerManagementDetails);

	}

	private void validateCustomerStoreDetails(CustomerManagementServiceRequest customerManagementDetails)
			throws InvalidInputParametersException {

		if (customerManagementDetails.getCustomerStoreDetailsRequestList() != null) {

			for (CustomerStoreDetailsRequest customerStoreDetailsRequest : customerManagementDetails
					.getCustomerStoreDetailsRequestList()) {

				CustomerStoreDetailsValidation.validateMarkupFactor(customerStoreDetailsRequest.getMarkupFactor(),
						CustomerManagementConstants.markupFactorLength);
				CustomerStoreDetailsValidation.validateHearAboutUs(customerStoreDetailsRequest.getHearAboutUs(),
						CustomerManagementConstants.hearAboutUsLength);
				CustomerStoreDetailsValidation.validateMarkets(customerStoreDetailsRequest.getMarkets(),
						CustomerManagementConstants.marketsLength);
				CustomerStoreDetailsValidation.validateStoreName(customerStoreDetailsRequest.getStoreName(),
						CustomerManagementConstants.storeNameLength);
				CustomerStoreDetailsValidation.validateStoreContact(customerStoreDetailsRequest.getStoreContact(),
						CustomerManagementConstants.storeContactLength);
				CustomerStoreDetailsValidation.validateStoreAddress(customerStoreDetailsRequest.getStoreAddress(),
						CustomerManagementConstants.storeAddressLength);
				CustomerStoreDetailsValidation.validateStoreCountry(customerStoreDetailsRequest.getStoreCountry(),
						CustomerManagementConstants.storeCountryLength);
				CustomerStoreDetailsValidation.validateStoreState(customerStoreDetailsRequest.getStoreState(),
						CustomerManagementConstants.storeStateLength);
				CustomerStoreDetailsValidation.validateStoreCity(customerStoreDetailsRequest.getStoreCity(),
						CustomerManagementConstants.storeCityLength);
				CustomerStoreDetailsValidation.validateStoreZipCode(customerStoreDetailsRequest.getStoreZipCode(),
						CustomerManagementConstants.storeZipCodeLength);
				CustomerStoreDetailsValidation.validateTelephone(customerStoreDetailsRequest.getTelephone(),
						CustomerManagementConstants.storeTelephoneLength);
				CustomerStoreDetailsValidation.validateWebsite(customerStoreDetailsRequest.getWebsite(),
						CustomerManagementConstants.storeWebsiteLength);
				CustomerStoreDetailsValidation.validateResaleLicense(customerStoreDetailsRequest.getResaleLicense(),
						CustomerManagementConstants.storeResaleLicenseLength);
				CustomerStoreDetailsValidation.validateBusinessType(customerStoreDetailsRequest.getBusinessType(),
						CustomerManagementConstants.storeBusinessTypeLength);
			}
		}
	}

	private void validateRolesAndPermissions(CustomerManagementServiceRequest customerManagementDetails)
			throws InvalidInputParametersException {

		if (customerManagementDetails.getRolesAndPermissionsRequestList() != null) {

			for (RolesAndPermissionsRequest rolesAndPermissionsRequest : customerManagementDetails
					.getRolesAndPermissionsRequestList()) {

				RolesAndPermissonsValidations.validateRoleName(rolesAndPermissionsRequest.getRoleName(),
						CustomerManagementConstants.roleNameLength);
				RolesAndPermissonsValidations.validateUpdatePermissions(
						rolesAndPermissionsRequest.getUpdatedPermissions(),
						CustomerManagementConstants.updatePermissionsLength);

			}
		}
	}

	private void validateCustomerPersonalDetails(CustomerManagementServiceRequest customerManagementDetails)
			throws InvalidInputParametersException {

		if (customerManagementDetails.getCustomerPersonalDetailsRequestList() != null) {

			for (CustomerPersonalDetailsRequest customerPersonalRequest : customerManagementDetails
					.getCustomerPersonalDetailsRequestList()) {

				CustomerPersonalDetailsValidation.validateFirstName(customerPersonalRequest.getFirstName(),
						CustomerManagementConstants.firstNameLength);
				CustomerPersonalDetailsValidation.validateLastName(customerPersonalRequest.getLastName(),
						CustomerManagementConstants.lastNameLength);
				CustomerPersonalDetailsValidation.validateMiddleName(customerPersonalRequest.getMiddleName(),
						CustomerManagementConstants.middleNameLength);
				CustomerPersonalDetailsValidation.validateAge(customerPersonalRequest.getAge(),
						CustomerManagementConstants.ageLength);
				CustomerPersonalDetailsValidation.validateGender(customerPersonalRequest.getGender(),
						CustomerManagementConstants.genderLength);
				CustomerPersonalDetailsValidation.validateCustomerEmailId(customerPersonalRequest.getCustomerEmailId(),
						CustomerManagementConstants.customerEmailIdLength);
				CustomerPersonalDetailsValidation.validateAlternativeEmailId(
						customerPersonalRequest.getAlternativeEmailId(),
						CustomerManagementConstants.alternativeEmailIdLength);
				CustomerPersonalDetailsValidation.validateCustomeMobileNumber(
						customerPersonalRequest.getCustomerMobileNumber(),
						CustomerManagementConstants.customerMobileNumberLength);
				CustomerPersonalDetailsValidation.validateAlternativeMobileNumber(
						customerPersonalRequest.getAlternativeMobileNumber(),
						CustomerManagementConstants.alternativeMobileNumberLength);
				CustomerPersonalDetailsValidation.validateCustomerLandlineNumber(
						customerPersonalRequest.getCustomerLandlineNumber(),
						CustomerManagementConstants.customerLandlineNumberLength);

			}
		}
	}

	private void validateAddressDetails(CustomerManagementServiceRequest customerManagementDetails)
			throws InvalidInputParametersException {
		if (customerManagementDetails.getAddressRequestList() != null) {

			for (AddressRequest addressRequest : customerManagementDetails.getAddressRequestList()) {

				AddressDetailsValidation.validateAddressLine1(addressRequest.getAddressLine1(),
						CustomerManagementConstants.addresLine1length, true);
				AddressDetailsValidation.validateCity(addressRequest.getCity(), CustomerManagementConstants.cityLength,
						true);
				AddressDetailsValidation.validateState(addressRequest.getState(),
						CustomerManagementConstants.stateLength, true);
				AddressDetailsValidation.validateCountry(addressRequest.getCountry(),
						CustomerManagementConstants.countryLength, true);
				AddressDetailsValidation.validateZipCode(addressRequest.getZipCode(),
						CustomerManagementConstants.zipCodeLength, true);
				AddressDetailsValidation.validateAddressLine2(addressRequest.getAddressLine2(),
						CustomerManagementConstants.addresLine2length);
				AddressDetailsValidation.validateLandmark(addressRequest.getLandmark(),
						CustomerManagementConstants.landMarkLength);
				AddressDetailsValidation.validateEmailId(addressRequest.getEmailId(),
						CustomerManagementConstants.emailIdLength);
				AddressDetailsValidation.validateMobileNumber(addressRequest.getMobileNumber(),
						CustomerManagementConstants.mobilenumberLength);
				AddressDetailsValidation.validateLandLineNumber(addressRequest.getLandlineNumber(),
						CustomerManagementConstants.landlineNumberLength);
			}
		}
	}

	private void validateCustomerBasicDetails(CustomerManagementServiceRequest customerManagementDetails)
			throws InvalidInputParametersException {

		if (customerManagementDetails.getCustomerBasicDetailsRequest() != null) {

			CustomerBasicDetailsValidation.validateUserId(
					customerManagementDetails.getCustomerBasicDetailsRequest().getUserId(),
					CustomerManagementConstants.userIdlength);
			CommonValidation.validateApplicatinIdAndTenantId(
					customerManagementDetails.getCustomerBasicDetailsRequest().getApplicationId(),
					customerManagementDetails.getCustomerBasicDetailsRequest().getTenantId());
			CustomerBasicDetailsValidation.validateCustomerEmail(
					customerManagementDetails.getCustomerBasicDetailsRequest().getEmailId(),
					CustomerManagementConstants.customerEmailLength);
			CustomerBasicDetailsValidation.validateUsername(
					customerManagementDetails.getCustomerBasicDetailsRequest().getUserName(),
					CustomerManagementConstants.userNameLength);
		}
	}
}
