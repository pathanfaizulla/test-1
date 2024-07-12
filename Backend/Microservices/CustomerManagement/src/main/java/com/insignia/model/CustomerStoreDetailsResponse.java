package com.insignia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerStoreDetailsResponse {

	private Integer storeSequenceNumber;
	
	private String storeManager;

	private String markupFactor;

	private String hearAboutUs;

	private String markets;

	private String storeName;

	private String storeContact;

	private String storeAddress;

	private String storeCountry;

	private String storeState;
	
	private String storeCity;
	
	private String storeZipCode;
	
	private String telephone;
	
	private String website;
	
	private String resaleLicense;
	
	private String businessType;

}
