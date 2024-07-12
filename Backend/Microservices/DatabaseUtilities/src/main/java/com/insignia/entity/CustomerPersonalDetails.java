package com.insignia.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_personal_details")
public class CustomerPersonalDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sequence_number")
	private Integer sequenceNumber;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "middle_name")
	private String middleName;

	private String age;

	private String gender;

	@Column(name = "customer_email_id")
	private String customerEmailId;

	@Column(name = "alternative_email_id")
	private String alternativeEmailId;

	@Column(name = "customer_mobile_number")
	private String customerMobileNumber;

	@Column(name = "alternative_mobile_number")
	private String alternativeMobileNumber;

	@Column(name = "customer_landline_number")
	private String customerLandlineNumber;

	@Column(name = "customer_sequence_number")
	private Long customerSequenceNumber;

	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}

	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCustomerEmailId() {
		return customerEmailId;
	}

	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}

	public String getAlternativeEmailId() {
		return alternativeEmailId;
	}

	public void setAlternativeEmailId(String alternativeEmailId) {
		this.alternativeEmailId = alternativeEmailId;
	}

	public String getCustomerMobileNumber() {
		return customerMobileNumber;
	}

	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}

	public String getAlternativeMobileNumber() {
		return alternativeMobileNumber;
	}

	public void setAlternativeMobileNumber(String alternativeMobileNumber) {
		this.alternativeMobileNumber = alternativeMobileNumber;
	}

	public String getCustomerLandlineNumber() {
		return customerLandlineNumber;
	}

	public void setCustomerLandlineNumber(String customerLandlineNumber) {
		this.customerLandlineNumber = customerLandlineNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "CustomerPersonalDetails [sequenceNumber=" + sequenceNumber + ", fullName=" + fullName + ", firstName="
				+ firstName + ", lastName=" + lastName + ", middleName=" + middleName + ", age=" + age + ", gender="
				+ gender + ", customerEmailId=" + customerEmailId + ", alternativeEmailId=" + alternativeEmailId
				+ ", customerMobileNumber=" + customerMobileNumber + ", alternativeMobileNumber="
				+ alternativeMobileNumber + ", customerLandlineNumber=" + customerLandlineNumber + "]";
	}

	public CustomerPersonalDetails(String firstName, String lastName, String middleName, String age, String gender,
			String customerEmailId, String alternativeEmailId, String customerMobileNumber,
			String alternativeMobileNumber, String customerLandlineNumber) {
		super();

		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.age = age;
		this.gender = gender;
		this.customerEmailId = customerEmailId;
		this.alternativeEmailId = alternativeEmailId;
		this.customerMobileNumber = customerMobileNumber;
		this.alternativeMobileNumber = alternativeMobileNumber;
		this.customerLandlineNumber = customerLandlineNumber;
	}

	public CustomerPersonalDetails() {
		// TODO Auto-generated constructor stub
	}

}
