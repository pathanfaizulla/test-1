package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.AppPreferenceDetailsDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.AppPreferenceDetailsEntity;
import com.insignia.entity.ApplicationEntity;
import com.insignia.model.AppPreferenceRequest;
import com.insignia.model.AppPreferenceResponse;
import com.insignia.model.ApplicationPreferenceRequest;
import com.insignia.model.ApplicationPreferenceResponse;

@ExtendWith(MockitoExtension.class)
public class TestAppPreferenceServiceImpl {

	@InjectMocks
	private AppPreferenceServiceImpl serviceImpl;

	@Mock
	private AppPreferenceDetailsDaoInterface daoRepo;

	@Mock
	private TokenDaoInterface tokenRepo;

	ApplicationPreferenceRequest applicationPreferenceRequest = new ApplicationPreferenceRequest();
	ApplicationPreferenceResponse applicationPreferenceResponse = new ApplicationPreferenceResponse();
	AppPreferenceRequest appPreferenceRequest = new AppPreferenceRequest();
	AppPreferenceResponse appPreferenceResponse = new AppPreferenceResponse();
	AppPreferenceDetailsEntity appPreferenceDetails = new AppPreferenceDetailsEntity();
	ApplicationEntity applicationEntity = new ApplicationEntity();
	List<AppPreferenceDetailsEntity> appPreferenceDetailsEntityList = new ArrayList<>();
	List<AppPreferenceResponse> appPreferenceResponseList = new ArrayList<>();
	List<AppPreferenceRequest> appPreferenceRequestList = new ArrayList<>();
	List<Object[]> expectedData = new ArrayList<>();

	public void dataInitilization() {

		expectedData.add(new Object[] { 5, 5 }); // String and String
		expectedData.add(new Object[] { "Value2", "5" }); // String and String

		applicationEntity.setApplicationId(5);
		applicationEntity.setApplicationName("moneybridge");
		applicationEntity.setApplicationDescription("I am");

		appPreferenceDetails.setApplicationId(5);
		appPreferenceDetails.setCustomerSequenceNumber(8L);
		appPreferenceDetails.setPreferenceId(5L);
		appPreferenceDetails.setPreferenceType("Backend");
		appPreferenceDetails.setPreferenceValue("A");
		appPreferenceDetails.setPreferenceModifiedOn(new Date());

		appPreferenceDetailsEntityList.add(appPreferenceDetails);

		applicationEntity.setAppPreferenceDetailsEntity(appPreferenceDetailsEntityList);

		applicationPreferenceRequest.setApplicationId(5);
		applicationPreferenceRequest.setCustomerSequenceNumber(8L);
		applicationPreferenceRequest.setExpirationDuration(15);
		applicationPreferenceRequest.setApplicationName("moneybridge");
		applicationEntity.setApplicationId(5);

		appPreferenceRequest.setPreferenceType("Backend");
		appPreferenceRequest.setPreferenceValue("A");
		appPreferenceRequestList.add(appPreferenceRequest);

		applicationPreferenceRequest.setAppPreferenceRequestList(appPreferenceRequestList);

		applicationPreferenceResponse.setApplicationName("moneybridge");

		appPreferenceResponse.setPreferenceType("Backend");
		appPreferenceResponse.setPreferenceValue("Hi");

		appPreferenceResponseList.add(appPreferenceResponse);
		applicationPreferenceResponse.setAppPreferenceResponseList(appPreferenceResponseList);

	}

	@Test
	public void testSaveApplicationPreferenceDetails() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(daoRepo.findByApplicationId("moneybridge", 5)).thenReturn(expectedData);

		ApplicationPreferenceResponse saveApplicationPreferenceDetails = serviceImpl
				.saveApplicationPreferenceDetails(applicationPreferenceRequest);
		assertNotNull(saveApplicationPreferenceDetails);

	}

	@Test
	public void testUpdateAndGetApplicationDetails() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		Integer applicationId = 1;
		Long customerSequenceNumber = 8L;

		applicationPreferenceRequest.setCustomerSequenceNumber(8l);

		appPreferenceDetailsEntityList.add(appPreferenceDetails);

		appPreferenceRequest.setPreferenceType("Backend");
		appPreferenceRequest.setPreferenceValue("B");
		appPreferenceRequestList.add(appPreferenceRequest);

		applicationPreferenceRequest.setAppPreferenceRequestList(appPreferenceRequestList);

		appPreferenceDetailsEntityList.add(appPreferenceDetails);

		when(daoRepo.getApplicationPreferenceDetails(customerSequenceNumber, applicationId))
				.thenReturn(appPreferenceDetailsEntityList);

		List<Object[]> mockData = new ArrayList<>();
		Object[] data1 = { 1, "Insignia" };
		mockData.add(data1);
		when(daoRepo.findByApplicationId(any(), any())).thenReturn(mockData);

		serviceImpl.saveApplicationPreferenceDetails(applicationPreferenceRequest);

	}

	@Test
	public void testGetApplicationDetails() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();
		Integer applicationId = 5;
		Long customer_sequence_number = 8L;

		when(daoRepo.getApplicationPreferenceDetails(customer_sequence_number, applicationId))
				.thenReturn(appPreferenceDetailsEntityList);
		ApplicationPreferenceResponse applicationPreferenceDetails = serviceImpl
				.getApplicationPreferenceDetails(applicationPreferenceRequest);
		assertNotNull(applicationPreferenceDetails);

	}

	@Test
	public void testSaveApplicationDetails() throws Exception {
		dataInitilization();

		Method declaredMethod = AppPreferenceServiceImpl.class.getDeclaredMethod("saveApplicationDetails",
				ApplicationPreferenceRequest.class, ApplicationEntity.class);
		declaredMethod.setAccessible(true);
		applicationEntity.setApplicationId(5);
		when(daoRepo.saveApplicationDetails(applicationEntity)).thenReturn(applicationEntity);
		assertNotNull(declaredMethod.invoke(serviceImpl, applicationPreferenceRequest, applicationEntity));
	}
	


}
