package com.insignia.repo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.MeasurementUnits;

public interface MeasurementUnitRepository extends JpaRepository<MeasurementUnits, Serializable> {

	public static final String SEQUENCE_NUMBER = "sequence_number";

	public static final String UNIT_DESCRIPTION = "unit_description";
	
	public static final String UNIT_NAME = "unit_name";
	
	public static final String APPLICATION_ID = "application_id";

	public static final String TENANT_ID = "tenant_id";
	
	public static final String findByUnitName = "select unit_name from measurement_units where unit_name = :unit_name and application_id =:application_id and tenant_id =:tenant_id";
	
	public static final String deleteByUnitName = "delete from measurement_units where unit_name = :unit_name and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForMeasurementName = "update measurement_units SET unit_name=:unit_name where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForMeasurementDescription = "update measurement_units SET unit_description=:unit_description where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForMeasurementNameAndDescription = "update measurement_units SET unit_name=:unit_name, unit_description=:unit_description where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";
	
	
	public static final String fetchQueryForUpdate = "select * from measurement_units where sequence_number = :sequence_number and application_id =:application_id and tenant_id =:tenant_id";
	
	public static final String fetchQueryForAllMeasurementUnit = "select * from measurement_units where application_id = :application_id and tenant_id = :tenant_id";
	
	@Query(value = fetchQueryForAllMeasurementUnit, nativeQuery = true)
	public List<MeasurementUnits> fetchQueryForAllMeasurementUnit(@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);
	
	
	@Query(value = fetchQueryForUpdate, nativeQuery = true)
	public Optional<MeasurementUnits> fetchQueryForUpdate(@Param(SEQUENCE_NUMBER) Integer sequenceNumber,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	
	@Query(value = findByUnitName, nativeQuery = true)
	public Object findByUnitName(@Param(UNIT_NAME) String unitName,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = deleteByUnitName, nativeQuery = true)
	public void deleteByUnitName(@Param(UNIT_NAME) String unitName,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForMeasurementName, nativeQuery = true)
	public void updateQueryForMeasurementName(@Param(UNIT_NAME) String unitName,
			@Param(SEQUENCE_NUMBER) Integer sequenceNumber,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForMeasurementDescription, nativeQuery = true)
	public void updateQueryForMeasurementDescription(@Param(UNIT_DESCRIPTION) String unitDescription,
			@Param(SEQUENCE_NUMBER) Integer sequenceNumber,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForMeasurementNameAndDescription, nativeQuery = true)
	public void updateQueryForMeasurementNameAndDescription(@Param(UNIT_NAME) String unitName, @Param(UNIT_DESCRIPTION) String unitDescription,
			@Param(SEQUENCE_NUMBER) Integer sequenceNumber,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

}
