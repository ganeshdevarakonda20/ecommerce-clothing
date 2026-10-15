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
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.CategoryRequestDto;
import com.ecommerce.dto.CategoryResponseDto;
import com.ecommerce.service.CategoryService;
import java.util.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping
	public ResponseEntity<CategoryResponseDto> addCategory(@Valid @RequestBody CategoryRequestDto dto){
		CategoryResponseDto response=categoryService.addCategory(dto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable int categoryId){
		CategoryResponseDto response=categoryService.getCategoryById(categoryId);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping
	public ResponseEntity<List<CategoryResponseDto>> getAllCategories(){
		List<CategoryResponseDto> dtos=categoryService.getAllCategories();
		return ResponseEntity.ok(dtos);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable int CategoryId,@Valid @RequestBody CategoryRequestDto dto) {
		CategoryResponseDto response=categoryService.updateCategory(CategoryId, dto);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable int categoryId) {
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok("Category deleted successfully");
	}
	
}
