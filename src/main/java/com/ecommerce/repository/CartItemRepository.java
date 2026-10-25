package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem,Integer>{
	
	Optional<CartItem> findByCartCartIdAndProductProductId(int cartId,int productId);
	void deleteByCartCartId(int cartId);
	
}
