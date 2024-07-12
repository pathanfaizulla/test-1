package com.insignia.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsResponse {

	private Long productSequenceNumber;
	private String productId;
	private String productName;
	private String description;
	private String measuringQuantity;
	private String measuringUnit;
	private Long subcategoryId;
	private String productImagePath;
	private BigDecimal productPerUnitActualPrice;
	private BigDecimal productPerUnitCurrentPrice;
	private BigDecimal productLength;
	private BigDecimal width;
	private BigDecimal height;
	private String dimensionUnit;
	private String materials;
	private String colours;
	private String hexCode;
	private Integer productQuantity;
	private Integer productFamily;
	private String productBrand;
	private String productCatalogue;
	private Boolean newArrival;
	private Boolean featuredProduct;
	private Boolean topSellingProduct;
	private Boolean bestSeller;
	private String productFinish;
	private BigDecimal prodWholesaleTier1Price;
	private BigDecimal prodWholesaleTier2Price;
	private BigDecimal prodWholesaleTier1SalePrice;
	private BigDecimal prodWholesaleTier2SalePrice;
	private BigDecimal prodRetailTier1Price;
	private BigDecimal prodRetailTier2Price;
	private BigDecimal prodRetailTier1SalePrice;
	private BigDecimal prodRetailTier2SalePrice;
	private String defaultImage;
	private Boolean someOfAKind;
	private Boolean saleItems;
	private Boolean closeOut;
	private Boolean quickShip;
	private String eta;	
	private Boolean oneOfKind;
	private Boolean isAnchorProduct;
	private Boolean isPackageProduct;
	
	private List<ProductFilterResponse> additionalProducts = null;

}
