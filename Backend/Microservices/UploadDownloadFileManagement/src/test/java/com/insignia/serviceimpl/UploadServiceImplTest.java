package com.insignia.serviceimpl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.IOUtils;
import com.insignia.config.AwsS3Bean;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoImpl.TokenDaoImpl;
import com.insignia.model.UploadRequest;
import com.insignia.model.UploadResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UploadServiceImplTest {

    @InjectMocks
    UploadServiceImpl uploadService;

    @Mock
    AmazonS3Client amazonS3;

    @Mock
    AwsS3Bean awsS3Bean;

    @Mock
    TokenDaoImpl tokenDao;

    @Test
    void uploadFile() throws TokenExpiredException, InvalidInputParametersException, IOException {
        File file = new File("src/main/resources/test.txt");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

        doNothing().when(tokenDao).checkTokenValidity(any(), any());
        when(awsS3Bean.getBucketName()).thenReturn("");
        when(amazonS3.putObject(any())).thenReturn(new PutObjectResult());
        UploadResponse uploadResponse = uploadService.uploadFile(UploadRequest.builder().applicationName("A")
                .functionalObject("B")
                .functionalObjectId("C")
                .tenantName("D")
                .fileType("E").build(), Collections.singletonList(multipartFile));
        assertNotNull(uploadResponse);
    }

    @Test
    void shouldGetException() throws IOException {
        File file = new File("src/main/resources/test.txt");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                "", "application/json", IOUtils.toByteArray(input));

        assertThrows(InvalidInputParametersException.class, () -> uploadService.uploadFile(UploadRequest.builder().applicationName("A")
                .functionalObject("B")
                .functionalObjectId("C")
                .tenantName("D")
                .fileType("E").build(), Collections.singletonList(multipartFile)));
    }
}