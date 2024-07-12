package com.insignia.repo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insignia.entity.ProductMaterial;

public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Serializable> {

	public static final String SEQUENCE_NUMBER = "sequence_number";

	public static final String MATERIAL_DESCRIPTION = "material_description";
	
	public static final String MATERIAL_NAME = "material_name";
	
	public static final String APPLICATION_ID = "application_id";

	public static final String TENANT_ID = "tenant_id";

	public static final String findByMaterialName = "select material_name from product_material where material_name = :material_name and application_id =:application_id and tenant_id =:tenant_id";

	public static final String deleteByMaterialName = "delete from product_material where material_name = :material_name and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForMaterialName = "update product_material SET material_name=:material_name where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForMaterialDescription = "update product_material SET material_description=:material_description where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String updateQueryForMaterialNameAndDescription = "update product_material SET material_name=:material_name, material_description=:material_description where sequence_number =:sequence_number and application_id =:application_id and tenant_id =:tenant_id";

	public static final String fetchQueryForUpdateProductMaterial = "select * from product_material where sequence_number = :sequence_number and application_id =:application_id and tenant_id =:tenant_id";
	
	public static final String fetchQueryForAllProductMaterial = "select * from product_material where application_id = :application_id and tenant_id = :tenant_id";
	
	@Query(value = fetchQueryForAllProductMaterial, nativeQuery = true)
	public List<ProductMaterial> fetchQueryForAllProductMaterial(@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);
	
	@Query(value = fetchQueryForUpdateProductMaterial, nativeQuery = true)
	public Optional<ProductMaterial> fetchQueryForUpdateProductMaterial(@Param(SEQUENCE_NUMBER) Integer sequenceNumber,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	
	@Query(value = findByMaterialName, nativeQuery = true)
	public Object findByMaterialName(@Param("material_name") String materialName,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = deleteByMaterialName, nativeQuery = true)
	public void deleteByMaterialName(@Param(MATERIAL_NAME) String materialName,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForMaterialName, nativeQuery = true)
	public void updateQueryForMaterialName(@Param(MATERIAL_NAME) String materialName,
			@Param(SEQUENCE_NUMBER) Integer sequenceNumber,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForMaterialDescription, nativeQuery = true)
	public void updateQueryForMaterialDescription(@Param(MATERIAL_DESCRIPTION) String materialDescription,
			@Param(SEQUENCE_NUMBER) Integer sequenceNumber,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

	@Modifying
	@Query(value = updateQueryForMaterialNameAndDescription, nativeQuery = true)
	public void updateQueryForMaterialNameAndDescription(@Param(MATERIAL_NAME) String materialName,
			@Param(MATERIAL_DESCRIPTION) String materialDescription, @Param(SEQUENCE_NUMBER) Integer sequenceNumber,@Param(APPLICATION_ID) String applicationId, @Param(TENANT_ID) String tenantId);

}
