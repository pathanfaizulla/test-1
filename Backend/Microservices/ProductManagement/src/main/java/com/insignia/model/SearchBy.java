package com.insignia.model;

public enum SearchBy {

    PRODUCT_NAME("product_name"),
    PRODUCT_DESC("description"),
    PRODUCT_ID("product_id");

    private final String fieldName;

    SearchBy(String fieldName) {
        this.fieldName = fieldName;

    }

    public String getSearchBy() {
        return fieldName;
    }
    }
