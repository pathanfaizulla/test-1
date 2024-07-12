package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.CurrencyDetailsDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.CurrencyDetails;
import com.insignia.model.CurrencyDetailsRequest;
import com.insignia.model.CurrencyDetailsResponse;

@ExtendWith(MockitoExtension.class)
public class TestCurrencyDetailsServiceImpl {

	@InjectMocks
	private CurrencyDetailsServiceImpl currencyDetailsServiceImpl;

	@Mock
	private CurrencyDetailsDaoInterface currencyDetailsDaoInterface;

	@Mock
	private TokenDaoInterface tokenRepo;

	CurrencyDetailsRequest currencyDetailsRequest = new CurrencyDetailsRequest();
	CurrencyDetailsRequest currencyDetailsRequestForUpdate = new CurrencyDetailsRequest();

	CurrencyDetailsResponse currencyDetailsResponse = new CurrencyDetailsResponse();

	List<CurrencyDetailsResponse> currencyDetailsResponseList = new ArrayList<>();
	CurrencyDetails currencyDetails = new CurrencyDetails();

	List<CurrencyDetails> currencyDetailsList = new ArrayList<>();

	public void dataInitilization() {

		currencyDetailsRequest.setCustomerSequenceNumber(5L);
		currencyDetailsRequest.setExpirationDuration(15);
		currencyDetailsRequest.setCurrencyName("indian rupee");
		currencyDetailsRequest.setCurrencyDescription("for india");
		currencyDetailsRequest.setApplicationId("hinges-design");
		currencyDetailsRequest.setTenantId("LU008");
		currencyDetailsRequest.setCurrencyCode("INR");
		currencyDetailsRequest.setBaseRateCurrency("inr");

		currencyDetailsResponse.setSequenceNumber(5);
		currencyDetailsResponse.setCurrencyName("indian rupee");
		currencyDetailsResponse.setCurrencyDescription("for india");
		currencyDetailsResponse.setCurrencyCode("INR");
		currencyDetailsResponse.setBaseRateCurrency("inr");

		currencyDetailsResponseList.add(currencyDetailsResponse);

		currencyDetails.setCurrencyName("indian rupee");
		currencyDetails.setCurrencyDescription("for india");
		currencyDetails.setApplicationId("hinges-design");
		currencyDetails.setTenantId("LU008");
		currencyDetails.setCurrencyCode("INR");
		currencyDetails.setBaseRateCurrency("inr");

		currencyDetailsList.add(currencyDetails);
	}

	@Test
	public void testSaveCurrencyDetails()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(currencyDetailsDaoInterface.findByCurrencyName(currencyDetailsRequest.getCurrencyName(),
				currencyDetailsRequest.getApplicationId(), currencyDetailsRequest.getTenantId())).thenReturn(null);

		when(currencyDetailsDaoInterface.saveCurrencyDetails(any(CurrencyDetails.class))).thenReturn(currencyDetails);

		CurrencyDetailsResponse saveCurrencyDetails = currencyDetailsServiceImpl
				.saveCurrencyDetails(currencyDetailsRequest);

		assertNotNull(saveCurrencyDetails);
	}

	@Test
	public void testSaveCurrencyDetailsDuplicateDataException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();
		
		String currencyName = "indian rupee";
		currencyDetails.setSequenceNumber(1);
		currencyDetailsRequest.setSequenceNumber(1);
		
		when(currencyDetailsDaoInterface.findByCurrencyName(currencyDetailsRequest.getCurrencyName(),
				currencyDetailsRequest.getApplicationId(), currencyDetailsRequest.getTenantId()))
				.thenReturn(currencyName);

		assertThrows(InvalidInputParametersException.class, () -> {

			currencyDetailsServiceImpl.saveCurrencyDetails(currencyDetailsRequest);
		});
		verify(currencyDetailsDaoInterface, times(1)).findByCurrencyName(currencyDetailsRequest.getCurrencyName(),
				currencyDetailsRequest.getApplicationId(), currencyDetailsRequest.getTenantId());
	}

	@Test
	public void testUpdateCurrencyDetailsNameAndCurrencyDetailsDescription()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		currencyDetailsRequestForUpdate.setCustomerSequenceNumber(105L);
		currencyDetailsRequestForUpdate.setSequenceNumber(20);
		currencyDetailsRequestForUpdate.setCurrencyName("indian rupee");
		currencyDetailsRequestForUpdate.setCurrencyDescription("for india");
		currencyDetailsRequestForUpdate.setApplicationId("hinges-design");
		currencyDetailsRequestForUpdate.setTenantId("LU008");
		currencyDetailsRequestForUpdate.setCurrencyCode("INR");
		currencyDetailsRequestForUpdate.setBaseRateCurrency("inr");

		Integer sequenceNumber = 20;
		String applicationId = "hinges-design";
		String tenantId = "LU008";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(currencyDetailsDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(currencyDetails));

		when(currencyDetailsDaoInterface.updateCurrencyDetails(currencyDetails)).thenReturn(currencyDetails);

		CurrencyDetailsResponse updateCurrencyDetails = currencyDetailsServiceImpl
				.updateCurrencyDetails(currencyDetailsRequestForUpdate);
		assertNotNull(updateCurrencyDetails);
	}

	@Test
	public void testUpdateCurrencyDescription()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		currencyDetailsRequestForUpdate.setCustomerSequenceNumber(105L);
		currencyDetailsRequestForUpdate.setSequenceNumber(20);
		currencyDetailsRequestForUpdate.setCurrencyName("indian rupee");
		currencyDetailsRequestForUpdate.setCurrencyDescription("for india");
		currencyDetailsRequestForUpdate.setApplicationId("hinges-design");
		currencyDetailsRequestForUpdate.setTenantId("LU008");
		currencyDetailsRequestForUpdate.setCurrencyCode("INR");
		currencyDetailsRequestForUpdate.setBaseRateCurrency("inr");

		Integer sequenceNumber = 20;
		String applicationId = "hinges-design";
		String tenantId = "LU008";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(currencyDetailsDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(currencyDetails));

		when(currencyDetailsDaoInterface.updateCurrencyDetails(currencyDetails)).thenReturn(currencyDetails);

		CurrencyDetailsResponse updateCurrencyDetails = currencyDetailsServiceImpl
				.updateCurrencyDetails(currencyDetailsRequestForUpdate);
		assertNotNull(updateCurrencyDetails);
	}

	@Test
	public void testUpdateCurrencyName()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		currencyDetailsRequestForUpdate.setCustomerSequenceNumber(105L);
		currencyDetailsRequestForUpdate.setSequenceNumber(20);
		currencyDetailsRequestForUpdate.setCurrencyName("indian rupee");
		currencyDetailsRequestForUpdate.setCurrencyDescription("for india");
		currencyDetailsRequestForUpdate.setApplicationId("hinges-design");
		currencyDetailsRequestForUpdate.setTenantId("LU008");
		currencyDetailsRequestForUpdate.setCurrencyCode("INR");
		currencyDetailsRequestForUpdate.setBaseRateCurrency("inr");
		currencyDetailsRequestForUpdate.setBaseRate(new BigDecimal("23.00"));
		
		Integer sequenceNumber = 20;
		String applicationId = "hinges-design";
		String tenantId = "LU008";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(currencyDetailsDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(currencyDetails));

		when(currencyDetailsDaoInterface.updateCurrencyDetails(currencyDetails)).thenReturn(currencyDetails);

		CurrencyDetailsResponse currencyDetailsResponse = currencyDetailsServiceImpl
				.updateCurrencyDetails(currencyDetailsRequestForUpdate);
		assertNotNull(currencyDetailsResponse);
	}

	@Test
	public void testUpdateCurrencyDetailsNoExistException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		when(currencyDetailsDaoInterface.findBySequenceNumber(currencyDetailsRequest.getSequenceNumber(),
				currencyDetailsRequest.getApplicationId(), currencyDetailsRequest.getTenantId()))
				.thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class, () -> {

			currencyDetailsServiceImpl.updateCurrencyDetails(currencyDetailsRequest);
		});
		verify(currencyDetailsDaoInterface, times(1)).findBySequenceNumber(currencyDetailsRequest.getSequenceNumber(),
				currencyDetailsRequest.getApplicationId(), currencyDetailsRequest.getTenantId());
	}

	@Test
	public void testDeleteCurrencyDetails()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();
		String currencyName = "indian rupee";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(currencyDetailsDaoInterface.findByCurrencyName(currencyDetailsRequest.getCurrencyName(),
				currencyDetailsRequest.getApplicationId(), currencyDetailsRequest.getTenantId()))
				.thenReturn(currencyName);

		doNothing().when(currencyDetailsDaoInterface).deleteCurrencyDetails(currencyDetailsRequest.getCurrencyName(),
				currencyDetailsRequest.getApplicationId(), currencyDetailsRequest.getTenantId());

		currencyDetailsServiceImpl.deleteCurrencyDetails(currencyDetailsRequest);

		verify(currencyDetailsDaoInterface, times(1)).deleteCurrencyDetails(currencyDetailsRequest.getCurrencyName(),
				currencyDetailsRequest.getApplicationId(), currencyDetailsRequest.getTenantId());
	}

	@Test
	public void testDeleteCurrencyDetailsNotExistedException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		when(currencyDetailsDaoInterface.findByCurrencyName(currencyDetailsRequest.getCurrencyName(),
				currencyDetailsRequest.getApplicationId(), currencyDetailsRequest.getTenantId())).thenReturn(null);

		assertThrows(InvalidInputParametersException.class, () -> {

			currencyDetailsServiceImpl.deleteCurrencyDetails(currencyDetailsRequest);
		});
		verify(currencyDetailsDaoInterface, times(1)).findByCurrencyName(currencyDetailsRequest.getCurrencyName(),
				currencyDetailsRequest.getApplicationId(), currencyDetailsRequest.getTenantId());
	}

	@Test
	public void testGetAllCurrencyDetails()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(currencyDetailsDaoInterface.getAllCurrencyDetails(currencyDetailsRequest.getApplicationId(),
				currencyDetailsRequest.getTenantId())).thenReturn(currencyDetailsList);

		List<CurrencyDetailsResponse> allCurrencyDetails= currencyDetailsServiceImpl
				.getAllCurrencyDetails(currencyDetailsRequest);

		assertNotNull(allCurrencyDetails);
	}

}
