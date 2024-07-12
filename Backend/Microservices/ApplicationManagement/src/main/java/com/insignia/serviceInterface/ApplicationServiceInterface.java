package com.insignia.serviceInterface;

import java.util.Optional;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.ApplicationRequest;
import com.insignia.model.ApplicationResponse;

public interface ApplicationServiceInterface {

	public ApplicationResponse saveApplicationDetails(ApplicationRequest applicationRequest) throws TokenExpiredException, InvalidInputParametersException;

	public ApplicationResponse updateApplicationDetails(ApplicationRequest applicationRequest)throws TokenExpiredException, InvalidInputParametersException;

	public void deleteByApplicationId(Integer applicationId, Long customerSequenceNumber, Integer expirationDuration)throws TokenExpiredException, InvalidInputParametersException;

	public Optional<ApplicationResponse> getApplicationDetails(Integer applicationId, Long customerSequenceNumber, Integer expirationDuration)throws TokenExpiredException, InvalidInputParametersException;
}
