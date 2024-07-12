package com.insignia.daoInterface;

import java.util.List;
import java.util.Optional;

import com.insignia.entity.MeasurementUnits;

public interface MeasurementUnitsDaoInterface {

	public MeasurementUnits saveMeasurementUnits(MeasurementUnits measurementUnits);

	public MeasurementUnits updateMeasurementUnits(MeasurementUnits measurementUnits);

	public void deleteMeasurementUnits(String unitName, String applicationId, String tenantId);

	public List<MeasurementUnits> getAllMeasurementUnits(String applicationId, String tenantId);

	public Object findByUnitName(String unitName, String applicationId, String tenantId);

	public Optional<MeasurementUnits> findBySequenceNumber(Integer sequenceNumber, String applicationId, String tenantId);

	public void updateUnitName(String unitName, Integer sequenceNumber, String applicationId, String tenantId);

	public void updateUnitDescription(String unitDescrption, Integer sequenceNumber, String applicationId, String tenantId);

	public void updateUnitNameAndDescription(String unitName, String unitDescrption, Integer sequenceNumber, String applicationId, String tenantId);
}
