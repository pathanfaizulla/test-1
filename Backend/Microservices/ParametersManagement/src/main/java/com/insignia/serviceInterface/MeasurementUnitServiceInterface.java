package com.insignia.serviceInterface;

import java.util.List;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.MeasurementUnitRequest;
import com.insignia.model.MeasurementUnitResponse;

public interface MeasurementUnitServiceInterface {

	public MeasurementUnitResponse saveMeasurementUnits(MeasurementUnitRequest measurementUnitRequest) throws InvalidInputParametersException, TokenExpiredException;

	public MeasurementUnitResponse updateMeasurementUnits(MeasurementUnitRequest measurementUnitRequest) throws InvalidInputParametersException, TokenExpiredException;

	public void deleteMeasurementUnits(MeasurementUnitRequest measurementUnitRequest) throws InvalidInputParametersException, TokenExpiredException;

	public List<MeasurementUnitResponse> getAllMeasurementUnits(MeasurementUnitRequest measurementUnitRequest) throws TokenExpiredException;

}
