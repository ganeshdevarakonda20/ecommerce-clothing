package com.ecommerce.dto;

import com.ecommerce.entity.*;

public class ProductResponseDto {
	
	private int productId;
	private String name;
	private String description;
	private double price;
	private int stockQuantity;
	private String brand;
	private String size;
	private String color;
	private String imageUrl;
	private CategoryResponseDto category;
	
	public ProductResponseDto() {
		
	}

	public ProductResponseDto(int productId, String name, String description, double price, int stockQuantity,
			String brand, String size, String color, String imageUrl, CategoryResponseDto category) {
		super();
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.brand = brand;
		this.size = size;
		this.color = color;
		this.imageUrl = imageUrl;
		this.category = category;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
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

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
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

	public CategoryResponseDto getCategory() {
		return category;
	}

	public void setCategory(CategoryResponseDto category) {
		this.category = category;
	}
	
	
	
}
