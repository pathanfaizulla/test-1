package com.insignia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppPreferenceRequest {
	
	private String preferenceType;
	
	private String preferenceValue;
	
}
