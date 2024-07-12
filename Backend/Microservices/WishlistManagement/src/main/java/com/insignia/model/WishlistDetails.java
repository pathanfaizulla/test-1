package com.insignia.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WishlistDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long wishlistId;
	private Date timestamp;
	private ProductDetailsResponse productDetailsResponse;

	public Long getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(Long wishlistId) {
		this.wishlistId = wishlistId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public ProductDetailsResponse getProductDetailsResponse() {
		return productDetailsResponse;
	}

	public void setProductDetailsResponse(ProductDetailsResponse productDetailsResponse) {
		this.productDetailsResponse = productDetailsResponse;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "WishlistDetails [wishlistId=" + wishlistId + ", timestamp=" + timestamp + ", productDetailsResponse="
				+ productDetailsResponse + "]";
	}

}
