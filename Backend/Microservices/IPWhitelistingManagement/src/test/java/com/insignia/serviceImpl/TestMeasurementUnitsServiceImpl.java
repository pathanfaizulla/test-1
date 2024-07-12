package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.PreferredIpDetailsDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.PreferredIpDetails;
import com.insignia.model.CustomerIpDetails;
import com.insignia.model.IpDetails;
import com.insignia.model.PreferredIpDetailsRequest;
import com.insignia.model.PreferredIpDetailsResponse;

@ExtendWith(MockitoExtension.class)
public class TestMeasurementUnitsServiceImpl {

	@InjectMocks
	private PreferredIpDetailsServiceImpl preferredIpDetailsServiceImpl;

	@Mock
	private PreferredIpDetailsDaoInterface preferredIpDetailsDaoInterface;

	@Mock
	private TokenDaoInterface tokenRepo;

	List<CustomerIpDetails> customerIpDetailsList = new ArrayList<>();
	List<IpDetails> ipDetailsList = new ArrayList<>();
	List<PreferredIpDetailsResponse> preferredIpDetailsResponseList = new ArrayList<>();
	List<PreferredIpDetails> preferredIpDetailsList = new ArrayList<>();

	PreferredIpDetailsRequest preferredIpDetailsRequest = new PreferredIpDetailsRequest();
	PreferredIpDetailsResponse preferredIpDetailsResponse = new PreferredIpDetailsResponse();
	CustomerIpDetails customerIpDetails = new CustomerIpDetails();
	IpDetails ipDetails = new IpDetails();

	PreferredIpDetails preferredIpDetails = new PreferredIpDetails();

	PreferredIpDetails preferredIpDetailsEntity = new PreferredIpDetails();

	public void dataInitilization() {
		preferredIpDetailsRequest.setCustomerSequenceNumber(5L);
		preferredIpDetailsRequest.setExpirationDuration(15);
		preferredIpDetailsRequest.setIpDetails("130.0.0.0");
		preferredIpDetailsRequest.setIpType("IPv4");

		ipDetails.setIpDetails("130.0.0.0");
		ipDetails.setIpType("IPv4");

		ipDetailsList.add(ipDetails);

		customerIpDetails.setCustomerSequenceNumber(5L);
		customerIpDetails.setIpDetailsList(ipDetailsList);
		customerIpDetailsList.add(customerIpDetails);
		preferredIpDetailsResponse.setCustomerIpDetailsList(customerIpDetailsList);
		preferredIpDetailsResponseList.add(preferredIpDetailsResponse);

		preferredIpDetails.setCustomerSequenceNumber(5L);
		preferredIpDetails.setIpDetails("130.0.0.0");
		preferredIpDetails.setIpType("IPv4");

		preferredIpDetailsEntity.setCustomerSequenceNumber(5L);
		preferredIpDetailsEntity.setIpDetails("130.0.0.0");
		preferredIpDetailsEntity.setIpType("IPv4");

		preferredIpDetailsList.add(preferredIpDetails);
		preferredIpDetailsList.add(preferredIpDetailsEntity);
	}

	@Test
	public void testSavePreferredIpDetails() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(preferredIpDetailsDaoInterface.ipDetailsExistOrNot(any(), any())).thenReturn(null);

		when(preferredIpDetailsDaoInterface.saveIPWhitelistingManagement(preferredIpDetails))
				.thenReturn(preferredIpDetails);

		PreferredIpDetailsResponse savePreferredIpDetails = preferredIpDetailsServiceImpl
				.savePreferredIpDetails(preferredIpDetailsRequest);

		assertNotNull(savePreferredIpDetails);
	}

	@Test
	public void testSavePreferredIpDetailsDuplicateDataException()
			throws InvalidInputParametersException, TokenExpiredException {

		dataInitilization();

		when(preferredIpDetailsDaoInterface.ipDetailsExistOrNot(any(), any())).thenReturn(customerIpDetails);

		assertThrows(InvalidInputParametersException.class, () -> {

			preferredIpDetailsServiceImpl.savePreferredIpDetails(preferredIpDetailsRequest);
		});
		verify(preferredIpDetailsDaoInterface, times(1)).ipDetailsExistOrNot(any(), any());
	}

	@Test
	public void testDeletePreferredIpDetailsWithIpDetails()
			throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());
		when(preferredIpDetailsDaoInterface.ipDetailsExistOrNot(any(), any())).thenReturn("exampleDetails");

		preferredIpDetailsServiceImpl.deletePreferredIpDetails(preferredIpDetailsRequest);

		verify(preferredIpDetailsDaoInterface, times(1)).deleteByIpDetails(any(), any());
	}

	@Test
	public void testDeletePreferredIpDetailsWithCustomerSequenceNumber()
			throws InvalidInputParametersException, TokenExpiredException {

		preferredIpDetailsRequest = new PreferredIpDetailsRequest();
		preferredIpDetailsRequest.setIpDetails(null);
		preferredIpDetailsRequest.setCustomerSequenceNumber(5L);
		List<Long> customerSequenceNumberList = Arrays.asList(5L);
		doNothing().when(tokenRepo).checkTokenValidity(any(), any());
		when(preferredIpDetailsDaoInterface.detailsExistByCustomerSequeceNumber(any()))
				.thenReturn(customerSequenceNumberList);

		preferredIpDetailsServiceImpl.deletePreferredIpDetails(preferredIpDetailsRequest);

		verify(preferredIpDetailsDaoInterface, times(1)).detailsExistByCustomerSequeceNumber(any());
	}

	@Test
	public void testDeletePreferredIpDetailsWithCustomerSequenceNumberNotFoundException()
			throws InvalidInputParametersException, TokenExpiredException {

		preferredIpDetailsRequest.setCustomerSequenceNumber(1L);

		when(preferredIpDetailsDaoInterface.detailsExistByCustomerSequeceNumber(any())).thenReturn(Arrays.asList());

		assertThrows(InvalidInputParametersException.class,
				() -> preferredIpDetailsServiceImpl.deletePreferredIpDetails(preferredIpDetailsRequest));
		verify(preferredIpDetailsDaoInterface, times(1)).detailsExistByCustomerSequeceNumber(any());
	}

	@Test
	public void testDeletePreferredIpDetailsIsNotExistInTheSystemException()
			throws InvalidInputParametersException, TokenExpiredException {

		preferredIpDetailsRequest.setIpDetails("130.0.0.0");
		preferredIpDetailsRequest.setCustomerSequenceNumber(1L);

		assertThrows(InvalidInputParametersException.class,
				() -> preferredIpDetailsServiceImpl.deletePreferredIpDetails(preferredIpDetailsRequest));
		verify(preferredIpDetailsDaoInterface, times(1)).ipDetailsExistOrNot(any(), any());
	}

	@Test
	public void testGetAllPreferredIpDetailsWithIpDetails()
			throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(preferredIpDetailsDaoInterface.getDetailsByCustomerSequeceNumber(any()))
				.thenReturn(preferredIpDetailsList);

		List<PreferredIpDetailsResponse> allPreferredIpDetails = preferredIpDetailsServiceImpl
				.getAllPreferredIpDetails(preferredIpDetailsRequest);

		assertNotNull(allPreferredIpDetails);
	}

	@Test
	public void testGetAllPreferredIpDetailsWithCustomerSequenceNumber()
			throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();
		preferredIpDetailsRequest.setCustomerSequenceNumber(-1L);

		when(preferredIpDetailsDaoInterface.getAllIpDetails()).thenReturn(preferredIpDetailsList);

		List<PreferredIpDetailsResponse> allPreferredIpDetails = preferredIpDetailsServiceImpl
				.getAllPreferredIpDetails(preferredIpDetailsRequest);

		assertNotNull(allPreferredIpDetails);
	}
}