package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubCategoryRequest {

	private Long categoryId;
	private Long subCategoryId;
	private String subCategoryName;
	private String subCategoryDescription;
	private String subCategoryImagePath;
	private String subCategoryDefaultImage;
	private Long customerSequenceNumber;
	private Integer expirationDuration;
	private boolean isSubCategoryNameUpdated;
	private boolean isSubCategoryDescriptionUpdated;
	private boolean isSubCategoryImagePathUpdated;
	private boolean isSubCategoryDefaultImageUpdated;

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
		this.isSubCategoryNameUpdated = true;
	}

	public boolean isSubCategoryNameUpdated() {
		return isSubCategoryNameUpdated;
	}

	public String getSubCategoryDescription() {
		return subCategoryDescription;
	}

	public void setSubCategoryDescription(String subCategoryDescription) {
		this.subCategoryDescription = subCategoryDescription;
		this.isSubCategoryDescriptionUpdated = true;
	}

	public boolean isSubCategoryDescriptionUpdated() {
		return isSubCategoryDescriptionUpdated;
	}

	public String getSubCategoryImagePath() {
		return subCategoryImagePath;
	}

	public void setSubCategoryImagePath(String subCategoryImagePath) {
		this.subCategoryImagePath = subCategoryImagePath;
		this.isSubCategoryImagePathUpdated = true;
	}

	public boolean isSubCategoryImagePathUpdated() {
		return isSubCategoryImagePathUpdated;
	}

	public String getSubCategoryDefaultImage() {
		return subCategoryDefaultImage;
	}

	public void setSubCategoryDefaultImage(String subCategoryDefaultImage) {
		this.subCategoryDefaultImage = subCategoryDefaultImage;
		this.isSubCategoryDefaultImageUpdated = true;
	}

	public boolean isSubCategoryDefaultImageUpdated() {
		return isSubCategoryDefaultImageUpdated;
	}
}
