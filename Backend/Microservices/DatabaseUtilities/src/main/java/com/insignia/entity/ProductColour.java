package com.insignia.entity;

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
@Table(name = "product_colour")
public class ProductColour {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sequence_number", length = 4)
	private Integer sequenceNumber;

	@Column(name = "application_id")
	private String applicationId;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "colour_name", length = 16)
	private String colourName;

	@Column(name = "colour_description", length = 128)
	private String colourDescription;
	
	@Column(name = "hex_code", length = 8)
	private String hexCode;

}
