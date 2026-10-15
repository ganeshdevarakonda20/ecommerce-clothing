package com.ecommerce.service;

import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.CategoryRequestDto;
import com.ecommerce.dto.CategoryResponseDto;
import com.ecommerce.entity.Category;
import com.ecommerce.exception.DuplicateResourceException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UserAlreadyExistsException;
import com.ecommerce.repository.CategoryRepository;
import java.util.*;

@Service
public class CategoryService {
	
	private final UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	CategoryService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public CategoryResponseDto mapToResponseDto(Category category){
		CategoryResponseDto crd=new CategoryResponseDto();
		crd.setCategoryId(category.getCategoryId());
		crd.setCategoryName(category.getCategoryName());
		crd.setDescription(category.getDescription());
		return crd;
		
	}
	
	public CategoryResponseDto addCategory(CategoryRequestDto dto) {
		if(categoryRepository.existsByCategoryName(dto.getCategoryName())) {
			throw new DuplicateResourceException("Category already exists : "+dto.getCategoryName());
		}
		Category category=new Category();
		category.setCategoryName(dto.getCategoryName());
		category.setDescription(dto.getDescription());
		Category savedCategory=categoryRepository.save(category);
		return mapToResponseDto(savedCategory);
	}
	
	public CategoryResponseDto getCategoryById(int categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found with ID = "+categoryId));
		return mapToResponseDto(category);
	}
	
	public List<CategoryResponseDto>getAllCategories() {
		List<Category> categories=categoryRepository.findAll();
		List<CategoryResponseDto> dtos=new ArrayList<>();
		for(Category category : categories) {
			dtos.add(mapToResponseDto(category));
		}
		return dtos;
	}
	
	public CategoryResponseDto updateCategory(int categoryId,CategoryRequestDto dto) {
		 Category existingCategory=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found with ID = "+categoryId));
		 if(!existingCategory.getCategoryName().equalsIgnoreCase(dto.getCategoryName()) && categoryRepository.existsByCategoryName(dto.getCategoryName())) {
			 throw new DuplicateResourceException("Cateegory already exists with ID = "+categoryId);
		 }
		 existingCategory.setCategoryName(dto.getCategoryName());
		 existingCategory.setDescription(dto.getDescription());
		 Category savedCategory=categoryRepository.save(existingCategory);
		 return mapToResponseDto(savedCategory);
	}
	
	public void deleteCategory(int categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found with ID = "+categoryId));
		categoryRepository.delete(category);
	}
	
}
