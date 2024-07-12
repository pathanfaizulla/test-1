package com.insignia.serviceInterface;

import java.util.List;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.AddressRequest;
import com.insignia.model.AddressResponse;

public interface AddressServiceInterface {

	public AddressResponse saveAddressDetails(AddressRequest addressRequest) throws InvalidInputParametersException, TokenExpiredException;

	public void deleteAddressForCustomer(Long customerSequenceNumber, Integer expirationDuration) throws TokenExpiredException, InvalidInputParametersException;

	public AddressResponse updateAddressDetails(AddressRequest addressRequest) throws InvalidInputParametersException, TokenExpiredException;

	public List<AddressResponse> getAddressList(Long customerSequenceNumber, Integer expirationDuration)
			throws TokenExpiredException, InvalidInputParametersException;

	public void deleteByAddressId(Integer sequenceNumber, Long customerSequenceNumber, Integer expirationDuration)
			throws TokenExpiredException, InvalidInputParametersException;

}
