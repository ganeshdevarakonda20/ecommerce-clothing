package com.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dto.CartItemRequestDto;
import com.ecommerce.dto.CartItemResponseDto;
import com.ecommerce.dto.CartResponseDto;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

@Service
@Transactional
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	public CartResponseDto mapToResponseDto(Cart cart) {
		CartResponseDto dto=new CartResponseDto();
		dto.setCartId(cart.getCartId());
		dto.setUserId(cart.getUser().getUserId());
		dto.setTotalPrice(cart.getTotalPrice());
		
		List<CartItemResponseDto> itemDtos=new ArrayList<>();
		
		if(cart.getItems()!=null) {
			for(CartItem item:cart.getItems()) {
				CartItemResponseDto itemDto=new CartItemResponseDto();
				itemDto.setCartItemId(item.getCartItemId());
				itemDto.setProductId(item.getProduct().getProductId());
				itemDto.setProductName(item.getProduct().getName());
				itemDto.setPrice(item.getProduct().getPrice());
				itemDto.setQuantity(item.getQuantity());
				itemDto.setSubTotal(item.getSubTotal());
				itemDto.setImageUrl(item.getProduct().getImageUrl());
				itemDtos.add(itemDto);
			}
		}
		dto.setItems(itemDtos);
		return dto;
	}
	
	private void recalculateCartTotal(Cart cart) {
		double total=0.0;
		if(cart.getItems()!=null) {
			for(CartItem item:cart.getItems()) {
				total+=item.getSubTotal();
			}
		}
		cart.setTotalPrice(total);
	}
	
	public Cart getOrCreateCart(int userId) {
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with ID ="+userId));
		return cartRepository.findByUserUserId(userId).orElseGet(()->{
			Cart cart=new Cart();
			cart.setUser(user);
			cart.setTotalPrice(0.0);
			return cartRepository.save(cart);
		});
	}
	
	public CartResponseDto addItemToCart(int userId,CartItemRequestDto dto) {
		Cart cart=getOrCreateCart(userId);
		Product product=productRepository.findById(dto.getProductId()).orElseThrow(()->new ResourceNotFoundException("Product not found with ID = "+dto.getProductId()));
		if(product.getStockQuantity()<dto.getQuantity()) {
			throw new RuntimeException("Insufficient stock for product "+product.getName()+". Available stock : "+product.getStockQuantity());
		}
		Optional<CartItem> existingItemOpt=cartItemRepository.findByCartCartIdAndProductProductId(cart.getCartId(), product.getProductId());
		
		if(existingItemOpt.isPresent()) {
			CartItem existingItem=existingItemOpt.get();
			int newQuantity=existingItem.getQuantity()+dto.getQuantity();
			if(product.getStockQuantity()<newQuantity) {
				throw new RuntimeException("Cannot add more. Exceeds available stock for product: "+product.getName());
			}
			existingItem.setQuantity(newQuantity);
			existingItem.setSubTotal(newQuantity*product.getPrice());
			cartItemRepository.save(existingItem);
		}
		else {
			CartItem newItem=new CartItem();
			newItem.setCart(cart);
			newItem.setProduct(product);
			newItem.setQuantity(dto.getQuantity());
			newItem.setSubTotal(dto.getQuantity()*product.getPrice());
			cart.getItems().add(newItem);
			cartItemRepository.save(newItem);
		}
		recalculateCartTotal(cart);
		Cart savedCart=cartRepository.save(cart);
		return mapToResponseDto(savedCart);
	}
	
	public CartResponseDto getCartByUserId(int userId) {
		Cart cart=getOrCreateCart(userId);
		return mapToResponseDto(cart);
	}
	
	public CartResponseDto updateItemQuantity(int userId,int cartItemId,int newQuantity) {
		if(newQuantity<=0) {
			return removeItemFromCart(userId,cartItemId);
		}
		Cart cart=getOrCreateCart(userId);
		CartItem item=cartItemRepository.findById(cartItemId).orElseThrow(()->new ResourceNotFoundException("Cart item not found with ID = "+cartItemId));
		if(item.getCart().getCartId()!=cart.getCartId()) {
			throw new RuntimeException("Item does not belong to this user's cart");
		}
		Product product=item.getProduct();
		if(product.getStockQuantity()<newQuantity) {
			throw new RuntimeException("Insufficient stocks. Available stock "+product.getStockQuantity());
		}
		item.setQuantity(newQuantity);
		item.setSubTotal(newQuantity*product.getPrice());
		cartItemRepository.save(item);
		recalculateCartTotal(cart);
		Cart savedCart=cartRepository.save(cart);
		return mapToResponseDto(savedCart);
	}
	
	public CartResponseDto removeItemFromCart(int userId,int cartItemId) {
		Cart cart=getOrCreateCart(userId);
		CartItem item=cartItemRepository.findById(cartItemId).orElseThrow(()->new ResourceNotFoundException("Cart item not found with ID = "+cartItemId));
		if(item.getCart().getCartId()!=cart.getCartId()) {
			throw new RuntimeException("Item does not belong to this user's cart");
		}
		cart.getItems().remove(item);
		cartItemRepository.delete(item);
		recalculateCartTotal(cart);
		Cart savedCart=cartRepository.save(cart);
		return mapToResponseDto(savedCart);
		
	}
	
	public void clearEntireCart(int userId) {
		Cart cart=getOrCreateCart(userId);
		cartItemRepository.deleteByCartCartId(cart.getCartId());
		cart.getItems().clear();
		cart.setTotalPrice(0.0);
		cartRepository.save(cart);
	}
	
}
