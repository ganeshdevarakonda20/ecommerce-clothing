package com.ecommerce.controller;

import java.util.List;

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

import com.ecommerce.dto.ProductRequestDto;
import com.ecommerce.dto.ProductResponseDto;
import com.ecommerce.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping
	public ResponseEntity<ProductResponseDto> addProduct(@Valid @RequestBody ProductRequestDto dto ){
		ProductResponseDto createdProduct=productService.addProduct(dto);
		return new ResponseEntity<>(createdProduct,HttpStatus.CREATED);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponseDto> getProductById(@PathVariable int productId){
		ProductResponseDto product=productService.getProductById(productId);
		return ResponseEntity.ok(product);
	}
	
	@GetMapping
	public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
		List<ProductResponseDto> products=productService.getAllProducts();
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<ProductResponseDto>> getProductByCategory(@PathVariable int categoryId){
		List<ProductResponseDto> dtos=productService.findProductsByCategory(categoryId);
		return ResponseEntity.ok(dtos);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<ProductResponseDto>> searchProducts(@RequestParam("keyword") String keyword){
		List<ProductResponseDto> products=productService.searchProducts(keyword);
		return ResponseEntity.ok(products);
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable int productId,@Valid @RequestBody ProductRequestDto dto){
		ProductResponseDto product=productService.updateProduct(productId, dto);
		return ResponseEntity.ok(product);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable int productId) {
		productService.deleteProduct(productId);
		return ResponseEntity.ok("Product is successfully deleted");
	}
	
}