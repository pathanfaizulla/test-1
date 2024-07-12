package com.insignia.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.constants.CommonConstant;
import com.insignia.constants.PreferredIpDetailsContants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.PreferredIpDetailsDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.PreferredIpDetails;
import com.insignia.model.CustomerIpDetails;
import com.insignia.model.IpDetails;
import com.insignia.model.PreferredIpDetailsRequest;
import com.insignia.model.PreferredIpDetailsResponse;
import com.insignia.serviceInterface.PreferredIpDetailsServiceInterface;

@Service
public class PreferredIpDetailsServiceImpl implements PreferredIpDetailsServiceInterface {

	@Autowired
	private PreferredIpDetailsDaoInterface peferredIpDetailsDaoInterface;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Transactional
	@Override
	public PreferredIpDetailsResponse savePreferredIpDetails(PreferredIpDetailsRequest preferredIpDetailsRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(preferredIpDetailsRequest.getCustomerSequenceNumber(),
				preferredIpDetailsRequest.getExpirationDuration());

		Object ipDetails = peferredIpDetailsDaoInterface.ipDetailsExistOrNot(
				preferredIpDetailsRequest.getCustomerSequenceNumber(), preferredIpDetailsRequest.getIpDetails());
		if (ipDetails != null) {
			throw new InvalidInputParametersException(
					PreferredIpDetailsContants.duplicateDataInPreferredIpDetailsErrorCode,
					PreferredIpDetailsContants.duplicateDataInPreferredIpDetailsMessage);
		}
		PreferredIpDetails preferredIpDetails = new PreferredIpDetails();

		preferredIpDetails.setCustomerSequenceNumber(preferredIpDetailsRequest.getCustomerSequenceNumber());
		preferredIpDetails.setIpDetails(preferredIpDetailsRequest.getIpDetails());
		preferredIpDetails.setIpType(preferredIpDetailsRequest.getIpType());
		PreferredIpDetails preferredIpDetailsEntity = peferredIpDetailsDaoInterface
				.saveIPWhitelistingManagement(preferredIpDetails);

		return createResponseForPreferredIpDetailsResponse(preferredIpDetailsEntity);
	}

	@Transactional
	@Override
	public List<PreferredIpDetailsResponse> getAllPreferredIpDetails(
			PreferredIpDetailsRequest preferredIpDetailsRequest) throws TokenExpiredException {

		if (preferredIpDetailsRequest.getCustomerSequenceNumber() != CommonConstant.nonLoggedCustomerSequenceNumber) {
			tokenDao.checkTokenValidity(preferredIpDetailsRequest.getCustomerSequenceNumber(), preferredIpDetailsRequest.getExpirationDuration());
		}
		List<PreferredIpDetailsResponse> preferredIpDetailsResponseList = new ArrayList<>();
		if (preferredIpDetailsRequest.getCustomerSequenceNumber() != null && preferredIpDetailsRequest.getCustomerSequenceNumber() != CommonConstant.nonLoggedCustomerSequenceNumber) {

			List<PreferredIpDetails> preferredIpDetailsList = peferredIpDetailsDaoInterface
					.getDetailsByCustomerSequeceNumber(preferredIpDetailsRequest.getCustomerSequenceNumber());

			createResponseForPreferredIpDetailsResponseList(preferredIpDetailsList, preferredIpDetailsResponseList);
		} else {

			List<PreferredIpDetails> preferredIpDetailsList = peferredIpDetailsDaoInterface.getAllIpDetails();

			createResponseForPreferredIpDetailsResponseList(preferredIpDetailsList, preferredIpDetailsResponseList);
		}
		return preferredIpDetailsResponseList;
	}

	@Transactional
	@Override
	public void deletePreferredIpDetails(PreferredIpDetailsRequest preferredIpDetailsRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDao.checkTokenValidity(preferredIpDetailsRequest.getCustomerSequenceNumber(),
				preferredIpDetailsRequest.getExpirationDuration());

		if (preferredIpDetailsRequest.getIpDetails() != null) {

			Object detailsExistByIpDetails = peferredIpDetailsDaoInterface.ipDetailsExistOrNot(
					preferredIpDetailsRequest.getCustomerSequenceNumber(), preferredIpDetailsRequest.getIpDetails());
			if (detailsExistByIpDetails != null) {
				peferredIpDetailsDaoInterface.deleteByIpDetails(preferredIpDetailsRequest.getCustomerSequenceNumber(),
						preferredIpDetailsRequest.getIpDetails());
			} else {
				throw new InvalidInputParametersException(
						PreferredIpDetailsContants.detailsWithIpDetailsNotExistErrorCode,
						PreferredIpDetailsContants.detailsWithIpDetailsNotExistMessage);
			}
		} else if (preferredIpDetailsRequest.getCustomerSequenceNumber() != null) {
			List<Long> detailsExistByCustomerSequeceNumber = peferredIpDetailsDaoInterface
					.detailsExistByCustomerSequeceNumber(preferredIpDetailsRequest.getCustomerSequenceNumber());
			if (!detailsExistByCustomerSequeceNumber.isEmpty()) {
				peferredIpDetailsDaoInterface
						.deleteByCustomerSequenceNumber(preferredIpDetailsRequest.getCustomerSequenceNumber());
			} else {
				throw new InvalidInputParametersException(
						PreferredIpDetailsContants.detailsWithCustomerSequenceNumberNotExistErrorCode,
						PreferredIpDetailsContants.detailsWithCustomerSequenceNumberNotExistMessage);
			}
		}
	}

	private PreferredIpDetailsResponse createResponseForPreferredIpDetailsResponse(
			PreferredIpDetails preferredIpDetails) {
		PreferredIpDetailsResponse preferredIpDetailsResponse = new PreferredIpDetailsResponse();

		CustomerIpDetails customerIpDetailsResponse = new CustomerIpDetails();
		List<CustomerIpDetails> customerIpDetailsList = new ArrayList<>();

		List<IpDetails> ipDetailsList = new ArrayList<>();
		IpDetails ipDetails = new IpDetails();

		ipDetails.setIpType(preferredIpDetails.getIpType());
		ipDetails.setIpDetails(preferredIpDetails.getIpDetails());
		ipDetailsList.add(ipDetails);
		customerIpDetailsResponse.setCustomerSequenceNumber(preferredIpDetails.getCustomerSequenceNumber());

		customerIpDetailsResponse.setIpDetailsList(ipDetailsList);
		customerIpDetailsList.add(customerIpDetailsResponse);

		preferredIpDetailsResponse.setCustomerIpDetailsList(customerIpDetailsList);
		return preferredIpDetailsResponse;
	}

	private void createResponseForPreferredIpDetailsResponseList(List<PreferredIpDetails> preferredIpDetailsList,
			List<PreferredIpDetailsResponse> preferredIpDetailsResponseList) {

		for (PreferredIpDetails preferredIpDetails : preferredIpDetailsList) {
			PreferredIpDetailsResponse preferredIpDetailsResponse = null;

			for (PreferredIpDetailsResponse ipDetailsResponse : preferredIpDetailsResponseList) {
				if (!ipDetailsResponse.getCustomerIpDetailsList().isEmpty()
						&& ipDetailsResponse.getCustomerIpDetailsList().get(0)
								.getCustomerSequenceNumber() == preferredIpDetails.getCustomerSequenceNumber()) {
					preferredIpDetailsResponse = ipDetailsResponse;
					break;
				}
			}

			if (preferredIpDetailsResponse == null) {
				preferredIpDetailsResponse = new PreferredIpDetailsResponse();
				preferredIpDetailsResponse.setCustomerIpDetailsList(new ArrayList<>());

				CustomerIpDetails customerIpDetails = new CustomerIpDetails();
				customerIpDetails.setCustomerSequenceNumber(preferredIpDetails.getCustomerSequenceNumber());

				List<IpDetails> ipDetailsList = new ArrayList<>();
				customerIpDetails.setIpDetailsList(ipDetailsList);
				preferredIpDetailsResponse.getCustomerIpDetailsList().add(customerIpDetails);

				preferredIpDetailsResponseList.add(preferredIpDetailsResponse);
			}

			IpDetails ipDetails = new IpDetails();
			ipDetails.setIpType(preferredIpDetails.getIpType());
			ipDetails.setIpDetails(preferredIpDetails.getIpDetails());

			if (preferredIpDetailsResponse.getCustomerIpDetailsList().isEmpty()) {
				preferredIpDetailsResponse.setCustomerIpDetailsList(new ArrayList<>());
			}

			preferredIpDetailsResponse.getCustomerIpDetailsList().get(0).getIpDetailsList().add(ipDetails);
		}
	}

}
