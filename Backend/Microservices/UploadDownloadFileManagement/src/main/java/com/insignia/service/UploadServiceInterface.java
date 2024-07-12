package com.insignia.service;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.UploadRequest;
import com.insignia.model.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UploadServiceInterface {

     UploadResponse uploadFile(UploadRequest uploadRequest, List<MultipartFile> files) throws TokenExpiredException, InvalidInputParametersException;

}

