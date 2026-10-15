package com.ecommerce.controller;

import java.util.List;
import java.util.Optional;

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

import com.ecommerce.dto.UserRequestDto;
import com.ecommerce.dto.UserResponseDto;
import com.ecommerce.entity.User;
import com.ecommerce.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping
	public ResponseEntity<UserResponseDto> addUser(@Valid @RequestBody UserRequestDto dto) {
		UserResponseDto createdUser=userService.addUser(dto);
		return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable int userId) {
		UserResponseDto user=userService.getUserById(userId);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email){
		UserResponseDto user=userService.getUserByEmail(email);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping
	public ResponseEntity<List<UserResponseDto>> getAllUsers() {
		List<UserResponseDto> users=userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserResponseDto> updateUser(@PathVariable int userId,@Valid @RequestBody UserRequestDto dto) {
		UserResponseDto updatedUser=userService.updateUser(userId,dto);
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable int userId) {
		userService.deleteUser(userId);
		return ResponseEntity.ok("User deleted successfully");
	}
	
}
