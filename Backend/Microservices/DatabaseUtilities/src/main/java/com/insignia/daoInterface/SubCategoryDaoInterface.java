package com.insignia.daoInterface;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.entity.ProductSubCategory;

import java.util.List;
import java.util.Optional;

public interface SubCategoryDaoInterface {

    Optional<ProductSubCategory> findBySubCategoryId(Long subCategoryId);

    Optional<List<ProductSubCategory>> fetchAllSubCategories();

    ProductSubCategory saveSubCategory(ProductSubCategory productSubCategory) throws InvalidInputParametersException;

    Optional<List<ProductSubCategory>> findByCategoryId(Long categoryId);

    Optional<List<ProductSubCategory>> getInactiveSubCategories(Long categoryId);

    Optional<ProductSubCategory> findById(Long subCategoryId);
}
