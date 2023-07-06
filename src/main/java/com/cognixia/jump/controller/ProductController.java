package com.cognixia.jump.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Product;
import com.cognixia.jump.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/product")
	public ResponseEntity<?> getProducts(){
		
		List<Product> products = productService.getProducts();
		
		return ResponseEntity.status(200).body(products);
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProductById(@PathVariable int id) throws Exception {
		
		Product product = productService.getProductById(id);
		
		return ResponseEntity.status(200).body(product);
	}
	
	
	@PostMapping("/product")
	public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) throws Exception {
		
			Product created = productService.createProduct(product);
			
			return ResponseEntity.status(201).body(product);
	}
	
	@PutMapping("/product")
	public ResponseEntity<?> updateProduct(@Valid @RequestBody Product product) throws Exception{
		
		
		Product updated = productService.updateProduct(product);
		
		return ResponseEntity.status(200).body(updated);
		
	}
	
	@DeleteMapping("/product")
	public ResponseEntity<?> deleteProduct(@PathVariable int id) throws Exception{
		
		Product deleted = productService.deleteProduct(id);
		
		return ResponseEntity.status(200).body(deleted);
	}
	
	
	
	
	
}
