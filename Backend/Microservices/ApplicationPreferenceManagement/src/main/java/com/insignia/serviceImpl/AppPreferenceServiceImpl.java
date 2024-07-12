package com.insignia.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.constants.AppPreferenceConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.AppPreferenceDetailsDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.AppPreferenceDetailsEntity;
import com.insignia.entity.ApplicationEntity;
import com.insignia.model.AppPreferenceRequest;
import com.insignia.model.AppPreferenceResponse;
import com.insignia.model.ApplicationPreferenceRequest;
import com.insignia.model.ApplicationPreferenceResponse;
import com.insignia.serviceInterface.AppPreferenceServiceInterface;

@Service
public class AppPreferenceServiceImpl implements AppPreferenceServiceInterface {

	@Autowired
	private AppPreferenceDetailsDaoInterface appPreferenceDao;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Transactional
	@Override
	public ApplicationPreferenceResponse saveApplicationPreferenceDetails(
			ApplicationPreferenceRequest applicationPreferenceRequest)
			throws TokenExpiredException, InvalidInputParametersException {

		tokenDao.checkTokenValidity(applicationPreferenceRequest.getCustomerSequenceNumber(),
				applicationPreferenceRequest.getExpirationDuration());

		ApplicationEntity applicationManagementEntity = new ApplicationEntity();

		List<Object[]> isAppicationDataExists = appPreferenceDao.findByApplicationId(
				applicationPreferenceRequest.getApplicationName(), applicationPreferenceRequest.getApplicationId());

		if (isAppicationDataExists != null && !isAppicationDataExists.isEmpty()) {

			applicationPreferenceRequest.setApplicationId(Integer.valueOf(isAppicationDataExists.get(0)[0].toString()));
			applicationPreferenceRequest.setApplicationName(isAppicationDataExists.get(0)[1].toString());

			List<AppPreferenceDetailsEntity> fetchApplicationDetailsByApplicationName = getListOfAppPreferenceDetailsEntity(
					applicationPreferenceRequest);

			List<AppPreferenceRequest> appPreferenceDataFromRequest = applicationPreferenceRequest
					.getAppPreferenceRequestList();

			applicationManagementEntity.setApplicationName(applicationPreferenceRequest.getApplicationName());
			applicationManagementEntity.setApplicationId(applicationPreferenceRequest.getApplicationId());
			applicationManagementEntity.setAppPreferenceDetailsEntity(fetchApplicationDetailsByApplicationName);

			List<AppPreferenceDetailsEntity> cleanUpApplicationDetailsData = new ArrayList<>();

			for (AppPreferenceRequest appPreferenceRequest : appPreferenceDataFromRequest) {
				boolean preferenceMatch = false;
				boolean preferenceTypeExists = false;

				for (AppPreferenceDetailsEntity detailsEntity : fetchApplicationDetailsByApplicationName) {
					if (appPreferenceRequest.getPreferenceType().equalsIgnoreCase(detailsEntity.getPreferenceType())) {
						preferenceTypeExists = true;

						if (appPreferenceRequest.getPreferenceValue() != null && preferenceTypeExists) {

							// Scenario 1: Update an existing record
							if (!appPreferenceRequest.getPreferenceValue()
									.equalsIgnoreCase(detailsEntity.getPreferenceValue())) {
								detailsEntity.setPreferenceValue(appPreferenceRequest.getPreferenceValue());
								detailsEntity.setPreferenceModifiedOn(new Date());
								appPreferenceDao.updateApplicationDetails(detailsEntity);
							}
							preferenceMatch = true;

						} else if (appPreferenceRequest.getPreferenceValue() == null) {
							// Scenario 3: Delete an existing record
							appPreferenceDao.deleteByPreferenceId(detailsEntity.getPreferenceId());
							cleanUpApplicationDetailsData.add(detailsEntity);
							preferenceMatch = true;
						}
					}
				}

				if (!preferenceMatch) {
					// Scenario 2: Create a new record
					if (!preferenceTypeExists && appPreferenceRequest.getPreferenceValue() != null) {

						AppPreferenceDetailsEntity newPreference = new AppPreferenceDetailsEntity();
						newPreference
								.setCustomerSequenceNumber(applicationPreferenceRequest.getCustomerSequenceNumber());
						newPreference.setApplicationId(applicationPreferenceRequest.getApplicationId());
						newPreference.setPreferenceType(appPreferenceRequest.getPreferenceType());
						newPreference.setPreferenceValue(appPreferenceRequest.getPreferenceValue());
						newPreference.setPreferenceModifiedOn(new Date());
						appPreferenceDao.saveAppPreferenceDetails(newPreference);
						applicationManagementEntity.getAppPreferenceDetailsEntity().add(newPreference);

					}
				}
			}

			if (!cleanUpApplicationDetailsData.isEmpty())
				applicationManagementEntity.getAppPreferenceDetailsEntity().removeAll(cleanUpApplicationDetailsData);

			return createApplicationPreferenceResponse(applicationManagementEntity);
		} else {
			return createApplicationPreferenceResponse(
					saveApplicationDetails(applicationPreferenceRequest, applicationManagementEntity));
		}

	}

	@Override
	public ApplicationPreferenceResponse getApplicationPreferenceDetails(
			ApplicationPreferenceRequest applicationPreferenceRequest)
			throws TokenExpiredException, InvalidInputParametersException {

		tokenDao.checkTokenValidity(applicationPreferenceRequest.getCustomerSequenceNumber(),
				applicationPreferenceRequest.getExpirationDuration());

		ApplicationPreferenceResponse applicationAndAppPreferenceResponse = new ApplicationPreferenceResponse();

		List<AppPreferenceDetailsEntity> fetchApplicationDetailsByApplicationName = getListOfAppPreferenceDetailsEntity(
				applicationPreferenceRequest);

		if (fetchApplicationDetailsByApplicationName.isEmpty()) {
			throw new InvalidInputParametersException(AppPreferenceConstants.validateAppPreferenceDetailsErrorCode,
					AppPreferenceConstants.validateAppPreferenceDetails);
		}

		if (fetchApplicationDetailsByApplicationName != null) {
			applicationAndAppPreferenceResponse.setAppPreferenceResponseList(
					createListOfAppPreferenceResponseForEntity(fetchApplicationDetailsByApplicationName));

		}
		applicationAndAppPreferenceResponse.setApplicationId(applicationPreferenceRequest.getApplicationId());
		applicationAndAppPreferenceResponse.setApplicationName(applicationPreferenceRequest.getApplicationName());

		return applicationAndAppPreferenceResponse;
	}

	private List<AppPreferenceDetailsEntity> getListOfAppPreferenceDetailsEntity(
			ApplicationPreferenceRequest applicationPreferenceRequest) throws InvalidInputParametersException {

		if (applicationPreferenceRequest.getApplicationId() == null
				|| applicationPreferenceRequest.getApplicationName() == null) {

			applicationIdAndNameExist(applicationPreferenceRequest);
		}
		List<AppPreferenceDetailsEntity> fetchApplicationDetailsByApplicationName = appPreferenceDao
				.getApplicationPreferenceDetails(applicationPreferenceRequest.getCustomerSequenceNumber(),
						applicationPreferenceRequest.getApplicationId());

		return fetchApplicationDetailsByApplicationName;
	}

	private ApplicationEntity saveApplicationDetails(ApplicationPreferenceRequest applicationPreferenceRequest,
			ApplicationEntity applicationManagementEntity) {

		if (applicationPreferenceRequest.getApplicationName() != null) {
			applicationManagementEntity.setApplicationName(applicationPreferenceRequest.getApplicationName());
		}
		ApplicationEntity saveApplicationDetails = appPreferenceDao.saveApplicationDetails(applicationManagementEntity);
		Integer applicationId = saveApplicationDetails.getApplicationId();

		if (applicationId == null) {

			applicationIdAndNameExist(applicationPreferenceRequest);
		}

		List<AppPreferenceDetailsEntity> applicationPreferenceDetails = new ArrayList<>();
		List<AppPreferenceRequest> appPreferenceRequestList = applicationPreferenceRequest
				.getAppPreferenceRequestList();

		if (appPreferenceRequestList != null && !appPreferenceRequestList.isEmpty()) {
			for (AppPreferenceRequest appPreferenceRequest : appPreferenceRequestList) {
				if (appPreferenceRequest.getPreferenceValue() != null) {

					AppPreferenceDetailsEntity appPreferenceDetailsEntity = new AppPreferenceDetailsEntity();
					appPreferenceDetailsEntity.setApplicationId(applicationId);
					appPreferenceDetailsEntity
							.setCustomerSequenceNumber(applicationPreferenceRequest.getCustomerSequenceNumber());
					appPreferenceDetailsEntity.setPreferenceType(appPreferenceRequest.getPreferenceType());
					appPreferenceDetailsEntity.setPreferenceValue(appPreferenceRequest.getPreferenceValue());
					appPreferenceDetailsEntity.setPreferenceModifiedOn(new Date());

					applicationPreferenceDetails.add(appPreferenceDetailsEntity);
				}
			}
			applicationManagementEntity.setAppPreferenceDetailsEntity(applicationPreferenceDetails);
		}

		return appPreferenceDao.saveApplicationDetails(applicationManagementEntity);
	}

	private void applicationIdAndNameExist(ApplicationPreferenceRequest applicationPreferenceRequest) {
		List<Object[]> applicationIdAndNameExist = appPreferenceDao.findByApplicationId(
				applicationPreferenceRequest.getApplicationName(), applicationPreferenceRequest.getApplicationId());

		if (applicationIdAndNameExist != null && !applicationIdAndNameExist.isEmpty()) {
			applicationPreferenceRequest
					.setApplicationId(Integer.valueOf(applicationIdAndNameExist.get(0)[0].toString()));
			applicationPreferenceRequest.setApplicationName(applicationIdAndNameExist.get(0)[1].toString());
		}
	}

	private ApplicationPreferenceResponse createApplicationPreferenceResponse(ApplicationEntity applicationEntity) {
		ApplicationPreferenceResponse applicationAndAppPreferenceResponse = new ApplicationPreferenceResponse();

		if (applicationEntity != null) {

			applicationAndAppPreferenceResponse.setApplicationName(applicationEntity.getApplicationName());
			applicationAndAppPreferenceResponse.setApplicationId(applicationEntity.getApplicationId());

			if (applicationEntity.getAppPreferenceDetailsEntity() != null
					&& !applicationEntity.getAppPreferenceDetailsEntity().isEmpty()) {
				applicationAndAppPreferenceResponse.setAppPreferenceResponseList(
						createListOfAppPreferenceResponseForEntity(applicationEntity.getAppPreferenceDetailsEntity()));
			}
		}
		return applicationAndAppPreferenceResponse;
	}

	private List<AppPreferenceResponse> createListOfAppPreferenceResponseForEntity(
			List<AppPreferenceDetailsEntity> appPreferenceDetails) {

		List<AppPreferenceResponse> appPreferenceResponseList = new ArrayList<>();

		for (AppPreferenceDetailsEntity appPreferenceDetailsEntity : appPreferenceDetails) {

			AppPreferenceResponse appPreferenceResponse = new AppPreferenceResponse();

			appPreferenceResponse.setPreferenceValue(appPreferenceDetailsEntity.getPreferenceValue());
			appPreferenceResponse.setPreferenceType(appPreferenceDetailsEntity.getPreferenceType());

			appPreferenceResponseList.add(appPreferenceResponse);
		}
		return appPreferenceResponseList;
	}

}
