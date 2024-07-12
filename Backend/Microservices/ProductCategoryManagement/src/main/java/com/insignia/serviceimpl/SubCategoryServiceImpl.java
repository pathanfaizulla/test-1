package com.insignia.serviceimpl;

import static com.insignia.constants.CategoryConstant.validateCategoryId;
import static com.insignia.constants.CategoryConstant.validateCategoryIdCode;
import static com.insignia.constants.SubCategoryConstant.validateSubCategoryId;
import static com.insignia.constants.SubCategoryConstant.validateSubCategoryIdCode;
import static com.insignia.constants.SubCategoryConstant.validateSubCategoryName;
import static com.insignia.constants.SubCategoryConstant.validateSubCategoryNameCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.SubCategoryDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductSubCategory;
import com.insignia.model.SubCategoryRequest;
import com.insignia.model.SubCategoryResponse;
import com.insignia.service.SubCategoryServiceInterface;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubCategoryServiceImpl implements SubCategoryServiceInterface {

	@Autowired
	public SubCategoryDaoInterface subcategoryDaoImpl;

	@Autowired
	private TokenDaoInterface tokenDaoInterface;

	@Transactional(rollbackOn = InvalidInputParametersException.class)
	@Override
	public SubCategoryResponse saveSubCategory(SubCategoryRequest subcategoryRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDaoInterface.checkTokenValidity(subcategoryRequest.getCustomerSequenceNumber(),
				subcategoryRequest.getExpirationDuration());
		checkInactiveCategories(subcategoryRequest);
		ProductSubCategory productsubCategory = new ProductSubCategory();
		productsubCategory.setCategoryId(subcategoryRequest.getCategoryId());
		productsubCategory.setSubCategoryName(subcategoryRequest.getSubCategoryName());
		productsubCategory.setSubCategoryDescription(subcategoryRequest.getSubCategoryDescription());
		productsubCategory.setSubCategoryImagePath(subcategoryRequest.getSubCategoryImagePath());
		productsubCategory.setDefaultImage(subcategoryRequest.getSubCategoryDefaultImage());
		productsubCategory.setIsSoftDeleted(Boolean.FALSE);

		ProductSubCategory subcategoryResponse = subcategoryDaoImpl.saveSubCategory(productsubCategory);
		return mapEntityToDto(Collections.singletonList(subcategoryResponse)).get().get(0);

	}

	@Transactional
	@Override
	public Optional<List<SubCategoryResponse>> getSubCategories(SubCategoryRequest subcategoryRequest)
			throws TokenExpiredException, InvalidInputParametersException {

		log.info("Request at getSubCategories");
		if (subcategoryRequest.getCustomerSequenceNumber() != CommonConstant.nonLoggedCustomerSequenceNumber) {
			tokenDaoInterface.checkTokenValidity(subcategoryRequest.getCustomerSequenceNumber(),
					subcategoryRequest.getExpirationDuration());
		}
		Optional<List<ProductSubCategory>> productSubCategories = Optional.of(new ArrayList());

		if (subcategoryRequest.getSubCategoryId() != null) {
			Optional<ProductSubCategory> productSubCategory = subcategoryDaoImpl
					.findBySubCategoryId(subcategoryRequest.getSubCategoryId());

			if (productSubCategory.isPresent())
				productSubCategories.get().addAll(Collections.singletonList(productSubCategory.get()));
			else
				throw new InvalidInputParametersException(validateSubCategoryIdCode, validateSubCategoryId);
		} else if (subcategoryRequest.getCategoryId() != null) {
			productSubCategories = subcategoryDaoImpl.findByCategoryId(subcategoryRequest.getCategoryId());

			if (productSubCategories.isPresent() && productSubCategories.get().isEmpty())
				throw new InvalidInputParametersException(validateCategoryIdCode, validateCategoryId);

		} else {
			productSubCategories = subcategoryDaoImpl.fetchAllSubCategories();
		}
		if (productSubCategories.isPresent())
			return mapEntityToDto(productSubCategories.get());

		return Optional.empty();
	}

	@Transactional
	@Override
	public SubCategoryResponse updateSubCategory(SubCategoryRequest subcategoryRequest)
			throws InvalidInputParametersException, TokenExpiredException {

		tokenDaoInterface.checkTokenValidity(subcategoryRequest.getCustomerSequenceNumber(),
				subcategoryRequest.getExpirationDuration());

		if (subcategoryRequest.getSubCategoryId() != null) {
			Optional<ProductSubCategory> productSubCategory = subcategoryDaoImpl
					.findBySubCategoryId(subcategoryRequest.getSubCategoryId());
			if (productSubCategory.isPresent()) {
				ProductSubCategory subcategoryEntity = productSubCategory.get();

				if (subcategoryRequest.isSubCategoryNameUpdated()) {
					subcategoryEntity.setSubCategoryName(subcategoryRequest.getSubCategoryName());
				}
				if (subcategoryRequest.isSubCategoryDescriptionUpdated()) {
					subcategoryEntity.setSubCategoryDescription(subcategoryRequest.getSubCategoryDescription());
				}
				if (subcategoryRequest.isSubCategoryImagePathUpdated()) {
					subcategoryEntity.setSubCategoryImagePath(subcategoryRequest.getSubCategoryImagePath());
				}
				if (subcategoryRequest.isSubCategoryDefaultImageUpdated()) {
					subcategoryEntity.setDefaultImage(subcategoryRequest.getSubCategoryDefaultImage());
				}

				ProductSubCategory subcategoryResponse = subcategoryDaoImpl.saveSubCategory(subcategoryEntity);
				return mapEntityToDto(Collections.singletonList(subcategoryResponse)).get().get(0);
			}
		}

		return SubCategoryResponse.builder().build();
	}

	@Transactional
	@Override
	public String disableSubCategory(SubCategoryRequest subCategoryRequest)
			throws TokenExpiredException, InvalidInputParametersException {
		log.info("Got Request at disableSubCategory");
		tokenDaoInterface.checkTokenValidity(subCategoryRequest.getCustomerSequenceNumber(),
				subCategoryRequest.getExpirationDuration());

		if (subCategoryRequest.getSubCategoryId() != null) {
			Optional<ProductSubCategory> productSubCategory = subcategoryDaoImpl
					.findBySubCategoryId(subCategoryRequest.getSubCategoryId());
			if (productSubCategory.isPresent()) {
				productSubCategory.get().setIsSoftDeleted(Boolean.TRUE);
				subcategoryDaoImpl.saveSubCategory(productSubCategory.get());
				return "Category disabled successfully";
			}
		}
		throw new InvalidInputParametersException(validateSubCategoryIdCode, validateSubCategoryId);

	}

	@Transactional
	@Override
	public SubCategoryResponse enableSubCategory(SubCategoryRequest subCategoryRequest)
			throws TokenExpiredException, InvalidInputParametersException {
		log.info("Got Request at enableSubCategory");
		tokenDaoInterface.checkTokenValidity(subCategoryRequest.getCustomerSequenceNumber(),
				subCategoryRequest.getExpirationDuration());

		if (subCategoryRequest.getSubCategoryId() != null) {
			Optional<ProductSubCategory> productSubCategory = subcategoryDaoImpl
					.findById(subCategoryRequest.getSubCategoryId());
			if (productSubCategory.isPresent()) {
				productSubCategory.get().setIsSoftDeleted(Boolean.FALSE);
				subcategoryDaoImpl.saveSubCategory(productSubCategory.get());
				return mapEntityToDto(Collections.singletonList(productSubCategory.get())).get().get(0);
			}
		}
		throw new InvalidInputParametersException(validateSubCategoryIdCode, validateSubCategoryId);
	}

	private Optional<List<SubCategoryResponse>> mapEntityToDto(List<ProductSubCategory> productSubCategories) {

		Optional<List<SubCategoryResponse>> subcategoryResponseList = Optional
				.of(new ArrayList<>(productSubCategories.size()));

		for (ProductSubCategory productsubCategory : productSubCategories) {

			subcategoryResponseList.get()
					.add(SubCategoryResponse.builder().subcategoryId(productsubCategory.getSubCategoryId())
							.categoryId(productsubCategory.getCategoryId())
							.subcategoryName(productsubCategory.getSubCategoryName())
							.subCategoryDescription(productsubCategory.getSubCategoryDescription())
							.subCategoryDefaultImage(productsubCategory.getDefaultImage())
							.subCategoryImagePath(productsubCategory.getSubCategoryImagePath()).build());
		}

		return subcategoryResponseList;
	}

	private void checkInactiveCategories(SubCategoryRequest subCategoryRequest) throws InvalidInputParametersException {
		Optional<List<ProductSubCategory>> inactiveCategories = subcategoryDaoImpl
				.getInactiveSubCategories(subCategoryRequest.getCategoryId());
		if (inactiveCategories.isPresent()) {
			if (inactiveCategories.get().stream().map(ProductSubCategory::getSubCategoryName)

					.anyMatch(name -> name.toLowerCase(Locale.ROOT)
							.equals(subCategoryRequest.getSubCategoryName().toLowerCase()))) {

				throw new InvalidInputParametersException(validateSubCategoryNameCode, validateSubCategoryName);
			}
		}
	}

}
