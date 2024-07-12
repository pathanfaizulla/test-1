package com.insignia.serviceImpl;

import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.ApplicationDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ApplicationEntity;
import com.insignia.model.ApplicationRequest;
import com.insignia.model.ApplicationResponse;
import com.insignia.serviceInterface.ApplicationServiceInterface;

@Service
public class ApplicationServiceImpl implements ApplicationServiceInterface {

	@Autowired
	private ApplicationDaoInterface applicationDao;

	@Autowired
	private TokenDaoInterface tokenDao;

	@Transactional(rollbackOn = InvalidInputParametersException.class)
	@Override
	public ApplicationResponse saveApplicationDetails(ApplicationRequest applicationRequest)
			throws TokenExpiredException, InvalidInputParametersException {

		tokenDao.checkTokenValidity(applicationRequest.getCustomerSequenceNumber(),
				applicationRequest.getExpirationDuration());

		ApplicationEntity applicationEntity = new ApplicationEntity();
		applicationEntity.setApplicationId(applicationRequest.getApplicationId());
		applicationEntity.setApplicationName(applicationRequest.getApplicationName());
		applicationEntity.setApplicationDescription(applicationRequest.getApplicationDescription());

		ApplicationEntity saveApplicationDetails = applicationDao.saveApplicationDetails(applicationEntity);

		ApplicationResponse applicationResponse = new ApplicationResponse();

		applicationResponse.setApplicationName(saveApplicationDetails.getApplicationName());
		applicationResponse.setApplicationDescription(saveApplicationDetails.getApplicationDescription());

		return applicationResponse;
	}

	@Transactional(rollbackOn = InvalidInputParametersException.class)
	@Override
	public ApplicationResponse updateApplicationDetails(ApplicationRequest applicationRequest)
			throws TokenExpiredException, InvalidInputParametersException {

		tokenDao.checkTokenValidity(applicationRequest.getCustomerSequenceNumber(),
				applicationRequest.getExpirationDuration());

		Optional<ApplicationEntity> applicationDetails = applicationDao
				.getApplicationDetails(applicationRequest.getApplicationId());

		ApplicationResponse applicationResponse = new ApplicationResponse();

		if (applicationDetails != null) {

			ApplicationEntity applicationEntity = applicationDetails.get();

			if (applicationRequest.isApplicationNameUpdate()) {
				applicationEntity.setApplicationName(applicationRequest.getApplicationName());
			}
			if (applicationRequest.isApplicationDescriptionUpdated()) {
				applicationEntity.setApplicationDescription(applicationRequest.getApplicationDescription());
			}

			ApplicationEntity updateApplicationDetails = applicationDao.updateApplicationDetails(applicationEntity);

			applicationResponse.setApplicationName(updateApplicationDetails.getApplicationName());
			applicationResponse.setApplicationDescription(updateApplicationDetails.getApplicationDescription());
		}
		return applicationResponse;
	}

	@Transactional
	@Override
	public void deleteByApplicationId(Integer applicationId, Long customerSequenceNumber, Integer expirationDuration)
			throws TokenExpiredException, InvalidInputParametersException {

		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);

		applicationDao.deleteByApplicationId(applicationId);

	}

	@Transactional
	@Override
	public Optional<ApplicationResponse> getApplicationDetails(Integer applicationId, Long customerSequenceNumber,
			Integer expirationDuration) throws TokenExpiredException, InvalidInputParametersException {

		tokenDao.checkTokenValidity(customerSequenceNumber, expirationDuration);

		Optional<ApplicationEntity> applicationDetails = applicationDao.getApplicationDetails(applicationId);
		ApplicationResponse applicationResponse = new ApplicationResponse();

		if (applicationDetails != null) {

			ApplicationEntity entity = applicationDetails.get();

			applicationResponse.setApplicationId(entity.getApplicationId());
			applicationResponse.setApplicationName(entity.getApplicationName());
			applicationResponse.setApplicationDescription(entity.getApplicationDescription());
		}
		return Optional.of(applicationResponse);
	}

}
