package com.insignia.daoImpl;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.daoInterface.SubCategoryDaoInterface;
import com.insignia.entity.ProductSubCategory;
import com.insignia.repo.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import static com.insignia.constants.SubCategoryConstant.validateSubCategoryNameAndCategoryId;
import static com.insignia.constants.SubCategoryConstant.validateSubCategoryNameAndCategoryIdCode;

@Repository
public class SubCategoryDaoImpl implements SubCategoryDaoInterface {

    @Autowired
    SubCategoryRepository subcategoryRepository;

    @Override
    public Optional<ProductSubCategory> findBySubCategoryId(Long subcategoryId) {
        return subcategoryRepository.findBySubCategoryId(subcategoryId);
    }

    @Override
    public Optional<List<ProductSubCategory>> fetchAllSubCategories() {
        return subcategoryRepository.fetchAllSubCategories();
    }

    @Transactional(rollbackOn = InvalidInputParametersException.class)
    @Override
    public ProductSubCategory saveSubCategory(ProductSubCategory productSubCategory) throws InvalidInputParametersException {
        try {
            return subcategoryRepository.save(productSubCategory);

        } catch (
                DataIntegrityViolationException e) {
            throw new InvalidInputParametersException(validateSubCategoryNameAndCategoryIdCode,
                    validateSubCategoryNameAndCategoryId);
        }

    }

    @Override
    public Optional<List<ProductSubCategory>> findByCategoryId(Long categoryId) {
        return subcategoryRepository.findByCategoryId(categoryId);
    }

    @Override
    public Optional<List<ProductSubCategory>> getInactiveSubCategories(Long categoryId) {
        return subcategoryRepository.getInactiveSubCategories(categoryId);
    }

    @Override
    public Optional<ProductSubCategory> findById(Long subCategoryId) {
        return subcategoryRepository.findById(subCategoryId);
    }
}

