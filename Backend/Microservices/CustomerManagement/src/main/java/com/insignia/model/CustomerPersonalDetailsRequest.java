package com.insignia.model;

public class CustomerPersonalDetailsRequest {

	private Integer sequenceNumber;
	private Long customerSequenceNumber;

	private Integer expirationDuration;

	private String fullName;

	private String firstName;
	private boolean isFirstNameUpdated;

	private String lastName;
	private boolean isLastNameUpdated;

	private String middleName;
	private boolean isMiddleNameUpdated;

	private String age;
	private boolean isAgeUpdated;

	private String gender;
	private boolean isGenderUpdated;

	private String customerEmailId;
	private boolean isCustomerEmailIdUpdated;

	private String alternativeEmailId;
	private boolean isAlternativeEmailIdUpdated;

	private String customerMobileNumber;
	private boolean isCustomerMobileNumberUpdated;

	private String alternativeMobileNumber;
	private boolean isAlternativeMobileNumberUpdated;

	private String customerLandlineNumber;
	private boolean isCustomerLandlineUpdated;

	public Integer getExpirationDuration() {
		return expirationDuration;
	}

	public void setExpirationDuration(Integer expirationDuration) {
		this.expirationDuration = expirationDuration;
	}

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
		this.isFirstNameUpdated = true;
	}

	public boolean isFirstNameUpdated() {
		return isFirstNameUpdated;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		this.isLastNameUpdated = true;
	}

	public boolean isLastNameUpdated() {
		return isLastNameUpdated;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
		this.isMiddleNameUpdated = true;
	}

	public boolean isMiddleNameUpdated() {
		return isMiddleNameUpdated;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
		this.isAgeUpdated = true;
	}

	public boolean isAgeUpdated() {
		return isAgeUpdated;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
		this.isGenderUpdated = true;
	}

	public boolean isGenderUpdated() {
		return isGenderUpdated;
	}

	public String getCustomerEmailId() {
		return customerEmailId;
	}

	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
		this.isCustomerEmailIdUpdated = true;
	}

	public boolean isCustomerEmailIdUpdated() {
		return isCustomerEmailIdUpdated;
	}

	public String getAlternativeEmailId() {
		return alternativeEmailId;
	}

	public void setAlternativeEmailId(String alternativeEmailId) {
		this.alternativeEmailId = alternativeEmailId;
		this.isAlternativeEmailIdUpdated = true;
	}

	public boolean isAlternativeEmailIdUpdated() {
		return isAlternativeEmailIdUpdated;
	}

	public String getCustomerMobileNumber() {
		return customerMobileNumber;
	}

	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
		this.isCustomerMobileNumberUpdated = true;
	}

	public boolean isCustomerMobileNumberUpdated() {
		return isCustomerMobileNumberUpdated;
	}

	public String getAlternativeMobileNumber() {
		return alternativeMobileNumber;
	}

	public void setAlternativeMobileNumber(String alternativeMobileNumber) {
		this.alternativeMobileNumber = alternativeMobileNumber;
		this.isAlternativeMobileNumberUpdated = true;
	}

	public boolean isAlternativeMobileNumberUpdated() {
		return isAlternativeMobileNumberUpdated;
	}

	public String getCustomerLandlineNumber() {
		return customerLandlineNumber;
	}

	public void setCustomerLandlineNumber(String customerLandlineNumber) {
		this.customerLandlineNumber = customerLandlineNumber;
		this.isCustomerLandlineUpdated = true;
	}

	public boolean isCustomerLandlineUpdated() {
		return isCustomerLandlineUpdated;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "CustomerPersonalDetailsRequest [sequenceNumber=" + sequenceNumber + ", customerSequenceNumber="
				+ customerSequenceNumber + ", expirationDuration=" + expirationDuration + ", fullName=" + fullName
				+ ", firstName=" + firstName + ", isFirstNameUpdated=" + isFirstNameUpdated + ", lastName=" + lastName
				+ ", isLastNameUpdated=" + isLastNameUpdated + ", middleName=" + middleName + ", isMiddleNameUpdated="
				+ isMiddleNameUpdated + ", age=" + age + ", isAgeUpdated=" + isAgeUpdated + ", gender=" + gender
				+ ", isGenderUpdated=" + isGenderUpdated + ", customerEmailId=" + customerEmailId
				+ ", isCustomerEmailIdUpdated=" + isCustomerEmailIdUpdated + ", alternativeEmailId="
				+ alternativeEmailId + ", isAlternativeEmailIdUpdated=" + isAlternativeEmailIdUpdated
				+ ", customerMobileNumber=" + customerMobileNumber + ", isCustomerMobileNumberUpdated="
				+ isCustomerMobileNumberUpdated + ", alternativeMobileNumber=" + alternativeMobileNumber
				+ ", isAlternativeMobileNumberUpdated=" + isAlternativeMobileNumberUpdated + ", customerLandlineNumber="
				+ customerLandlineNumber + ", isCustomerLandlineUpdated=" + isCustomerLandlineUpdated + "]";
	}
}
