package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProductResponse implements Serializable  {

	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String errorMessage;
	private Long totalRecords;

	private List<ProductDetailsResponse> productDetailsResponse;



	public ProductResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
