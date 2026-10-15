package com.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@Column(name="name")
	private String name;
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	@Column(name="password")
	private String password;
	@Column(name="phone")
	private String phone;
	@Column(name="address")
	private String address;
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;
	
	public User() {
		
	}

	public User(String name, String email, String password, String phone, String address, Role role) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.role = role;
	}
	
	public void setUserId(int userId) {
		this.userId=userId;
	}
	
	public int getUserId() {
		return userId;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public String toString() {
		return userId+" "+name+" "+email+" "+phone+" "+address+" "+role;
	}
	
}
