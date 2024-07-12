package com.insignia.serviceimpl;

import static com.insignia.constants.CategoryConstant.validateCategory;
import static com.insignia.constants.CategoryConstant.validateCategoryCode;
import static com.insignia.constants.CategoryConstant.validateCategoryId;
import static com.insignia.constants.CategoryConstant.validateCategoryIdCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.constants.CategoryConstant;
import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.CategoryDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductCategory;
import com.insignia.entity.ProductSubCategory;
import com.insignia.model.CategoryRequest;
import com.insignia.model.CategoryResponse;
import com.insignia.model.SubCategoryResponse;
import com.insignia.service.CategoryServiceInterface;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryServiceInterface {

	@Autowired
	public CategoryDaoInterface categoryDaoImpl;

	@Autowired
	private TokenDaoInterface tokenDaoInterface;

	@Transactional(rollbackOn = InvalidInputParametersException.class)
	@Override
	public CategoryResponse saveCategory(CategoryRequest categoryRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		log.info("Got Request at saveCategory");
		ProductCategory categoryResponse;

		tokenDaoInterface.checkTokenValidity(categoryRequest.getCustomerSequenceNumber(),
				categoryRequest.getExpirationDuration());

		checkInactiveCategories(categoryRequest);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setCategoryName(categoryRequest.getCategoryName());
		productCategory.setCategoryDescription(categoryRequest.getCategoryDescription());
		productCategory.setCategoryImagePath(categoryRequest.getCategoryImagePath());
		productCategory.setDefaultImage(categoryRequest.getDefaultImage());
		productCategory.setIsSoftDeleted(Boolean.FALSE);
		productCategory.setApplicationId(categoryRequest.getApplicationId());
		productCategory.setTenantId(categoryRequest.getTenantId());
		
		categoryResponse = categoryDaoImpl.saveCategory(productCategory);

		return mapToDto(Collections.singletonList(categoryResponse)).get().get(0);

	}

	@Transactional
	@Override
	public Optional<List<CategoryResponse>> getCategories(CategoryRequest categoryRequest)
			throws TokenExpiredException, InvalidInputParametersException {

		log.info("Got Request at getCategories");
		if (categoryRequest.getCustomerSequenceNumber() != CommonConstant.nonLoggedCustomerSequenceNumber) {
			tokenDaoInterface.checkTokenValidity(categoryRequest.getCustomerSequenceNumber(),
					categoryRequest.getExpirationDuration());
		}

		List<ProductCategory> productCategories = new ArrayList();

		if (categoryRequest.getCategoryId() != null) {
			Optional<ProductCategory> productCategory = categoryDaoImpl
					.findByCategoryId(categoryRequest.getCategoryId(), categoryRequest.getApplicationId(), categoryRequest.getTenantId());
			if (productCategory.isPresent())
				productCategories.addAll(Collections.singletonList(productCategory.get()));
			else
				throw new InvalidInputParametersException(CategoryConstant.validateCategoryIdCode, CategoryConstant.validateCategoryId);
		} else {
			productCategories = categoryDaoImpl.fetchAllCategories(categoryRequest.getApplicationId(), categoryRequest.getTenantId());
		}
		if (productCategories != null)
			return mapToDto(productCategories);

		return Optional.empty();
	}

	@Transactional
	@Override
	public String disableCategory(CategoryRequest categoryRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		log.info("Got Request at inactiveCategory");
		tokenDaoInterface.checkTokenValidity(categoryRequest.getCustomerSequenceNumber(),
				categoryRequest.getExpirationDuration());

		if (categoryRequest.getCategoryId() != null) {
			Optional<ProductCategory> productCategory = categoryDaoImpl
					.findByCategoryId(categoryRequest.getCategoryId(), categoryRequest.getApplicationId(), categoryRequest.getTenantId());
			if (productCategory.isPresent()) {
				productCategory.get().setIsSoftDeleted(Boolean.TRUE);
				categoryDaoImpl.saveCategory(productCategory.get());
				return "Category disabled successfully";
			}
		}
		throw new InvalidInputParametersException(validateCategoryIdCode, validateCategoryId);
	}

	@Transactional
	@Override
	public CategoryResponse updateCategory(CategoryRequest categoryRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		log.info("Got Request at updateCategory");

		tokenDaoInterface.checkTokenValidity(categoryRequest.getCustomerSequenceNumber(),
				categoryRequest.getExpirationDuration());

		if (categoryRequest.getCategoryId() != null) {
			checkInactiveCategories(categoryRequest);
			Optional<ProductCategory> productCategory = categoryDaoImpl.findByCategoryId(
					categoryRequest.getCategoryId(), categoryRequest.getApplicationId(), categoryRequest.getTenantId());
			if (productCategory.isPresent()) {
				ProductCategory categoryEntity = productCategory.get();

				categoryEntity.setIsSoftDeleted(Boolean.FALSE);
				if (categoryRequest.isCategoryNameUpdated()) {
					categoryEntity.setCategoryName(categoryRequest.getCategoryName());
				}
				if (categoryRequest.isCategoryDescriptionUpdated()) {
					categoryEntity.setCategoryDescription(categoryRequest.getCategoryDescription());
				}
				if (categoryRequest.isCategoryImagePathUpdated()) {
					categoryEntity.setCategoryImagePath(categoryRequest.getCategoryImagePath());
				}
				if (categoryRequest.isDefaultImageUpdated()) {
					categoryEntity.setDefaultImage(categoryRequest.getDefaultImage());
				}

				ProductCategory categoryResponse = categoryDaoImpl.saveCategory(categoryEntity);
				return mapToDto(Collections.singletonList(categoryResponse)).get().get(0);
			}
		}

		throw new InvalidInputParametersException(validateCategoryIdCode, validateCategoryId);
	}

	@Transactional
	@Override
	public CategoryResponse enableCategory(CategoryRequest categoryRequest)
			throws TokenExpiredException, InvalidInputParametersException {
		log.info("Got Request at inactiveCategory");
		tokenDaoInterface.checkTokenValidity(categoryRequest.getCustomerSequenceNumber(),
				categoryRequest.getExpirationDuration());

		if (categoryRequest.getCategoryName() != null && !categoryRequest.getCategoryName().trim().isEmpty()) {
			ProductCategory productCategory = categoryDaoImpl.findByCategoryName(categoryRequest.getCategoryName(), categoryRequest.getApplicationId(), categoryRequest.getTenantId());
			if (productCategory != null) {
				productCategory.setIsSoftDeleted(Boolean.FALSE);
				categoryDaoImpl.saveCategory(productCategory);
				return mapToDto(Collections.singletonList(productCategory)).get().get(0);
			}
		}
		throw new InvalidInputParametersException(validateCategoryIdCode, validateCategoryId);
	}

	private void checkInactiveCategories(CategoryRequest categoryRequest) throws InvalidInputParametersException {
		Optional<List<ProductCategory>> inactiveCategories = categoryDaoImpl.getInactiveCategories(categoryRequest.getApplicationId(), categoryRequest.getTenantId());
		if (inactiveCategories.isPresent()) {
			if (inactiveCategories.get().stream().map(ProductCategory::getCategoryName)

					.anyMatch(name -> name.toLowerCase(Locale.ROOT)
							.equals(categoryRequest.getCategoryName().toLowerCase()))) {
				throw new InvalidInputParametersException(validateCategoryCode,
						validateCategory + " categoryName:" + categoryRequest.getCategoryName());
			}
		}
	}

	private Optional<List<CategoryResponse>> mapToDto(List<ProductCategory> productCategories) {

		Optional<List<CategoryResponse>> categoryResponseList = Optional.of(new ArrayList());

		for (ProductCategory productCategory : productCategories) {
			List<SubCategoryResponse> subCategoryList = new ArrayList<>();
			if (productCategory.getProductSubCategory() != null) {
				for (ProductSubCategory productSubCategory : productCategory.getProductSubCategory()) {

					SubCategoryResponse subCategoryResponse = SubCategoryResponse.builder()
							.subcategoryId(productSubCategory.getSubCategoryId())
							.subcategoryName(productSubCategory.getSubCategoryName())
							.subCategoryDescription(productSubCategory.getSubCategoryDescription())
							.subCategoryDefaultImage(productSubCategory.getDefaultImage())
							.subCategoryImagePath(productSubCategory.getSubCategoryImagePath()).build();
					subCategoryList.add(subCategoryResponse);
				}
			}
			categoryResponseList.get()
					.add(CategoryResponse.builder().categoryId(productCategory.getCategoryId())
							.categoryName(productCategory.getCategoryName())
							.categoryDescription(productCategory.getCategoryDescription())
							.categoryImagePath(productCategory.getCategoryImagePath())
							.categoryDefaultImage(productCategory.getDefaultImage())
							.subCategoryResponseList(subCategoryList).build());
		}

		return categoryResponseList;
	}

}
