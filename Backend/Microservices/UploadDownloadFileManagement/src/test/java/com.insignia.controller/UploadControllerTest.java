package com.insignia.controller;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.UploadRequest;
import com.insignia.model.UploadResponse;
import com.insignia.serviceimpl.UploadServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UploadControllerTest {

    @InjectMocks
    private UploadController uploadController;
    @Mock
    private UploadServiceImpl uploadServiceInterface;

    @Test
    public void whenFileUploaded_thenVerifyStatus()
            throws Exception {

        when(uploadServiceInterface.uploadFile(any(), any())).thenReturn(UploadResponse.builder().build());
        ResponseEntity<UploadResponse> uploadResponseResponseEntity = uploadController.uploadFiles(null, UploadRequest.builder().applicationName("A")
                .functionalObject("PRODUCT")
                .functionalObjectId("C")
                .tenantName("D")
                .fileType("IMAGES").build());

        assertEquals(HttpStatus.OK,uploadResponseResponseEntity.getStatusCode());

    }

    @Test
    public void whenFileUploaded_thenExceptionShouldGet()
            throws InvalidInputParametersException, TokenExpiredException {

        when(uploadServiceInterface.uploadFile(any(), any())).thenThrow(new InvalidInputParametersException("", ""));
        ResponseEntity<UploadResponse> uploadResponseResponseEntity = uploadController.uploadFiles(null,
                UploadRequest.builder().applicationName("A")
                        .functionalObject("PRODUCT")
                        .functionalObjectId("C")
                        .tenantName("D")
                        .fileType("IMAGES").build());
        assertEquals(HttpStatus.BAD_REQUEST,uploadResponseResponseEntity.getStatusCode());

    }

    @Test
    public void shouldGetTokenExpiredException()
            throws InvalidInputParametersException, TokenExpiredException {

        when(uploadServiceInterface.uploadFile(any(), any())).thenThrow(new TokenExpiredException("", ""));
        ResponseEntity<UploadResponse> uploadResponseResponseEntity = uploadController.uploadFiles(null,
                UploadRequest.builder().applicationName("A")
                        .functionalObject("PRODUCT")
                        .functionalObjectId("C")
                        .tenantName("D")
                        .fileType("IMAGES").build());

        assertEquals(HttpStatus.BAD_REQUEST,uploadResponseResponseEntity.getStatusCode());

    }


    @Test
    public void shouldGetInternalException()
            throws InvalidInputParametersException, TokenExpiredException {

        when(uploadServiceInterface.uploadFile(any(), any())).thenThrow(new RuntimeException("Something went wrong"));
        ResponseEntity<UploadResponse> uploadResponseResponseEntity = uploadController.uploadFiles(null,
                UploadRequest.builder().applicationName("A")
                        .functionalObject("PRODUCT")
                        .functionalObjectId("C")
                        .tenantName("D")
                        .fileType("IMAGES").build());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,uploadResponseResponseEntity.getStatusCode());

    }
}
