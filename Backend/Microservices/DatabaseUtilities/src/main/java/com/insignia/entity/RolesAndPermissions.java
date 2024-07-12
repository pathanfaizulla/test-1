package com.insignia.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles_and_permissions")
public class RolesAndPermissions {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id

	@Column(name = "role_id")
	private Integer roleId;

	@Column(name = "role_name")
	private String roleName;

	private Boolean permission1;

	private Boolean permission2;

	private Boolean permission3;

	private Boolean permission4;

	@Column(name = "role_approved_date")
	private Date roleApprovedDate;

	@Column(name = "role_revoked_date")
	private Date roleRevokedDate;

	@Column(name = "permission_change_date")
	private Date permissionChangeDate;

	@Column(name = "updated_permissions")
	private String updatedPermissions;

	@Column(name = "customer_sequence_number")
	private Long customerSequenceNumber;

	

}
