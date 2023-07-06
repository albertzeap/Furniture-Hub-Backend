package com.cognixia.jump.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.Order;
import com.cognixia.jump.model.Product;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.OrderRepo;
import com.cognixia.jump.repository.ProductRepo;
import com.cognixia.jump.repository.UserRepo;

@Service
public class OrderService {
	
	@Autowired
	OrderRepo orderRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ProductRepo productRepo;

	public List<Order> getAllOrders() {
		
		List<Order> orders = orderRepo.findAll();
		
		return orders;
	}

	public Order getOrderById(int id) throws Exception {
		
		Optional<Order> order = orderRepo.findById(id);
		
		if(order.isEmpty()) {
			throw new Exception();
		}
		
		return order.get();
	}

	public List<Order> getUserOrders(int userId) {
		
		List<Order> userOrders = orderRepo.findUserOrders(userId);
		
		return userOrders;
	}

	public Order deleteOrder(int id) throws Exception {
		Optional<Order> found = orderRepo.findById(id);
		
		if(found.isEmpty()) {
			throw new Exception();
		}
		
		Order deleted = found.get();
		orderRepo.delete(found.get());
		
		return deleted;
	}

	public Order createOrder(Order order) throws Exception {
		
		Optional<User> user = userRepo.findById(order.getUser().getId());
		// If user does not exists
		if(user.isEmpty()) {
			throw new Exception();
		}
		
		// If item is out of stock
		List<Product> products = order.getProducts();
		for(Product prod : products) {
			
			// Check if products exist
			Optional<Product> exists = productRepo.findById(prod.getId());
			if (exists.isEmpty()) {
			    throw new Exception("Product not found");
			}
			
			// Check the stock level and update it
			if(prod.getStock() > 0) {
				prod.setStock(prod.getStock() - 1);
				productRepo.updateProduct(
											prod.getProductName(), prod.getStock(), 
											prod.getPrice(), prod.getImage(), 
											prod.getDescription(), prod.getId()
										 );
			} else
			{
				throw new Exception();
			}
			
		}
		
		order.setId(null);
		order.setProducts(products);
		order.setOrderDate(LocalDateTime.now());
		
		Order created = orderRepo.save(order);
		
		
		return created;
	}

}
