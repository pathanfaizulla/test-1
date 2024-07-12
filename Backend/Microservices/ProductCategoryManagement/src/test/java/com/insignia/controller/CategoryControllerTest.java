package com.insignia.controller;

import static com.insignia.constants.CommonConstant.validateUnexpectedErrorCode;
import static com.insignia.constants.CommonConstant.validateUnexpectedErrorMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.CategoryRequest;
import com.insignia.model.CategoryResponse;
import com.insignia.service.CategoryServiceInterface;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryServiceInterface categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private CategoryRequest categoryRequest;
    private CategoryResponse categoryResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dataInitialization();
    }
    private void dataInitialization() {
        categoryRequest = new CategoryRequest();
        categoryRequest.setCategoryId(1L);
        categoryRequest.setCategoryName("ExampleCategory");
        categoryRequest.setCategoryDescription("ExampleDescription");
        categoryRequest.setCategoryImagePath("ExampleImagePath");
        categoryRequest.setCustomerSequenceNumber(1L);
        categoryRequest.setExpirationDuration(30);
        categoryRequest.setApplicationId("insignia");
        categoryRequest.setTenantId("LU008");

        categoryResponse = new CategoryResponse();
  
    }

    @Test
    void testGetAllCategories() throws InvalidInputParametersException, TokenExpiredException {
        when(categoryService.getCategories(categoryRequest)).thenReturn(Optional.of(Collections.singletonList(categoryResponse)));

        ResponseEntity<List<CategoryResponse>> responseEntity = categoryController.getAllCategories(categoryRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());

        verify(categoryService, times(1)).getCategories(categoryRequest);
    }

    @Test
    void testGetAllCategories_InvalidInputParametersException() throws InvalidInputParametersException, TokenExpiredException {
        when(categoryService.getCategories(categoryRequest)).thenThrow(new InvalidInputParametersException("Error", "Invalid input"));

        ResponseEntity<List<CategoryResponse>> responseEntity = categoryController.getAllCategories(categoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());

        verify(categoryService, times(1)).getCategories(categoryRequest);
    }

    @Test
    void testGetAllCategories_TokenExpiredException() throws InvalidInputParametersException, TokenExpiredException {
        when(categoryService.getCategories(categoryRequest)).thenThrow(new TokenExpiredException("Token expired", "Token has expired"));

        ResponseEntity<List<CategoryResponse>> responseEntity = categoryController.getAllCategories(categoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());

        verify(categoryService, times(1)).getCategories(categoryRequest);
    }

    @Test
    void testGetAllCategories_Exception() throws InvalidInputParametersException, TokenExpiredException {
        when(categoryService.getCategories(categoryRequest)).thenThrow(new RuntimeException("Simulating an unexpected exception"));

        ResponseEntity<List<CategoryResponse>> responseEntity = categoryController.getAllCategories(categoryRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());

        verify(categoryService, times(1)).getCategories(categoryRequest);
    }

    @Test
    void testSaveCategory() throws InvalidInputParametersException, TokenExpiredException {
        when(categoryService.saveCategory(categoryRequest)).thenReturn(categoryResponse);

        ResponseEntity<CategoryResponse> responseEntity = categoryController.saveCategory(categoryRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(categoryResponse, responseEntity.getBody());

        verify(categoryService, times(1)).saveCategory(categoryRequest);
    }

    @Test
    void testSaveCategory_InvalidInputParametersException() throws InvalidInputParametersException, TokenExpiredException {
        when(categoryService.saveCategory(categoryRequest)).thenThrow(new InvalidInputParametersException("Error", "Invalid input"));

        ResponseEntity<CategoryResponse> responseEntity = categoryController.saveCategory(categoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(categoryService, times(1)).saveCategory(categoryRequest);
    }

    @Test
    void testSaveCategory_TokenExpiredException() throws InvalidInputParametersException, TokenExpiredException {
        when(categoryService.saveCategory(categoryRequest)).thenThrow(new TokenExpiredException("TokenExpired", "Token has expired"));

        ResponseEntity<CategoryResponse> responseEntity = categoryController.saveCategory(categoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("TokenExpired", responseEntity.getBody().getErrorCode());
        assertEquals("Token has expired", responseEntity.getBody().getErrorMessage());

        verify(categoryService, times(1)).saveCategory(categoryRequest);
    }

    @Test
    void testUpdateCategory() throws InvalidInputParametersException, TokenExpiredException {
        when(categoryService.updateCategory(categoryRequest)).thenReturn(categoryResponse);

        ResponseEntity<CategoryResponse> responseEntity = categoryController.updateCategory(categoryRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(categoryResponse, responseEntity.getBody());

        verify(categoryService, times(1)).updateCategory(categoryRequest);
    }

    @Test
    void testUpdateCategory_InvalidInputParametersException() throws InvalidInputParametersException, TokenExpiredException {
        when(categoryService.updateCategory(categoryRequest)).thenThrow(new InvalidInputParametersException("Error", "Invalid input"));

        ResponseEntity<CategoryResponse> responseEntity = categoryController.updateCategory(categoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(categoryService, times(1)).updateCategory(categoryRequest);
    }

    @Test
    void testUpdateCategory_TokenExpiredException() throws InvalidInputParametersException, TokenExpiredException {
        when(categoryService.updateCategory(categoryRequest)).thenThrow(new TokenExpiredException("TokenExpired", "Token has expired"));

        ResponseEntity<CategoryResponse> responseEntity = categoryController.updateCategory(categoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("TokenExpired", responseEntity.getBody().getErrorCode());
        assertEquals("Token has expired", responseEntity.getBody().getErrorMessage());

        verify(categoryService, times(1)).updateCategory(categoryRequest);
    }

    @Test
    void testDisableCategory() throws InvalidInputParametersException, TokenExpiredException {
        when(categoryService.disableCategory(categoryRequest)).thenReturn("Category disabled successfully");

        ResponseEntity<String> responseEntity = categoryController.disableCategory(categoryRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Category disabled successfully", responseEntity.getBody());

        verify(categoryService, times(1)).disableCategory(categoryRequest);
    }
    @Test
    void testDisableCategory_InvalidInputParametersException() throws InvalidInputParametersException, TokenExpiredException {
        when(categoryService.disableCategory(categoryRequest)).thenThrow(new InvalidInputParametersException("Error", "Invalid input"));

        ResponseEntity<String> responseEntity = categoryController.disableCategory(categoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(categoryService, times(1)).disableCategory(categoryRequest);
    }
    @Test
    void testDisableCategory_TokenExpiredException() throws InvalidInputParametersException, TokenExpiredException {
        when(categoryService.disableCategory(categoryRequest)).thenThrow(new TokenExpiredException("TokenExpired", "Token has expired"));

        ResponseEntity<String> responseEntity = categoryController.disableCategory(categoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Token has expired", responseEntity.getBody());

        verify(categoryService, times(1)).disableCategory(categoryRequest);
    }

    @Test
    void testEnableCategory() throws InvalidInputParametersException, TokenExpiredException {
        when(categoryService.enableCategory(categoryRequest)).thenReturn(categoryResponse);

        ResponseEntity<CategoryResponse> responseEntity = categoryController.enableCategory(categoryRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(categoryResponse, responseEntity.getBody());

        verify(categoryService, times(1)).enableCategory(categoryRequest);
    }

    @Test
    void testEnableCategory_InvalidInputParametersException() throws InvalidInputParametersException, TokenExpiredException {
        when(categoryService.enableCategory(categoryRequest)).thenThrow(new InvalidInputParametersException("Error", "Invalid input"));

        ResponseEntity<CategoryResponse> responseEntity = categoryController.enableCategory(categoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(categoryService, times(1)).enableCategory(categoryRequest);
    }

    @Test
    void testEnableCategory_TokenExpiredException() throws InvalidInputParametersException, TokenExpiredException {
        when(categoryService.enableCategory(categoryRequest)).thenThrow(new TokenExpiredException("TokenExpired", "Token has expired"));

        ResponseEntity<CategoryResponse> responseEntity = categoryController.enableCategory(categoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("TokenExpired", responseEntity.getBody().getErrorCode());
        assertEquals("Token has expired", responseEntity.getBody().getErrorMessage());

        verify(categoryService, times(1)).enableCategory(categoryRequest);
    }
    @Test
    void testDisableCategory_Exception() throws InvalidInputParametersException, TokenExpiredException {

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setApplicationId("hinges-design");
        categoryRequest.setTenantId("LU008");
        when(categoryService.disableCategory(categoryRequest)).thenThrow(new RuntimeException("Simulating an unexpected exception"));

        ResponseEntity<String> responseEntity = categoryController.disableCategory(categoryRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Simulating an unexpected exception", responseEntity.getBody());
        verify(categoryService, times(1)).disableCategory(categoryRequest);
    }
    @Test
    public void testEnableCategory_Exception() throws TokenExpiredException, InvalidInputParametersException {

        when(categoryService.enableCategory(eq(categoryRequest))).thenThrow(new RuntimeException("Simulating an unexpected exception"));
        ResponseEntity<?> responseEntity = categoryController.enableCategory(categoryRequest);
        assertErrorResponse(responseEntity, HttpStatus.INTERNAL_SERVER_ERROR, validateUnexpectedErrorCode, validateUnexpectedErrorMessage);
        verify(categoryService, times(1)).enableCategory(eq(categoryRequest));
    }
    @Test
    public void testEnableCategory_ExceptionHandling() throws TokenExpiredException, InvalidInputParametersException {

    	categoryRequest.setApplicationId("hinges-design");
    	categoryRequest.setTenantId("LU008");
        when(categoryService.enableCategory(any())).thenThrow(new RuntimeException("Simulating an unexpected exception"));

        ResponseEntity<CategoryResponse> responseEntity = categoryController.enableCategory(categoryRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        CategoryResponse responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(validateUnexpectedErrorCode, responseBody.getErrorCode());
        assertTrue(responseBody.getErrorMessage().contains(validateUnexpectedErrorMessage));
        verify(categoryService, times(1)).enableCategory(any());
    }
    @Test
    public void testSaveCategory_ExceptionHandling() throws TokenExpiredException, InvalidInputParametersException {

    	categoryRequest.setApplicationId("hinges-design");
    	categoryRequest.setTenantId("LU008");
        when(categoryService.saveCategory(any())).thenThrow(new RuntimeException("Simulating an unexpected exception"));

        ResponseEntity<CategoryResponse> responseEntity = categoryController.saveCategory(categoryRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        CategoryResponse responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(validateUnexpectedErrorCode, responseBody.getErrorCode());
        assertTrue(responseBody.getErrorMessage().contains(validateUnexpectedErrorMessage));
     
    }

    private void assertErrorResponse(ResponseEntity<?> responseEntity, HttpStatus expectedStatus, String expectedErrorCode, String expectedErrorMessage) {
        assertNotNull(responseEntity);
        assertEquals(expectedStatus, responseEntity.getStatusCode());

        if (responseEntity.getBody() != null) {
            if (responseEntity.getBody() instanceof CategoryResponse) {
                CategoryResponse categoryResponse = (CategoryResponse) responseEntity.getBody();
                assertNotNull(categoryResponse);
                assertEquals(expectedErrorCode, categoryResponse.getErrorCode());
                assertTrue(categoryResponse.getErrorMessage().contains(expectedErrorMessage));
            } else if (responseEntity.getBody() instanceof String) {
                String errorMessage = (String) responseEntity.getBody();
                assertTrue(errorMessage.contains(expectedErrorMessage));
            } else {
                fail("Unexpected response body type");
            }
        } else {
            fail("Response body is null");
        }
    }
    }
