package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerCartRequest {

	private Long customerSequenceNumber;
	private Long customerCartSequenceNumber;
	private Integer expirationDuration;
	private Long productSequenceNumber;
	private Long cartSequenceNumber;
	private String productQuantity;

}
