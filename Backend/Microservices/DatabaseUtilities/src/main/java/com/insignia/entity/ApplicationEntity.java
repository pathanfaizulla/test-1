package com.insignia.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="applications")
public class ApplicationEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name="application_id")
	private Integer applicationId;
	
	@Column(name="application_name")
	private String applicationName;
	
	@Column(name="application_description")
	private String applicationDescription;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "application_id")
	private List<AppPreferenceDetailsEntity> appPreferenceDetailsEntity = new ArrayList<>();

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationDescription() {
		return applicationDescription;
	}

	public void setApplicationDescription(String applicationDescription) {
		this.applicationDescription = applicationDescription;
	}

	public List<AppPreferenceDetailsEntity> getAppPreferenceDetailsEntity() {
		return appPreferenceDetailsEntity;
	}

	public void setAppPreferenceDetailsEntity(List<AppPreferenceDetailsEntity> appPreferenceDetailsEntity) {
		this.appPreferenceDetailsEntity = appPreferenceDetailsEntity;
	}

	public ApplicationEntity(String applicationName, String applicationDescription,
			List<AppPreferenceDetailsEntity> appPreferenceDetailsEntity) {
		super();
		this.applicationName = applicationName;
		this.applicationDescription = applicationDescription;
		this.appPreferenceDetailsEntity = appPreferenceDetailsEntity;
	}

	public ApplicationEntity() {
		super();
	}

	@Override
	public String toString() {
		return "ApplicationEntity [applicationId=" + applicationId + ", applicationName=" + applicationName
				+ ", applicationDescription=" + applicationDescription + ", appPreferenceDetailsEntity="
				+ appPreferenceDetailsEntity + "]";
	}

	public ApplicationEntity(Integer applicationId, String applicationName, String applicationDescription,
			List<AppPreferenceDetailsEntity> appPreferenceDetailsEntity) {
		super();
		this.applicationId = applicationId;
		this.applicationName = applicationName;
		this.applicationDescription = applicationDescription;
		this.appPreferenceDetailsEntity = appPreferenceDetailsEntity;
	}

	
	
}
