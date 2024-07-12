package com.insignia.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_store_details")
public class CustomerStoreDetails {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "store_sequence_number")
	private Integer storeSequenceNumber;

	@Column(name = "customer_sequence_number")
	private Long customerSequenceNumber;
	
	@Column(name = "store_manager")
	private String storeManager;
	

	@Column(name = "markup_factor")
	private String markupFactor;

	@Column(name = "hear_about_us")
	private String hearAboutUs;

	@Column(name = "markets")
	private String markets;

	@Column(name = "store_name")
	private String storeName;

	@Column(name = "store_contact")
	private String storeContact;

	@Column(name = "store_address")
	private String storeAddress;

	@Column(name = "store_country")
	private String storeCountry;

	@Column(name = "store_state")
	private String storeState;

	@Column(name = "store_city")
	private String storeCity;
	
	@Column(name = "store_zipCode")
	private String storeZipCode;
	
	@Column(name = "telephone")
	private String telephone;
	
	@Column(name = "website")
	private String website;
	
	@Column(name = "resale_license")
	private String resaleLicense;
	
	@Column(name = "business_type")
	private String businessType;
	
}
