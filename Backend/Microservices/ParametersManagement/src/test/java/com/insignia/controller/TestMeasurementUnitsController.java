package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.entity.MeasurementUnits;
import com.insignia.model.MeasurementUnitRequest;
import com.insignia.model.MeasurementUnitResponse;
import com.insignia.serviceInterface.MeasurementUnitServiceInterface;

@ExtendWith(MockitoExtension.class)
public class TestMeasurementUnitsController {

	@InjectMocks
	private MeasurementUnitController measurementUnitsController;

	@Mock
	private MeasurementUnitServiceInterface measurementServiceInterface;

	MeasurementUnitRequest measurementUnitRequest = new MeasurementUnitRequest();
	MeasurementUnitResponse measurementUnitResponse = new MeasurementUnitResponse();

	List<MeasurementUnitResponse> measurementUnitResponseList = new ArrayList<>();
	MeasurementUnits measurementUnits = new MeasurementUnits();

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

		measurementUnits.setSequenceNumber(5);
		measurementUnits.setUnitName("inches");
		measurementUnits.setUnitDescription("for bench");

	}

	@Test
	public void testSaveMeasurementUnits() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();

		when(measurementServiceInterface.saveMeasurementUnits(measurementUnitRequest))
				.thenReturn(measurementUnitResponse);
		ResponseEntity<?> response = measurementUnitsController.saveMeasurementUnit(measurementUnitRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteMeasurementUnit() throws Exception {

		dataInitilization();
		doNothing().when(measurementServiceInterface).deleteMeasurementUnits(measurementUnitRequest);

		measurementUnitsController.deleteMeasurementUnit(measurementUnitRequest);
		verify(measurementServiceInterface, times(1)).deleteMeasurementUnits(measurementUnitRequest);
	}

	@Test
	public void testGetAllMeasurementUnits() throws TokenExpiredException, InvalidInputParametersException {

		dataInitilization();

		when(measurementServiceInterface.getAllMeasurementUnits(measurementUnitRequest))
				.thenReturn(measurementUnitResponseList);
		ResponseEntity<?> response = measurementUnitsController.getAllMeasurementUnit(measurementUnitRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateMeasurementUnits() throws InvalidInputParametersException, TokenExpiredException {
		dataInitilization();
		measurementUnitRequest.setSequenceNumber(5);

		when(measurementServiceInterface.updateMeasurementUnits(measurementUnitRequest))
				.thenReturn(measurementUnitResponse);
		ResponseEntity<?> response = measurementUnitsController.updateMeasurementUnit(measurementUnitRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testForTokenExpired() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(measurementServiceInterface.saveMeasurementUnits(measurementUnitRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> saveMeasurementUnit = measurementUnitsController.saveMeasurementUnit(measurementUnitRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveMeasurementUnit.getStatusCode());

		doThrow(new TokenExpiredException("")).when(measurementServiceInterface)
				.deleteMeasurementUnits(measurementUnitRequest);
		ResponseEntity<?> deleteMeasurementUnit = measurementUnitsController
				.deleteMeasurementUnit(measurementUnitRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteMeasurementUnit.getStatusCode());

		when(measurementServiceInterface.getAllMeasurementUnits(measurementUnitRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> getAllMeasurementUnit = measurementUnitsController
				.getAllMeasurementUnit(measurementUnitRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllMeasurementUnit.getStatusCode());

		when(measurementServiceInterface.updateMeasurementUnits(measurementUnitRequest))
				.thenThrow(new TokenExpiredException(""));
		ResponseEntity<?> updateMeasurementUnit = measurementUnitsController
				.updateMeasurementUnit(measurementUnitRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateMeasurementUnit.getStatusCode());

	}

	@Test
	public void testForException() throws TokenExpiredException, InvalidInputParametersException {
		dataInitilization();

		when(measurementServiceInterface.saveMeasurementUnits(measurementUnitRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> saveMeasurementUnit = measurementUnitsController.saveMeasurementUnit(measurementUnitRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveMeasurementUnit.getStatusCode());

		doThrow(new NullPointerException("")).when(measurementServiceInterface)
				.deleteMeasurementUnits(measurementUnitRequest);
		ResponseEntity<?> deleteMeasurementUnit = measurementUnitsController
				.deleteMeasurementUnit(measurementUnitRequest);
		assertEquals(HttpStatus.BAD_REQUEST, deleteMeasurementUnit.getStatusCode());

		when(measurementServiceInterface.getAllMeasurementUnits(measurementUnitRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> getAllMeasurementUnit = measurementUnitsController
				.getAllMeasurementUnit(measurementUnitRequest);

		assertEquals(HttpStatus.BAD_REQUEST, getAllMeasurementUnit.getStatusCode());

		when(measurementServiceInterface.updateMeasurementUnits(measurementUnitRequest))
				.thenThrow(new NullPointerException(""));
		ResponseEntity<?> updateMeasurementUnit = measurementUnitsController
				.updateMeasurementUnit(measurementUnitRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateMeasurementUnit.getStatusCode());

	}
	@Test
	public void testForSaveMeasurementUnitsInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(measurementServiceInterface)
				.saveMeasurementUnits(measurementUnitRequest);

		ResponseEntity<?> saveMeasurementUnit = measurementUnitsController
				.saveMeasurementUnit(measurementUnitRequest);

		verify(measurementServiceInterface).saveMeasurementUnits(measurementUnitRequest);

		assertEquals(HttpStatus.BAD_REQUEST, saveMeasurementUnit.getStatusCode());

	}
	@Test
	public void testForUpdateMeasurementUnitsInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(measurementServiceInterface)
				.updateMeasurementUnits(measurementUnitRequest);
		ResponseEntity<?> updateMeasurementUnit = measurementUnitsController
				.updateMeasurementUnit(measurementUnitRequest);

		verify(measurementServiceInterface).updateMeasurementUnits(measurementUnitRequest);

		assertEquals(HttpStatus.BAD_REQUEST, updateMeasurementUnit.getStatusCode());

	}
	@Test
	public void testForDeleteMeasurementUnitsInvalidInputParametersException()
			throws InvalidInputParametersException, TokenExpiredException, ParseException {
		dataInitilization();

		doThrow(new InvalidInputParametersException("255", "Invalid data")).when(measurementServiceInterface)
				.deleteMeasurementUnits(measurementUnitRequest);
		ResponseEntity<?> deleteMeasurementUnits = measurementUnitsController
				.deleteMeasurementUnit(measurementUnitRequest);

		verify(measurementServiceInterface).deleteMeasurementUnits(measurementUnitRequest);

		assertEquals(HttpStatus.BAD_REQUEST, deleteMeasurementUnits.getStatusCode());

	}
}
