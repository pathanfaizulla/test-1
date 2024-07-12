package com.insignia.entity;

public enum EmploymentType {

	SALARIED(1), PENSIONER(2);
	
	private final int value;

	private EmploymentType (int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
