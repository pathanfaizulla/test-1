package com.insignia.daoInterface;

import java.util.Optional;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.entity.ApplicationEntity;

public interface ApplicationDaoInterface {

	public ApplicationEntity saveApplicationDetails(ApplicationEntity applicationEntity) throws InvalidInputParametersException;
	
	public ApplicationEntity updateApplicationDetails(ApplicationEntity applicationEntity) throws InvalidInputParametersException;
	
	public void deleteByApplicationId(Integer applicationId);
	
	public Optional<ApplicationEntity> getApplicationDetails(Integer applicationId);
}

