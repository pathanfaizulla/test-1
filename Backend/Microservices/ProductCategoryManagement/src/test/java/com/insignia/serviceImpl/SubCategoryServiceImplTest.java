package com.insignia.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
import com.insignia.daoInterface.SubCategoryDaoInterface;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.entity.ProductSubCategory;
import com.insignia.model.SubCategoryRequest;
import com.insignia.model.SubCategoryResponse;
import com.insignia.serviceimpl.SubCategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class SubCategoryServiceImplTest {

	@Mock
	private SubCategoryDaoInterface subcategoryDaoInterface;

	@Mock
	private TokenDaoInterface tokenDaoInterface;

	@InjectMocks
	private SubCategoryServiceImpl subcategoryService;

	@Test
	void saveSubCategory_ValidInput_Success() throws TokenExpiredException, InvalidInputParametersException {
		SubCategoryRequest subCategoryRequest = new SubCategoryRequest();
		subCategoryRequest.setSubCategoryName("TestSubCategory");
		subCategoryRequest.setExpirationDuration(60);
		subCategoryRequest.setCustomerSequenceNumber(123L);

		ProductSubCategory savedSubCategory = new ProductSubCategory();
		savedSubCategory.setSubCategoryId(1L);
		savedSubCategory.setSubCategoryName("TestSubCategory");

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());

		when(subcategoryDaoInterface.saveSubCategory(any())).thenReturn(savedSubCategory);

		SubCategoryResponse result = subcategoryService.saveSubCategory(subCategoryRequest);

		assertEquals(savedSubCategory.getSubCategoryId(), result.getSubcategoryId());
		assertEquals(savedSubCategory.getCategoryId(), result.getCategoryId());
		assertEquals(savedSubCategory.getSubCategoryName(), result.getSubcategoryName());
		assertEquals(savedSubCategory.getSubCategoryDescription(), result.getSubCategoryDescription());
		assertEquals(savedSubCategory.getSubCategoryImagePath(), result.getSubCategoryImagePath());

	}

	@Test
	void updateSubCategory_ValidInput_Success() throws TokenExpiredException, InvalidInputParametersException {
		SubCategoryRequest subCategoryRequest = new SubCategoryRequest();
		subCategoryRequest.setSubCategoryId(1L);
		subCategoryRequest.setSubCategoryName("UpdatedCategory");
		subCategoryRequest.setSubCategoryDescription("test");
		subCategoryRequest.setSubCategoryImagePath("testImage");
		subCategoryRequest.setSubCategoryImagePath("test");
		subCategoryRequest.setSubCategoryDefaultImage("testDefault");
		subCategoryRequest.setExpirationDuration(7);
		subCategoryRequest.setCustomerSequenceNumber(123L);
		subCategoryRequest.setCategoryId(8L);

		ProductSubCategory existingSubCategory = createProductSubCategory();
		ProductSubCategory updatedSubCategory = createProductSubCategory();
		updatedSubCategory.setSubCategoryName("UpdatedName");

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());
		when(subcategoryDaoInterface.findBySubCategoryId(anyLong())).thenReturn(Optional.of(existingSubCategory));
		when(subcategoryDaoInterface.saveSubCategory(any())).thenReturn(updatedSubCategory);

		SubCategoryResponse result = subcategoryService.updateSubCategory(subCategoryRequest);

		assertEquals(updatedSubCategory.getSubCategoryId(), result.getSubcategoryId());
		assertEquals(updatedSubCategory.getCategoryId(), result.getCategoryId());
		assertEquals(updatedSubCategory.getSubCategoryName(), result.getSubcategoryName());
		assertEquals(updatedSubCategory.getSubCategoryDescription(), result.getSubCategoryDescription());
		assertEquals(updatedSubCategory.getSubCategoryImagePath(), result.getSubCategoryImagePath());
	}

	@Test
	void disableSubCategory_ValidInput_Success() throws TokenExpiredException, InvalidInputParametersException {
		SubCategoryRequest subCategoryRequest = new SubCategoryRequest();
		subCategoryRequest.setSubCategoryId(1L);
		subCategoryRequest.setSubCategoryName("UpdatedCategory");
		subCategoryRequest.setSubCategoryDescription("test");
		subCategoryRequest.setSubCategoryImagePath("testImage");
		subCategoryRequest.setSubCategoryImagePath("test");
		subCategoryRequest.setSubCategoryDefaultImage("testDefault");
		subCategoryRequest.setExpirationDuration(7);
		subCategoryRequest.setCustomerSequenceNumber(123L);
		subCategoryRequest.setCategoryId(8L);

		ProductSubCategory existingSubCategory = createProductSubCategory();

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());
		when(subcategoryDaoInterface.findBySubCategoryId(anyLong())).thenReturn(Optional.of(existingSubCategory));
		when(subcategoryDaoInterface.saveSubCategory(any())).thenReturn(existingSubCategory);

		String result = subcategoryService.disableSubCategory(subCategoryRequest);

		assertEquals("Category disabled successfully", result);
		assertTrue(existingSubCategory.getIsSoftDeleted());
	}

	@Test
	void enableSubCategory_ValidInput_Success() throws TokenExpiredException, InvalidInputParametersException {
		SubCategoryRequest subCategoryRequest = new SubCategoryRequest();
		subCategoryRequest.setSubCategoryId(1L);
		subCategoryRequest.setSubCategoryName("UpdatedCategory");
		subCategoryRequest.setSubCategoryDescription("test");
		subCategoryRequest.setSubCategoryImagePath("testImage");
		subCategoryRequest.setSubCategoryImagePath("test");
		subCategoryRequest.setSubCategoryDefaultImage("testDefault");
		subCategoryRequest.setExpirationDuration(7);
		subCategoryRequest.setCustomerSequenceNumber(123L);
		subCategoryRequest.setCategoryId(8L);

		ProductSubCategory existingSubCategory = createProductSubCategory();
		existingSubCategory.setIsSoftDeleted(true);

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());
		when(subcategoryDaoInterface.findById(anyLong())).thenReturn(Optional.of(existingSubCategory));
		when(subcategoryDaoInterface.saveSubCategory(any())).thenReturn(existingSubCategory);

		SubCategoryResponse result = subcategoryService.enableSubCategory(subCategoryRequest);

		assertFalse(existingSubCategory.getIsSoftDeleted());
		assertEquals(existingSubCategory.getSubCategoryId(), result.getSubcategoryId());
		assertEquals(existingSubCategory.getCategoryId(), result.getCategoryId());
		assertEquals(existingSubCategory.getSubCategoryName(), result.getSubcategoryName());
		assertEquals(existingSubCategory.getSubCategoryDescription(), result.getSubCategoryDescription());
		assertEquals(existingSubCategory.getSubCategoryImagePath(), result.getSubCategoryImagePath());
	}

	@Test
	void disableSubCategory_SubCategoryNotFound_ThrowsInvalidInputParametersException() throws TokenExpiredException {
		SubCategoryRequest subCategoryRequest = new SubCategoryRequest();
		subCategoryRequest.setSubCategoryId(1L);
		subCategoryRequest.setSubCategoryName("UpdatedCategory");
		subCategoryRequest.setSubCategoryDescription("test");
		subCategoryRequest.setSubCategoryImagePath("testImage");
		subCategoryRequest.setSubCategoryImagePath("test");
		subCategoryRequest.setSubCategoryDefaultImage("testDefault");
		subCategoryRequest.setExpirationDuration(7);
		subCategoryRequest.setCustomerSequenceNumber(123L);
		subCategoryRequest.setCategoryId(8L);

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());
		when(subcategoryDaoInterface.findBySubCategoryId(anyLong())).thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class,
				() -> subcategoryService.disableSubCategory(subCategoryRequest));
	}

	@Test
	void enableSubCategory_SubCategoryNotFound_ThrowsInvalidInputParametersException() throws TokenExpiredException {
		SubCategoryRequest subCategoryRequest = new SubCategoryRequest();
		subCategoryRequest.setSubCategoryId(1L);
		subCategoryRequest.setSubCategoryName("UpdatedCategory");
		subCategoryRequest.setSubCategoryDescription("test");
		subCategoryRequest.setSubCategoryImagePath("testImage");
		subCategoryRequest.setSubCategoryImagePath("test");
		subCategoryRequest.setSubCategoryDefaultImage("testDefault");
		subCategoryRequest.setExpirationDuration(7);
		subCategoryRequest.setCustomerSequenceNumber(123L);
		subCategoryRequest.setCategoryId(8L);

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());
		when(subcategoryDaoInterface.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(InvalidInputParametersException.class,
				() -> subcategoryService.enableSubCategory(subCategoryRequest));
	}

	@Test
	void getSubCategories_BySubCategoryId_Success() throws TokenExpiredException, InvalidInputParametersException {
		SubCategoryRequest subCategoryRequest = new SubCategoryRequest();
		subCategoryRequest.setSubCategoryId(1L);
		subCategoryRequest.setSubCategoryName("UpdatedCategory");
		subCategoryRequest.setSubCategoryDescription("test");
		subCategoryRequest.setSubCategoryImagePath("testImage");
		subCategoryRequest.setSubCategoryImagePath("test");
		subCategoryRequest.setSubCategoryDefaultImage("testDefault");
		subCategoryRequest.setExpirationDuration(7);
		subCategoryRequest.setCustomerSequenceNumber(123L);
		subCategoryRequest.setCategoryId(8L);

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());
		ProductSubCategory mockSubCategory = createProductSubCategory();
		when(subcategoryDaoInterface.findBySubCategoryId(anyLong())).thenReturn(Optional.of(mockSubCategory));

		Optional<List<SubCategoryResponse>> result = subcategoryService.getSubCategories(subCategoryRequest);

		assertTrue(result.isPresent());
		assertEquals(1, result.get().size());
		SubCategoryResponse subCategoryResponse = result.get().get(0);
		assertEquals(mockSubCategory.getSubCategoryId(), subCategoryResponse.getSubcategoryId());
		assertEquals(mockSubCategory.getCategoryId(), subCategoryResponse.getCategoryId());
		assertEquals(mockSubCategory.getSubCategoryName(), subCategoryResponse.getSubcategoryName());
		assertEquals(mockSubCategory.getSubCategoryDescription(), subCategoryResponse.getSubCategoryDescription());
		assertEquals(mockSubCategory.getSubCategoryImagePath(), subCategoryResponse.getSubCategoryImagePath());
	}

	@Test
	void updateSubCategory_SubCategoryNotFound_ReturnsEmptySubCategoryResponse()
			throws TokenExpiredException, InvalidInputParametersException {

		SubCategoryRequest subCategoryRequest = new SubCategoryRequest();
		subCategoryRequest.setSubCategoryId(1L);
		subCategoryRequest.setSubCategoryName("UpdatedCategory");
		subCategoryRequest.setSubCategoryDescription("test");
		subCategoryRequest.setSubCategoryImagePath("testImage");
		subCategoryRequest.setSubCategoryImagePath("test");
		subCategoryRequest.setSubCategoryDefaultImage("testDefault");
		subCategoryRequest.setExpirationDuration(7);
		subCategoryRequest.setCustomerSequenceNumber(123L);
		subCategoryRequest.setCategoryId(8L);

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());
		when(subcategoryDaoInterface.findBySubCategoryId(anyLong())).thenReturn(Optional.empty());

		SubCategoryResponse result = subcategoryService.updateSubCategory(subCategoryRequest);

		assertNotNull(result);
		assertNull(result.getSubcategoryId());
		assertNull(result.getCategoryId());
		assertNull(result.getSubcategoryName());
		assertNull(result.getSubCategoryDescription());
		assertNull(result.getSubCategoryImagePath());
	}

	@Test
	void getSubCategories_CoverAllScenarios_Success() throws TokenExpiredException, InvalidInputParametersException {

		SubCategoryRequest subCategoryRequest = new SubCategoryRequest();
		subCategoryRequest.setSubCategoryId(1L);
		subCategoryRequest.setSubCategoryName("UpdatedCategory");
		subCategoryRequest.setSubCategoryDescription("test");
		subCategoryRequest.setSubCategoryImagePath("testImage");
		subCategoryRequest.setSubCategoryImagePath("test");
		subCategoryRequest.setSubCategoryDefaultImage("testDefault");
		subCategoryRequest.setExpirationDuration(7);
		subCategoryRequest.setCustomerSequenceNumber(123L);
		subCategoryRequest.setCategoryId(8L);

		doNothing().when(tokenDaoInterface).checkTokenValidity(any(), any());
		ProductSubCategory mockSubCategory = createProductSubCategory();
		when(subcategoryDaoInterface.findBySubCategoryId(anyLong())).thenReturn(Optional.of(mockSubCategory));

		Optional<List<SubCategoryResponse>> result = subcategoryService.getSubCategories(subCategoryRequest);

		assertTrue(result.isPresent());
		assertEquals(1, result.get().size());
		SubCategoryResponse subCategoryResponse = result.get().get(0);
		assertEquals(mockSubCategory.getSubCategoryId(), subCategoryResponse.getSubcategoryId());
		assertEquals(mockSubCategory.getCategoryId(), subCategoryResponse.getCategoryId());
		assertEquals(mockSubCategory.getSubCategoryName(), subCategoryResponse.getSubcategoryName());
		assertEquals(mockSubCategory.getSubCategoryDescription(), subCategoryResponse.getSubCategoryDescription());
		assertEquals(mockSubCategory.getSubCategoryImagePath(), subCategoryResponse.getSubCategoryImagePath());

		subCategoryRequest.setSubCategoryId(null);
		subCategoryRequest.setCategoryId(2L);
		when(subcategoryDaoInterface.findByCategoryId(anyLong())).thenReturn(Optional.of(Collections.emptyList()));

		assertThrows(InvalidInputParametersException.class,
				() -> subcategoryService.getSubCategories(subCategoryRequest));

		List<ProductSubCategory> mockSubCategories = Collections.singletonList(mockSubCategory);
		when(subcategoryDaoInterface.findByCategoryId(anyLong())).thenReturn(Optional.of(mockSubCategories));

		result = subcategoryService.getSubCategories(subCategoryRequest);

		assertTrue(result.isPresent());
		assertEquals(1, result.get().size());
		subCategoryResponse = result.get().get(0);
		assertEquals(mockSubCategory.getSubCategoryId(), subCategoryResponse.getSubcategoryId());
		assertEquals(mockSubCategory.getCategoryId(), subCategoryResponse.getCategoryId());
		assertEquals(mockSubCategory.getSubCategoryName(), subCategoryResponse.getSubcategoryName());
		assertEquals(mockSubCategory.getSubCategoryDescription(), subCategoryResponse.getSubCategoryDescription());
		assertEquals(mockSubCategory.getSubCategoryImagePath(), subCategoryResponse.getSubCategoryImagePath());

		subCategoryRequest.setSubCategoryId(null);
		subCategoryRequest.setCategoryId(null);
		when(subcategoryDaoInterface.fetchAllSubCategories()).thenReturn(Optional.of(mockSubCategories));

		result = subcategoryService.getSubCategories(subCategoryRequest);

		assertTrue(result.isPresent());
		assertEquals(1, result.get().size());
		subCategoryResponse = result.get().get(0);
		assertEquals(mockSubCategory.getSubCategoryId(), subCategoryResponse.getSubcategoryId());
		assertEquals(mockSubCategory.getCategoryId(), subCategoryResponse.getCategoryId());
		assertEquals(mockSubCategory.getSubCategoryName(), subCategoryResponse.getSubcategoryName());
		assertEquals(mockSubCategory.getSubCategoryDescription(), subCategoryResponse.getSubCategoryDescription());
		assertEquals(mockSubCategory.getSubCategoryImagePath(), subCategoryResponse.getSubCategoryImagePath());

		when(subcategoryDaoInterface.fetchAllSubCategories()).thenReturn(Optional.empty());

		result = subcategoryService.getSubCategories(subCategoryRequest);

		assertFalse(result.isPresent());
	}

	private ProductSubCategory createProductSubCategory() {
		ProductSubCategory subCategory = new ProductSubCategory();
		subCategory.setSubCategoryId(1L);
		subCategory.setCategoryId(1L);
		subCategory.setSubCategoryName("SubCategoryName");
		subCategory.setSubCategoryDescription("Description");
		subCategory.setSubCategoryImagePath("ImagePath");
		subCategory.setIsSoftDeleted(false);
		return subCategory;
	}
}
