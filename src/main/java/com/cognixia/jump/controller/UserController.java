package com.cognixia.jump.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.repository.UserRepo;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserRepo userRepo;
	
	@GetMapping("/user")
	public ResponseEntity<?> getUsers(){
		
		//TODO:
		
		return ResponseEntity.status(200).body(null);
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> createUser(){
		
		//TODO
		
		
		return ResponseEntity.status(201).body(null);
	}
}
