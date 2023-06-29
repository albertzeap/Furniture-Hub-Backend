package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

	@Query(value="SELECT * FROM Orders WHERE user_id = :userId", nativeQuery=true)
	public List<Order> findUserOrders(@Param(value="userId")int userId);
	
}
