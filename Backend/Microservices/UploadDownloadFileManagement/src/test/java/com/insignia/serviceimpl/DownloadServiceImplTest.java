package com.insignia.serviceimpl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.StringInputStream;
import com.insignia.config.AwsS3Bean;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoImpl.TokenDaoImpl;
import com.insignia.model.DownloadRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DownloadServiceImplTest {

    @InjectMocks
    DownloadServiceImpl downloadService;

    @Mock
    AmazonS3 amazonS3;

    @Mock
    AwsS3Bean awsS3Bean;
    @Mock
    S3Object ssObject;

    @Mock
    TokenDaoImpl tokenDao;
    @Mock
    ListObjectsV2Result listObjectsV2Result;

    private List<S3ObjectSummary> listS3ObjectSummary;
    private S3ObjectSummary s3ObjectSummary;


    @Test
    void zipDownload() throws TokenExpiredException, InvalidInputParametersException, IOException {

        s3ObjectSummary = new S3ObjectSummary();
        listS3ObjectSummary = new ArrayList<>();

        s3ObjectSummary.setBucketName("bucket");
        s3ObjectSummary.setKey("keyPath");
        listS3ObjectSummary.add(s3ObjectSummary);

        doNothing().when(tokenDao).checkTokenValidity(any(), any());
        when(awsS3Bean.getBucketName()).thenReturn("");
        when(listObjectsV2Result.getObjectSummaries()).thenReturn(listS3ObjectSummary);
        when(amazonS3.listObjectsV2((ListObjectsV2Request) any())).thenReturn(listObjectsV2Result);

        InputStream testInputStream = new StringInputStream("property");
        S3Object s3Object = new S3Object();
        s3Object.setObjectContent(testInputStream);
        s3Object.setKey("test/ab/test.png");
        Mockito.when(amazonS3.getObject(anyString(), anyString())).thenReturn(s3Object);

        downloadService.zipDownload(DownloadRequest.builder().build(), new MockHttpServletResponse());
        verify(amazonS3, times(1)).listObjectsV2((ListObjectsV2Request) any());
    }

    @Test
    void shouldGetException() throws TokenExpiredException {

        doNothing().when(tokenDao).checkTokenValidity(any(), any());
        when(awsS3Bean.getBucketName()).thenReturn("");
        when(amazonS3.listObjectsV2((ListObjectsV2Request) any())).thenReturn(new ListObjectsV2Result());

        assertThrows(InvalidInputParametersException.class, () -> downloadService.zipDownload(DownloadRequest.builder().build(), new MockHttpServletResponse()));
    }
}