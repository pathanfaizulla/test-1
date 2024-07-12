package com.insignia.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CartResponce implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorMessage;

	private Long customerSequenceNumber;
	private Long cartSequenceNumber;

	public CartResponce(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
