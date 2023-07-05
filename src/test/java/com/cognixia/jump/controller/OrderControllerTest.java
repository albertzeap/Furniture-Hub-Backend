package com.cognixia.jump.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
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
import com.cognixia.jump.model.Order;
import com.cognixia.jump.model.Product;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.service.OrderService;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {

	private static final String STARTING_URI = "http://localhost:8080/api";
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private OrderService orderService;
	
	@MockBean
	private PasswordEncoder encoder;
	
	@MockBean
	private JwtRequestFilter jwtRequestFilter;
	
	@InjectMocks
	private OrderController orderController;
	
	@Test
	void testGetOrderById() throws Exception {
		
		
		String uri = STARTING_URI + "/order/1";
		
		User user = new User(1, "Albert", "Paez", "2093287162", "albertzeap", "password", Role.ROLE_ADMIN , true, null);
		Order order = new Order(1, LocalDateTime.now(), user, null);
		when(orderService.getOrderById(1)).thenReturn(order);
		
		 mvc.perform(get(uri)) // perform get request
			.andDo(print()) // print request sent/response given
			.andExpect(status().isOk()) // expect a 200 status code
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
	        .andExpect(jsonPath("$.id").value(order.getId()))
	        .andExpect(jsonPath("$.orderDate").value(order.getOrderDate()))
//	        .andExpect(jsonPath("$.user").value(order.getUser()))
			.andExpect(jsonPath("$.products").value(order.getProducts()));

	        verify(orderService, times(1)).getOrderById(1); // getUsers() from service called once
			verifyNoMoreInteractions(orderService); // after checking above, service is no longer called
	}
	
	@Test
	void testGetUserOrders() throws Exception {
		
		String uri = STARTING_URI + "/order/user/1";
		User user = new User(1, "Albert", "Paez", "2093287162", "albertzeap", "password", Role.ROLE_ADMIN , true, null);
		
		List<Product> products = new ArrayList<>();
		products.add(new Product(1, "Chair", 100, 100.99, "", "A chair", null ));
		products.add(new Product(2, "Desk", 100, 149.99, "", "A desk", null));
		
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(1, LocalDateTime.now(), user, products));
		orders.add(new Order(2, LocalDateTime.now(), user, products));
		
		// Set the products to their order
		products.get(0).setOrder(orders.get(0));
		products.get(1).setOrder(orders.get(0));
		
		products.get(0).setOrder(orders.get(1));
		products.get(1).setOrder(orders.get(1));
		
		
		
		when(orderService.getUserOrders(1)).thenReturn(orders);
		
		mvc.perform(get(uri)) // perform get request
		.andDo(print()) // print request sent/response given
		.andExpect(status().isOk()) // expect a 200 status code
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) // checks content type is json
		.andExpect(jsonPath("$.length()").value(orders.size())) // length of the list matches one above
		
		.andExpect(jsonPath("$[0].id").value(orders.get(0).getId())) 
//        .andExpect(jsonPath("$[0].orderDate").value(orders.get(0).getOrderDate()))
//		.andExpect(jsonPath("$[0].products").value(orders.get(0).getProducts()))
		
		.andExpect(jsonPath("$[1].id").value(orders.get(1).getId())); 
//        .andExpect(jsonPath("$[1].orderDate").value(orders.get(1).getOrderDate()))
//		.andExpect(jsonPath("$[1].products").value(orders.get(1).getProducts()));
		
		verify(orderService, times(1)).getUserOrders(1);
		verifyNoMoreInteractions(orderService);
		
	}
	
	@Test 
	void createOrder() throws Exception {
		
		String uri = STARTING_URI + "/order";
		User user = new User(1, "Albert", "Paez", "2093287162", "albertzeap", "password", Role.ROLE_ADMIN , true, null);
		Order order = new Order(1, LocalDateTime.now(), user, null);
		
		List<Order> orders = new ArrayList<>();
		orders.add(order);
		user.setOrders(orders);
		
		when(orderService.createOrder(Mockito.any(Order.class))).thenReturn(order);
		
		mvc.perform(post(uri).content(order.toJson()) // data sent in body NEEDS to be in JSON format
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
		
		
	}
	
	@Test
	void deleteOrder() throws Exception {
		
		String uri = STARTING_URI + "/order";
		User user = new User(1, "Albert", "Paez", "2093287162", "albertzeap", "password", Role.ROLE_ADMIN , true, null);
		Order order = new Order(1, LocalDateTime.now(), user, null);
		
		List<Order> orders = new ArrayList<>();
		orders.add(order);
		user.setOrders(orders);
		
		when(orderService.deleteOrder(user.getOrders().get(0).getId())).thenReturn(order);
		
		mvc.perform(delete(uri).content(order.toJson())
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	
	
}
