package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.ecommerce.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{
	
	Optional<Payment> findByOrder_OrderId(int orderId);
    List<Payment> findByUser_UserId(int userId);
    Optional<Payment> findByTransactionId(String transactionId);
	
}
