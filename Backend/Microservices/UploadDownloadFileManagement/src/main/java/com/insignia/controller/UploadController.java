package com.insignia.controller;


import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.FunctionalObject;
import com.insignia.model.UploadFileType;
import com.insignia.model.UploadRequest;
import com.insignia.model.UploadResponse;
import com.insignia.service.UploadServiceInterface;
import com.insignia.validations.UploadDownloadFileValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadController {

    @Autowired
    private UploadServiceInterface uploadServiceImpl;

    @PostMapping(value = "/files", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UploadResponse> uploadFiles(@RequestPart("file") List<MultipartFile> files, @RequestPart("uploadRequest") UploadRequest uploadRequest) {
        try {
            validateUploadRequest(uploadRequest);
            return ResponseEntity.status(HttpStatus.OK).body(uploadServiceImpl.uploadFile(uploadRequest, files));
        } catch (InvalidInputParametersException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    UploadResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getStrMsg()).build());

        } catch (TokenExpiredException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    UploadResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getStrMsg()).build());

        } catch (Exception e) {
            log.error("UPLOAD-FILES-UploadDownloadFileManagement: Exception occurred .Error ={}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(UploadResponse.builder().errorCode(CommonConstant.validateUnexpectedErrorCode)
                            .errorMessage(CommonConstant.validateUnexpectedErrorMessage).build());
        }
    }

    private void validateUploadRequest(UploadRequest uploadRequest) throws InvalidInputParametersException {
        FunctionalObject.fromValue(uploadRequest.getFunctionalObject());
        UploadFileType.fromValue(uploadRequest.getFileType());
        UploadDownloadFileValidator.validateApplicationName(uploadRequest.getApplicationName());
        UploadDownloadFileValidator.validateTenantName(uploadRequest.getTenantName());
        UploadDownloadFileValidator.validateFunctionalObjectId(uploadRequest.getFunctionalObjectId());
    }
}