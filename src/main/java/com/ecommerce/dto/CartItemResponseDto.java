package com.ecommerce.dto;

public class CartItemResponseDto {
	
	private int cartItemId;
	private int productId;
	private String productName;
	private double price;
	private int quantity;
	private double subTotal;
	private String imageUrl;
	
	public CartItemResponseDto() {
		
	}

	public CartItemResponseDto(int cartItemId, int productId, String productName, double price, int quantity,
			double subTotal, String imageUrl) {
		super();
		this.cartItemId = cartItemId;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.subTotal = subTotal;
		this.imageUrl = imageUrl;
	}

	public int getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(int cartItemId) {
		this.cartItemId = cartItemId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProdctName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	
}
