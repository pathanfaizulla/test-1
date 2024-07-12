package com.insignia.model;

import java.io.Serializable;
import java.util.List;

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
public class CategoryResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long categoryId;
	private String categoryName;
	private String categoryDescription;
	private String categoryImagePath;
	private String categoryDefaultImage;
	private Long customerSequenceNumber;
	private Integer expirationDuration;
	private List<SubCategoryResponse> subCategoryResponseList;

	private String errorCode;
	private String errorMessage;

}
