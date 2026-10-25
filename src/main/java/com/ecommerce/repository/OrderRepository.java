package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;

import com.ecommerce.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Integer>{
	List<Order> findByUserUserIdOrderByOrderDateDesc(int userId);
}
