package com.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class UserRequestDto {
	
	@NotBlank(message="Name cannot be blank")
	private String name;
	
	@NotBlank(message="Email is required")
	@Email(message="Invalid email format")
	private String email;
	
	@NotBlank(message="Password is required")
	@Size(min=6, message="Password must be atleast 6 characters")
	private String password;
	
	@NotBlank(message="Phone number is required")
	private String phone;
	
	@NotBlank(message="Address is required")
	private String address;
	
	@NotNull(message="RoleId is required")
	private Integer roleId;
	
	
	public UserRequestDto() {
		super();
	}

	public UserRequestDto(@NotBlank(message = "Name cannot be blank") String name,
			@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,
			@NotBlank(message = "Password is required") @Size(min=6, message = "Password must be atleast 6 characters")  String password,
			@NotBlank(message = "Phone number is required") String phone,
			@NotBlank(message = "Address is required") String address,
			 Integer roleId) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.roleId = roleId;
	}
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	
	
}
