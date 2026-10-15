package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import java.util.*;
import com.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	List<Product> findByCategoryCategoryId(int categoryId);
	List<Product> findByBrand(String brand);
	List<Product> findByNameContainingIgnoreCase(String keyword);
	
}
