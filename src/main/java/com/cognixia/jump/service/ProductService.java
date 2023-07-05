package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.Product;
import com.cognixia.jump.repository.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	ProductRepo productRepo;

	public List<Product> getProducts() {
		List<Product> products = productRepo.findAll();
		return products;
	}

	public Product getProductById(int id) throws Exception {
		Optional<Product> product = productRepo.findById(id);
		
		if(product.isEmpty()) {
			throw new Exception();
		}
		
		return product.get();
	}

	public Product createProduct(Product product) throws Exception {
		
		
		Optional<Product> exists = productRepo.findByProductName(product.getProductName());
		
		if(exists.isPresent()) {
			throw new Exception();
		}
		
		product.setId(null);
		
		Product created = productRepo.save(product);
		
		return created;
	}

	public Product deleteProduct(int id) throws Exception {
		
		Optional<Product> exists = productRepo.findById(id);
		
		if(exists.isEmpty()) {
			throw new Exception();
		}
		
		Product deleted = exists.get();
		productRepo.delete(exists.get());
		
		return deleted;
	}

	public Product updateProduct(@Valid Product product) throws Exception {
		
		Optional<Product> exists = productRepo.findById(product.getId());
		
		if(exists.isEmpty()) {
			throw new Exception();
		}
		
		int updated = productRepo.updateProduct(
													product.getProductName(), product.getStock(), 
													product.getPrice(), product.getImage(), 
													product.getDescription(), product.getId()
												);
		
		if(updated < 1) {
			throw new Exception();
		}
		
		
		
		return exists.get();
	}

}
