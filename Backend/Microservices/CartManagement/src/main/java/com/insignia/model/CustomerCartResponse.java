package com.insignia.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CustomerCartResponse implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorMessage;
	private Long cartSequenceNumber;
	private List<Long> productSequenceNumber;

	private List<CartProductDetails> cartProductDetailsList;

	public CustomerCartResponse(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
