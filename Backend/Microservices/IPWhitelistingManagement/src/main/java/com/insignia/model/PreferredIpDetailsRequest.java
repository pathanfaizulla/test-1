package com.insignia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferredIpDetailsRequest {

	private Long customerSequenceNumber;
	private Integer expirationDuration;
	private Integer sequenceNumber;
	private String ipDetails;
	private String ipType;
	
}
