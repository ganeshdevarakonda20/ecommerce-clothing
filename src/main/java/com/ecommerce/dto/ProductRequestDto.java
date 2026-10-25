package com.ecommerce.dto;

import com.ecommerce.entity.Category;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductRequestDto {
	
	@NotBlank(message="Name is required")
	private String name;
	
	private String description;
	
	@NotNull(message="Price is required")
	@DecimalMin(value="0.0", inclusive=false, message="price must be greater than zero")
	private double price;
	
	@NotNull(message="Stock quantity is required ")
	@Min(value=0, message="Stock quantity cannot be negative")
	private int stockQuantitiy;
	
	private String brand;
	private String size;
	private String color;
	private String imageUrl;
	
	@NotNull(message="Category is required")
	private Integer categoryId;
	
	public ProductRequestDto() {
		
	}

	public ProductRequestDto(@NotBlank(message = "Name is required") String name, String description,
			@NotNull(message = "Price is required") @DecimalMin(value = "0.0", inclusive = false, message = "price must be greater than zero") double price,
			@NotNull(message = "Stock quantity is required ") @Min(value = 0, message = "Stock quantity cannot be negative") int stockQuantitiy,
			String brand, String size, String color, String imageUrl,
			@NotNull(message = "Category is required") Integer catagoryId) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.stockQuantitiy = stockQuantitiy;
		this.brand = brand;
		this.size = size;
		this.color = color;
		this.imageUrl = imageUrl;
		this.categoryId = catagoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStockQuantitiy() {
		return stockQuantitiy;
	}

	public void setStockQuanitiy(int stockQuantitiy) {
		this.stockQuantitiy = stockQuantitiy;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer catagoryId) {
		this.categoryId = catagoryId;
	}
	
	
	
}
