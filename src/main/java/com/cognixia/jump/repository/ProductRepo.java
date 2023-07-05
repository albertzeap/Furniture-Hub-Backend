package com.cognixia.jump.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{

	public Optional<Product> findByProductName(String productName);
	
	@Transactional
    @Modifying
    @Query(value = "UPDATE product SET product.productName = :productName, product.stock = :stock, product.price = :price, product.image = :image, product.description = :desc  WHERE product.id = :productId", nativeQuery = true)
    public int updateProduct(
    							@Param(value="productName") String productName, @Param(value="stock") int stock,
    							@Param(value="price") double price, @Param(value="image") String image, 
    							@Param(value="desc")String desc,   @Param(value="productId") int productId
    						);
	
	

		
}
