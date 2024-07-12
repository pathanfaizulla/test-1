package com.insignia.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterCriteria {

	private Boolean isToGetAllProduct;

	private String applicationId;
	private String tenantId;
	
	private Boolean availability = false;
	private Float minPrice;
	private Float maxPrice;
	private String searchString;

	private Boolean newArrival = false;
	private Boolean featuredProduct = false;
	private Boolean topSellingProduct = false;
	private Boolean bestSeller = false;

	private Boolean someOfAKind = false;
	private Boolean saleItems = false;
	private Boolean closeOut = false;
	private Boolean quickShip = false;
	private Boolean oneOfKind = false;

	private List<Long> subCategoryList;
	private List<String> materialList;
	private List<String> colourList;
	private List<Integer> productFamilyList;
	private List<String> productBrandList;
	private List<String> productCatalogueList;

}
