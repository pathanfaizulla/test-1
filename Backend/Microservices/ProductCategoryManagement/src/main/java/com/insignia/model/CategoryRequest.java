package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryRequest {

	private Long categoryId;
	private String applicationId;
	private String tenantId;
	private String categoryName;
	private String categoryDescription;
	private String categoryImagePath;
	private String defaultImage;
	private Long customerSequenceNumber;
	private Integer expirationDuration;
	private boolean isCategoryNameUpdated;
	private boolean isCategoryDescriptionUpdated;
	private boolean isCategoryImagePathUpdated;
	private boolean isDefaultImageUpdated;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
		this.isCategoryNameUpdated = true;

	}

	public boolean isCategoryNameUpdated() {
		return isCategoryNameUpdated;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
		this.isCategoryDescriptionUpdated = true;
	}

	public boolean isCategoryDescriptionUpdated() {
		return isCategoryDescriptionUpdated;
	}

	public String getCategoryImagePath() {
		return categoryImagePath;
	}

	public void setCategoryImagePath(String categoryImagePath) {
		this.categoryImagePath = categoryImagePath;
		this.isCategoryImagePathUpdated = true;
	}

	public boolean isCategoryImagePathUpdated() {
		return isCategoryImagePathUpdated;
	}

	public String getDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
		this.isDefaultImageUpdated = true;
	}

	public boolean isDefaultImageUpdated() {
		return isDefaultImageUpdated;
	}

}
