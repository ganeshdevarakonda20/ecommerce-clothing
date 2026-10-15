package com.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;
	@Column(name = "role_name", unique = true, nullable = false)
	private String roleName;
	
	//////////////////////////////////////Constructors
	
	public Role() {
		
	}
	
	public Role(String roleName) {
		this.roleName=roleName;
	}
	
	///////////////////////////////Setters 
	
	
	
	public void setRoleName(String roleName) {
		this.roleName=roleName;
	}
	
	/////////////////////////////////////Getters
	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}
	
	///////////////////////////////Overridden toString()
	
	public String toString() {
		return roleId+" "+roleName;
	}
	
	
}
