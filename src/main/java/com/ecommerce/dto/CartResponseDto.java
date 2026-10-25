package com.ecommerce.dto;

import java.util.List;

public class CartResponseDto {
	
	private int cartId;
	private int userId;
	private List<CartItemResponseDto> items;
	private double totalPrice;
	
	public CartResponseDto() {
		
	}

	public CartResponseDto(int cartId, int userId, List<CartItemResponseDto> items, double totalPrice) {
		super();
		this.cartId = cartId;
		this.userId = userId;
		this.items = items;
		this.totalPrice = totalPrice;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<CartItemResponseDto> getItems() {
		return items;
	}

	public void setItems(List<CartItemResponseDto> items) {
		this.items = items;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	
}
