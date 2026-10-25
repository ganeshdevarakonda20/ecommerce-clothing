package com.ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="cart_items")
public class CartItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int cartItemId;
	@ManyToOne
	@JoinColumn(name="cart_id",nullable=false)
	private Cart cart;
	@ManyToOne
	@JoinColumn(name="product_id",nullable=false)
	private Product product;
	@Column(name="quantity",nullable=false)
	private int quantity;
	@Column(name="price",nullable=false)
	private double subTotal;
	
	public CartItem() {
		
	}

	public CartItem(Cart cart, Product product, int quantity, double subTotal) {
		super();
		this.cart = cart;
		this.product = product;
		this.quantity = quantity;
		this.subTotal = subTotal;
	}

	public int getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(int cartItemId) {
		this.cartItemId = cartItemId;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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
	
	
	
	
}
