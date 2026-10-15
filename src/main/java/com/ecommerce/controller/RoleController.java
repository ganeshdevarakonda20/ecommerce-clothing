package com.ecommerce.controller;

import com.ecommerce.dto.RoleRequestDto;
import com.ecommerce.dto.RoleResponseDto;
import com.ecommerce.entity.Role;
import com.ecommerce.service.RoleService;

import jakarta.validation.Valid;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
	
	@Autowired
	RoleService roleService;
	
	@PostMapping
	public ResponseEntity<RoleResponseDto> addRole(@Valid @RequestBody RoleRequestDto roleDto) {
		RoleResponseDto createdRole=roleService.addRole(roleDto);
		return new ResponseEntity<>(createdRole,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{roleId}")
	public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable int roleId) {
		RoleResponseDto role=roleService.getRoleById(roleId);
		return ResponseEntity.ok(role);
	}
	
	
//	@GetMapping
//	public Role getRoleByName(String roleName) {
//		Role role=roleService.getRoleByName(roleName);
//		return role;
//	}
	
	@GetMapping
	public ResponseEntity<List<RoleResponseDto>> getAllRoles(){
		List<RoleResponseDto> roles=roleService.getAllRoles();
		return ResponseEntity.ok(roles);
		
	}
	
	
	@DeleteMapping("/{roleId}")
	public ResponseEntity<String> deleteRole(@PathVariable int roleId) {
		roleService.deleteRole(roleId);
		return ResponseEntity.ok("Role deleted successfully");
	}
	
	
}
