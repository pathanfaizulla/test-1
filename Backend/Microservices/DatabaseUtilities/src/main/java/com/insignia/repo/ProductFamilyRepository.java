package com.insignia.repo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.ProductFamily;

public interface ProductFamilyRepository extends JpaRepository<ProductFamily, Serializable> {

	public static final String FAMILY_DESCRIPTION = "family_description";

	public static final String SEQUENCE_NUMBER = "sequence_number";

	public static final String Family_NAME = "family_name";

	public static final String APPLICATION_ID = "application_id";

	public static final String TENANT_ID = "tenant_id";

	public static final String findByFamilyName = "select family_name from product_family where family_name = :family_name and application_id =:application_id and tenant_id =:tenant_id";

	public static final String deleteByFamilyName = "delete from product_family where family_name = :family_name and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForFamilyName = "update product_family SET family_name=:family_name where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForFamilyDescription = "update product_family SET family_description=:family_description where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForFamilyNameAndDescription = "UPDATE product_family SET family_name =:family_name, family_description =:family_description WHERE sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String fetchQueryForUpdate = "select * from product_family where sequence_number = :sequence_number and application_id = :application_id and tenant_id = :tenant_id";

	public static final String fetchQueryForAllProductFamily = "select * from product_family where application_id = :application_id and tenant_id = :tenant_id";
	
	@Query(value = fetchQueryForAllProductFamily, nativeQuery = true)
	public List<ProductFamily> fetchQueryForAllProductFamily(@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);
	
	@Query(value = fetchQueryForUpdate, nativeQuery = true)
	public Optional<ProductFamily> fetchQueryForUpdate(@Param(SEQUENCE_NUMBER) Integer sequenceNumber, @Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);
	
	@Query(value = findByFamilyName, nativeQuery = true)
	public Object findByFamilyName(@Param(Family_NAME) String familyName, @Param(APPLICATION_ID) String applicationId,
			@Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = deleteByFamilyName, nativeQuery = true)
	public void deleteByFamilyName(@Param(Family_NAME) String familyName, @Param(APPLICATION_ID) String applicationId,
			@Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForFamilyName, nativeQuery = true)
	public void updateQueryForFamilyName(@Param(Family_NAME) String familyName,
			@Param(SEQUENCE_NUMBER) Integer sequenceNumber, @Param(APPLICATION_ID) String applicationId,
			@Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForFamilyDescription, nativeQuery = true)
	public void updateQueryForFamilyDescription(@Param(FAMILY_DESCRIPTION) String familyDescription,
			@Param(SEQUENCE_NUMBER) Integer sequenceNumber, @Param(APPLICATION_ID) String applicationId,
			@Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForFamilyNameAndDescription, nativeQuery = true)
	public void updateQueryForFamilyNameAndDescription(@Param(Family_NAME) String familyName,
			@Param(FAMILY_DESCRIPTION) String familyDescription, @Param(SEQUENCE_NUMBER) Integer sequenceNumber,
			@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

}
