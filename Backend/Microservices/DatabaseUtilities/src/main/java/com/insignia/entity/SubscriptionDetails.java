package com.insignia.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription_plan_master_table")
public class SubscriptionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sequence_number")
	private Long sequenceNumber;
	
	@Column(name = "application_id")
	private String applicationId;

	@Column(name = "tenant_id")
	private String tenantId;

	@Column(name = "plan_id")
	private String planId;

	@Column(name = "plan_name")
	private String planName;

	@Column(name = "plan_description")
	private String planDescription;

	@Column(name = "plan_duration")
	private Integer planDuration;

	@Column(name = "plan_pricing")
	private BigDecimal planPricing;

	@Column(name = "plan_activation_status")
	private String planActivationStatus;

	@Column(name = "plan_activation_date")
	private Date planActivationDate;

	@Column(name = "plan_deactivation_date")
	private Date planDeactivationDate;

	@Column(name = "percentage_discount")
	private BigDecimal percentageDiscount;

	@Column(name = "active_till")
	private Date planActiveTill;

	@Column(name = "auto_renewal", columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean autoRenewal;

	@Column(name = "min_order_value")
	private BigDecimal minOrderValue;

	@OneToMany(mappedBy = "subscriptionDetails")
	private List<CustomerSubscriptionPlanLink> customerSubscriptionPlanLink;

	@OneToMany(mappedBy = "subscriptionDetails")
	private List<ProductSubscriptionPlanLink> productSubscriptionPlanLink;

}
