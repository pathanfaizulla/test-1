package com.insignia.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "product_subcategory")
public class ProductSubCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subcategory_id")
	private Long subCategoryId;


	@Column(name = "category_id",nullable = false)
	private Long categoryId;

	@Column(name = "subcategory_name",unique = true)
	private String subCategoryName;


	@Column(name = "subcategory_description")
	private String subCategoryDescription;

	@Column(name = "subcategory_image_path")
	private String subCategoryImagePath;

	@Column(columnDefinition = "boolean default false")
	private Boolean isSoftDeleted;

	@Column(name = "default_image",length = 50)
	private String defaultImage;

	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "subcategory_id")
	private List<ProductDetails> productDetails;
}
