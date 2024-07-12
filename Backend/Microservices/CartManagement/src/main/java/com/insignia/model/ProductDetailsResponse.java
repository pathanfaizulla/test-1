package com.insignia.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsResponse {

	private Long productSequenceNumber;

	private String productId;
	private String productName;
	private String description;
	private String measuringQuantity;
	private String measuringUnit;
	private Long subcategoryId;
	private String productImagePath;
	private BigDecimal productPerUnitActualPrice;
	private BigDecimal productPerUnitCurrentPrice;
	private BigDecimal productLength;
	private BigDecimal width;
	private BigDecimal height;
	private String dimensionUnit;
	private String materials;
	private String colours;
	private String defaultImage;
}
