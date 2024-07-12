package com.insignia.model;

import java.util.Date;

public class RolesAndPermissionsRequest {
	
	private Integer roleId;
	
	private Long customerSequenceNumber;
	
	private Integer expirationDuration;
	
	private String roleName;
	private boolean isRoleNameUpdated;	
	
	private Boolean permission1;
	private boolean isPermission1Updated;
	
	private Boolean permission2;
	private boolean isPermission2Updated;
	
	private Boolean permission3;
	private boolean isPermission3Updated;
	
	private Boolean permission4;
	private boolean isPermission4Updated;
	
	private Date roleApprovedDate;
	private boolean isRoleApprovedDateUpdated;
	
	private Date roleRevokedDate;
	private boolean isRoleRevokedDateUpdated;
	
	private Date permissionChangeDate;
	private boolean isPermissionChangeDateUpdated;
	
	private String updatedPermissions;
	private boolean isUpdatedPermissionsUpdated;
	
	
	
	public Integer getExpirationDuration() {
		return expirationDuration;
	}
	public void setExpirationDuration(Integer expirationDuration) {
		this.expirationDuration = expirationDuration;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Long getCustomerSequenceNumber() {
		return customerSequenceNumber;
	}
	public void setCustomerSequenceNumber(Long customerSequenceNumber) {
		this.customerSequenceNumber = customerSequenceNumber;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
		this.isRoleNameUpdated = true;
	}
	public boolean isRoleNameUpdated() {
		return isRoleNameUpdated;
	}
	
	public Boolean getPermission1() {
		return permission1;
	}
	public void setPermission1(Boolean permission1) {
		this.permission1 = permission1;
		this.isPermission1Updated = true;
	}
	public boolean isPermission1Updated() {
		return isPermission1Updated;
	}
	
	public Boolean getPermission2() {
		return permission2;
	}
	public void setPermission2(Boolean permission2) {
		this.permission2 = permission2;
		this.isPermission2Updated = true;
	}
	public boolean isPermission2Updated() {
		return isPermission2Updated;
	}
	
	public Boolean getPermission3() {
		return permission3;
	}
	public void setPermission3(Boolean permission3) {
		this.permission3 = permission3;
		this.isPermission3Updated = true;
	}
	public boolean isPermission3Updated() {
		return isPermission3Updated;
	}
	
	public Boolean getPermission4() {
		return permission4;
	}
	public void setPermission4(Boolean permission4) {
		this.permission4 = permission4;
		this.isPermission4Updated = true;
	}
	public boolean isPermission4Updated() {
		return isPermission4Updated;
	}
	public Date getRoleApprovedDate() {
		return roleApprovedDate;
	}
	public void setRoleApprovedDate(Date roleApprovedDate) {
		this.roleApprovedDate = roleApprovedDate;
		this.isRoleApprovedDateUpdated = true;
	}
	public boolean isRoleApprovedDateUpdated() {
		return isRoleApprovedDateUpdated;
	}
	
	public Date getRoleRevokedDate() {
		return roleRevokedDate;
	}
	public void setRoleRevokedDate(Date roleRevokedDate) {
		this.roleRevokedDate = roleRevokedDate;
		this.isRoleRevokedDateUpdated = true;
	}
	public boolean isRoleRevokedDateUpdated() {
		return isRoleRevokedDateUpdated;
	}
	
	public Date getPermissionChangeDate() {
		return permissionChangeDate;
	}
	public void setPermissionChangeDate(Date permissionChangeDate) {
		this.permissionChangeDate = permissionChangeDate;
		this.isPermissionChangeDateUpdated = true;
	}
	public boolean isPermissionChangeDateUpdated() {
		return isPermissionChangeDateUpdated;
	}
	
	public String getUpdatedPermissions() {
		return updatedPermissions;
	}
	public void setUpdatedPermissions(String updatedPermissions) {
		this.updatedPermissions = updatedPermissions;
		this.isUpdatedPermissionsUpdated = true;
	}
	public boolean isUpdatedPermissionsUpdated() {
		return isUpdatedPermissionsUpdated;
	}
	@Override
	public String toString() {
		return "RolesAndPermissionsRequest [roleId=" + roleId + ", customerSequenceNumber=" + customerSequenceNumber
				+ ", expirationDuration=" + expirationDuration + ", roleName=" + roleName + ", isRoleNameUpdated="
				+ isRoleNameUpdated + ", permission1=" + permission1 + ", isPermission1Updated=" + isPermission1Updated
				+ ", permission2=" + permission2 + ", isPermission2Updated=" + isPermission2Updated + ", permission3="
				+ permission3 + ", isPermission3Updated=" + isPermission3Updated + ", permission4=" + permission4
				+ ", isPermission4Updated=" + isPermission4Updated + ", roleApprovedDate=" + roleApprovedDate
				+ ", isRoleApprovedDateUpdated=" + isRoleApprovedDateUpdated + ", roleRevokedDate=" + roleRevokedDate
				+ ", isRoleRevokedDateUpdated=" + isRoleRevokedDateUpdated + ", permissionChangeDate="
				+ permissionChangeDate + ", isPermissionChangeDateUpdated=" + isPermissionChangeDateUpdated
				+ ", updatedPermissions=" + updatedPermissions + ", isUpdatedPermissionsUpdated="
				+ isUpdatedPermissionsUpdated + "]";
	}
	
	
		
	
}
