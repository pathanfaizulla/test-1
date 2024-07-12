package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.ApplicationDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ApplicationEntity;
import com.insignia.model.ApplicationRequest;
import com.insignia.model.ApplicationResponse;

@ExtendWith(MockitoExtension.class)
public class TestApplicationServiceImpl {

	@InjectMocks
	private ApplicationServiceImpl serviceImpl;

	@Mock
	private ApplicationDaoInterface daoRepo;

	@Mock
	private TokenDaoInterface tokenRepo;

	ApplicationEntity applicationEntityDetails = new ApplicationEntity();
	ApplicationRequest applicationRequest = new ApplicationRequest();
	ApplicationResponse applicationResponse = new ApplicationResponse();

	@BeforeEach
	public void dataInitilization() {
		applicationRequest.setApplicationId(5);
		applicationRequest.setApplicationName("moneybridge");
		applicationRequest.setApplicationDescription("I am Java Developer in Insignia");
		applicationRequest.setCustomerSequenceNumber(8L);
		applicationRequest.setExpirationDuration(15);

		applicationEntityDetails.setApplicationId(5);
		applicationEntityDetails.setApplicationName("moneybridge");
		applicationEntityDetails.setApplicationDescription("I am Java Developer in Insignia");

		applicationResponse.setApplicationId(5);
		applicationResponse.setApplicationName("moneybridge");
		applicationResponse.setApplicationDescription("I am Java Developer in Insignia");
		applicationResponse.setErrorCode("333");
		applicationResponse.setErrorMessage("Invalid");

	}

//	@Test
//	void testTokenExpiredException() throws TokenExpiredException, InvalidInputParametersException {
//		Integer applicationId = 5;
//		Long customer_sequence_number = 8L;
//		Integer expirationDuration = 5;
//
//		ApplicationRequest applicationRequest = new ApplicationRequest();
//		applicationRequest.setCustomerSequenceNumber(customer_sequence_number);
//		applicationRequest.setExpirationDuration(expirationDuration);
//
//		doNothing().when(tokenRepo).checkTokenValidity(customer_sequence_number, expirationDuration);
//		tokenRepo.checkTokenValidity(customer_sequence_number, expirationDuration);
//
//		assertThrows(TokenExpiredException.class, () -> {
//			serviceImpl.saveApplicationDetails(applicationRequest);
//		});
//
//		assertThrows(TokenExpiredException.class, () -> {
//			serviceImpl.updateApplicationDetails(applicationRequest);
//		});
//
//		assertThrows(TokenExpiredException.class, () -> {
//			serviceImpl.getApplicationDetails(applicationId, customer_sequence_number, expirationDuration);
//		});
//
//		assertThrows(TokenExpiredException.class, () -> {
//			serviceImpl.deleteByApplicationId(applicationId, customer_sequence_number, expirationDuration);
//		});
//	}

	@Test
	public void testSaveApplicationDetails() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(daoRepo.saveApplicationDetails(any(ApplicationEntity.class))).thenReturn(applicationEntityDetails);

		ApplicationResponse saveApplicationDetails = serviceImpl.saveApplicationDetails(applicationRequest);
		assertNotNull(saveApplicationDetails);
	}

	@Test
	public void testUpdateApplicationDetails() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		ApplicationEntity applicationEntityResponse = new ApplicationEntity();
		Optional<ApplicationEntity> response = Optional.ofNullable(applicationEntityResponse);

		when(daoRepo.getApplicationDetails(5)).thenReturn(response);
		when(daoRepo.updateApplicationDetails(any(ApplicationEntity.class))).thenReturn(applicationEntityDetails);

		ApplicationResponse updateApplicationDetails = serviceImpl.updateApplicationDetails(applicationRequest);

		assertNotNull(updateApplicationDetails);
	}

	@Test
	public void testGetApplicationDetails() throws TokenExpiredException, InvalidInputParametersException {

		Long customerSequenceNumber = 123L;
		Integer expirationDuration = 5;
		Integer applicationId = 5;

		ApplicationEntity applicationEntityResponse = new ApplicationEntity();
		Optional<ApplicationEntity> response = Optional.ofNullable(applicationEntityResponse);

		when(daoRepo.getApplicationDetails(applicationId)).thenReturn(response);

		Optional<ApplicationResponse> applicationDetails = serviceImpl.getApplicationDetails(applicationId,
				customerSequenceNumber, expirationDuration);
		assertTrue(applicationDetails.isPresent());
		ApplicationResponse applicationResponse = applicationDetails.get();
		assertNotNull(applicationResponse);
	}

	@Test
	public void testDeleteApplicationDetails() throws TokenExpiredException, InvalidInputParametersException {
		Long customerSequenceNumber = 123L;
		Integer expirationDuration = 5;
		Integer applicationId = 5;

		doNothing().when(daoRepo).deleteByApplicationId(applicationId);
		serviceImpl.deleteByApplicationId(applicationId, customerSequenceNumber, expirationDuration);

		verify(daoRepo, times(1)).deleteByApplicationId(applicationId);

	}

}
