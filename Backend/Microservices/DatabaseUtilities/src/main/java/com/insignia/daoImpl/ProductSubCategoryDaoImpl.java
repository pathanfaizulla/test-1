package com.insignia.daoImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.insignia.constants.ProductSubCategoryValidator;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.daoInterface.ProductSubCategoryDaoInterface;
import com.insignia.entity.ProductSubCategory;
import com.insignia.repo.ProductSubCategoryRepo;
import com.insignia.repo.TokensEntityRepo;

@Repository
@Transactional
public class ProductSubCategoryDaoImpl implements ProductSubCategoryDaoInterface {

	@Autowired
	private ProductSubCategoryRepo productSubCategoryRepo;

	public EntityManager entityManager;

	@Autowired
	private TokensEntityRepo tokensEntityRepo;

	@Override
	public ProductSubCategory addSubCategory(ProductSubCategory produSubCategory)
			throws InvalidInputParametersException {

		try {
			return productSubCategoryRepo.save(produSubCategory);
		} catch (DataIntegrityViolationException e) {
			throw new InvalidInputParametersException(ProductSubCategoryValidator.ERROR_CODE_DUBLICATE_DATA_FOUND,
					ProductSubCategoryValidator.ERROR_MESSAGE_DUBLICATE_DATA_FOUND);
		}
	}

	@Override
	public ProductSubCategory updateCategory(ProductSubCategory productCategory)
			throws InvalidInputParametersException {

		return entityManager.merge(productCategory);
	}

	@Override
	public void deleteCategory(Long categoryId) {
		productSubCategoryRepo.deleteById(categoryId);
	}

	@Override
	public Optional<ProductSubCategory> getByCategoryId(Long categoryId) {

		return productSubCategoryRepo.findById(categoryId);
	}

	@Override
	public List<ProductSubCategory> getAllCategory() {

		return productSubCategoryRepo.findAll();
	}

	@Override
	public Boolean isTokenNotValid(Long customerSequenceNumber) {
		Date currentTimeStamp = new Date();
		return tokensEntityRepo.checkTokenValidity(customerSequenceNumber, currentTimeStamp).isEmpty();
	}
}
