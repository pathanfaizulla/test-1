package com.insignia.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartProductDetails {

	private Long customerCartSequenceNumber;
	private String productQuantity;
   
	private ProductDetailsResponse productDetailsResponse;

}
