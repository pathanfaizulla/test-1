package com.insignia.repo;

import com.insignia.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<ProductCategory, Serializable> {

	@Query("select c from ProductCategory c where c.isSoftDeleted = false and c.categoryId = ?1 and c.applicationId = ?2 and c.tenantId = ?3")
	Optional<ProductCategory> findByCategoryId(Long categoryId, String applicationId, String tenantId);

	@Query("select c from ProductCategory c where c.isSoftDeleted = false and c.applicationId = :applicationId and c.tenantId = :tenantId")
	List<ProductCategory> fetchAllCategory(String applicationId, String tenantId);

	@Query("select c from ProductCategory c where c.isSoftDeleted = true and c.applicationId = :applicationId and c.tenantId = :tenantId")
	Optional<List<ProductCategory>> getInactiveCategories(String applicationId, String tenantId);

	@Query("select c from ProductCategory c where c.categoryName = :categoryName and c.applicationId = :applicationId and c.tenantId = :tenantId")
	ProductCategory findByCategoryName(String categoryName, String applicationId, String tenantId);


}
