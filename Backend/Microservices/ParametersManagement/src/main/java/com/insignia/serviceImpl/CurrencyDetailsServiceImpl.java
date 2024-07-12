package com.insignia.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.constants.ParametersManagementConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.CurrencyDetailsDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.CurrencyDetails;
import com.insignia.model.CurrencyDetailsRequest;
import com.insignia.model.CurrencyDetailsResponse;
import com.insignia.serviceInterface.CurrencyDetailsServiceInterface;

import jakarta.transaction.Transactional;

@Service
public class CurrencyDetailsServiceImpl implements CurrencyDetailsServiceInterface {

	@Autowired
	private CurrencyDetailsDaoInterface currencyDetailsDaoInterface;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Transactional
	@Override
	public CurrencyDetailsResponse saveCurrencyDetails(CurrencyDetailsRequest currencyDetailsRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(currencyDetailsRequest.getCustomerSequenceNumber(),
				currencyDetailsRequest.getExpirationDuration());

		CurrencyDetailsResponse currencyDetailsResponse = null;
		Object findByCurrencyName = currencyDetailsDaoInterface.findByCurrencyName(currencyDetailsRequest.getCurrencyName(),
				currencyDetailsRequest.getApplicationId(), currencyDetailsRequest.getTenantId());

		if (findByCurrencyName != null
				&& findByCurrencyName.toString().equalsIgnoreCase(currencyDetailsRequest.getCurrencyName())) {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.duplicateDataInCurrencyDetailsErrorCode,
					ParametersManagementConstants.duplicateDataInCurrencyDetailsMessage);
		}

		CurrencyDetails currencyDetails = new CurrencyDetails();
		currencyDetails.setApplicationId(currencyDetailsRequest.getApplicationId());
		currencyDetails.setTenantId(currencyDetailsRequest.getTenantId());
		currencyDetails.setCurrencyName(currencyDetailsRequest.getCurrencyName());
		currencyDetails.setCurrencyDescription(currencyDetailsRequest.getCurrencyDescription());
		currencyDetails.setCurrencyCode(currencyDetailsRequest.getCurrencyCode());
		currencyDetails.setBaseRate(currencyDetailsRequest.getBaseRate());
		currencyDetails.setBaseRateCurrency(currencyDetailsRequest.getBaseRateCurrency());
		
		CurrencyDetails currencyDetailsEntity = currencyDetailsDaoInterface.saveCurrencyDetails(currencyDetails);

		currencyDetailsResponse = createResponseForCurrencyDeatailsEntity(currencyDetailsEntity);

		return currencyDetailsResponse;
	}

	@Transactional
	@Override
	public CurrencyDetailsResponse updateCurrencyDetails(CurrencyDetailsRequest currencyDetailsRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(currencyDetailsRequest.getCustomerSequenceNumber(),
				currencyDetailsRequest.getExpirationDuration());

		Optional<CurrencyDetails> currencyDetailsList = currencyDetailsDaoInterface.findBySequenceNumber(
				currencyDetailsRequest.getSequenceNumber(), currencyDetailsRequest.getApplicationId(),
				currencyDetailsRequest.getTenantId());

		if (currencyDetailsList.isPresent()) {
			CurrencyDetails currencyDetails = currencyDetailsList.get();

			if (currencyDetailsRequest.isCurrencyNameUpdated()) {
				currencyDetails.setCurrencyName(currencyDetailsRequest.getCurrencyName());
			}
			if (currencyDetailsRequest.isCurrencyDescriptionUpdated()) {
				currencyDetails.setCurrencyDescription(currencyDetailsRequest.getCurrencyDescription());
			}
			if (currencyDetailsRequest.isCurrencyCodeUpdated()) {
				currencyDetails.setCurrencyCode(currencyDetailsRequest.getCurrencyCode());
			}
			if (currencyDetailsRequest.isBaseRateIsUpdated()) {
				currencyDetails.setBaseRate(currencyDetailsRequest.getBaseRate());
			}
			if (currencyDetailsRequest.isBaseRateCurrencyIsUpdated()) {
				currencyDetails.setBaseRateCurrency(currencyDetailsRequest.getBaseRateCurrency());
			}
			
			currencyDetails = currencyDetailsDaoInterface.updateCurrencyDetails(currencyDetails);

			return createResponseForCurrencyDeatailsEntity(currencyDetails);
		} else {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.currencyDetailsNotExistedErrorCode,
					ParametersManagementConstants.currencyDetailsNotExistedMessage);
		}
	}

	@Transactional
	@Override
	public void deleteCurrencyDetails(CurrencyDetailsRequest currencyDetailsRequest)
			throws InvalidInputParametersException, TokenExpiredException {
		tokenDao.checkTokenValidity(currencyDetailsRequest.getCustomerSequenceNumber(),
				currencyDetailsRequest.getExpirationDuration());

		Object currencyName = currencyDetailsDaoInterface.findByCurrencyName(currencyDetailsRequest.getCurrencyName(),
				currencyDetailsRequest.getApplicationId(), currencyDetailsRequest.getTenantId());
		if (currencyName != null
				&& currencyName.toString().equalsIgnoreCase(currencyDetailsRequest.getCurrencyName())) {
			currencyDetailsDaoInterface.deleteCurrencyDetails(currencyDetailsRequest.getCurrencyName(),
					currencyDetailsRequest.getApplicationId(), currencyDetailsRequest.getTenantId());
		} else {
			throw new InvalidInputParametersException(
					ParametersManagementConstants.currencyDetailsNotExistedErrorCode,
					ParametersManagementConstants.currencyDetailsNotExistedMessage);
		}

	}

	@Transactional
	@Override
	public List<CurrencyDetailsResponse> getAllCurrencyDetails(CurrencyDetailsRequest currencyDetailsRequest)
			throws TokenExpiredException {

		tokenDao.checkTokenValidity(currencyDetailsRequest.getCustomerSequenceNumber(),
				currencyDetailsRequest.getExpirationDuration());

		List<CurrencyDetailsResponse> currencyDetailsResponseList = new ArrayList<>();
		List<CurrencyDetails> currencyDetailsList = currencyDetailsDaoInterface
				.getAllCurrencyDetails(currencyDetailsRequest.getApplicationId(), currencyDetailsRequest.getTenantId());

		if (currencyDetailsList != null) {
			for (CurrencyDetails currencyDetails : currencyDetailsList) {
				CurrencyDetailsResponse currencyDetailsResponse = createResponseForCurrencyDeatailsEntity(currencyDetails);
				currencyDetailsResponseList.add(currencyDetailsResponse);
			}
		}

		return currencyDetailsResponseList;
	}

	private CurrencyDetailsResponse createResponseForCurrencyDeatailsEntity(CurrencyDetails currencyDetails) {
		CurrencyDetailsResponse currencyDetailsResponse = new CurrencyDetailsResponse();
		currencyDetailsResponse.setSequenceNumber(currencyDetails.getSequenceNumber());
		currencyDetailsResponse.setCurrencyName(currencyDetails.getCurrencyName());
		currencyDetailsResponse.setCurrencyDescription(currencyDetails.getCurrencyDescription());
		currencyDetailsResponse.setCurrencyCode(currencyDetails.getCurrencyCode());
		currencyDetailsResponse.setBaseRate(currencyDetails.getBaseRate());
		currencyDetailsResponse.setBaseRateCurrency(currencyDetails.getBaseRateCurrency());
		return currencyDetailsResponse;
	}

}
