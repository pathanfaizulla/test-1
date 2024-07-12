package com.insignia.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_subscription_plan_link")
public class CustomerSubscriptionPlanLink {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "sequence_number")
	private Long sequenceNumber;

	@Column(name = "customer_sequence_number")
	private Long customerSequenceNumber;
	
	@Column(name = "auto_renewal")
	private Boolean autoRenewal;
	
	@Column(name = "plan_status")
	private String subscriptionPlanStatus;
	
	@Column(name = "activation_date")
	private Date activationDate;
	
	@Column(name = "cancellation_date")
	private Date cancellationDate;

	@Column(name = "deactivation_date")
	private Date deactivationDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subscription_plan_sequence_number")
	private SubscriptionDetails subscriptionDetails;

}
