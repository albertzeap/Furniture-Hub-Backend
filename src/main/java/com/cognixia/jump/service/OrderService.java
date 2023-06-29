package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.Order;
import com.cognixia.jump.repository.OrderRepo;

@Service
public class OrderService {
	
	@Autowired
	OrderRepo orderRepo;

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

	public Order deleteOrder(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
