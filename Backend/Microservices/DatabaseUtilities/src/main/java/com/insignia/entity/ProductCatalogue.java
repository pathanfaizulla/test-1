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
@Table(name = "product_catalogue")
public class ProductCatalogue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sequence_number")
	private Integer sequenceNumber;
	
	@Column(name = "application_id")
	private String applicationId;

	@Column(name = "tenant_id")
	private String tenantId;

	@Column(name = "catalogue_name", length = 32, unique = true)
	private String catalogueName;

	@Column(name = "catalogue_description", length = 256)
	private String catalogueDescription;
	
	@Column(name = "product_catalogue_image_path")
	private String productCatalogueImagePath;

	@Column(name = "default_image", length = 50)
	private String defaultImage;

}
