package com.insignia.daoInterface;

import java.util.List;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.entity.AddressDetails;

public interface AddressDaoInterface {

	public AddressDetails saveAddressDetails(AddressDetails addressDetails)  throws InvalidInputParametersException;

	public AddressDetails updateAddressDetails(AddressDetails addressDetails) throws InvalidInputParametersException;

	public void deleteAddressForCustomer(Long customerSequenceNumber);

	public void deleteByAddressId(Integer sequenceNumber);

	public List<AddressDetails> getAddressDetails(Long customerSequenceNumber);

	public AddressDetails findByUserAddress(Long customersequencenumber, Integer sequenceNumber);

}
