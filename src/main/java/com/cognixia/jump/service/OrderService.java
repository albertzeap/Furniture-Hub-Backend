package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.Order;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.OrderRepo;
import com.cognixia.jump.repository.UserRepo;

@Service
public class OrderService {
	
	@Autowired
	OrderRepo orderRepo;
	
	@Autowired
	UserRepo userRepo;

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
		
//		Optional<User> user = userRepo.findById(order.getUser().getId());
//		if(user.isEmpty()) {
//			throw new Exception();
//		}
		
		order.setId(null);
		
		Order created = orderRepo.save(order);
		
		
		return created;
	}

}
