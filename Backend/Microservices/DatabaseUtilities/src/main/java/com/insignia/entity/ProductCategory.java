package com.insignia.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_category")
public class ProductCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Long categoryId;
	
	@Column(name = "application_id")
	private String applicationId;

	@Column(name = "tenant_id")
	private String tenantId;

	@Column(name = "category_name",unique = true)
	private String categoryName;

	@Column(name = "category_description")
	private String categoryDescription;

	@Column(name = "category_image_path")
	private String categoryImagePath;

	@Column(columnDefinition = "boolean default false")
	private Boolean isSoftDeleted;

	@Column(name = "default_image",length = 50)
	private String defaultImage;

	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id")
	private List<ProductSubCategory> productSubCategory;
}
