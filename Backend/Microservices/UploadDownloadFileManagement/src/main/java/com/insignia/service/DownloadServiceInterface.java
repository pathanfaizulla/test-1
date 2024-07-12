package com.insignia.service;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.customExceptions.TokenExpiredException;
import com.insignia.model.DownloadRequest;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface DownloadServiceInterface {

     void zipDownload(DownloadRequest downloadRequest, HttpServletResponse response) throws IOException, InvalidInputParametersException, TokenExpiredException;
}

