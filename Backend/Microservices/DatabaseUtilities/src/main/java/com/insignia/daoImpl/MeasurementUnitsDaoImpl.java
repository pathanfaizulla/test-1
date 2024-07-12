package com.insignia.daoImpl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insignia.daoInterface.MeasurementUnitsDaoInterface;
import com.insignia.entity.MeasurementUnits;
import com.insignia.repo.MeasurementUnitRepository;

@Repository
public class MeasurementUnitsDaoImpl implements MeasurementUnitsDaoInterface {

	@Autowired
	private MeasurementUnitRepository measurementUnitRepository;
	
	
	@Autowired
	private EntityManager entityManager;
	
	
	@Override
	public MeasurementUnits saveMeasurementUnits(MeasurementUnits measurementUnits) {
		return measurementUnitRepository.save(measurementUnits);
	}

	@Override
	public MeasurementUnits updateMeasurementUnits(MeasurementUnits measurementUnits) {
		return entityManager.merge(measurementUnits);
	}

	@Override
	public void deleteMeasurementUnits(String unitName, String applicationId, String tenantId) {
		measurementUnitRepository.deleteByUnitName(unitName, applicationId, tenantId);

	}

	@Override
	public List<MeasurementUnits> getAllMeasurementUnits(String applicationId, String tenantId) {
		return measurementUnitRepository.fetchQueryForAllMeasurementUnit(applicationId, tenantId);
	}

	@Override
	public Object findByUnitName(String unitName, String applicationId, String tenantId) {
		return measurementUnitRepository.findByUnitName(unitName, applicationId, tenantId);
	}

	@Override
	public Optional<MeasurementUnits> findBySequenceNumber(Integer sequenceNumber, String applicationId, String tenantId) {
		
		return measurementUnitRepository.findById(sequenceNumber);
	}

	@Override
	public void updateUnitName(String unitName, Integer sequenceNumber, String applicationId, String tenantId) {
		measurementUnitRepository.updateQueryForMeasurementName(unitName, sequenceNumber, applicationId, tenantId);
		
	}

	@Override
	public void updateUnitDescription(String unitDescrption, Integer sequenceNumber, String applicationId, String tenantId) {
		measurementUnitRepository.updateQueryForMeasurementDescription(unitDescrption, sequenceNumber, applicationId, tenantId);
	}

	@Override
	public void updateUnitNameAndDescription(String unitName, String unitDescrption, Integer sequenceNumber, String applicationId, String tenantId) {
		measurementUnitRepository.updateQueryForMeasurementNameAndDescription(unitName, unitDescrption, sequenceNumber, applicationId, tenantId);
		
	}
	

}
