package com.insignia.repo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.ProductBrand;

import jakarta.transaction.Transactional;

public interface ProductBrandRepository extends JpaRepository<ProductBrand, Serializable> {

	public static final String SEQUENCE_NUMBER = "sequence_number";

	public static final String BRAND_DESCRIPTION = "brand_description";

	public static final String BRAND_NAME = "brand_name";
	
	public static final String APPLICATION_ID = "application_id";

	public static final String TENANT_ID = "tenant_id";

	public static final String findByBrandName = "select brand_name from product_brand where brand_name = :brand_name and application_id =:application_id and tenant_id =:tenant_id";

	public static final String deleteByBrandName = "delete from product_brand where brand_name = :brand_name and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForBrandName = "update product_brand SET brand_name=:brand_name where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForBrandDescription = "update product_brand SET brand_description=:brand_description where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForBrandNameAndDescription = "update product_brand SET brand_name=:brand_name, brand_description=:brand_description where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String fetchQueryForUpdate = "select * from product_brand where sequence_number = :sequence_number and application_id = :application_id and tenant_id = :tenant_id";

	public static final String fetchQueryForAllProductBrand = "select * from product_brand where application_id = :application_id and tenant_id = :tenant_id";
	
	@Query(value = fetchQueryForAllProductBrand, nativeQuery = true)
	public List<ProductBrand> fetchQueryForAllProductBrand(@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);
	
	@Query(value = fetchQueryForUpdate, nativeQuery = true)
	public Optional<ProductBrand> fetchQueryForUpdate(@Param(SEQUENCE_NUMBER) Integer sequenceNumber, @Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);
	
	@Query(value = findByBrandName, nativeQuery = true)
	public Object findByBrandName(@Param(BRAND_NAME) String brandName,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = deleteByBrandName, nativeQuery = true)
	public void deleteByBrandName(@Param(BRAND_NAME) String brandName,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Transactional
	@Query(value = updateQueryForBrandName, nativeQuery = true)
	public void updateQueryForBrandName(@Param(BRAND_NAME) String colourName,
			@Param(SEQUENCE_NUMBER) Integer sequenceNumber,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForBrandDescription, nativeQuery = true)
	public void updateQueryForBrandDescription(@Param(BRAND_DESCRIPTION) String colourDescription,
			@Param(SEQUENCE_NUMBER) Integer sequenceNumber,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForBrandNameAndDescription, nativeQuery = true)
	public void updateQueryForBrandNameAndDescription(@Param(BRAND_NAME) String colourName,
			@Param(BRAND_DESCRIPTION) String colourDescription, @Param(SEQUENCE_NUMBER) Integer sequenceNumber,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);
}
