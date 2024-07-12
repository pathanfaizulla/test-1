package com.insignia.model;

import java.math.BigDecimal;

public class SubscriptionRequest {

	private Long customerSequenceNumber;
	private Integer expirationDuration;
	private Long sequenceNumber;

	private String applicationId;
	private String tenantId;

	private String planId;
	private boolean isUpdatedPlanId;

	private String planName;
	private boolean isUpdatedPlanName;

	private String planDescription;
	private boolean isUpdatedPlanDescription;

	private Integer planDuration;
	private boolean isUpdatedPlanDuration;

	private BigDecimal planPricing;
	private boolean isUpdatedPlanPricing;

	private String planActivationStatus;
	private boolean isUpdatedPlanActivationStatus;

	private String planActivationDate;
	private boolean isActivationDateUpdated;

	private String planDeactivationDate;
	private boolean isDeactivationDateUpdated;

	private BigDecimal percentageDiscount;
	private boolean isUpdatedPercentageDiscount;

	private String planActiveTill;

	private boolean autoRenewal;
	private boolean isAutoRenewalUpdated;

	private BigDecimal minOrderValue;
	private boolean isMinOrderValueUpdated;

	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}

	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
	}

	public Integer getExpirationDuration() {
		return expirationDuration;
	}

	public void setExpirationDuration(Integer expirationDuration) {
		this.expirationDuration = expirationDuration;
	}

	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
		this.isUpdatedPlanId = true;
	}

	public boolean isUpdatedPlanId() {
		return isUpdatedPlanId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
		this.isUpdatedPlanName = true;
	}

	public boolean isUpdatedPlanName() {
		return isUpdatedPlanName;
	}

	public String getPlanDescription() {
		return planDescription;
	}

	public void setPlanDescription(String planDescription) {
		this.planDescription = planDescription;
		this.isUpdatedPlanDescription = true;
	}

	public boolean isUpdatedPlanDescription() {
		return isUpdatedPlanDescription;
	}

	public Integer getPlanDuration() {
		return planDuration;
	}

	public void setPlanDuration(Integer planDuration) {
		this.planDuration = planDuration;
		this.isUpdatedPlanDuration = true;
	}

	public boolean isUpdatedPlanDuration() {
		return isUpdatedPlanDuration;
	}

	public BigDecimal getPlanPricing() {
		return planPricing;
	}

	public void setPlanPricing(BigDecimal planPricing) {
		this.planPricing = planPricing;
		this.isUpdatedPlanPricing = true;
	}

	public boolean isUpdatedPlanPricing() {
		return isUpdatedPlanPricing;
	}

	public String getPlanActivationStatus() {
		return planActivationStatus;
	}

	public void setPlanActivationStatus(String planActivationStatus) {
		this.planActivationStatus = planActivationStatus;
		this.isUpdatedPlanActivationStatus = true;
	}

	public boolean isUpdatedPlanActivationStatus() {
		return isUpdatedPlanActivationStatus;
	}

	public String getPlanActivationDate() {
		return planActivationDate;
	}

	public void setPlanActivationDate(String planActivationDate) {
		this.planActivationDate = planActivationDate;
		this.isActivationDateUpdated = true;
	}

	public boolean isActivationDateUpdated() {
		return isActivationDateUpdated;
	}

	public String getPlanDeactivationDate() {
		return planDeactivationDate;
	}

	public void setPlanDeactivationDate(String planDeactivationDate) {
		this.planDeactivationDate = planDeactivationDate;
		this.isDeactivationDateUpdated = true;
	}

	public boolean isDeactivationDateUpdated() {
		return isDeactivationDateUpdated;
	}

	public BigDecimal getPercentageDiscount() {
		return percentageDiscount;
	}

	public void setPercentageDiscount(BigDecimal percentageDiscount) {
		this.percentageDiscount = percentageDiscount;
		this.isUpdatedPercentageDiscount = true;
	}

	public boolean isUpdatedPercentageDiscount() {
		return isUpdatedPercentageDiscount;
	}

	public String getPlanActiveTill() {
		return planActiveTill;
	}

	public void setPlanActiveTill(String planActiveTill) {
		this.planActiveTill = planActiveTill;
	}

	public boolean getAutoRenewal() {
		return autoRenewal;
	}

	public void setAutoRenewal(boolean autoRenewal) {
		this.autoRenewal = autoRenewal;
		this.isAutoRenewalUpdated = true;
	}

	public boolean isAutoRenewalUpdated() {
		return isAutoRenewalUpdated;
	}

	public BigDecimal getMinOrderValue() {
		return minOrderValue;
	}

	public void setMinOrderValue(BigDecimal minOrderValue) {
		this.minOrderValue = minOrderValue;
		this.isMinOrderValueUpdated = true;
	}

	public boolean isMinOrderValueUpdated() {
		return isMinOrderValueUpdated;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
