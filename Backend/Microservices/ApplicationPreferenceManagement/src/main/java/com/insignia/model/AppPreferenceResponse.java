package com.insignia.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AppPreferenceResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String preferenceType;

	private String preferenceValue;

}
