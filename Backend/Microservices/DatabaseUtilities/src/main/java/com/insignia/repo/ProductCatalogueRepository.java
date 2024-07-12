package com.insignia.repo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.ProductCatalogue;

public interface ProductCatalogueRepository extends JpaRepository<ProductCatalogue, Serializable> {

	public static final String SEQUENCE_NUMBER = "sequence_number";

	public static final String CATALOGUE_DESCRIPTION = "catalogue_description";

	public static final String CATALOGUE_NAME = "catalogue_name";

	public static final String APPLICATION_ID = "application_id";

	public static final String TENANT_ID = "tenant_id";

	public static final String findByCatalogueName = "select catalogue_name from product_catalogue where catalogue_name = :catalogue_name and application_id =:application_id and tenant_id =:tenant_id";

	public static final String deleteByCatalogueName = "delete from product_catalogue where catalogue_name = :catalogue_name and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForCatalogueName = "update product_catalogue SET catalogue_name=:catalogue_name where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForCatalogueDescription = "update product_catalogue SET catalogue_description=:catalogue_description where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForCatalogueNameAndDescription = "update product_catalogue SET catalogue_name=:catalogue_name, catalogue_description=:catalogue_description where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String fetchQueryForUpdate = "select * from product_catalogue where sequence_number = :sequence_number and application_id = :application_id and tenant_id = :tenant_id";
		
	public static final String fetchQueryForAllProductCatalogue = "select * from product_catalogue where application_id = :application_id and tenant_id = :tenant_id";
	
	@Query(value = fetchQueryForAllProductCatalogue, nativeQuery = true)
	public List<ProductCatalogue> fetchQueryForAllProductCatalogue(@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);
	
	@Query(value = fetchQueryForUpdate, nativeQuery = true)
	public Optional<ProductCatalogue> fetchQueryForUpdate(@Param(SEQUENCE_NUMBER) Integer sequenceNumber, @Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);
	
	@Query(value = findByCatalogueName, nativeQuery = true)
	public Object findByCatalogueName(@Param(CATALOGUE_NAME) String catalogueName,
			@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = deleteByCatalogueName, nativeQuery = true)
	public void deleteByCatalogueName(@Param(CATALOGUE_NAME) String catalogueName, @Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForCatalogueName, nativeQuery = true)
	public void updateQueryForCatalogueName(@Param(CATALOGUE_NAME) String catalogueName,
			@Param(SEQUENCE_NUMBER) Integer sequenceNumber, @Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForCatalogueDescription, nativeQuery = true)
	public void updateQueryForCatalogueDescription(@Param(CATALOGUE_DESCRIPTION) String catalogueDescription,
			@Param(SEQUENCE_NUMBER) Integer sequenceNumber, @Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForCatalogueNameAndDescription, nativeQuery = true)
	public void updateQueryForCatalogueNameAndDescription(@Param(CATALOGUE_NAME) String catalogueName,
			@Param(CATALOGUE_DESCRIPTION) String catalogueDescription, @Param(SEQUENCE_NUMBER) Integer sequenceNumber, @Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);
}
