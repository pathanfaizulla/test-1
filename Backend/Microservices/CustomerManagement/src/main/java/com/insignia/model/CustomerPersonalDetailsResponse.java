package com.insignia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPersonalDetailsResponse {
	
	private Integer sequenceNumber;
	private String firstName;
	private String lastName;
	private String middleName;
	private String age;
	private String gender;
	private String customerEmailId;
	private String alternativeEmailId;
	private String customerMobileNumber;
	private String alternativeMobileNumber;
	private String customerLandlineNumber;

}
