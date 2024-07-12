package com.insignia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {

	private Integer cartId;
	private Long customerSequenceNumber;
	private Long cartSequenceNumber;
	private Integer expirationDuration;


}
