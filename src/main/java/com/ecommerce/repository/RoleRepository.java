package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Role findByRoleName(String roleName);
	Boolean existsByRoleName(String roleName);
	
}
