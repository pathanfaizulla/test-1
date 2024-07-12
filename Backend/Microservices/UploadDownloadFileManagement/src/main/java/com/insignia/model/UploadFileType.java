package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.insignia.customExceptions.InvalidInputParametersException;

import static com.insignia.constants.FileUploadDownloadConstant.validateFileType;
import static com.insignia.constants.FileUploadDownloadConstant.validateFileTypeCode;

public enum UploadFileType {
    IMAGES,
    DOCUMENTS,
	VIDEOS;

    @JsonCreator
    public static UploadFileType fromValue(String text) throws InvalidInputParametersException {
        for (UploadFileType uploadFileType : UploadFileType.values()) {
            if (String.valueOf(uploadFileType).equals(text)) {
                return uploadFileType;
            }
        }
        throw new InvalidInputParametersException(validateFileTypeCode,validateFileType);
    }

}
