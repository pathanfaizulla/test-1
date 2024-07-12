package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.insignia.customExceptions.InvalidInputParametersException;

import static com.insignia.constants.ProductValidatorConstants.validateSortOrder;
import static com.insignia.constants.ProductValidatorConstants.validateSortOrderCode;

public enum SortOrder {
    ASC,
    DESC;

    @JsonCreator
    public static SortOrder fromValue(String text) throws InvalidInputParametersException {
        for (SortOrder sortOrder : SortOrder.values()) {
            if (String.valueOf(sortOrder).equals(text)) {
                return sortOrder;
            }
        }
        throw new InvalidInputParametersException(validateSortOrderCode, validateSortOrder);
    }
}
