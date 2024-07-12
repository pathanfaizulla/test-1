package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.insignia.customExceptions.InvalidInputParametersException;

import static com.insignia.constants.FileUploadDownloadConstant.validateFunctionalObjectCode;
import static com.insignia.constants.FileUploadDownloadConstant.validateFunctionalObjectName;

public enum FunctionalObject {

    PRODUCT("PRODUCT"),
    CATEGORY("CATEGORY"),
    SUB_CATEGORY("SUB_CATEGORY"),
    PRODUCT_BRAND("PRODUCT_BRAND"),
    PRODUCT_FAMILY("PRODUCT_FAMILY"),
    PRODUCT_CATALOGUE("PRODUCT_CATALOGUE");

    private final String fieldName;

    FunctionalObject(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFunctionalObject() {
        return this.fieldName;
    }

    @JsonCreator
    public static String fromValue(String text) throws InvalidInputParametersException {
        for (FunctionalObject functionalObject : FunctionalObject.values()) {
            if (String.valueOf(functionalObject.fieldName).equals(text)) {
                return functionalObject.fieldName;
            }
        }
        throw new InvalidInputParametersException(validateFunctionalObjectCode, validateFunctionalObjectName);
    }

}
