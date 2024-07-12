package com.insignia.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAndStoreInformationResponse {

	private Long customerSequenceNumber;

	private String fullName;

	private String storeName;

	private String storeContact;

	private String storeAddress;

	private Date registeredOn;

	private String businessType;

}
