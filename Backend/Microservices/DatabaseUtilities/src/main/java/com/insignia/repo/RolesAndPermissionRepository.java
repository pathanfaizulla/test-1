package com.insignia.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insignia.entity.RolesAndPermissions;

public interface RolesAndPermissionRepository extends JpaRepository<RolesAndPermissions, Serializable> {

	
}
