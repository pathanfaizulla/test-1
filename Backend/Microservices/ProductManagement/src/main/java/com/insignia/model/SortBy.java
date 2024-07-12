package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.insignia.customExceptions.InvalidInputParametersException;

import static com.insignia.constants.ProductValidatorConstants.validateSortBy;
import static com.insignia.constants.ProductValidatorConstants.validateSortByCode;

public enum SortBy {

    PRODUCT_PRICE("product_per_unit_current_price"),
    NEW_ARRIVAL("new_arrival"),
    AVAILABILITY("product_quantity");

    private final String fieldName;

    SortBy(String fieldName) {
        this.fieldName = fieldName;

    }

    public String getSortBy() {
        return this.fieldName;
    }
    @JsonCreator
    public static SortBy fromValue(String text) throws InvalidInputParametersException {
        for (SortBy sortBy : SortBy.values()) {
            if (String.valueOf(sortBy).equals(text)) {
                return sortBy;
            }
        }
        throw new InvalidInputParametersException(validateSortByCode,validateSortBy);
    }
}
