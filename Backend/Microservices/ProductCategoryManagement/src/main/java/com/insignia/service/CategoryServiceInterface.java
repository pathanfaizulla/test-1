package com.insignia.service;

import java.util.List;
import java.util.Optional;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.CategoryRequest;
import com.insignia.model.CategoryResponse;


public interface CategoryServiceInterface {
	
	CategoryResponse saveCategory(CategoryRequest categoryRequest)throws InvalidInputParametersException ,TokenExpiredException;

	Optional<List<CategoryResponse>> getCategories(CategoryRequest cartRequest) throws TokenExpiredException, InvalidInputParametersException;

    String disableCategory(CategoryRequest categoryRequest) throws InvalidInputParametersException, TokenExpiredException;

	CategoryResponse updateCategory(CategoryRequest categoryRequest) throws InvalidInputParametersException, TokenExpiredException;

	CategoryResponse enableCategory(CategoryRequest categoryRequest) throws TokenExpiredException, InvalidInputParametersException;
}

