package com.insignia.serviceimpl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.insignia.config.AwsS3Bean;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.daoInterface.TokenDaoInterface;
import com.insignia.model.UploadRequest;
import com.insignia.model.UploadResponse;
import com.insignia.service.UploadServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.insignia.constants.FileUploadDownloadConstant.validateUploadedFile;
import static com.insignia.constants.FileUploadDownloadConstant.validateUploadedFileCode;

@Service
@Slf4j
public class UploadServiceImpl implements UploadServiceInterface {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private AwsS3Bean awsS3Bean;

    @Autowired
    private TokenDaoInterface tokenDaoInterface;

    String fileSeparator = "/";

    @Transactional
    @Override
    public UploadResponse uploadFile(UploadRequest uploadRequest, List<MultipartFile> files) throws TokenExpiredException, InvalidInputParametersException {

        StringBuilder baseFilePath = new StringBuilder();

        tokenDaoInterface.checkTokenValidity(uploadRequest.getCustomerSequenceNumber(),
                uploadRequest.getExpirationDuration());

        baseFilePath.append(uploadRequest.getApplicationName()).append(fileSeparator)
                .append(uploadRequest.getTenantName()).append(fileSeparator)
                .append(uploadRequest.getFunctionalObject()).append(fileSeparator)
                .append(uploadRequest.getFunctionalObjectId()).append(fileSeparator)
                .append(uploadRequest.getFileType()).append(fileSeparator);
        String basePath = baseFilePath.toString().replaceAll(" ","_");

        for (MultipartFile file : files) {
            String filePath = basePath + file.getOriginalFilename();
            uploadFile(filePath, convertMultiPartFileToFile(file));

        }

        return UploadResponse.builder()
                .baseFilePath(((AmazonS3Client) amazonS3).getResourceUrl(awsS3Bean.getBucketName(), basePath))
                .build();
    }

    private void uploadFile(String fileName, File file) {

        final PutObjectRequest putObjectRequest = new PutObjectRequest(awsS3Bean.getBucketName(), fileName, file);
        amazonS3.putObject(putObjectRequest);
    }

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) throws InvalidInputParametersException {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException e) {
            log.error("UPLOAD-FILES-convertMultiPartFileToFile: Exception occurred .Error ={}", e.getMessage());
            throw new InvalidInputParametersException(
                    validateUploadedFileCode, validateUploadedFile);
        }
        return file;
    }
}


