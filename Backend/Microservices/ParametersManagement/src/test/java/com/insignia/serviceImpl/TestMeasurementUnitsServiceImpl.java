package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.insignia.daoInterface.MeasurementUnitsDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.MeasurementUnits;
import com.insignia.model.MeasurementUnitRequest;
import com.insignia.model.MeasurementUnitResponse;

@ExtendWith(MockitoExtension.class)
public class TestMeasurementUnitsServiceImpl {

	@InjectMocks
	private MeasurementUnitServiceImpl measurementUnitServiceImpl;

	@Mock
	private MeasurementUnitsDaoInterface measurementUnitsDaoInterface;

	@Mock
	private TokenDaoInterface tokenRepo;

	MeasurementUnitRequest measurementUnitRequest = new MeasurementUnitRequest();
	MeasurementUnitResponse measurementUnitResponse = new MeasurementUnitResponse();
	MeasurementUnitRequest measurementUnitRequestForUpdate = new MeasurementUnitRequest();
	List<MeasurementUnitResponse> measurementUnitResponseList = new ArrayList<>();
	MeasurementUnits measurementUnits = new MeasurementUnits();

	List<MeasurementUnits> measurementUnitsList = new ArrayList<>();

	public void dataInitilization() {

		measurementUnitRequest.setCustomerSequenceNumber(5L);
		measurementUnitRequest.setExpirationDuration(15);
		measurementUnitRequest.setUnitName("inches");
		measurementUnitRequest.setUnitDescription("for bench");
		measurementUnitRequest.setApplicationId("insignia");
		measurementUnitRequest.setTenantId("LU008");

		measurementUnitResponse.setSequenceNumber(5);
		measurementUnitResponse.setUnitName("inches");
		measurementUnitResponse.setUnitDescription("for bench");

		measurementUnitResponseList.add(measurementUnitResponse);

		measurementUnits.setUnitName("inches");
		measurementUnits.setUnitDescription("for bench");
		measurementUnits.setApplicationId("insignia");
		measurementUnits.setTenantId("LU008");
		
		measurementUnitsList.add(measurementUnits);
	}

	@Test
	public void testSaveMeasurementUnits()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(measurementUnitsDaoInterface.findByUnitName(anyString(), anyString(), anyString())).thenReturn(null);

		when(measurementUnitsDaoInterface.saveMeasurementUnits(measurementUnits)).thenReturn(measurementUnits);

		MeasurementUnitResponse saveMeasurementUnits = measurementUnitServiceImpl
				.saveMeasurementUnits(measurementUnitRequest);

		assertNotNull(saveMeasurementUnits);
	}

	@Test
	public void testSaveMeasurementUnitsDuplicateDataException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		String unitName = "inches";
		when(measurementUnitsDaoInterface.findByUnitName(anyString(), anyString(), anyString())).thenReturn(unitName);

		assertThrows(InvalidInputParametersException.class, () -> {

			measurementUnitServiceImpl.saveMeasurementUnits(measurementUnitRequest);
		});
		verify(measurementUnitsDaoInterface, times(1)).findByUnitName(anyString(), anyString(), anyString());
	}

	@Test
	public void testUpdateMeasurementUnitName()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		measurementUnitRequestForUpdate.setCustomerSequenceNumber(105L);
		measurementUnitRequestForUpdate.setSequenceNumber(20);
		measurementUnitRequestForUpdate.setUnitName("inch555");
		measurementUnitRequestForUpdate.setUnitDescription("tttt");
		measurementUnitRequestForUpdate.setApplicationId("hinges-design");
		measurementUnitRequestForUpdate.setTenantId("LU008");

		Integer sequenceNumber = 20;
		String applicationId = "hinges-design";
		String tenantId = "LU008";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(measurementUnitsDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(measurementUnits));
		
		when(measurementUnitsDaoInterface.updateMeasurementUnits(measurementUnits)).thenReturn(measurementUnits);

		MeasurementUnitResponse measurementUnits = measurementUnitServiceImpl
				.updateMeasurementUnits(measurementUnitRequestForUpdate);

		assertNotNull(measurementUnits);
	}

	@Test
	public void testUpdateMeasurementUnitDescription()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		measurementUnitRequestForUpdate.setCustomerSequenceNumber(105L);
		measurementUnitRequestForUpdate.setSequenceNumber(20);
		measurementUnitRequestForUpdate.setUnitName("inch555");
		measurementUnitRequestForUpdate.setUnitDescription("tttt");
		measurementUnitRequestForUpdate.setApplicationId("hinges-design");
		measurementUnitRequestForUpdate.setTenantId("LU008");		
		
		Integer sequenceNumber = 20;
		String applicationId = "hinges-design";
		String tenantId = "LU008";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(measurementUnitsDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(measurementUnits));
		
		when(measurementUnitsDaoInterface.updateMeasurementUnits(measurementUnits)).thenReturn(measurementUnits);

		MeasurementUnitResponse measurementUnits = measurementUnitServiceImpl
				.updateMeasurementUnits(measurementUnitRequestForUpdate);

		assertNotNull(measurementUnits);
	}

	@Test
	public void testUpdateMeasurementUnitNameAndMeasurementDescription()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		measurementUnitRequestForUpdate.setCustomerSequenceNumber(105L);
		measurementUnitRequestForUpdate.setSequenceNumber(20);
		measurementUnitRequestForUpdate.setUnitName("inch555");
		measurementUnitRequestForUpdate.setUnitDescription("tttt");
		measurementUnitRequestForUpdate.setApplicationId("hinges-design");
		measurementUnitRequestForUpdate.setTenantId("LU008");

		Integer sequenceNumber = 20;
		String applicationId = "hinges-design";
		String tenantId = "LU008";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(measurementUnitsDaoInterface.findBySequenceNumber(sequenceNumber, applicationId, tenantId))
				.thenReturn(Optional.of(measurementUnits));
		
		when(measurementUnitsDaoInterface.updateMeasurementUnits(measurementUnits)).thenReturn(measurementUnits);

		MeasurementUnitResponse measurementUnits = measurementUnitServiceImpl
				.updateMeasurementUnits(measurementUnitRequestForUpdate);

		assertNotNull(measurementUnits);
	}

	@Test
	public void testUpdateMeasurementUnitsDetailsNoExistException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();
		measurementUnitRequest.setSequenceNumber(5);
		when(measurementUnitsDaoInterface.findBySequenceNumber(measurementUnitRequest.getSequenceNumber(),
				measurementUnitRequest.getApplicationId(), measurementUnitRequest.getTenantId()))
				.thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class, () -> {

			measurementUnitServiceImpl.updateMeasurementUnits(measurementUnitRequest);
		});
		verify(measurementUnitsDaoInterface, times(1)).findBySequenceNumber(measurementUnitRequest.getSequenceNumber(),
				measurementUnitRequest.getApplicationId(), measurementUnitRequest.getTenantId());
	}

	@Test
	public void testDeleteMeasurementUnits()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();
		String unitName = "inches";

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(measurementUnitsDaoInterface.findByUnitName(measurementUnitRequest.getUnitName(),
				measurementUnitRequest.getApplicationId(), measurementUnitRequest.getTenantId())).thenReturn(unitName);

		doNothing().when(measurementUnitsDaoInterface).deleteMeasurementUnits(measurementUnitRequest.getUnitName(),
				measurementUnitRequest.getApplicationId(), measurementUnitRequest.getTenantId());

		measurementUnitServiceImpl.deleteMeasurementUnits(measurementUnitRequest);

		verify(measurementUnitsDaoInterface, times(1)).deleteMeasurementUnits(measurementUnitRequest.getUnitName(),
				measurementUnitRequest.getApplicationId(), measurementUnitRequest.getTenantId());
	}

	@Test
	public void testDeleteMeasurementUnitsDetailsNotExistedException()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {

		dataInitilization();

		when(measurementUnitsDaoInterface.findByUnitName(measurementUnitRequest.getUnitName(),
				measurementUnitRequest.getApplicationId(), measurementUnitRequest.getTenantId())).thenReturn(null);

		assertThrows(InvalidInputParametersException.class, () -> {

			measurementUnitServiceImpl.deleteMeasurementUnits(measurementUnitRequest);
		});
		verify(measurementUnitsDaoInterface, times(1)).findByUnitName(measurementUnitRequest.getUnitName(),
				measurementUnitRequest.getApplicationId(), measurementUnitRequest.getTenantId());
	}

	@Test
	public void testGetAllMeasurementUnits()
			throws InvalidInputParametersException, ParseException, TokenExpiredException {
		dataInitilization();

		doNothing().when(tokenRepo).checkTokenValidity(any(), any());

		when(measurementUnitsDaoInterface.getAllMeasurementUnits(measurementUnitRequest.getApplicationId(),
				measurementUnitRequest.getTenantId())).thenReturn(measurementUnitsList);

		List<MeasurementUnitResponse> allMeasurementUnits = measurementUnitServiceImpl
				.getAllMeasurementUnits(measurementUnitRequest);

		assertNotNull(allMeasurementUnits);
	}

}