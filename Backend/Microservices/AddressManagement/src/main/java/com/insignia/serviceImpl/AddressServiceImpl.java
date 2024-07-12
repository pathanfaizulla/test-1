package com.insignia.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.AddressDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.AddressDetails;
import com.insignia.model.AddressRequest;
import com.insignia.model.AddressResponse;
import com.insignia.serviceInterface.AddressServiceInterface;

@Service
public class AddressServiceImpl implements AddressServiceInterface {

	@Autowired
	private AddressDaoInterface addressDao;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Transactional(rollbackOn = InvalidInputParametersException.class)
	@Modifying
	@Override
	public AddressResponse saveAddressDetails(AddressRequest addressRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(addressRequest.getCustomerSequenceNumber(), addressRequest.getExpirationDuration());

		AddressDetails addressDetails = new AddressDetails();
		addressDetails.setCustomerSequenceNumber(addressRequest.getCustomerSequenceNumber());
		addressDetails.setAddressLine1(addressRequest.getAddressLine1());
		addressDetails.setAddressLine2(addressRequest.getAddressLine2());
		addressDetails.setLandmark(addressRequest.getLandmark());
		addressDetails.setCity(addressRequest.getCity());
		addressDetails.setState(addressRequest.getState());
		addressDetails.setCountry(addressRequest.getCountry());
		addressDetails.setZipCode(addressRequest.getZipCode());
		addressDetails.setMobileNumber(addressRequest.getMobileNumber());
		addressDetails.setLandlineNumber(addressRequest.getLandlineNumber());
		addressDetails.setEmailId(addressRequest.getEmailId());
		addressDetails.setDefaultAddress(addressRequest.getIsDefaultAddress());
		addressDetails.setBillingAddress(addressRequest.getIsBillingAddress());

		addressDao.saveAddressDetails(addressDetails);

		AddressResponse addressResponse = createAddressDetailsResponseForEntity(addressDetails);

		return addressResponse;

	}

	@Transactional(rollbackOn = InvalidInputParametersException.class)
	@Override
	public AddressResponse updateAddressDetails(AddressRequest addressRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(addressRequest.getCustomerSequenceNumber(), addressRequest.getExpirationDuration());
		
		AddressDetails addressDetails = addressDao.findByUserAddress(addressRequest.getCustomerSequenceNumber(),
				addressRequest.getSequenceNumber());

		AddressResponse addressResponse = new AddressResponse();

		if (addressDetails != null) {
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
			addressResponse = createAddressDetailsResponseForEntity(addressDao.updateAddressDetails(addressDetails));
		}

		return addressResponse;
	}

	@Override
	public void deleteByAddressId(Integer sequenceNumber, Long customerSequenceNumber, Integer expirationDuration)
			throws TokenExpiredException, InvalidInputParametersException {
		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);
		addressDao.deleteByAddressId(sequenceNumber);
	}

	@Transactional
	@Override
	public void deleteAddressForCustomer(Long customerSequenceNumber, Integer expirationDuration)
			throws TokenExpiredException, InvalidInputParametersException {
		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);

		addressDao.deleteAddressForCustomer(customerSequenceNumber);

	}

	@Transactional
	@Override
	public List<AddressResponse> getAddressList(Long customerSequenceNumber, Integer expirationDuration)
			throws TokenExpiredException, InvalidInputParametersException {
		
		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);

		List<AddressDetails> listOfCustomerAdddresListdata = addressDao.getAddressDetails(customerSequenceNumber);

		List<AddressResponse> addressDetailsResponseList = new ArrayList<>();
		if (!listOfCustomerAdddresListdata.isEmpty()) {

			for (AddressDetails addressData : listOfCustomerAdddresListdata) {
				addressDetailsResponseList.add(createAddressDetailsResponseForEntity(addressData));
			}

		}
		return addressDetailsResponseList;
	}

	private AddressResponse createAddressDetailsResponseForEntity(AddressDetails addressDetails) {

		AddressResponse addressResponse = new AddressResponse();

		if (addressDetails != null) {
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
		}
		return addressResponse;
	}

}
