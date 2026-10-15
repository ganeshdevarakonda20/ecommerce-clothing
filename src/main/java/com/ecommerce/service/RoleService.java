package com.ecommerce.service;

import com.ecommerce.dto.RoleRequestDto;
import com.ecommerce.dto.RoleResponseDto;
import com.ecommerce.entity.Role;
import com.ecommerce.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	/////////////////////////////Converting role entity to RoleResponseDto
	
	public RoleResponseDto mapToResponseDto(Role role) {
		RoleResponseDto dto=new RoleResponseDto();
		dto.setRoleId(role.getRoleId());
		dto.setRoleName(role.getRoleName());
		return dto;
	}
	
	
	public RoleResponseDto addRole(RoleRequestDto dto){
		Role role=new Role();
		role.setRoleName(dto.getRoleName());
		
		Role savedRole=roleRepository.save(role);
		return mapToResponseDto(savedRole);
	}
	
	public RoleResponseDto getRoleById(int roleId) {
		Role role=roleRepository.findById(roleId).orElseThrow(()-> new ResourceNotFoundException("Role not found with ID = "+roleId));
		return mapToResponseDto(role);
	}
	
	
	public List<RoleResponseDto> getAllRoles() {
		List<Role> roles=roleRepository.findAll();
		List<RoleResponseDto> dto=new ArrayList<>();
		for(Role role : roles) {
			dto.add(mapToResponseDto(role));
		}
		return dto;
	}
	
//	public String updateRole(Role role) {
//		if(roleRepository.findByRoleName(role.getRoleName())!=null) {
//			roleRepository.save(role);
//			return "Role Updated";
//		}
//		return "Role Not Found";
//	}
	
	
	public void deleteRole(int roleId) {
		Role role=roleRepository.findById(roleId).orElseThrow(()->new ResourceNotFoundException("Role not found with ID = "+roleId));
		roleRepository.delete(role);
	}
	
}
