package com.cognixia.jump.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.filter.JwtRequestFilter;
import com.cognixia.jump.model.User;
import com.cognixia.jump.service.UserService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTests {

	private static final String STARTING_URI = "http://localhost:8080/api";
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private PasswordEncoder encoder;
	
	@MockBean
	private JwtRequestFilter jwtRequestFilter;
	
	@InjectMocks
	private UserController userController;
	
	
	@Test
	void testGetUsers() throws Exception {
		
		String uri = STARTING_URI + "/users";
		List<User> allUsers = new ArrayList<>();
		User user = new User(1, "Albert", "Paez", "2093287162", "albertzeap", "password", User.Role.ROLE_ADMIN, true, null);
		User user2 = new User(2, "Andry", "Paez", "2093287162", "albertzeap", "password", User.Role.ROLE_USER, true, null);
		allUsers.add(user);
		allUsers.add(user2);
		
		when(userService.getUsers()).thenReturn(allUsers);
				
				mvc.perform(get(uri)) // perform get request
				.andDo(print()) // print request sent/response given
				.andExpect(status().isOk()) // expect a 200 status code
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) // checks content type is json
				.andExpect(jsonPath("$.length()").value(allUsers.size())) // length of the list matches one above
				
				.andExpect(jsonPath("$[0].id").value(user.getId())) 
		        .andExpect(jsonPath("$[0].firstName").value(user.getFirstName()))
				.andExpect(jsonPath("$[0].lastName").value(user.getLastName()))
				.andExpect(jsonPath("$[0].phoneNumber").value(user.getPhoneNumber()))
				.andExpect(jsonPath("$[0].username").value(user.getUsername()))
				.andExpect(jsonPath("$[0].password").value(user.getPassword()))
				.andExpect(jsonPath("$[0].role").value(user.getRole()))
				.andExpect(jsonPath("$[0].enabled").value(user.isEnabled()))
				
				.andExpect(jsonPath("$[1].id").value(user.getId())) 
		        .andExpect(jsonPath("$[1].firstName").value(user.getFirstName()))
				.andExpect(jsonPath("$[1].lastName").value(user.getLastName()))
				.andExpect(jsonPath("$[1].phoneNumber").value(user.getPhoneNumber()))
				.andExpect(jsonPath("$[1].username").value(user.getUsername()))
				.andExpect(jsonPath("$[1].password").value(user.getPassword()))
				.andExpect(jsonPath("$[1].role").value(user.getRole()))
				.andExpect(jsonPath("$[1].enabled").value(user.isEnabled()));
	}
	
	
	@Test
	void testGetUserById() throws Exception {
		
		
		String uri = STARTING_URI + "/user/1";
		User user = new User(1, "Albert", "Paez", "2093287162", "albertzeap", "password", User.Role.ROLE_ADMIN, true, null);
		when(userService.getUserById(1)).thenReturn(user);
		
		 mvc.perform(get(uri)) // perform get request
			.andDo(print()) // print request sent/response given
			.andExpect(status().isOk()) // expect a 200 status code
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
	        .andExpect(jsonPath("$.id").value(user.getId())) 
	        .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
			.andExpect(jsonPath("$.lastName").value(user.getLastName()))
			.andExpect(jsonPath("$.phoneNumber").value(user.getPhoneNumber()))
			.andExpect(jsonPath("$.username").value(user.getUsername()))
			.andExpect(jsonPath("$.password").value(user.getPassword()))
			 .andExpect(jsonPath("$.role").value(user.getRole()))
			.andExpect(jsonPath("$.enabled").value(user.isEnabled()));

	        verify(userService, times(1)).getUserById(1); // getUsers() from service called once
			verifyNoMoreInteractions(userService); // after checking above, service is no longer called
	}
	
	@Test
	void testCreateUser() throws Exception {
		
		String uri = STARTING_URI + "/user";
		
		User user = new User(1, "Albert", "Paez", "2093287162", "albertzeap", "password", User.Role.ROLE_ADMIN, true, null);
		
		when(userService.createUser(Mockito.any(User.class))).thenReturn(user);
		
		mvc.perform(post(uri).content(user.toJson()) // data sent in body NEEDS to be in JSON format
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
		
	}
	
	@Test
	void testInvalidCreateUser() throws Exception{
		
		// ARRANGE
		String uri = STARTING_URI + "/user";
		
		User user = new User(1, "Albert", "Paez", "2093287162", "albertzeap", "password", User.Role.ROLE_ADMIN, true, null);
		
		when(userService.createUser(Mockito.any(User.class))).thenReturn(user);
		
		mvc.perform(post(uri).content(user.toJson()) 
			.contentType(MediaType.APPLICATION_JSON_VALUE))
	        .andDo(print())
	        .andExpect(status().isBadRequest());
		
	}
	
	
}
