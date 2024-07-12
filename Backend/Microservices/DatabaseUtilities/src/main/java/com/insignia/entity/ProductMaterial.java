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
@Table(name = "product_material")
public class ProductMaterial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sequence_number", length = 4)
	private Integer sequenceNumber;
	
	@Column(name = "application_id")
	private String applicationId;

	@Column(name = "tenant_id")
	private String tenantId;

	@Column(name = "material_name", length = 30)
	private String materialName;

	@Column(name = "material_description", length = 128)
	private String materialDescription;
	
}
