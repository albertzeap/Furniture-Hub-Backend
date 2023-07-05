package com.cognixia.jump.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime orderDate;
	

	@ManyToOne
	@JoinColumn( name = "user_id", referencedColumnName = "id")
	private User user;	

	@JsonProperty( access = Access.WRITE_ONLY )
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//	@JoinColumn(name=  "product_id", referencedColumnName = "id")
	private List<Product> products;

	public Order() {
		
	}
	
	public Order(Integer id, LocalDateTime orderDate, User user, List<Product> products) {
		super();
		this.id = id;
		this.orderDate = orderDate;
		this.user = user;
		this.products = products;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderDate=" + orderDate + ", user=" + user + ", products=" + products + "]";
	}

	public String toJson() {
		// TODO Auto-generated method stub
		return "{\"id\" : " + id 
				+ ", \"orderDate\" : \"" + orderDate + "\""
				+ ", \"products\" : \"" + products + "\"}";
	}
	
	
	
	
	

}
