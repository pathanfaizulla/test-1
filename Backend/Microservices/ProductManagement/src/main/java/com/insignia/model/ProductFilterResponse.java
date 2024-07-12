package com.insignia.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProductFilterResponse {

    private String errorCode;
    private String errorMessage;

    private Long productSequenceNumber;
    private String productId;
    private String productName;
    private String description;
    private String productImagePath;
    private String defaultImage;
    private BigDecimal productPerUnitCurrentPrice;
}