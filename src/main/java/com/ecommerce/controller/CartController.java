package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.CartItemRequestDto;
import com.ecommerce.dto.CartResponseDto;
import com.ecommerce.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/user/{userId}/items")
	public ResponseEntity<CartResponseDto> addItemToCart(@PathVariable int userId,@Valid @RequestBody CartItemRequestDto dto){
		CartResponseDto response=cartService.addItemToCart(userId, dto);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<CartResponseDto> getCartByUserId(@PathVariable int userId){
		CartResponseDto response=cartService.getCartByUserId(userId);
		
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/user/{userId}/items/{cartItemId}")
	public ResponseEntity<CartResponseDto> updateItemQuantity(@PathVariable int userId,@PathVariable int cartItemId,@RequestParam int quantity){
		CartResponseDto response=cartService.updateItemQuantity(userId, cartItemId, quantity);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/user/{userId}/items/{cartItemId}")
	public ResponseEntity<CartResponseDto> removeItemFromCart(@PathVariable int userId,@PathVariable int cartItemId){
		CartResponseDto response=cartService.removeItemFromCart(userId, cartItemId);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<String> clearCart(@PathVariable int userId){
		cartService.clearEntireCart(userId);
		return ResponseEntity.ok("Cart cleared successfully");
	}
	
	
}
