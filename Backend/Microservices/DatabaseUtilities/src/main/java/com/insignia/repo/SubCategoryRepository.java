package com.insignia.repo;

import com.insignia.entity.ProductSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<ProductSubCategory, Serializable> {

    @Query("select c from ProductSubCategory c where c.isSoftDeleted =false and c.subCategoryId=?1")
    Optional<ProductSubCategory> findBySubCategoryId(Long subcategoryId);

    @Query("select c from ProductSubCategory c where c.isSoftDeleted =false ")
    Optional<List<ProductSubCategory>> fetchAllSubCategories();

    @Query("select c from ProductSubCategory c where c.isSoftDeleted =false and c.categoryId=?1")
    Optional<List<ProductSubCategory>> findByCategoryId(Long categoryId);

    @Query("select c from ProductSubCategory c where c.isSoftDeleted =true and c.categoryId=?1  ")
    Optional<List<ProductSubCategory>> getInactiveSubCategories(Long categoryId);
}