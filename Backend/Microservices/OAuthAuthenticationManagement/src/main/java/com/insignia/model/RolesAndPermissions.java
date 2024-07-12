package com.insignia.model;

import java.util.Date;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RolesAndPermissions {
	
		private Integer roleId;
	
	
	private String roleName;
	
	
	private Boolean permission1;
	
	private Boolean permission2;
	
	private Boolean permission3;
	
	private Boolean permission4;
	
	
	private Date roleApprovedDate;
	
	

	private Date roleRevokedDate;
	
	
	
	private Date permissionChangeDate;
	
	
	
	private String updatedPermissions;
	

	public RolesAndPermissions(String roleName) {
		super();
		this.roleName = roleName;
	}
	
	//@JoinColumn(name="sequence_number")
	//@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	//private CustomerBasicDetailsEntity CustomerBasicDetailsEntity;
	
	
}
