package com.insignia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.CategoryResponse;
import com.insignia.model.SubCategoryRequest;
import com.insignia.model.SubCategoryResponse;
import com.insignia.service.SubCategoryServiceInterface;

@ExtendWith(MockitoExtension.class)
class SubCategoryControllerTest {

    @Mock
    private SubCategoryServiceInterface subCategoryService;

    @InjectMocks
    private SubCategoryController subCategoryController;

    private List<SubCategoryResponse> subCategoryResponseList = new ArrayList<>();
    private SubCategoryRequest subCategoryRequest = new SubCategoryRequest();
    private SubCategoryResponse subCategoryResponse = new SubCategoryResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public void dataInitialization() {
        subCategoryRequest.setSubCategoryName("TestSubCategory");
        subCategoryRequest.setSubCategoryDescription("Description");
        subCategoryRequest.setSubCategoryImagePath("ImagePath");

        subCategoryResponse.setErrorCode("200");
        subCategoryResponse.setErrorMessage("Success");

        subCategoryResponseList.add(subCategoryResponse);
    }

    @Test
    public void testGetAllSubCategories_Success() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.getSubCategories(Mockito.any(SubCategoryRequest.class)))
                .thenReturn(Optional.of(subCategoryResponseList));

        ResponseEntity<List<SubCategoryResponse>> response =
                subCategoryController.getAllSubCategories(subCategoryRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subCategoryResponseList, response.getBody());
    }

    @Test
    public void testGetAllSubCategories_InvalidInputParametersException() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.getSubCategories(Mockito.any(SubCategoryRequest.class)))
                .thenThrow(new InvalidInputParametersException("Invalid input parameters"));

        ResponseEntity<List<SubCategoryResponse>> response =
                subCategoryController.getAllSubCategories(subCategoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetAllSubCategories_TokenExpiredException() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.getSubCategories(Mockito.any(SubCategoryRequest.class)))
                .thenThrow(new TokenExpiredException("Token expired"));

        ResponseEntity<List<SubCategoryResponse>> response =
                subCategoryController.getAllSubCategories(subCategoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetAllSubCategories_InternalServerError() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.getSubCategories(Mockito.any(SubCategoryRequest.class)))
                .thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<List<SubCategoryResponse>> response =
                subCategoryController.getAllSubCategories(subCategoryRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testSaveSubCategory_Success() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.saveSubCategory(Mockito.any(SubCategoryRequest.class)))
                .thenReturn(subCategoryResponse);

        ResponseEntity<SubCategoryResponse> response =
                subCategoryController.saveSubCategory(subCategoryRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subCategoryResponse, response.getBody());
    }

    @Test
    public void testSaveSubCategory_InvalidInputParametersException() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.saveSubCategory(Mockito.any(SubCategoryRequest.class)))
                .thenThrow(new InvalidInputParametersException("Invalid input parameters"));

        ResponseEntity<SubCategoryResponse> response =
                subCategoryController.saveSubCategory(subCategoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testSaveSubCategory_TokenExpiredException() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.saveSubCategory(Mockito.any(SubCategoryRequest.class)))
                .thenThrow(new TokenExpiredException("Token expired"));

        ResponseEntity<SubCategoryResponse> response =
                subCategoryController.saveSubCategory(subCategoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testSaveSubCategory_InternalServerError() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.saveSubCategory(Mockito.any(SubCategoryRequest.class)))
                .thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<SubCategoryResponse> response =
                subCategoryController.saveSubCategory(subCategoryRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDisableSubCategory_Success() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.disableSubCategory(Mockito.any(SubCategoryRequest.class)))
                .thenReturn("Category disabled successfully");

        ResponseEntity<String> response =
                subCategoryController.disableSubCategory(subCategoryRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category disabled successfully", response.getBody());
    }

    @Test
    public void testDisableSubCategory_InvalidInputParametersException() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.disableSubCategory(Mockito.any(SubCategoryRequest.class)))
                .thenThrow(new InvalidInputParametersException("Invalid input parameters"));

        ResponseEntity<String> response =
                subCategoryController.disableSubCategory(subCategoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDisableSubCategory_TokenExpiredException() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.disableSubCategory(Mockito.any(SubCategoryRequest.class)))
                .thenThrow(new TokenExpiredException("Token expired"));

        ResponseEntity<String> response =
                subCategoryController.disableSubCategory(subCategoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDisableSubCategory_InternalServerError() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.disableSubCategory(Mockito.any(SubCategoryRequest.class)))
                .thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<String> response =
                subCategoryController.disableSubCategory(subCategoryRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }
              @Test
            public void testEnableSubCategorySuccess() throws TokenExpiredException, InvalidInputParametersException {
                dataInitialization();

                when(subCategoryService.enableSubCategory(subCategoryRequest)).thenReturn(subCategoryResponse);

                ResponseEntity<SubCategoryResponse> response = subCategoryController.enableSubCategory(subCategoryRequest);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(subCategoryResponse, response.getBody());
            }

    @Test
    public void testEnableSubCategory_InvalidInputParametersException() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.enableSubCategory(Mockito.any(SubCategoryRequest.class)))
                .thenThrow(new InvalidInputParametersException("Invalid input parameters"));

        ResponseEntity<SubCategoryResponse> response = subCategoryController.enableSubCategory(subCategoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testEnableSubCategory_TokenExpiredException() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.enableSubCategory(Mockito.any(SubCategoryRequest.class)))
                .thenThrow(new TokenExpiredException("Token expired"));

        ResponseEntity<SubCategoryResponse> response = subCategoryController.enableSubCategory(subCategoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testEnableSubCategory_InternalServerError() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.enableSubCategory(Mockito.any(SubCategoryRequest.class)))
                .thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<SubCategoryResponse> response = subCategoryController.enableSubCategory(subCategoryRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateSubCategory_Success() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.updateSubCategory(Mockito.any(SubCategoryRequest.class)))
                .thenReturn(subCategoryResponse);

        ResponseEntity<CategoryResponse> response = subCategoryController.updateSubCategory(subCategoryRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subCategoryResponse, response.getBody());
    }

    @Test
    public void testUpdateSubCategory_InvalidInputParametersException() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.updateSubCategory(Mockito.any(SubCategoryRequest.class)))
                .thenThrow(new InvalidInputParametersException("Invalid input parameters"));

        ResponseEntity<CategoryResponse> response =
                subCategoryController.updateSubCategory(subCategoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateSubCategory_TokenExpiredException() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.updateSubCategory(Mockito.any(SubCategoryRequest.class)))
                .thenThrow(new TokenExpiredException("Token expired"));

        ResponseEntity<CategoryResponse> response =
                subCategoryController.updateSubCategory(subCategoryRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateSubCategory_InternalServerError() throws TokenExpiredException, InvalidInputParametersException {
        dataInitialization();

        when(subCategoryService.updateSubCategory(Mockito.any(SubCategoryRequest.class)))
                .thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<CategoryResponse> response =
                subCategoryController.updateSubCategory(subCategoryRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}

