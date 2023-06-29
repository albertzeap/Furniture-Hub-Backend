package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Order;
import com.cognixia.jump.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@GetMapping("/order")
	public ResponseEntity<?> getAllOrders () {
		
		List<Order> orders = orderService.getAllOrders();
		
		return ResponseEntity.status(200).body(orders);
	}
	
	@GetMapping("/order/{id}")
	public ResponseEntity<?> getOrderById(@PathVariable int id) {
		
		Order order = orderService.getOrderById(id);
		
		return ResponseEntity.status(200).body(order);
	}
	
	@GetMapping("/order/user/{id}")
	public ResponseEntity<?> getUserOrders(@PathVariable int userId){
		
		List<Order> userOrders = orderService.getUserOrders(userId);
		
		return ResponseEntity.status(200).body(userOrders);
		
	}
	
	@PostMapping("/order")
	public ResponseEntity<?> createOrder(@RequestBody Order order){
		
//		Order created = orderService.createOrder(order);
		
		return ResponseEntity.status(201).body(null);
	}
	
	@DeleteMapping("/order/{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable int id){
		
		Order deleted = orderService.deleteOrder(id);
		
		return ResponseEntity.status(200).body(deleted);
	}

}
