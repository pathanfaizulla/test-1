package com.insignia.controller;


import com.insignia.constants.CommonConstant;
import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.DownloadRequest;
import com.insignia.service.DownloadServiceInterface;
import com.insignia.validations.UploadDownloadFileValidator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/download")
@Slf4j
public class DownloadController {

    @Autowired
    private DownloadServiceInterface downloadServiceImpl;

    @PostMapping(value = "/zipFile", produces = "application/zip")
    public void zipDownload(@RequestBody DownloadRequest downloadRequest, HttpServletResponse response){
        try {
            validateUploadRequest(downloadRequest);
            downloadServiceImpl.zipDownload(downloadRequest, response);
        } catch (InvalidInputParametersException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.addHeader(e.getErrorCode(), e.getMessage());

        } catch (TokenExpiredException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.addHeader(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("zipDownload-FILES-UploadDownloadFileManagement: Exception occurred .Error ={}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.addHeader(CommonConstant.validateUnexpectedErrorCode, CommonConstant.validateUnexpectedErrorMessage);

        }
    }

    private void validateUploadRequest(DownloadRequest downloadRequest) throws InvalidInputParametersException {
        UploadDownloadFileValidator.validateBasePath(downloadRequest.getBasePath());

    }
}