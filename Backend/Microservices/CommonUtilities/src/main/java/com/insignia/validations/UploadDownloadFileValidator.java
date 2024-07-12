package com.insignia.validations;

import com.insignia.constants.FileUploadDownloadConstant;
import com.insignia.customExceptions.InvalidInputParametersException;

public class UploadDownloadFileValidator {

    public static void validateApplicationName(String value) throws InvalidInputParametersException {
        if (value == null || value == "" || value.trim().isBlank()) {
            throw new InvalidInputParametersException(FileUploadDownloadConstant.validateApplicationNameCode,
                    FileUploadDownloadConstant.validateApplicationName);
        }
    }

    public static void validateTenantName(String value) throws InvalidInputParametersException {
        if (value == null || value == "" || value.trim().isBlank()) {
            throw new InvalidInputParametersException(FileUploadDownloadConstant.validateTenantNameCode,
                    FileUploadDownloadConstant.validateTenantName);
        }
    }

    public static void validateFunctionalObjectId(String value) throws InvalidInputParametersException {
        if (value == null || value == "" || value.trim().isBlank()) {
            throw new InvalidInputParametersException(FileUploadDownloadConstant.validateFunctionalObjectIdCode,
                    FileUploadDownloadConstant.validateFunctionalObjectId);
        }
    }

    public static void validateBasePath(String value) throws InvalidInputParametersException {
        if (value == null || value == "" || value.trim().isBlank()) {
            throw new InvalidInputParametersException(FileUploadDownloadConstant.validateBasePathCode,
                    FileUploadDownloadConstant.validateBasePath);
        }
    }
}
