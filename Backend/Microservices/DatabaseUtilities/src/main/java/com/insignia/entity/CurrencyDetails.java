package com.insignia.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "currency_details")
public class CurrencyDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sequence_number")
	private Integer sequenceNumber;

	@Column(name = "application_id", nullable = false)
	private String applicationId;

	@Column(name = "tenant_id", nullable = false)
	private String tenantId;

	@Column(name = "currency_name", nullable = false, length = 32)
	private String currencyName;

	@Column(name = "currency_description", length = 256)
	private String currencyDescription;

	@Column(name = "currency_code", nullable = false, length = 3)
	private String currencyCode;

	@Column(name = "base_rate")
	private BigDecimal baseRate;

	@Column(name = "base_rate_currency", length = 3)
	private String baseRateCurrency;

}
