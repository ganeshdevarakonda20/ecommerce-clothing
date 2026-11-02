package com.ecommerce.dto;

public class AuthResponseDto {
	
	private String token;
    private String tokenType = "Bearer";
    private int userId;
    private String email;
    private String role;
    private String message;
    
    public AuthResponseDto(){
    	
    }
    
    public AuthResponseDto(String token, int userId, String email, String role, String message) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.message = message;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    
}
