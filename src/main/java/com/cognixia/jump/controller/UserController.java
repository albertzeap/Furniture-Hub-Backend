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

import com.cognixia.jump.model.User;
import com.cognixia.jump.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/user")
	public ResponseEntity<?> getUsers() throws Exception{
		
		List<User> users = userService.getUsers();
		
		return ResponseEntity.status(200).body(users);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable(value="userId") int userId) throws Exception{
		
		User user = userService.getUserById(userId);
		
		return ResponseEntity.status(200).body(user);
	}
	
	
	
	@PostMapping("/user")
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) throws Exception{
		
		User created = userService.createUser(user);
		
		
		return ResponseEntity.status(201).body(created);
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id) throws Exception{
		
		User deleted = userService.deleteUser(id);
		
		return ResponseEntity.status(200).body(deleted);
		
	}
}
