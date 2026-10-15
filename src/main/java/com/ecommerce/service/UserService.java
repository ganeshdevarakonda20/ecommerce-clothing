package com.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.ecommerce.dto.RoleResponseDto;
import com.ecommerce.dto.UserRequestDto;
import com.ecommerce.dto.UserResponseDto;
import com.ecommerce.entity.User;
import com.ecommerce.entity.Role;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UserAlreadyExistsException;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	private UserResponseDto mapToResponseDto(User user) {
		
		UserResponseDto dto=new UserResponseDto();
		dto.setUserId(user.getUserId());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setPhone(user.getPhone());
		dto.setAddress(user.getAddress());
		
		if(user.getRole()!=null) {
			RoleResponseDto roleDto=new RoleResponseDto();
			roleDto.setRoleId(user.getRole().getRoleId());
			roleDto.setRoleName(user.getRole().getRoleName());
			dto.setRole(roleDto);
			
		}
		return dto;
	}
	
	
	
	public UserResponseDto addUser(UserRequestDto dto) {
		if(userRepository.existsByEmail(dto.getEmail())) {
			throw new UserAlreadyExistsException("Email already in use : "+dto.getEmail());
		}
		
		Role role=roleRepository.findById(dto.getRoleId()).orElseThrow(()->new ResourceNotFoundException("Role Not Found With ID : "+dto.getRoleId()));
		
		User user=new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setPhone(dto.getPhone());
		user.setAddress(dto.getAddress());
		user.setRole(role);
		
		User savedUser=userRepository.save(user);
		return mapToResponseDto(savedUser);
		
	}
	
	public UserResponseDto getUserById(int userId){
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with ID : "+userId));
		return mapToResponseDto(user);
	}
	
	public UserResponseDto getUserByEmail(String email){
		User user=userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found with email : "+email));
		return mapToResponseDto(user);
	}
	
	public List<UserResponseDto> getAllUsers() {
		List<User> users=userRepository.findAll();
		List<UserResponseDto> dtos=new ArrayList<>();
		
		for(User user: users) {
			dtos.add(mapToResponseDto(user));
		}
		return dtos;
	}
	
	public UserResponseDto updateUser(int userId,UserRequestDto user) {
		User existingUser=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with ID : "+userId));
		Role role=roleRepository.findById(user.getRoleId()).orElseThrow(()->new ResourceNotFoundException("Role not found with ID : "+user.getRoleId()));
		
		existingUser.setName(user.getName());
		existingUser.setPhone(user.getPhone());
		existingUser.setAddress(user.getAddress());
		existingUser.setPassword(user.getPassword());
		existingUser.setRole(role);
		
		User savedUser=userRepository.save(existingUser);
		return mapToResponseDto(savedUser);
		
	}
	
	public void deleteUser(int userId) {
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with ID : "+userId));
		userRepository.delete(user);
	}
	
}
