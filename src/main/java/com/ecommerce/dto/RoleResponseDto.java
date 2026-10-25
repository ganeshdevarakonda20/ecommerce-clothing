package com.ecommerce.dto;

import com.ecommerce.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
public class RoleResponseDto {
	
	private int roleId;
	private String roleName;
	
	public RoleResponseDto() {
		
	}
	
	public RoleResponseDto(int roleId,String roleName) {
		this.roleId=roleId;
		this.roleName=roleName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
	
}
