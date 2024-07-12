package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.CategoryDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductCategory;
import com.insignia.entity.ProductSubCategory;
import com.insignia.model.CategoryRequest;
import com.insignia.model.CategoryResponse;
import com.insignia.serviceimpl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

	@Mock
	private CategoryDaoInterface categoryDaoInterface;

	@Mock
	private TokenDaoInterface tokenDaoInterface;

	@InjectMocks
	private CategoryServiceImpl categoryService;

	@Test
	void enableCategory_InvalidCategoryName_ThrowsInvalidInputParametersException() {

		CategoryRequest categoryRequest = new CategoryRequest();
		categoryRequest.setCategoryName("NonExistentCategory");
		categoryRequest.setApplicationId("insignia");
		categoryRequest.setTenantId("LU008");

		when(categoryDaoInterface.findByCategoryName(anyString(), anyString(), anyString())).thenReturn(null);

		assertThrows(InvalidInputParametersException.class, () -> categoryService.enableCategory(categoryRequest));
	}

	@Test
	void enableCategory_WithNullCategoryName_ThrowsInvalidInputParametersException() {

		CategoryRequest categoryRequest = new CategoryRequest();

		assertThrows(InvalidInputParametersException.class, () -> categoryService.enableCategory(categoryRequest));

		verifyNoInteractions(categoryDaoInterface);
	}

	@Test
	void mapToDto_WithProductCategories_ReturnsCategoryResponseList() {

		ProductCategory category = new ProductCategory();
		category.setCategoryId(1L);
		category.setCategoryName("Test Category");

		List<ProductCategory> productCategories = Collections.singletonList(category);

		Optional<List<CategoryResponse>> result = invokeMapToDto(productCategories);

		assertTrue(result.isPresent());
		assertEquals(1, result.get().size());
		assertEquals(1L, result.get().get(0).getCategoryId());
		assertEquals("Test Category", result.get().get(0).getCategoryName());
	}

	@Test
	void saveCategory_ValidInput_ReturnsCategoryResponse()
			throws InvalidInputParametersException, TokenExpiredException {

		CategoryRequest categoryRequest = new CategoryRequest();
		categoryRequest.setCategoryName("TestCategory");
		categoryRequest.setExpirationDuration(60);
		categoryRequest.setCustomerSequenceNumber(123L);

		ProductCategory savedCategory = new ProductCategory();
		savedCategory.setCategoryId(1L);
		savedCategory.setCategoryName("TestCategory");

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());
		;
		when(categoryDaoInterface.saveCategory(any())).thenReturn(savedCategory);

		CategoryResponse result = categoryService.saveCategory(categoryRequest);

		assertNotNull(result);
		assertEquals(savedCategory.getCategoryId(), result.getCategoryId());
		assertEquals(savedCategory.getCategoryName(), result.getCategoryName());

		verify(tokenDaoInterface).checkTokenValidity(anyLong(), anyInt());
		verify(categoryDaoInterface).saveCategory(any());
	}

	@Test
	void getCategories_WithNullCategoryId_ReturnsAllCategories()
			throws TokenExpiredException, InvalidInputParametersException {

		String applicationId = "insignia";
		String tenantId = "LU008";

		CategoryRequest categoryRequest = new CategoryRequest();
		categoryRequest.setApplicationId("insignia");
		categoryRequest.setTenantId("LU008");

		categoryRequest.setCustomerSequenceNumber(5L);
		categoryRequest.setExpirationDuration(15);
		List<ProductSubCategory> subCategories1 = Arrays.asList(new ProductSubCategory(), new ProductSubCategory());

		List<ProductSubCategory> subCategories2 = Arrays.asList(new ProductSubCategory(), new ProductSubCategory());

		List<ProductCategory> mockedCategories = Arrays.asList(
				new ProductCategory(1L, "insignia", "LU008", "Category1", "Desc1", "Path1", false, null,
						subCategories1),
				new ProductCategory(2L, "insignia", "LU008", "Category2", "Desc2", "Path2", false, null,
						subCategories2));

		when(categoryDaoInterface.fetchAllCategories(applicationId, tenantId)).thenReturn(mockedCategories);

		Optional<List<CategoryResponse>> result = categoryService.getCategories(categoryRequest);

		assertTrue(result.isPresent());
		assertEquals(2, result.get().size());
		assertEquals(1L, result.get().get(0).getCategoryId());
		assertEquals(2L, result.get().get(1).getCategoryId());
	}

	@Test
	void getCategories_WithCategoryId_ReturnsCategoryResponseList()
			throws TokenExpiredException, InvalidInputParametersException {

		CategoryRequest categoryRequest = new CategoryRequest();
		categoryRequest.setCategoryId(1L);
		categoryRequest.setExpirationDuration(7);
		categoryRequest.setCustomerSequenceNumber(123L);
		categoryRequest.setApplicationId("insignia");
		categoryRequest.setTenantId("LU008");

		ProductCategory existingCategory = new ProductCategory();
		existingCategory.setCategoryId(1L);
		existingCategory.setCategoryName("TestCategory");

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());
		when(categoryDaoInterface.findByCategoryId(anyLong(), anyString(), anyString()))
				.thenReturn(Optional.of(existingCategory));

		Optional<List<CategoryResponse>> result = categoryService.getCategories(categoryRequest);

		assertTrue(result.isPresent());
		assertEquals(1, result.get().size());
		assertEquals(existingCategory.getCategoryId(), result.get().get(0).getCategoryId());
		assertEquals(existingCategory.getCategoryName(), result.get().get(0).getCategoryName());

		verify(tokenDaoInterface).checkTokenValidity(anyLong(), anyInt());
		verify(categoryDaoInterface).findByCategoryId(anyLong(), anyString(), anyString());
	}

	@Test
	void disableCategory_ValidInput_ReturnsSuccessMessage()
			throws InvalidInputParametersException, TokenExpiredException {

		CategoryRequest categoryRequest = new CategoryRequest();
		categoryRequest.setCategoryId(1L);
		categoryRequest.setExpirationDuration(8);
		categoryRequest.setCustomerSequenceNumber(123L);
		categoryRequest.setApplicationId("insignia");
		categoryRequest.setTenantId("LU008");

		ProductCategory existingCategory = new ProductCategory();
		existingCategory.setCategoryId(1L);
		existingCategory.setCategoryName("TestCategory");

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());
		when(categoryDaoInterface.findByCategoryId(anyLong(), anyString(), anyString()))
				.thenReturn(Optional.of(existingCategory));
		when(categoryDaoInterface.saveCategory(any())).thenReturn(existingCategory);

		String result = categoryService.disableCategory(categoryRequest);

		assertEquals("Category disabled successfully", result);

		verify(tokenDaoInterface).checkTokenValidity(anyLong(), anyInt());
		verify(categoryDaoInterface).findByCategoryId(anyLong(), anyString(), anyString());
		verify(categoryDaoInterface).saveCategory(any());
	}

	@Test
	void updateCategory_ValidInput_ReturnsCategoryResponse()
			throws InvalidInputParametersException, TokenExpiredException {

		CategoryRequest categoryRequest = new CategoryRequest();
		categoryRequest.setCategoryId(1L);
		categoryRequest.setCategoryName("UpdatedCategory");
		categoryRequest.setCategoryDescription("test");
		categoryRequest.setCategoryImagePath("testImage");
		categoryRequest.setDefaultImage("test");
		categoryRequest.setExpirationDuration(7);
		categoryRequest.setCustomerSequenceNumber(123L);
		categoryRequest.setApplicationId("insignia");
		categoryRequest.setTenantId("LU008");

		ProductCategory existingCategory = new ProductCategory();
		existingCategory.setCategoryId(1L);
		existingCategory.setCategoryName("TestCategory");

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());
		when(categoryDaoInterface.findByCategoryId(anyLong(), anyString(), anyString()))
				.thenReturn(Optional.of(existingCategory));
		when(categoryDaoInterface.saveCategory(any())).thenReturn(existingCategory);

		CategoryResponse result = categoryService.updateCategory(categoryRequest);

		assertNotNull(result);
		assertEquals(existingCategory.getCategoryId(), result.getCategoryId());
		assertEquals(categoryRequest.getCategoryName(), result.getCategoryName());

		verify(tokenDaoInterface).checkTokenValidity(anyLong(), anyInt());
		verify(categoryDaoInterface).findByCategoryId(anyLong(), anyString(), anyString());
		verify(categoryDaoInterface).saveCategory(any());
	}

	@Test
	void enableCategory_ValidInput_ReturnsCategoryResponse()
			throws InvalidInputParametersException, TokenExpiredException {

		CategoryRequest categoryRequest = new CategoryRequest();
		categoryRequest.setCategoryName("TestCategory");
		categoryRequest.setExpirationDuration(7);
		categoryRequest.setCustomerSequenceNumber(123L);
		categoryRequest.setApplicationId("insignia");
		categoryRequest.setTenantId("LU008");

		ProductCategory existingCategory = new ProductCategory();
		existingCategory.setCategoryId(1L);
		existingCategory.setCategoryName("TestCategory");

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());
		when(categoryDaoInterface.findByCategoryName(anyString(), anyString(), anyString()))
				.thenReturn(existingCategory);
		when(categoryDaoInterface.saveCategory(any())).thenReturn(existingCategory);

		CategoryResponse result = categoryService.enableCategory(categoryRequest);

		assertNotNull(result);
		assertEquals(existingCategory.getCategoryId(), result.getCategoryId());
		assertEquals(existingCategory.getCategoryName(), result.getCategoryName());

		verify(tokenDaoInterface).checkTokenValidity(anyLong(), anyInt());
		verify(categoryDaoInterface).findByCategoryName(anyString(), anyString(), anyString());
		verify(categoryDaoInterface).saveCategory(any());
	}

	private Optional<List<CategoryResponse>> invokeMapToDto(List<ProductCategory> productCategories) {
		try {
			java.lang.reflect.Method method = CategoryServiceImpl.class.getDeclaredMethod("mapToDto", List.class);
			method.setAccessible(true);
			return (Optional<List<CategoryResponse>>) method.invoke(categoryService, productCategories);
		} catch (Exception e) {
			throw new RuntimeException("Failed to invoke mapToDto method for testing.", e);
		}
	}
}
