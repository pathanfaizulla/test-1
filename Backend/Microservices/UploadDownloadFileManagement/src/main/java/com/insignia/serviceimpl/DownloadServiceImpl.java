package com.insignia.serviceimpl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.insignia.config.AwsS3Bean;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.model.DownloadRequest;
import com.insignia.service.DownloadServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.insignia.constants.FileUploadDownloadConstant.validateFilePath;
import static com.insignia.constants.FileUploadDownloadConstant.validateFilePathCode;

@Service
@Slf4j
public class DownloadServiceImpl implements DownloadServiceInterface {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private AwsS3Bean awsS3Bean;

    @Autowired
    private TokenDaoInterface tokenDaoInterface;

    @Transactional
    @Override
    public void zipDownload(DownloadRequest downloadRequest, HttpServletResponse response) throws IOException, InvalidInputParametersException, TokenExpiredException {

       tokenDaoInterface.checkTokenValidity(downloadRequest.getCustomerSequenceNumber(),
               downloadRequest.getExpirationDuration());

        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
        final ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(awsS3Bean.getBucketName())
                .withPrefix(StringUtils.substringAfter(downloadRequest.getBasePath(),".amazonaws.com/"));
        ListObjectsV2Result result;

        result = amazonS3.listObjectsV2(req);

        if (result.getObjectSummaries().size() == 0)
            throw new InvalidInputParametersException(validateFilePathCode,
                    validateFilePath);

        for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {

            log.info("Downloading an object with key= " + objectSummary.getKey());
            final S3Object s3Object = amazonS3.getObject(awsS3Bean.getBucketName(), objectSummary.getKey());
            final S3ObjectInputStream stream = s3Object.getObjectContent();

            log.info("File downloaded successfully." + s3Object.getKey());
            String[] split = s3Object.getKey().split("/");
            ZipEntry zipEntry = new ZipEntry(split[split.length - 1]);
            zipEntry.setSize(objectSummary.getSize());
            zipOut.putNextEntry(zipEntry);
            StreamUtils.copy(stream, zipOut);
            s3Object.close();
            zipOut.closeEntry();
        }
        zipOut.finish();
        zipOut.close();
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "DownloadFile" + "\"");

    }

}


