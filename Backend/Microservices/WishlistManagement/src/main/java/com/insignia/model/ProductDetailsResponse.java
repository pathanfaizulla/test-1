package com.insignia.model;

import java.math.BigDecimal;

public class ProductDetailsResponse {

	private Long productSequenceNumber;
	private Integer productAvailableQuantity;

	private Integer customListQuantity;

	private String productId;
	private String productName;
	private String description;
	private String measuringQuantity;
	private String measuringUnit;
	private String productImagePath;
	private BigDecimal productPerUnitActualPrice;
	private BigDecimal productPerUnitCurrentPrice;
	private String defaultImage;

	public Long getProductSequenceNumber() {
		return productSequenceNumber;
	}

	public void setProductSequenceNumber(Long productSequenceNumber) {
		this.productSequenceNumber = productSequenceNumber;
	}

	public Integer getProductAvailableQuantity() {
		return productAvailableQuantity;
	}

	public void setProductAvailableQuantity(Integer quantity) {
		this.productAvailableQuantity = quantity;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMeasuringQuantity() {
		return measuringQuantity;
	}

	public void setMeasuringQuantity(String measuringQuantity) {
		this.measuringQuantity = measuringQuantity;
	}

	public String getMeasuringUnit() {
		return measuringUnit;
	}

	public void setMeasuringUnit(String measuringUnit) {
		this.measuringUnit = measuringUnit;
	}

	public String getProductImagePath() {
		return productImagePath;
	}

	public void setProductImagePath(String productImagePath) {
		this.productImagePath = productImagePath;
	}

	public BigDecimal getProductPerUnitActualPrice() {
		return productPerUnitActualPrice;
	}

	public void setProductPerUnitActualPrice(BigDecimal productPerUnitActualPrice) {
		this.productPerUnitActualPrice = productPerUnitActualPrice;
	}

	public BigDecimal getProductPerUnitCurrentPrice() {
		return productPerUnitCurrentPrice;
	}

	public void setProductPerUnitCurrentPrice(BigDecimal productPerUnitCurrentPrice) {
		this.productPerUnitCurrentPrice = productPerUnitCurrentPrice;
	}

	public String getDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}

	public Integer getCustomListQuantity() {
		return customListQuantity;
	}

	public void setCustomListQuantity(Integer customListQuantity) {
		this.customListQuantity = customListQuantity;
	}

	@Override
	public String toString() {
		return "ProductDetailsResponse [productSequenceNumber=" + productSequenceNumber + ", productAvailableQuantity="
				+ productAvailableQuantity + ", customListQuantity=" + customListQuantity + ", productId=" + productId
				+ ", productName=" + productName + ", description=" + description + ", measuringQuantity="
				+ measuringQuantity + ", measuringUnit=" + measuringUnit + ", productImagePath=" + productImagePath
				+ ", productPerUnitActualPrice=" + productPerUnitActualPrice + ", productPerUnitCurrentPrice="
				+ productPerUnitCurrentPrice + ", defaultImage=" + defaultImage + "]";
	}

}
