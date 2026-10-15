package com.ecommerce.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.dto.CategoryResponseDto;
import com.ecommerce.dto.ProductRequestDto;
import com.ecommerce.dto.ProductResponseDto;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;

public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	private CategoryRepository categoryRepository;
	
	public ProductResponseDto mapToResponseDto(Product product) {
		
		ProductResponseDto dto=new ProductResponseDto();
		dto.setProductId(product.getProductId());
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		dto.setStockQuantity(product.getStockQuantity());
		dto.setBrand(product.getBrand());
		dto.setSize(product.getSize());
		dto.setColor(product.getColor());
		dto.setImaegUrl(product.getImageUrl());
		
		if(product.getCategory()!=null) {
			CategoryResponseDto crd=new CategoryResponseDto();
			crd.setCategoryId(product.getCategory().getCategoryId());
			crd.setCategoryName(product.getCategory().getCategoryName());
			crd.setDescription(product.getCategory().getDescription());
			dto.setCategory(crd);
		}
		return dto;
		
	}
	
	public ProductResponseDto addProduct(ProductRequestDto dto) {
		Category category=categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Category not found with ID = "+dto.getCategoryId()));
		Product product=new Product();
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setStockQuantity(dto.getStockQuanitiy());
		product.setBrand(dto.getBrand());
		product.setSize(dto.getSize());
		product.setColor(dto.getColor());
		product.setImageUrl(dto.getImageUrl());
		product.setCategory(category);
		
		Product savedProduct=productRepository.save(product);
		return mapToResponseDto(savedProduct);
	}
	
	public ProductResponseDto getProductById(int productId) {
		Product product=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found with ID = "+productId));
		return mapToResponseDto(product);
		
	}
	
	public List<ProductResponseDto> getAllProducts(){
		List<Product> products=productRepository.findAll();
		List<ProductResponseDto> dtos=new ArrayList<>();
		for(Product product : products) {
			dtos.add(mapToResponseDto(product));
		}
		return dtos;
	}
	
	
	
}
