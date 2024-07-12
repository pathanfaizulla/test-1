package com.insignia.daoInterface;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.entity.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface CategoryDaoInterface {


    Optional<ProductCategory> findByCategoryId(Long categoryId, String applicationId, String tenantId);

    List<ProductCategory> fetchAllCategories(String applicationId, String tenantId);

    ProductCategory saveCategory(ProductCategory productCategory) throws InvalidInputParametersException;

    Optional<List<ProductCategory>> getInactiveCategories(String applicationId, String tenantId);


    ProductCategory findByCategoryName(String categoryName, String applicationId, String tenantId);
}
