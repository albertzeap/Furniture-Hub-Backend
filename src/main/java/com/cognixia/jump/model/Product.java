package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Product implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	@Column(nullable = false)
	private String productName;
	
	@NotEmpty
	@Min(value=0)
	private Integer stock;
	
	@Min(value=0)
	private double price;
	
	@NotBlank
	private String image;
	
	@NotBlank
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order order;
	
	public Product() {
		
	}

	public Product(Integer id, @NotBlank String productName, @NotEmpty @Min(0) Integer stock, @Min(0) double price,
			@NotBlank String image, @NotBlank String description, Order order) {
		super();
		this.id = id;
		this.productName = productName;
		this.stock = stock;
		this.price = price;
		this.image = image;
		this.description = description;
		this.order = order;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", stock=" + stock + ", price=" + price
				+ ", image=" + image + ", description=" + description + ", order=" + order + "]";
	}
	
	
	
	
	
	
	
}
