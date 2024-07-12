package com.insignia.serviceInterface;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.ApplicationPreferenceRequest;
import com.insignia.model.ApplicationPreferenceResponse;

public interface AppPreferenceServiceInterface {

	public ApplicationPreferenceResponse saveApplicationPreferenceDetails(
			ApplicationPreferenceRequest applicationPreferenceRequest) throws TokenExpiredException, InvalidInputParametersException;
	
	
	public ApplicationPreferenceResponse getApplicationPreferenceDetails(
			ApplicationPreferenceRequest applicationPreferenceRequest) throws TokenExpiredException, InvalidInputParametersException;

	
}
