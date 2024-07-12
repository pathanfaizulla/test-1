package com.insignia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.insignia.constant.ParametersManagementConstant;
import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.MeasurementUnitRequest;
import com.insignia.model.MeasurementUnitResponse;
import com.insignia.serviceInterface.MeasurementUnitServiceInterface;
import com.insignia.validations.CommonValidation;
import com.insignia.validations.ParametersManagementValidations;

@CrossOrigin
@RestController
@RequestMapping("/measurementUnit")
public class MeasurementUnitController {

	@Autowired
	private MeasurementUnitServiceInterface measurementUnitServiceInterface;

	@PostMapping("/saveMeasurementUnit")
	public ResponseEntity<?> saveMeasurementUnit(@RequestBody MeasurementUnitRequest measurementUnitRequest) {
		try {
			ParametersManagementValidations.validateUnitName(measurementUnitRequest.getUnitName(),
					ParametersManagementConstant.unitNameLength, true);
			validationsForMeasurementUnit(measurementUnitRequest);

			return ResponseEntity.ok(measurementUnitServiceInterface.saveMeasurementUnits(measurementUnitRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new MeasurementUnitResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new MeasurementUnitResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MeasurementUnitResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PutMapping("/updateMeasurementUnit")
	public ResponseEntity<?> updateMeasurementUnit(@RequestBody MeasurementUnitRequest measurementUnitRequest) {
		try {
			ParametersManagementValidations.validateUnitName(measurementUnitRequest.getUnitName(),
					ParametersManagementConstant.unitNameLength, false);
			validationsForMeasurementUnit(measurementUnitRequest);
			return ResponseEntity.ok(measurementUnitServiceInterface.updateMeasurementUnits(measurementUnitRequest));
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new MeasurementUnitResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new MeasurementUnitResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MeasurementUnitResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	@PostMapping("/deleteMeasurementUnit")
	public ResponseEntity<?> deleteMeasurementUnit(@RequestBody MeasurementUnitRequest measurementUnitRequest) {
		try {
			measurementUnitServiceInterface.deleteMeasurementUnits(measurementUnitRequest);
			return ResponseEntity.ok(ParametersManagementConstant.successMessageForMeasurementUnitDeleteMethod);
		} catch (InvalidInputParametersException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new MeasurementUnitResponse(ex.getErrorCode(), ex.getStrMsg()));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new MeasurementUnitResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MeasurementUnitResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}

	}

	@PostMapping("/getAllMeasurementUnits")
	public ResponseEntity<?> getAllMeasurementUnit(@RequestBody MeasurementUnitRequest measurementUnitRequest) {
		try {
			return ResponseEntity.ok(measurementUnitServiceInterface.getAllMeasurementUnits(measurementUnitRequest));
		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new MeasurementUnitResponse(CommonConstant.validateTokenErrorCode, CommonConstant.validateToken));

		} catch (Exception exe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MeasurementUnitResponse(
					CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage));
		}
	}

	private void validationsForMeasurementUnit(MeasurementUnitRequest measurementUnitRequest)
			throws InvalidInputParametersException {
		CommonValidation.validateApplicatinIdAndTenantId(measurementUnitRequest.getApplicationId(),
				measurementUnitRequest.getTenantId());
		ParametersManagementValidations.validateUnitDescription(measurementUnitRequest.getUnitDescription(),
				ParametersManagementConstant.unitDescriptionLength);
	}
}
