package com.insignia.controller;

import static com.insignia.constants.SubCategoryConstant.subCategoryDescriptionLength;
import static com.insignia.constants.SubCategoryConstant.subCategoryImagePathLength;
import static com.insignia.constants.SubCategoryConstant.subCategoryNameLength;

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
import com.insignia.model.CategoryResponse;
import com.insignia.model.SubCategoryRequest;
import com.insignia.model.SubCategoryResponse;
import com.insignia.service.SubCategoryServiceInterface;
import com.insignia.validations.SubCategoryValidator;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@RequestMapping("/subcategories")
@Slf4j
public class SubCategoryController {

	@Autowired
	private SubCategoryServiceInterface subcategoryServiceImpl;

	@PostMapping("/getSubCategories")
	public ResponseEntity<List<SubCategoryResponse>> getAllSubCategories(
			@RequestBody SubCategoryRequest subcategoryRequest) {

		try {
			return ResponseEntity.ok(subcategoryServiceImpl.getSubCategories(subcategoryRequest).get());

		} catch (InvalidInputParametersException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(
					SubCategoryResponse.builder().errorCode(e.getStrMsg()).errorMessage(e.getErrorCode()).build()));

		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(
					SubCategoryResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getStrMsg()).build()));

		} catch (Exception e) {
			log.error("GET-ALL-SUB-CATEGORY: Exception occurred .Error ={}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonList(
							SubCategoryResponse.builder().errorCode(CommonConstant.validateUnexpectedErrorCode)
									.errorMessage(CommonConstant.validateUnexpectedErrorMessage).build()));
		}
	}

	@PostMapping("/saveSubCategory")
	public ResponseEntity<SubCategoryResponse> saveSubCategory(@RequestBody SubCategoryRequest subcategoryRequest) {
		try {
			SubCategoryValidator.validateSubCategoryName(subcategoryRequest.getSubCategoryName(), subCategoryNameLength, true);
			validateSubCategoryRequest(subcategoryRequest);
			return new ResponseEntity(subcategoryServiceImpl.saveSubCategory(subcategoryRequest), HttpStatus.OK);
		} catch (InvalidInputParametersException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					SubCategoryResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getStrMsg()).build());

		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					SubCategoryResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getStrMsg()).build());

		} catch (Exception e) {
			log.error("SAVE-CATEGORY: Exception occurred .Error ={}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(SubCategoryResponse.builder().errorCode(CommonConstant.validateUnexpectedErrorCode)
							.errorMessage(CommonConstant.validateUnexpectedErrorMessage).build());
		}
	}

	@PutMapping("/disableSubCategory")
	public ResponseEntity<String> disableSubCategory(@RequestBody SubCategoryRequest subCategoryRequest) {

		try {
			return new ResponseEntity<>(subcategoryServiceImpl.disableSubCategory(subCategoryRequest), HttpStatus.OK);

		} catch (InvalidInputParametersException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getStrMsg());

		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getStrMsg());

		} catch (Exception e) {
			log.error("DISABLE-SUB-CATEGORY: Exception occurred .Error ={}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PutMapping("/enableSubCategory")
	public ResponseEntity<SubCategoryResponse> enableSubCategory(@RequestBody SubCategoryRequest subCategoryRequest) {
		try {
			return new ResponseEntity(subcategoryServiceImpl.enableSubCategory(subCategoryRequest), HttpStatus.OK);

		} catch (InvalidInputParametersException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					SubCategoryResponse.builder().errorCode(e.getStrMsg()).errorMessage(e.getErrorCode()).build());

		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					SubCategoryResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getStrMsg()).build());

		} catch (Exception e) {
			log.error("ENABLE-SUB-CATEGORY: Exception occurred .Error ={}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(SubCategoryResponse.builder().errorCode(CommonConstant.validateUnexpectedErrorCode)
							.errorMessage(CommonConstant.validateUnexpectedErrorMessage).build());
		}
	}

	@PutMapping("/updateSubCategory")
	public ResponseEntity<CategoryResponse> updateSubCategory(@RequestBody SubCategoryRequest subCategoryRequest) {
		try {
			SubCategoryValidator.validateSubCategoryName(subCategoryRequest.getSubCategoryName(), subCategoryNameLength, false);
			validateSubCategoryRequest(subCategoryRequest);
			return new ResponseEntity(subcategoryServiceImpl.updateSubCategory(subCategoryRequest), HttpStatus.OK);

		} catch (InvalidInputParametersException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(CategoryResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getStrMsg()).build());

		} catch (TokenExpiredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(CategoryResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getStrMsg()).build());

		} catch (Exception e) {
			log.error("UPDATE-SUB-CATEGORY: Exception occurred .Error ={}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(CategoryResponse.builder().errorCode(CommonConstant.validateUnexpectedErrorCode)
							.errorMessage(CommonConstant.validateUnexpectedErrorMessage).build());
		}
	}

	private void validateSubCategoryRequest(SubCategoryRequest subCategoryRequest)
			throws InvalidInputParametersException {		
		SubCategoryValidator.validateSubCategoryDescription(subCategoryRequest.getSubCategoryDescription(),
				subCategoryDescriptionLength);
		SubCategoryValidator.validateSubCategoryImagePath(subCategoryRequest.getSubCategoryImagePath(),
				subCategoryImagePathLength);
	}

}
