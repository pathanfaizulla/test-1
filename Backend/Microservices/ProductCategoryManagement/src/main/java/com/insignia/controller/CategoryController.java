package com.insignia.controller;

import static com.insignia.constants.CategoryConstant.categoryDescriptionLength;
import static com.insignia.constants.CategoryConstant.categoryImagePathLength;
import static com.insignia.constants.CategoryConstant.categoryNameLength;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.CategoryRequest;
import com.insignia.model.CategoryResponse;
import com.insignia.service.CategoryServiceInterface;
import com.insignia.validations.CategoryValidator;
import com.insignia.validations.CommonValidation;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryServiceInterface categoryServiceImpl;

	/**
	 * Fetch all categories details in case of categoryId is null Get category
	 * details by categoryId
	 */
	@PostMapping("/getCategories")
	public ResponseEntity<List<CategoryResponse>> getAllCategories(@RequestBody CategoryRequest categoryRequest) {

		try {
			CommonValidation.validateApplicatinIdAndTenantId(categoryRequest.getApplicationId(),
					categoryRequest.getTenantId());
			return ResponseEntity.ok(categoryServiceImpl.getCategories(categoryRequest).get());

		} catch (InvalidInputParametersException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(
					CategoryResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getMessage()).build()));

		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(
					CategoryResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getStrMsg()).build()));

		} catch (Exception e) {
			log.error("GET-ALL-CATEGORY: Exception occurred .Error ={}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonList(
							CategoryResponse.builder().errorCode(CommonConstant.validateUnexpectedErrorCode)
									.errorMessage(CommonConstant.validateUnexpectedErrorMessage).build()));
		}

	}

	@PostMapping("/saveCategory")
	public ResponseEntity<CategoryResponse> saveCategory(@RequestBody CategoryRequest categoryRequest) {
		try {
			
			CategoryValidator.validateCategoryName(categoryRequest.getCategoryName(), categoryNameLength, true);
			validateCategoryRequest(categoryRequest);
			return new ResponseEntity<>(categoryServiceImpl.saveCategory(categoryRequest), HttpStatus.OK);

		} catch (InvalidInputParametersException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(CategoryResponse.builder().errorCode(e.getStrMsg()).errorMessage(e.getErrorCode()).build());

		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(CategoryResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getStrMsg()).build());

		} catch (Exception e) {
			log.error("SAVE-CATEGORY: Exception occurred .Error ={}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(CategoryResponse.builder().errorCode(CommonConstant.validateUnexpectedErrorCode)
							.errorMessage(CommonConstant.validateUnexpectedErrorMessage).build());
		}
	}

	@PutMapping("/disableCategory")
	public ResponseEntity<String> disableCategory(@RequestBody CategoryRequest categoryRequest) {
		try {
			CommonValidation.validateApplicatinIdAndTenantId(categoryRequest.getApplicationId(),
					categoryRequest.getTenantId());
			return new ResponseEntity<>(categoryServiceImpl.disableCategory(categoryRequest), HttpStatus.OK);

		} catch (InvalidInputParametersException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getStrMsg());

		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getStrMsg());

		} catch (Exception e) {
			log.error("DISABLE-CATEGORY: Exception occurred .Error ={}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PutMapping("/enableCategory")
	public ResponseEntity<CategoryResponse> enableCategory(@RequestBody CategoryRequest categoryRequest) {
		try {
			CommonValidation.validateApplicatinIdAndTenantId(categoryRequest.getApplicationId(),
					categoryRequest.getTenantId());
			return new ResponseEntity(categoryServiceImpl.enableCategory(categoryRequest), HttpStatus.OK);

		} catch (InvalidInputParametersException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(CategoryResponse.builder().errorCode(e.getStrMsg()).errorMessage(e.getErrorCode()).build());

		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(CategoryResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getStrMsg()).build());

		} catch (Exception e) {
			log.error("ENABLE-CATEGORY: Exception occurred .Error ={}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(CategoryResponse.builder().errorCode(CommonConstant.validateUnexpectedErrorCode)
							.errorMessage(CommonConstant.validateUnexpectedErrorMessage).build());
		}
	}

	@PutMapping("/updateCategory")
	public ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryRequest categoryRequest) {
		try {
			CategoryValidator.validateCategoryName(categoryRequest.getCategoryName(), categoryNameLength, false);
			validateCategoryRequest(categoryRequest);
			CategoryResponse categoryResponse = categoryServiceImpl.updateCategory(categoryRequest);
			return new ResponseEntity<>(categoryResponse, HttpStatus.OK);

		} catch (InvalidInputParametersException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(CategoryResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getStrMsg()).build());

		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(CategoryResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getStrMsg()).build());

		} catch (Exception e) {
			log.error("UPDATE-CATEGORY: Exception occurred .Error ={}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(CategoryResponse.builder().errorCode(CommonConstant.validateUnexpectedErrorCode)
							.errorMessage(CommonConstant.validateUnexpectedErrorMessage).build());
		}
	}

	private void validateCategoryRequest(CategoryRequest categoryRequest) throws InvalidInputParametersException {
		CommonValidation.validateApplicatinIdAndTenantId(categoryRequest.getApplicationId(),
				categoryRequest.getTenantId());

		CategoryValidator.validateCategoryDescription(categoryRequest.getCategoryDescription(),
				categoryDescriptionLength);
		CategoryValidator.validateCategoryImagePath(categoryRequest.getCategoryImagePath(), categoryImagePathLength);
	}

}
