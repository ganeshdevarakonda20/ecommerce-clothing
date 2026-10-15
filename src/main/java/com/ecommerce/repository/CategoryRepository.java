package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;

import com.ecommerce.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	Optional<Category> findByCategoryName(String CategoryName);
	boolean existsByCategoryName(String categoryName);
	
}
