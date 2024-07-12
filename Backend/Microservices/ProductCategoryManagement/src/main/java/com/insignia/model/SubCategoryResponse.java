package com.insignia.model;

import java.io.Serializable;

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
public class SubCategoryResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long categoryId;
	private Long subcategoryId;
	private String subcategoryName;
	private String subCategoryDescription;
	private String subCategoryImagePath;
	private String subCategoryDefaultImage;
	private Long customerSequenceNumber;
	private Integer expirationDuration;

	private String errorCode;
	private String errorMessage;
}
