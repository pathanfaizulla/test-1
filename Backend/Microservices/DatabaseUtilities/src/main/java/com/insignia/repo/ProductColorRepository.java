package com.insignia.repo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.ProductColour;

public interface ProductColorRepository extends JpaRepository<ProductColour, Serializable> {

	public static final String SEQUENCE_NUMBER = "sequence_number";

	public static final String COLOUR_DESCRIPTION = "colour_description";

	public static final String COLOUR_NAME = "colour_name";
	
	public static final String APPLICATION_ID = "application_id";

	public static final String TENANT_ID = "tenant_id";

	public static final String findByColourName = "select colour_name from product_colour where colour_name = :colour_name and application_id =:application_id and tenant_id =:tenant_id";

	public static final String deleteByColourName = "delete from product_colour where colour_name = :colour_name and application_id =:application_id and tenant_id =:tenant_id and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForColourName = "update product_colour SET colour_name=:colour_name where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForColourDescription = "update product_colour SET colour_description=:colour_description where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForColourNameAndDescription = "update product_colour SET colour_name=:colour_name, colour_description=:colour_description where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String fetchQueryForUpdate = "select * from product_colour where sequence_number = :sequence_number and application_id =:application_id and tenant_id =:tenant_id";
	
	public static final String fetchQueryForAllProductColour = "select * from product_colour where application_id = :application_id and tenant_id = :tenant_id";
	
	@Query(value = fetchQueryForAllProductColour, nativeQuery = true)
	public List<ProductColour> fetchQueryForAllProductColour(@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);
	
	
	@Query(value = fetchQueryForUpdate, nativeQuery = true)
	public Optional<ProductColour> fetchQueryForUpdate(@Param(SEQUENCE_NUMBER) Integer sequenceNumber,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	
	@Query(value = findByColourName, nativeQuery = true)
	public Object findByColourName(@Param(COLOUR_NAME) String colourName,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = deleteByColourName, nativeQuery = true)
	public void deleteByColourName(@Param(COLOUR_NAME) String colourName, @Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForColourName, nativeQuery = true)
	public void updateQueryForColourName(@Param(COLOUR_NAME) String colourName,
			@Param(SEQUENCE_NUMBER) Integer sequenceNumber, @Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForColourDescription, nativeQuery = true)
	public void updateQueryForColourDescription(@Param(COLOUR_DESCRIPTION) String colourDescription,
			@Param(SEQUENCE_NUMBER) Integer sequenceNumber, @Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForColourNameAndDescription, nativeQuery = true)
	public void updateQueryForColourNameAndDescription(@Param(COLOUR_NAME) String colourName,
			@Param(COLOUR_DESCRIPTION) String colourDescription, @Param(SEQUENCE_NUMBER) Integer sequenceNumber, @Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

}
