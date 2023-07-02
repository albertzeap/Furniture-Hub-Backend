package com.cognixia.jump.controller;

import java.util.List;

import javax.validation.Valid;

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
	public ResponseEntity<?> getOrderById(@PathVariable int id) throws Exception {
		
		Order order = orderService.getOrderById(id);
		
		return ResponseEntity.status(200).body(order);
	}
	
	@GetMapping("/order/user/{userId}")
	public ResponseEntity<?> getUserOrders(@PathVariable(value="userId") int userId){
		
		List<Order> userOrders = orderService.getUserOrders(userId);
		
		return ResponseEntity.status(200).body(userOrders);
		
	}
	
	@PostMapping("/order")
	public ResponseEntity<?> createOrder(@Valid @RequestBody Order order) throws Exception{
		
		Order created = orderService.createOrder(order);
		
		return ResponseEntity.status(201).body(created);
	}
	
	@DeleteMapping("/order/{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable int id){
		
		Order deleted = orderService.deleteOrder(id);
		
		return ResponseEntity.status(200).body(deleted);
	}

}
