package com.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryRequestDto {
	
	@NotBlank(message="Category name is required")
	@Size(min=2, max=50, message="Category name must be between 2 and 50 characters ")
	private String categoryName;
	
	private String description;
	
	public CategoryRequestDto() {
		
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
