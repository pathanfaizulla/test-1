package com.insignia.daoInterface;

import java.util.List;
import java.util.Optional;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.entity.ProductSubCategory;

public interface ProductSubCategoryDaoInterface {

	public ProductSubCategory addSubCategory(ProductSubCategory produSubCategory) throws InvalidInputParametersException;
	
	public ProductSubCategory updateCategory(ProductSubCategory productCategory) throws InvalidInputParametersException;

	public void deleteCategory(Long categoryId);

	public Optional<ProductSubCategory> getByCategoryId(Long categoryId);

	public List<ProductSubCategory> getAllCategory();
	
	public Boolean isTokenNotValid(Long customerSequenceNumber);

}
