package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.AddressDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.AddressDetails;
import com.insignia.model.AddressRequest;
import com.insignia.model.AddressResponse;

@ExtendWith(MockitoExtension.class)
public class TestAddressServiceImpl {

	@InjectMocks
	private AddressServiceImpl serviceImpl;

	@Mock
	private AddressDaoInterface daoRepo;
	
	@Mock
	private TokenDaoInterface tokenRepo;

	AddressRequest addressRequest = new AddressRequest();
	AddressResponse addressRes = new AddressResponse();
	AddressDetails addressDetails = new AddressDetails();
	
	@BeforeEach
	public void dataInitilization() {
		addressRequest.setCustomerSequenceNumber(8L);
		addressRequest.setSequenceNumber(8);
		addressRequest.setAddressLine1("VinayakaTemple");
		addressRequest.setAddressLine2("CinemaRoad");
		addressRequest.setLandmark("Opp:ApolloHospital");
		addressRequest.setCity("Kakinada");
		addressRequest.setState("AndhraPradesh");
		addressRequest.setCountry("India");
		addressRequest.setEmailId("lakshmisidharth678@gmail.com");
		addressRequest.setZipCode("8765");
		addressRequest.setMobileNumber("9875689378");
		addressRequest.setLandlineNumber("98765895");
		addressRequest.setisBillingAddress(false);
		addressRequest.setIsDefaultAddress(false);
		addressRequest.setExpirationDuration(5);

		addressRes.setAddressLine1("VinayakaTemple");
		addressRes.setAddressLine2("CinemaRoad");
		addressRes.setLandmark("Opp:ApolloHospital");
		addressRes.setCity("Kakinada");
		addressRes.setState("AndhraPradesh");
		addressRes.setCountry("India");
		addressRes.setEmailId("lakshmisidharth678@gmail.com");
		addressRes.setZipCode("8765");
		addressRes.setMobileNumber("9875689378");
		addressRes.setLandlineNumber("98765895");
		addressRes.setBillingAddress(false);
		addressRes.setDefaultAddress(false);
	}
	public AddressDetails getAddressDetailsEntity() {

		
		AddressDetails addressDetails = new AddressDetails();

		addressDetails.setSequenceNumber(8);
		addressDetails.setAddressLine1("VinayakaTemple");
		addressDetails.setAddressLine2("CinemaRoad");
		addressDetails.setLandmark("Opp:ApolloHospital");
		addressDetails.setCity("Kakinada");
		addressDetails.setState("AndhraPradesh");
		addressDetails.setCountry("India");
		addressDetails.setEmailId("lakshmisidharth678@gmail.com");
		addressDetails.setZipCode("8765");
		addressDetails.setMobileNumber("9875689378");
		addressDetails.setLandlineNumber("98765895");
		
		return addressDetails;
	}


	@Test
	void testSaveAddress() throws TokenExpiredException, InvalidInputParametersException {
		Long customer_sequence_number = 8L;
		Integer expirationDuration = 5;
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(customer_sequence_number, expirationDuration);
		tokenRepo.checkTokenValidity(customer_sequence_number, expirationDuration);

		assertNotNull(serviceImpl.saveAddressDetails(addressRequest));

	}

	@Test
	void testUpdateAddress() throws TokenExpiredException, InvalidInputParametersException {
		Long customerSequenceNumber = 8L;
		Integer sequenceNumber = 8;
		Integer expirationDuration = 5;
		dataInitilization();
		getAddressDetailsEntity();
	
		AddressDetails addressDetailsResponse = addressDetails;
		addressDetailsResponse.setAddressLine1("5677554");

		System.out.println(addressDetails);
		when(daoRepo.findByUserAddress(customerSequenceNumber, sequenceNumber)).thenReturn(addressDetails);
		
		doNothing().when(tokenRepo).checkTokenValidity(customerSequenceNumber, expirationDuration);
		tokenRepo.checkTokenValidity(customerSequenceNumber, expirationDuration);

		serviceImpl.updateAddressDetails(addressRequest);
		
		assertNotNull(serviceImpl.updateAddressDetails(addressRequest));

	}

	@Test
	void testDeleteByAddressId() throws TokenExpiredException, InvalidInputParametersException {
		Integer sequenceNumber = 1;
		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;

		doNothing().when(tokenRepo).checkTokenValidity(customerSequenceNumber, expirationDuration);
		tokenRepo.checkTokenValidity(customerSequenceNumber, sequenceNumber);

		serviceImpl.deleteByAddressId(sequenceNumber,customerSequenceNumber, expirationDuration);
		verify(daoRepo, times(1)).deleteByAddressId(sequenceNumber);

	}

	@Test
	void testDeleteAddressForCustomer() throws TokenExpiredException, InvalidInputParametersException {

		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;

		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(customerSequenceNumber, expirationDuration);
		
		tokenRepo.checkTokenValidity(customerSequenceNumber, expirationDuration);

		serviceImpl.deleteAddressForCustomer(customerSequenceNumber, expirationDuration);
		
		verify(daoRepo, times(1)).deleteAddressForCustomer(customerSequenceNumber);

	}

	@Test
	void testGetAddressList() throws TokenExpiredException, InvalidInputParametersException {

		Long customerSequenceNumber = 8L;
		Integer expirationDuration = 5;
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(customerSequenceNumber, expirationDuration);
		tokenRepo.checkTokenValidity(customerSequenceNumber, expirationDuration);

		
		serviceImpl.getAddressList(customerSequenceNumber, expirationDuration);
		verify(daoRepo, times(1)).getAddressDetails(customerSequenceNumber);

	}

}
