package com.insignia.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAndProductLinkResponse {

	private Integer productQuantity;
	private BigDecimal totalPrice;
	private BigDecimal perUnitPrice;

	private ProductDetailsResponse productDetailsResponse;
}
