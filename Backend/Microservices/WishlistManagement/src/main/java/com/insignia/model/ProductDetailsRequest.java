package com.insignia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsRequest {
	
	private Long productSequenceNumber;
	private Integer quantity;
	
}
