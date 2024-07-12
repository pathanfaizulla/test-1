package com.insignia.daoImpl;

import java.util.Optional;

import jakarta.persistence.EntityManager;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.insignia.constants.ApplicationConstants;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.daoInterface.ApplicationDaoInterface;
import com.insignia.entity.ApplicationEntity;
import com.insignia.repo.ApplicationRepository;

@Repository
public class ApplicationDaoImpl implements ApplicationDaoInterface {

	@Autowired
	private ApplicationRepository applicationRepo;

	@Autowired
	private EntityManager entityManager;

	@Override
	public ApplicationEntity saveApplicationDetails(ApplicationEntity applicationEntity)
			throws InvalidInputParametersException {
		try {
			return applicationRepo.save(applicationEntity);
		} catch (DataIntegrityViolationException e) {
			throw new InvalidInputParametersException(ApplicationConstants.validateApplicationDetailsErrorCode,
					ApplicationConstants.validateApplicationDetailsMessage);
		}
	}

	@Override
	public ApplicationEntity updateApplicationDetails(ApplicationEntity applicationEntity)
			throws InvalidInputParametersException {
		try {
			return entityManager.merge(applicationEntity);
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				throw new InvalidInputParametersException(ApplicationConstants.validateApplicationDetailsErrorCode,
						ApplicationConstants.validateApplicationDetailsMessage);
			}
			throw e;
		}
	}

	@Override
	public void deleteByApplicationId(Integer applicationId) {
		applicationRepo.deleteById(applicationId);

	}

	@Override
	public Optional<ApplicationEntity> getApplicationDetails(Integer applicationId) {
		return applicationRepo.findById(applicationId);
	}

}
