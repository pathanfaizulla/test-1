package com.insignia.model;

import java.util.Date;

public class CustomerSubscriptonLinkResponse extends SubscriptonLinkResponse {

	private Boolean autoRenewal;
	private String subscriptionPlanStatus;
	private Date activationDate;
	private Date cancellationDate;
	private Date deactivationDate;

	public Boolean getAutoRenewal() {
		return autoRenewal;
	}

	public void setAutoRenewal(Boolean autoRenewal) {
		this.autoRenewal = autoRenewal;
	}

	public String getSubscriptionPlanStatus() {
		return subscriptionPlanStatus;
	}

	public void setSubscriptionPlanStatus(String subscriptionPlanStatus) {
		this.subscriptionPlanStatus = subscriptionPlanStatus;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public Date getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(Date cancellationDate) {
		this.cancellationDate = cancellationDate;
	}

	public Date getDeactivationDate() {
		return deactivationDate;
	}

	public void setDeactivationDate(Date deactivationDate) {
		this.deactivationDate = deactivationDate;
	}

}
