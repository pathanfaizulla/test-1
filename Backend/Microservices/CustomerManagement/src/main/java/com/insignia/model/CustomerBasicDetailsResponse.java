package com.insignia.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBasicDetailsResponse {
	
	private Long customerSequenceNumber;
	
	private String fullName;
	
	private String applicationId;
	
	private String tenantId;
	
	private String emailId;
	
	private String userName;
	
	private Date registeredOn;
	
	private String customerId;
}
