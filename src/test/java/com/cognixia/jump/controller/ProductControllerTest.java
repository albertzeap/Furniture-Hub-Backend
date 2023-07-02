package com.cognixia.jump.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import com.cognixia.jump.model.Student;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.service.ProductService;


@WebMvcTest(Product.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {
	
	private static final String STARTING_URI = "http://localhost:8080/api";
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ProductService productService;
	
	@MockBean
	private PasswordEncoder encoder;
	
	@MockBean
	private JwtRequestFilter jwtRequestFilter;
	
	@InjectMocks
	private ProductController productController;
	
	@Test 
	void testGetProducts() throws Exception {
String uri = STARTING_URI + "/product";
		
		List<Product> products = new ArrayList<>();
			
		
		products.add(new Product(1, "Chair", 100, 100.99, "", "A chair", null ));
		products.add(new Product(2, "Desk", 100, 149.99, "", "A desk", null));
		
		when(productService.getProducts()).thenReturn(products);
		
		mvc.perform(get(uri)) // perform get request
		.andDo(print()) // print request sent/response given
		.andExpect(status().isOk()) // expect a 200 status code
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) // checks content type is json
		.andExpect(jsonPath("$.length()").value(products.size())) // length of the list matches one above
		
		.andExpect(jsonPath("$[0].id").value(products.get(0).getId())) 
        .andExpect(jsonPath("$[0].productName").value(products.get(0).getProductName()))
		.andExpect(jsonPath("$[0].stock").value(products.get(0).getStock()))
		.andExpect(jsonPath("$[0].price").value(products.get(0).getPrice()))
		.andExpect(jsonPath("$[0].image").value(products.get(0).getImage()))
		.andExpect(jsonPath("$[0].description").value(products.get(0).getDescription()))
		
		.andExpect(jsonPath("$[1].id").value(products.get(1).getId())) 
		.andExpect(jsonPath("$[1].productName").value(products.get(1).getProductName()))
		.andExpect(jsonPath("$[1].stock").value(products.get(1).getStock()))
		.andExpect(jsonPath("$[1].price").value(products.get(1).getPrice()))
		.andExpect(jsonPath("$[1].image").value(products.get(1).getImage()))
		.andExpect(jsonPath("$[1].description").value(products.get(1).getDescription()));
		
		
		
		verify(productService, times(1)).getProducts();
		verifyNoMoreInteractions(productService);
	}
	
	@Test
	void testGetProductById() throws Exception {
		String uri = STARTING_URI + "/order/1";
		
		Product product = new Product(1, "Chair", 100, 100.99, "", "A chair", null);
		when(productService.getProductById(1)).thenReturn(product);
		
		 mvc.perform(get(uri)) // perform get request
			.andDo(print()) // print request sent/response given
			.andExpect(status().isOk()) // expect a 200 status code
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.id").value(product.getId())) 
			.andExpect(jsonPath("$.productName").value(product.getProductName()))
			.andExpect(jsonPath("$.stock").value(product.getStock()))
			.andExpect(jsonPath("$.price").value(product.getPrice()))
			.andExpect(jsonPath("$.image").value(product.getImage()))
			.andExpect(jsonPath("$.description").value(product.getDescription()));
	        verify(productService, times(1)).getProductById(1); // getUsers() from service called once
			verifyNoMoreInteractions(productService); // after checking above, service is no longer called
	}
	
	@Test
	void testCreateProduct() throws Exception{
		String uri = STARTING_URI + "/product";
		Product product = new Product(1, "Chair", 100, 100.99, "", "A chair", null);
		
		when(productService.createProduct(Mockito.any(Product.class)));
		
		mvc.perform(post(uri).content(product.toJson()) // data sent in body NEEDS to be in JSON format
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}
	
	@Test
	void testUpdateProduct() throws Exception {
		String uri = STARTING_URI + "/product";

		Product product = new Product(1, "Chair", 100, 100.99, "", "A chair", null);

		when(productService.updateProduct(Mockito.any(Product.class))).thenReturn(product);

		mvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(product.toJson()))
				.andExpect(status().isOk());

		verify(productService, times(1)).updateProduct(Mockito.any(Product.class));
		verifyNoMoreInteractions(productService);
	}
	
	@Test void testDeleteProduct() throws Exception {
		String uri = STARTING_URI + "/product";
		Product product = new Product(1, "Chair", 100, 100.99, "", "A chair", null);
		
		when(productService.deleteProduct(1)).thenReturn(product);
		
		mvc.perform(delete(uri).content(product.toJson())
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
}
