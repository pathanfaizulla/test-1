package com.insignia.serviceInterface;

import java.text.ParseException;
import java.util.List;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.CustomListManagementRequest;
import com.insignia.model.CustomListManagementResponse;

public interface CustomListServiceInterface {

	public CustomListManagementResponse saveCustomList(CustomListManagementRequest customListManagementRequest)
			throws InvalidInputParametersException, TokenExpiredException, ParseException;

	public CustomListManagementResponse getCustomListForCustomer(Long customerSequenceNumber,
			Integer expirationDuration) throws TokenExpiredException;

	public void deleteAllCustomListForCustomer(Long customerSequenceNumber, Integer expirationDuration)
			throws TokenExpiredException;

	public void deleteCustomList(Long customerSequenceNumber, List<String> customListNameList,
			Integer expirationDuration) throws TokenExpiredException;

	public CustomListManagementResponse createCustomList(CustomListManagementRequest customListManagementRequest)
			throws InvalidInputParametersException, TokenExpiredException, ParseException;
	

	public CustomListManagementResponse updateCustomListName(CustomListManagementRequest customListManagementRequest)
			throws TokenExpiredException, InvalidInputParametersException;
}
