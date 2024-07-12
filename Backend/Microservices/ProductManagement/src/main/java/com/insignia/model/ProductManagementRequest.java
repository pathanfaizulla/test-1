package com.insignia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductManagementRequest {

	private Long customerSequenceNumber;
	private Integer expirationDuration;

	private ProductRequest productRequest;
	private ProductFilterCriteria filterCriteria;
	private Pagination pagination;
	private SortCriteria sortCriteria;

}
