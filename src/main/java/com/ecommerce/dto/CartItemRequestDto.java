package com.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartItemRequestDto {
	
	@NotNull(message="Product id is required")
	private int productId;
	@Min(value = 1,message="Quantity must be atleast 1")
	private int quantity;
	
	public CartItemRequestDto() {
		
	}

	public CartItemRequestDto(@NotNull(message = "Product id is required") int productId,
			@Min(value = 1, message = "Quantity must be atleast 1") int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	
}
