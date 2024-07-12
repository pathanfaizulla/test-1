package com.insignia.serviceImpl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.insignia.constants.CommonConstant;
import com.insignia.constants.CustomerBasicDetailsConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.CustomerDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.security.EncryptionUtility;
import com.insignia.entity.AddressDetails;
import com.insignia.entity.CustomerBasicDetailsEntity;
import com.insignia.entity.CustomerPersonalDetails;
import com.insignia.entity.CustomerStoreDetails;
import com.insignia.entity.RolesAndPermissions;
import com.insignia.model.AddressRequest;
import com.insignia.model.AddressResponse;
import com.insignia.model.CustomerAndStoreInformationResponse;
import com.insignia.model.CustomerBasicDetailsRequest;
import com.insignia.model.CustomerBasicDetailsResponse;
import com.insignia.model.CustomerManagementServiceRequest;
import com.insignia.model.CustomerManagementServiceResponse;
import com.insignia.model.CustomerPersonalDetailsRequest;
import com.insignia.model.CustomerPersonalDetailsResponse;
import com.insignia.model.CustomerStoreDetailsRequest;
import com.insignia.model.CustomerStoreDetailsResponse;
import com.insignia.model.RolesAndPermissionsRequest;
import com.insignia.model.RolesAndPermissionsResponse;
import com.insignia.serviceInterface.CustomerServiceInterface;

@Service
public class CustomerServiceImpl implements CustomerServiceInterface {

	@Autowired
	private CustomerDaoInterface customerDao;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Autowired
	private EncryptionUtility encryptionUtility;

	@Transactional(rollbackOn = InvalidInputParametersException.class)
	@Modifying
	@Override
	public CustomerManagementServiceResponse saveAllCustomerDetails(
			CustomerManagementServiceRequest customerManagementServiceDetails)
			throws InvalidInputParametersException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		CustomerBasicDetailsEntity cbde = new CustomerBasicDetailsEntity();
		boolean isValueUpdated = false;

		CustomerBasicDetailsRequest customerBasicDetailsRequest = customerManagementServiceDetails
				.getCustomerBasicDetailsRequest();

		if (customerBasicDetailsRequest != null) {

			String password = encryptionUtility.getBCryptEncryptedPassword(
					customerManagementServiceDetails.getCustomerBasicDetailsRequest().getPassword());

			cbde.setFullName(customerBasicDetailsRequest.getFullName());
			cbde.setApplicationId(customerBasicDetailsRequest.getApplicationId());
			cbde.setTenantId(customerBasicDetailsRequest.getTenantId());
			cbde.setUserId(customerBasicDetailsRequest.getUserId());
			cbde.setCustomerPassword(password);
			cbde.setCustomerEmail(customerBasicDetailsRequest.getEmailId());
			cbde.setUserName(customerBasicDetailsRequest.getUserName());
			cbde.setRegisteredOn(new Date());
			cbde.setCustomerId(customerBasicDetailsRequest.getCustomerId());

			cbde = customerDao.saveAllCustomerDetails(cbde);

			if (customerBasicDetailsRequest.getCustomerId() == null
					|| customerBasicDetailsRequest.getCustomerId().isEmpty()) {

				String customerId = String.format(CommonConstant.paddingLength, cbde.getCustomerSequenceNumber());
				cbde.setCustomerId(customerId);
				isValueUpdated = true;
			}
		}

		if (customerManagementServiceDetails.getRolesAndPermissionsRequestList() != null) {

			List<RolesAndPermissionsRequest> rolesAndPermissionsRequestList = customerManagementServiceDetails
					.getRolesAndPermissionsRequestList();

			List<RolesAndPermissions> rapList = new ArrayList<>();

			for (RolesAndPermissionsRequest rolesAndPermissionsRequest : rolesAndPermissionsRequestList) {
				RolesAndPermissions rap = new RolesAndPermissions();

				rap.setRoleName(rolesAndPermissionsRequest.getRoleName());
				rap.setPermission1(rolesAndPermissionsRequest.getPermission1());
				rap.setPermission2(rolesAndPermissionsRequest.getPermission2());
				rap.setPermission3(rolesAndPermissionsRequest.getPermission3());
				rap.setPermission4(rolesAndPermissionsRequest.getPermission4());
				rap.setRoleApprovedDate(rolesAndPermissionsRequest.getRoleApprovedDate());
				rap.setRoleRevokedDate(rolesAndPermissionsRequest.getRoleRevokedDate());
				rap.setPermissionChangeDate(rolesAndPermissionsRequest.getPermissionChangeDate());
				rap.setUpdatedPermissions(rolesAndPermissionsRequest.getUpdatedPermissions());
				rap.setCustomerSequenceNumber(cbde.getCustomerSequenceNumber());

				rapList.add(rap);

			}

			cbde.setRolesAndPermissions(rapList);
			isValueUpdated = true;

		}

		if (customerManagementServiceDetails.getCustomerPersonalDetailsRequestList() != null) {
			List<CustomerPersonalDetailsRequest> customerPersonalDetailsRequestList2 = customerManagementServiceDetails
					.getCustomerPersonalDetailsRequestList();
			List<CustomerPersonalDetails> cpdList = new ArrayList<>();

			for (CustomerPersonalDetailsRequest customerPersonalDetailsRequest : customerPersonalDetailsRequestList2) {
				CustomerPersonalDetails cpde = new CustomerPersonalDetails();

				cpde.setFullName(customerPersonalDetailsRequest.getFullName());

				cpde.setFirstName(customerPersonalDetailsRequest.getFirstName());
				cpde.setMiddleName(customerPersonalDetailsRequest.getMiddleName());
				cpde.setLastName(customerPersonalDetailsRequest.getLastName());
				cpde.setAge(customerPersonalDetailsRequest.getAge());
				cpde.setGender(customerPersonalDetailsRequest.getGender());
				cpde.setCustomerEmailId(customerPersonalDetailsRequest.getCustomerEmailId());
				cpde.setAlternativeEmailId(customerPersonalDetailsRequest.getAlternativeEmailId());
				cpde.setCustomerMobileNumber(customerPersonalDetailsRequest.getCustomerMobileNumber());
				cpde.setAlternativeMobileNumber(customerPersonalDetailsRequest.getAlternativeMobileNumber());
				cpde.setCustomerLandlineNumber(customerPersonalDetailsRequest.getCustomerLandlineNumber());
				cpde.setCustomerSequenceNumber(cbde.getCustomerSequenceNumber());
				cpdList.add(cpde);

			}
			cbde.setCustomerPersonalDetails(cpdList);
			isValueUpdated = true;
		}

		if (customerManagementServiceDetails.getAddressRequestList() != null) {
			List<AddressRequest> addressRequestList = customerManagementServiceDetails.getAddressRequestList();
			List<AddressDetails> adList = new ArrayList<>();

			for (AddressRequest addressRequest : addressRequestList) {
				AddressDetails ad = new AddressDetails();
				ad.setAddressLine1(addressRequest.getAddressLine1());
				ad.setAddressLine2(addressRequest.getAddressLine2());
				ad.setLandmark(addressRequest.getLandmark());
				ad.setCity(addressRequest.getCity());
				ad.setState(addressRequest.getState());
				ad.setCountry(addressRequest.getCountry());
				ad.setZipCode(addressRequest.getZipCode());
				ad.setMobileNumber(addressRequest.getMobileNumber());
				ad.setLandlineNumber(addressRequest.getLandlineNumber());
				ad.setEmailId(addressRequest.getEmailId());
				ad.setDefaultAddress(addressRequest.getIsDefaultAddress());
				ad.setBillingAddress(addressRequest.getIsBillingAddress());
				ad.setCustomerSequenceNumber(cbde.getCustomerSequenceNumber());
				adList.add(ad);

			}

			cbde.setAddressDetails(adList);
			isValueUpdated = true;

		}

		if (isValueUpdated) {

			cbde = customerDao.saveAllCustomerDetails(cbde);
		}

		return createResponse(cbde);

	}

	@Override
	@Transactional
	public Optional<CustomerManagementServiceResponse> getCustomerDetails(
			CustomerManagementServiceRequest customerManagementServiceRequest)
			throws TokenExpiredException, InvalidInputParametersException {
		CustomerBasicDetailsRequest customerBasicDetailsRequest = customerManagementServiceRequest
				.getCustomerBasicDetailsRequest();
		tokenDao.checkTokenValidity(customerBasicDetailsRequest.getCustomerSequenceNumber(),
				customerBasicDetailsRequest.getExpirationDuration());
		CustomerBasicDetailsEntity customerBasicDetailsEntity = getCustomerData(customerManagementServiceRequest);
		CustomerManagementServiceResponse customerManagementServiceResponse = createResponseForGettingCustomerData(
				customerBasicDetailsEntity);
		return Optional.of(customerManagementServiceResponse);

	}

	@Transactional
	@Override
	public List<CustomerManagementServiceResponse> getAllCustomerData(
			CustomerManagementServiceRequest customerManagementServiceRequest) throws TokenExpiredException {
		CustomerBasicDetailsRequest customerBasicDetailsRequest = customerManagementServiceRequest
				.getCustomerBasicDetailsRequest();
		tokenDao.checkTokenValidity(customerBasicDetailsRequest.getCustomerSequenceNumber(),
				customerBasicDetailsRequest.getExpirationDuration());

		List<CustomerManagementServiceResponse> customerManagementServiceResponseList = new ArrayList<>();

		List<CustomerBasicDetailsEntity> customerDataList = customerDao.getAllCustomerData(
				customerBasicDetailsRequest.getApplicationId(), customerBasicDetailsRequest.getTenantId());

		for (CustomerBasicDetailsEntity customerBasicDetailsEntity : customerDataList) {
			CustomerManagementServiceResponse customerManagementServiceResponse = createResponseForGettingCustomerData(
					customerBasicDetailsEntity);

			customerManagementServiceResponseList.add(customerManagementServiceResponse);
		}
		return customerManagementServiceResponseList;
	}

	private CustomerBasicDetailsEntity getCustomerData(
			CustomerManagementServiceRequest customerManagementServiceRequest) throws InvalidInputParametersException {
		CustomerBasicDetailsRequest customerBasicDetailsRequest = customerManagementServiceRequest
				.getCustomerBasicDetailsRequest();
		Optional<CustomerBasicDetailsEntity> customerData = customerDao.getCustomerDetails(
				customerBasicDetailsRequest.getCustomerSequenceNumber(), customerBasicDetailsRequest.getApplicationId(),
				customerBasicDetailsRequest.getTenantId());

		return customerData.orElseThrow(
				() -> new InvalidInputParametersException(CustomerBasicDetailsConstants.badCredentialsErrorCode,
						CustomerBasicDetailsConstants.badCredentialsErrorMessage));
	}

	@Transactional
	@Override
	public void deleteCustomerAssociatedDetails(CustomerManagementServiceRequest customerManagementDetails)
			throws TokenExpiredException, InvalidInputParametersException {

		CustomerBasicDetailsRequest customerBasicDetailsRequest = customerManagementDetails
				.getCustomerBasicDetailsRequest();
		tokenDao.checkTokenValidity(customerBasicDetailsRequest.getCustomerSequenceNumber(),
				customerBasicDetailsRequest.getExpirationDuration());

		Optional<CustomerBasicDetailsEntity> customerBasicDetailsEntity = customerDao.getCustomerDetails(
				customerBasicDetailsRequest.getCustomerSequenceNumber(), customerBasicDetailsRequest.getApplicationId(),
				customerBasicDetailsRequest.getTenantId());
		if (customerBasicDetailsEntity.isPresent()) {

			customerDao.deleteCustomerAssociatedDetails(customerBasicDetailsRequest.getCustomerSequenceNumber(),
					customerBasicDetailsRequest.getApplicationId(), customerBasicDetailsRequest.getTenantId());
		} else {
			throw new InvalidInputParametersException(CustomerBasicDetailsConstants.detailsNotFoundInTheSystemErrorCode,
					CustomerBasicDetailsConstants.detailsNotFoundInTheSystemErrorMessage);
		}
	}

	@Transactional
	@Override
	public CustomerManagementServiceResponse getCustomerAndStoreInformation(Long customerSequenceNumber,
			Integer expirationDuration) throws TokenExpiredException, ParseException {
		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);

		List<Object[]> customerAndStoreInformationList = customerDao.getCustomerAndStoreInformation();

		CustomerManagementServiceResponse customerManagementServiceResponse = new CustomerManagementServiceResponse();
		List<CustomerAndStoreInformationResponse> customerAndStoreInformationResponseList = new ArrayList<>();

		for (Object[] customerAndStoreInformation : customerAndStoreInformationList) {
			CustomerAndStoreInformationResponse customerAndStoreInformationResponse = new CustomerAndStoreInformationResponse();

			customerAndStoreInformationResponse.setCustomerSequenceNumber((customerAndStoreInformation[0] != null)
					? Long.valueOf(String.valueOf(customerAndStoreInformation[0]))
					: null);

			customerAndStoreInformationResponse.setFullName(
					(customerAndStoreInformation[1] != null) ? String.valueOf(customerAndStoreInformation[1]) : null);

			if (customerAndStoreInformation[2] != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date registeredOnDate = dateFormat.parse(String.valueOf(customerAndStoreInformation[2]));
				customerAndStoreInformationResponse.setRegisteredOn(registeredOnDate);
			}

			customerAndStoreInformationResponse.setStoreName(
					(customerAndStoreInformation[3] != null) ? String.valueOf(customerAndStoreInformation[3]) : null);

			customerAndStoreInformationResponse.setStoreContact(
					(customerAndStoreInformation[4] != null) ? String.valueOf(customerAndStoreInformation[4]) : null);

			customerAndStoreInformationResponse.setStoreAddress(
					(customerAndStoreInformation[5] != null) ? String.valueOf(customerAndStoreInformation[5]) : null);

			customerAndStoreInformationResponse.setBusinessType(
					(customerAndStoreInformation[6] != null) ? String.valueOf(customerAndStoreInformation[6]) : null);

			customerAndStoreInformationResponseList.add(customerAndStoreInformationResponse);
		}

		customerManagementServiceResponse
				.setCustomerAndStoreInformationResponseList(customerAndStoreInformationResponseList);

		return customerManagementServiceResponse;
	}

	@Transactional(rollbackOn = InvalidInputParametersException.class)
	@Override
	public CustomerManagementServiceResponse updateAllCustomerDetails(
			CustomerManagementServiceRequest customerManagementServiceRequest)
			throws TokenExpiredException, InvalidInputParametersException {

		tokenDao.checkTokenValidity(
				customerManagementServiceRequest.getCustomerBasicDetailsRequest().getCustomerSequenceNumber(),
				customerManagementServiceRequest.getCustomerBasicDetailsRequest().getExpirationDuration());

		CustomerBasicDetailsEntity customerBasicDetailsEntity = getCustomerData(customerManagementServiceRequest);

		if (customerManagementServiceRequest.getCustomerBasicDetailsRequest() != null) {
			CustomerBasicDetailsRequest customerBasicDetailsRequest = customerManagementServiceRequest
					.getCustomerBasicDetailsRequest();

			if (customerBasicDetailsRequest.getCustomerSequenceNumber() == customerBasicDetailsEntity
					.getCustomerSequenceNumber()) {

				if (customerBasicDetailsRequest.isFullNameUpdated()) {
					customerBasicDetailsEntity.setFullName(customerBasicDetailsRequest.getFullName());
				}
				if (customerBasicDetailsRequest.isCustomerIdUpdated()) {
					customerBasicDetailsEntity.setCustomerId(customerBasicDetailsRequest.getCustomerId());
				}
			}
		}

		if (customerManagementServiceRequest.getAddressRequestList() != null) {

			List<AddressRequest> addressRequestList = customerManagementServiceRequest.getAddressRequestList();

			for (AddressRequest addressRequest : addressRequestList) {

				for (AddressDetails addressDetails : customerBasicDetailsEntity.getAddressDetails()) {

					if (addressRequest.getSequenceNumber() == addressDetails.getSequenceNumber()) {

						if (addressRequest.isAddressLine1Updated()) {
							addressDetails.setAddressLine1(addressRequest.getAddressLine1());
						}
						if (addressRequest.isAddressLine2Updated()) {
							addressDetails.setAddressLine2(addressRequest.getAddressLine2());
						}
						if (addressRequest.isLandMarkUpdated()) {
							addressDetails.setLandmark(addressRequest.getLandmark());
						}
						if (addressRequest.isCityUpdated()) {
							addressDetails.setCity(addressRequest.getCity());
						}
						if (addressRequest.isStateUpdated()) {
							addressDetails.setState(addressRequest.getState());
						}
						if (addressRequest.isCountryUpdated()) {
							addressDetails.setCountry(addressRequest.getCountry());
						}
						if (addressRequest.isZipCodeUpdated()) {
							addressDetails.setZipCode(addressRequest.getZipCode());
						}
						if (addressRequest.isMobileNumberUpdated()) {
							addressDetails.setMobileNumber(addressRequest.getMobileNumber());
						}
						if (addressRequest.isLandLineNumberUpdated()) {
							addressDetails.setLandlineNumber(addressRequest.getLandlineNumber());
						}
						if (addressRequest.isEmailAddressUpdated()) {
							addressDetails.setEmailId(addressRequest.getEmailId());
						}
						if (addressRequest.isDefaultAddressUpdated()) {
							addressDetails.setDefaultAddress(addressRequest.getIsDefaultAddress());
						}
						if (addressRequest.isBillingAddressUpdated()) {
							addressDetails.setBillingAddress(addressRequest.getIsBillingAddress());
						}

						break;
					}

				}

			}
		}

		if (customerManagementServiceRequest.getRolesAndPermissionsRequestList() != null) {

			List<RolesAndPermissionsRequest> rolesAndPermissionsRequestList = customerManagementServiceRequest
					.getRolesAndPermissionsRequestList();

			for (RolesAndPermissionsRequest rolesAndPermissionsRequest : rolesAndPermissionsRequestList) {

				for (RolesAndPermissions rolesAndPermissions : customerBasicDetailsEntity.getRolesAndPermissions()) {

					if (rolesAndPermissionsRequest.getRoleId() == rolesAndPermissions.getRoleId()) {

						if (rolesAndPermissionsRequest.isRoleNameUpdated()) {
							rolesAndPermissions.setRoleName(rolesAndPermissionsRequest.getRoleName());
						}
						if (rolesAndPermissionsRequest.isPermission1Updated()) {
							rolesAndPermissions.setPermission1(rolesAndPermissionsRequest.getPermission1());
						}
						if (rolesAndPermissionsRequest.isPermission2Updated()) {
							rolesAndPermissions.setPermission2(rolesAndPermissionsRequest.getPermission2());
						}
						if (rolesAndPermissionsRequest.isPermission3Updated()) {
							rolesAndPermissions.setPermission3(rolesAndPermissionsRequest.getPermission3());
						}
						if (rolesAndPermissionsRequest.isPermission4Updated()) {
							rolesAndPermissions.setPermission4(rolesAndPermissionsRequest.getPermission4());
						}
						if (rolesAndPermissionsRequest.isRoleApprovedDateUpdated()) {
							rolesAndPermissions.setRoleApprovedDate(rolesAndPermissionsRequest.getRoleApprovedDate());
						}
						if (rolesAndPermissionsRequest.isRoleRevokedDateUpdated()) {
							rolesAndPermissions.setRoleRevokedDate(rolesAndPermissionsRequest.getRoleRevokedDate());
						}
						if (rolesAndPermissionsRequest.isPermissionChangeDateUpdated()) {
							rolesAndPermissions
									.setPermissionChangeDate(rolesAndPermissionsRequest.getPermissionChangeDate());
						}
						if (rolesAndPermissionsRequest.isUpdatedPermissionsUpdated()) {
							rolesAndPermissions
									.setUpdatedPermissions(rolesAndPermissionsRequest.getUpdatedPermissions());
						}
						break;
					}

				}

			}
		}
		if (customerManagementServiceRequest.getCustomerPersonalDetailsRequestList() != null) {

			List<CustomerPersonalDetailsRequest> customerPersonalDetailsRequestList = customerManagementServiceRequest
					.getCustomerPersonalDetailsRequestList();

			for (CustomerPersonalDetailsRequest customerPersonalDetailsRequest : customerPersonalDetailsRequestList) {

				for (CustomerPersonalDetails customerPersonalDetails : customerBasicDetailsEntity
						.getCustomerPersonalDetails()) {

					if (customerPersonalDetailsRequest.getSequenceNumber() == customerPersonalDetails
							.getSequenceNumber()) {

						if (customerPersonalDetailsRequest.isFirstNameUpdated()) {
							customerPersonalDetails.setFirstName(customerPersonalDetailsRequest.getFirstName());
						}
						if (customerPersonalDetailsRequest.isLastNameUpdated()) {
							customerPersonalDetails.setLastName(customerPersonalDetailsRequest.getLastName());
						}
						if (customerPersonalDetailsRequest.isMiddleNameUpdated()) {
							customerPersonalDetails.setMiddleName(customerPersonalDetailsRequest.getMiddleName());
						}
						if (customerPersonalDetailsRequest.isAgeUpdated()) {
							customerPersonalDetails.setAge(customerPersonalDetailsRequest.getAge());
						}
						if (customerPersonalDetailsRequest.isGenderUpdated()) {
							customerPersonalDetails.setGender(customerPersonalDetailsRequest.getGender());
						}
						if (customerPersonalDetailsRequest.isCustomerEmailIdUpdated()) {
							customerPersonalDetails
									.setCustomerEmailId(customerPersonalDetailsRequest.getCustomerEmailId());
						}
						if (customerPersonalDetailsRequest.isAlternativeEmailIdUpdated()) {
							customerPersonalDetails
									.setAlternativeEmailId(customerPersonalDetailsRequest.getAlternativeEmailId());
						}
						if (customerPersonalDetailsRequest.isCustomerMobileNumberUpdated()) {
							customerPersonalDetails
									.setCustomerMobileNumber(customerPersonalDetailsRequest.getCustomerMobileNumber());
						}
						if (customerPersonalDetailsRequest.isAlternativeMobileNumberUpdated()) {
							customerPersonalDetails.setAlternativeMobileNumber(
									customerPersonalDetailsRequest.getAlternativeMobileNumber());
						}
						if (customerPersonalDetailsRequest.isCustomerLandlineUpdated()) {
							customerPersonalDetails.setCustomerLandlineNumber(
									customerPersonalDetailsRequest.getCustomerLandlineNumber());
						}
						break;
					}

				}

			}
		}
		if (customerManagementServiceRequest.getCustomerStoreDetailsRequestList() != null) {

			List<CustomerStoreDetailsRequest> customerStoreDetailsRequestList = customerManagementServiceRequest
					.getCustomerStoreDetailsRequestList();

			for (CustomerStoreDetailsRequest customerStoreDetailsRequest : customerStoreDetailsRequestList) {

				boolean isStoreDetailsPresent = false;

				for (CustomerStoreDetails customerStoreDetails : customerBasicDetailsEntity
						.getCustomerStoreDetailsEntity()) {

					isStoreDetailsPresent = true;

					if (customerStoreDetailsRequest.isStoreManagerUpdated()) {
						customerStoreDetails.setStoreManager(customerStoreDetailsRequest.getStoreManager());
					}

					if (customerStoreDetailsRequest.isMarkupFactorUpdated()) {
						customerStoreDetails.setMarkupFactor(customerStoreDetailsRequest.getMarkupFactor());
					}
					if (customerStoreDetailsRequest.isHearAboutUsUpdated()) {
						customerStoreDetails.setHearAboutUs(customerStoreDetailsRequest.getHearAboutUs());
					}
					if (customerStoreDetailsRequest.isMarketsUpdated()) {
						customerStoreDetails.setMarkets(customerStoreDetailsRequest.getMarkets());
					}
					if (customerStoreDetailsRequest.isStoreNameUpdated()) {
						customerStoreDetails.setStoreName(customerStoreDetailsRequest.getStoreName());
					}
					if (customerStoreDetailsRequest.isStoreContactUpdated()) {
						customerStoreDetails.setStoreContact(customerStoreDetailsRequest.getStoreContact());
					}
					if (customerStoreDetailsRequest.isStoreAddressUpdated()) {
						customerStoreDetails.setStoreAddress(customerStoreDetailsRequest.getStoreAddress());
					}
					if (customerStoreDetailsRequest.isStoreCountryUpdated()) {
						customerStoreDetails.setStoreCountry(customerStoreDetailsRequest.getStoreCountry());
					}
					if (customerStoreDetailsRequest.isStoreStateUpdated()) {
						customerStoreDetails.setStoreState(customerStoreDetailsRequest.getStoreState());
					}
					if (customerStoreDetailsRequest.isStoreCityUpdated()) {
						customerStoreDetails.setStoreCity(customerStoreDetailsRequest.getStoreCity());
					}
					if (customerStoreDetailsRequest.isStoreZipCodeUpdated()) {
						customerStoreDetails.setStoreZipCode(customerStoreDetailsRequest.getStoreZipCode());
					}
					if (customerStoreDetailsRequest.isTelephoneUpdated()) {
						customerStoreDetails.setTelephone(customerStoreDetailsRequest.getTelephone());
					}
					if (customerStoreDetailsRequest.isWebsiteUpdated()) {
						customerStoreDetails.setWebsite(customerStoreDetailsRequest.getWebsite());
					}
					if (customerStoreDetailsRequest.isResaleLicenseUpdated()) {
						customerStoreDetails.setResaleLicense(customerStoreDetailsRequest.getResaleLicense());
					}
					if (customerStoreDetailsRequest.isBusinessTypeUpdated()) {
						customerStoreDetails.setBusinessType(customerStoreDetailsRequest.getBusinessType());
					}
					break;
				}
				if (!isStoreDetailsPresent) {

					CustomerStoreDetails customerStoreDetails = new CustomerStoreDetails();
					customerStoreDetails
							.setCustomerSequenceNumber(customerManagementServiceRequest.getCustomerSequenceNumber());
					customerStoreDetails.setStoreManager(customerStoreDetailsRequest.getStoreManager());
					customerStoreDetails.setMarkupFactor(customerStoreDetailsRequest.getMarkupFactor());
					customerStoreDetails.setHearAboutUs(customerStoreDetailsRequest.getHearAboutUs());
					customerStoreDetails.setMarkets(customerStoreDetailsRequest.getMarkets());
					customerStoreDetails.setStoreName(customerStoreDetailsRequest.getStoreName());
					customerStoreDetails.setStoreContact(customerStoreDetailsRequest.getStoreContact());
					customerStoreDetails.setStoreAddress(customerStoreDetailsRequest.getStoreAddress());
					customerStoreDetails.setStoreCountry(customerStoreDetailsRequest.getStoreCountry());
					customerStoreDetails.setStoreState(customerStoreDetailsRequest.getStoreState());
					customerStoreDetails.setStoreCity(customerStoreDetailsRequest.getStoreCity());
					customerStoreDetails.setStoreZipCode(customerStoreDetailsRequest.getStoreZipCode());
					customerStoreDetails.setTelephone(customerStoreDetailsRequest.getTelephone());
					customerStoreDetails.setWebsite(customerStoreDetailsRequest.getWebsite());
					customerStoreDetails.setResaleLicense(customerStoreDetailsRequest.getResaleLicense());
					customerStoreDetails.setBusinessType(customerStoreDetailsRequest.getBusinessType());

					customerBasicDetailsEntity.getCustomerStoreDetailsEntity().add(customerStoreDetails);

				}

			}

		}

		CustomerBasicDetailsEntity customerBasicDetail = customerDao
				.updateAllCustomerDetails(customerBasicDetailsEntity);

		return createResponse(customerBasicDetail);
	}

	private CustomerManagementServiceResponse createResponse(CustomerBasicDetailsEntity customerBasicDetailsEntity) {

		CustomerManagementServiceResponse customerManagementServiceResponse = new CustomerManagementServiceResponse();

		// CustomerBasicDetailsResponse
		if (customerBasicDetailsEntity != null) {

			customerManagementServiceResponse.setCustomerBasicDetailsResponse(
					createResponseForCustomerBasicDetailsEntity(customerBasicDetailsEntity));

		}

		// AddressResponse//
		if (customerBasicDetailsEntity != null && customerBasicDetailsEntity.getAddressDetails() != null
				&& !customerBasicDetailsEntity.getAddressDetails().isEmpty())
			customerManagementServiceResponse.setAddressResponseList(
					createAddressDetailsResponseForEntity(customerBasicDetailsEntity.getAddressDetails()));

		// CustomerPersonalDetailsResponse//
		if (customerBasicDetailsEntity != null && customerBasicDetailsEntity.getCustomerPersonalDetails() != null
				&& !customerBasicDetailsEntity.getCustomerPersonalDetails().isEmpty())
			customerManagementServiceResponse
					.setCustomerPersonalDetailsResponseList(createCustomerPersonalDetailsResponseForEntity(
							customerBasicDetailsEntity.getCustomerPersonalDetails()));

		// RolesAndPermissionsResponse//
		if (customerBasicDetailsEntity != null && customerBasicDetailsEntity.getRolesAndPermissions() != null
				&& !customerBasicDetailsEntity.getRolesAndPermissions().isEmpty())
			customerManagementServiceResponse.setRolesAndPermissionsResponseList(
					createRolesAndPermissionsResponseForEntity(customerBasicDetailsEntity.getRolesAndPermissions()));

		// CustomerStoreDetailsResponse//
		if (customerBasicDetailsEntity != null && customerBasicDetailsEntity.getCustomerStoreDetailsEntity() != null
				&& !customerBasicDetailsEntity.getCustomerStoreDetailsEntity().isEmpty())
			customerManagementServiceResponse
					.setCustomerStoreDetailsResponseList(createCustomerStoreDetailsResponseForEntity(
							customerBasicDetailsEntity.getCustomerStoreDetailsEntity()));

		return customerManagementServiceResponse;
	}

	private CustomerBasicDetailsResponse createResponseForCustomerBasicDetailsEntity(
			CustomerBasicDetailsEntity customerBasicDetailsEntity) {
		CustomerBasicDetailsResponse customerBasicDetailsResponse = new CustomerBasicDetailsResponse();
		customerBasicDetailsResponse.setCustomerSequenceNumber(customerBasicDetailsEntity.getCustomerSequenceNumber());
		customerBasicDetailsResponse.setFullName(customerBasicDetailsEntity.getFullName());
		customerBasicDetailsResponse.setApplicationId(customerBasicDetailsEntity.getApplicationId());
		customerBasicDetailsResponse.setTenantId(customerBasicDetailsEntity.getTenantId());
		customerBasicDetailsResponse.setEmailId(customerBasicDetailsEntity.getCustomerEmail());
		customerBasicDetailsResponse.setUserName(customerBasicDetailsEntity.getUserName());
		customerBasicDetailsResponse.setRegisteredOn(customerBasicDetailsEntity.getRegisteredOn());
		customerBasicDetailsResponse.setCustomerId(customerBasicDetailsEntity.getCustomerId());

		return customerBasicDetailsResponse;
	}

	private List<RolesAndPermissionsResponse> createRolesAndPermissionsResponseForEntity(
			List<RolesAndPermissions> rolesAndPermissions) {

		List<RolesAndPermissionsResponse> rolesAndPermissionsResponseList = new ArrayList<>();

		for (RolesAndPermissions rolesAndPermissionsList : rolesAndPermissions) {

			RolesAndPermissionsResponse rolesAndPermissionsResponse = new RolesAndPermissionsResponse();

			rolesAndPermissionsResponse.setRoleId(rolesAndPermissionsList.getRoleId());
			rolesAndPermissionsResponse.setRoleName(rolesAndPermissionsList.getRoleName());
			rolesAndPermissionsResponse.setPermission1(rolesAndPermissionsList.getPermission1());
			rolesAndPermissionsResponse.setPermission2(rolesAndPermissionsList.getPermission2());
			rolesAndPermissionsResponse.setPermission3(rolesAndPermissionsList.getPermission3());
			rolesAndPermissionsResponse.setPermission4(rolesAndPermissionsList.getPermission4());
			rolesAndPermissionsResponse.setRoleApprovedDate(rolesAndPermissionsList.getRoleApprovedDate());
			rolesAndPermissionsResponse.setRoleRevokedDate(rolesAndPermissionsList.getRoleRevokedDate());
			rolesAndPermissionsResponse.setPermissionChangeDate(rolesAndPermissionsList.getPermissionChangeDate());
			rolesAndPermissionsResponse.setUpdatedPermissions(rolesAndPermissionsList.getUpdatedPermissions());

			rolesAndPermissionsResponseList.add(rolesAndPermissionsResponse);
		}
		return rolesAndPermissionsResponseList;
	}

	private List<CustomerPersonalDetailsResponse> createCustomerPersonalDetailsResponseForEntity(
			List<CustomerPersonalDetails> customerPersonalDetails) {

		List<CustomerPersonalDetailsResponse> customerPersonalDetailsResponseList = new ArrayList<>();

		for (CustomerPersonalDetails customerPersonalDetailsList : customerPersonalDetails) {

			CustomerPersonalDetailsResponse customerPersonalDetailsResponse = new CustomerPersonalDetailsResponse();

			customerPersonalDetailsResponse.setSequenceNumber(customerPersonalDetailsList.getSequenceNumber());
			customerPersonalDetailsResponse.setFirstName(customerPersonalDetailsList.getFirstName());
			customerPersonalDetailsResponse.setLastName(customerPersonalDetailsList.getLastName());
			customerPersonalDetailsResponse.setMiddleName(customerPersonalDetailsList.getMiddleName());
			customerPersonalDetailsResponse.setAge(customerPersonalDetailsList.getAge());
			customerPersonalDetailsResponse.setGender(customerPersonalDetailsList.getGender());
			customerPersonalDetailsResponse.setCustomerEmailId(customerPersonalDetailsList.getCustomerEmailId());
			customerPersonalDetailsResponse.setAlternativeEmailId(customerPersonalDetailsList.getAlternativeEmailId());
			customerPersonalDetailsResponse
					.setCustomerMobileNumber(customerPersonalDetailsList.getCustomerMobileNumber());
			customerPersonalDetailsResponse
					.setAlternativeMobileNumber(customerPersonalDetailsList.getAlternativeMobileNumber());
			customerPersonalDetailsResponse
					.setCustomerLandlineNumber(customerPersonalDetailsList.getCustomerLandlineNumber());

			customerPersonalDetailsResponseList.add(customerPersonalDetailsResponse);
		}
		return customerPersonalDetailsResponseList;

	}

	private List<AddressResponse> createAddressDetailsResponseForEntity(List<AddressDetails> addressDetailsList) {
		List<AddressResponse> addressResponseList = new ArrayList<>();

		for (AddressDetails addressDetails : addressDetailsList) {

			AddressResponse addressResponse = new AddressResponse();

			addressResponse.setSequenceNumber(addressDetails.getSequenceNumber());
			addressResponse.setAddressLine1(addressDetails.getAddressLine1());
			addressResponse.setAddressLine2(addressDetails.getAddressLine2());
			addressResponse.setLandmark(addressDetails.getLandmark());
			addressResponse.setCity(addressDetails.getCity());
			addressResponse.setState(addressDetails.getState());
			addressResponse.setCountry(addressDetails.getCountry());
			addressResponse.setZipCode(addressDetails.getZipCode());
			addressResponse.setMobileNumber(addressDetails.getMobileNumber());
			addressResponse.setLandlineNumber(addressDetails.getLandlineNumber());
			addressResponse.setEmailId(addressDetails.getEmailId());

			addressResponseList.add(addressResponse);
		}

		return addressResponseList;
	}

	private List<CustomerStoreDetailsResponse> createCustomerStoreDetailsResponseForEntity(
			List<CustomerStoreDetails> customerStoreDetailsList) {

		List<CustomerStoreDetailsResponse> customerStoreDetailsResponseList = new ArrayList<>();

		for (CustomerStoreDetails customerStoreDetails : customerStoreDetailsList) {

			CustomerStoreDetailsResponse customerStoreDetailsResponse = new CustomerStoreDetailsResponse();

			customerStoreDetailsResponse.setStoreManager(customerStoreDetails.getStoreManager());
			customerStoreDetailsResponse.setStoreSequenceNumber(customerStoreDetails.getStoreSequenceNumber());
			customerStoreDetailsResponse.setMarkupFactor(customerStoreDetails.getMarkupFactor());
			customerStoreDetailsResponse.setHearAboutUs(customerStoreDetails.getHearAboutUs());
			customerStoreDetailsResponse.setMarkets(customerStoreDetails.getMarkets());
			customerStoreDetailsResponse.setStoreName(customerStoreDetails.getStoreName());
			customerStoreDetailsResponse.setStoreContact(customerStoreDetails.getStoreContact());
			customerStoreDetailsResponse.setStoreAddress(customerStoreDetails.getStoreAddress());
			customerStoreDetailsResponse.setStoreCountry(customerStoreDetails.getStoreCountry());
			customerStoreDetailsResponse.setStoreState(customerStoreDetails.getStoreState());
			customerStoreDetailsResponse.setStoreCity(customerStoreDetails.getStoreCity());
			customerStoreDetailsResponse.setStoreZipCode(customerStoreDetails.getStoreZipCode());
			customerStoreDetailsResponse.setTelephone(customerStoreDetails.getTelephone());
			customerStoreDetailsResponse.setWebsite(customerStoreDetails.getWebsite());
			customerStoreDetailsResponse.setResaleLicense(customerStoreDetails.getResaleLicense());
			customerStoreDetailsResponse.setBusinessType(customerStoreDetails.getBusinessType());

			customerStoreDetailsResponseList.add(customerStoreDetailsResponse);
		}
		return customerStoreDetailsResponseList;
	}

	private CustomerManagementServiceResponse createResponseForGettingCustomerData(
			CustomerBasicDetailsEntity customerBasicDetailsEntity) {
		CustomerManagementServiceResponse customerManagementServiceResponse = new CustomerManagementServiceResponse();

		customerManagementServiceResponse.setCustomerBasicDetailsResponse(
				createResponseForCustomerBasicDetailsEntity(customerBasicDetailsEntity));

		customerManagementServiceResponse.setAddressResponseList(
				createAddressDetailsResponseForEntity(customerBasicDetailsEntity.getAddressDetails()));

		customerManagementServiceResponse.setRolesAndPermissionsResponseList(
				createRolesAndPermissionsResponseForEntity(customerBasicDetailsEntity.getRolesAndPermissions()));

		customerManagementServiceResponse
				.setCustomerPersonalDetailsResponseList(createCustomerPersonalDetailsResponseForEntity(
						customerBasicDetailsEntity.getCustomerPersonalDetails()));
		customerManagementServiceResponse
				.setCustomerStoreDetailsResponseList(createCustomerStoreDetailsResponseForEntity(
						customerBasicDetailsEntity.getCustomerStoreDetailsEntity()));
		return customerManagementServiceResponse;
	}

}
