package com.cognixia.jump.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	UserRepo userRepo;

	public List<User> getUsers() {
	
		//TODO
		
		return null;
	}
	
	public User getUserById(int id) {
		// TODO
		
		return null;
	}
	
	public User createUser(User user) {
		
		//TODO
		
		return null;
	}
	
	public User deleteUser(int id) {
		// TODO
		
		return null;
	}
	
}
