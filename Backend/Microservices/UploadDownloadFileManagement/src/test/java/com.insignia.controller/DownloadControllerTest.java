package com.insignia.controller;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.DownloadRequest;
import com.insignia.model.UploadRequest;
import com.insignia.model.UploadResponse;
import com.insignia.serviceimpl.DownloadServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DownloadControllerTest {

    @InjectMocks
    private DownloadController downloadController;
    @Mock
    private DownloadServiceImpl downloadService;

    @Test
    public void whenFileUploaded_thenVerifyStatus()
            throws Exception {

        doNothing().when(downloadService).zipDownload(any(), any());
        downloadController.zipDownload(DownloadRequest.builder().basePath("test/").build(), null);
        verify(downloadService, times(1)).zipDownload(DownloadRequest.builder().basePath("test/").build(), null);

    }

    @Test
    public void whenFileUploaded_thenExceptionShouldGet()
            throws InvalidInputParametersException, TokenExpiredException, IOException {

        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        doThrow(new InvalidInputParametersException("Provide correct input")).when(downloadService).zipDownload(any(), any());
        downloadController.zipDownload(
                DownloadRequest.builder().basePath("test/").build(),mockHttpServletResponse);

        assertEquals(HttpStatus.BAD_REQUEST.value(),mockHttpServletResponse.getStatus());
    }

    @Test
    public void shouldGetTokenExpiredException()
            throws InvalidInputParametersException, TokenExpiredException, IOException {

        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        doThrow(new TokenExpiredException("Token is expired")).when(downloadService).zipDownload(any(), any());
        downloadController.zipDownload(
                DownloadRequest.builder().basePath("test/").build(),mockHttpServletResponse);

        assertEquals(HttpStatus.BAD_REQUEST.value(),mockHttpServletResponse.getStatus());

    }


    @Test
    public void shouldGetInternalException()
            throws InvalidInputParametersException, TokenExpiredException, IOException {
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        doThrow(new RuntimeException("Something went wrong")).when(downloadService).zipDownload(any(), any());
        downloadController.zipDownload(
               DownloadRequest.builder().basePath("test/").build(),mockHttpServletResponse);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),mockHttpServletResponse.getStatus());
    }

}