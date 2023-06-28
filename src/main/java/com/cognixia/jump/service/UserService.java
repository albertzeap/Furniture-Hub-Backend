package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	UserRepo userRepo;

	public List<User> getUsers() throws Exception {
	
		List<User> users = userRepo.findAll();
		
		return users;
	}
	
	public User getUserById(int id) throws Exception {
		
		Optional<User> user = userRepo.findById(id);
		
		if(user.isEmpty()) {
			throw new Exception();
		}
		
		return user.get();
	}
	
	public User createUser(User user) throws Exception {
		
		Optional<User> exists = userRepo.findByUsername(user.getUsername());
		if(exists.isPresent()) {
			throw new Exception();
		}
		
		// Set id to null for auto increment
		user.setId(null);
		
		User created = userRepo.save(user);
		
		return created;
	}
	
	public User deleteUser(int id) throws Exception {
		
		Optional<User> exists = userRepo.findById(id);
		if(exists.isEmpty()) {
			throw new Exception();
		}
		
		User deleted = exists.get();
		userRepo.delete(exists.get());
		
		return deleted;
	}
	
}
