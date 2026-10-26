package com.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.PaymentRequestDto;
import com.ecommerce.dto.PaymentResponseDto;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.entity.Payment;
import com.ecommerce.entity.PaymentStatus;
import com.ecommerce.exception.DuplicateResourceException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.PaymentRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentService {
	
	@Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    public PaymentResponseDto processPayment(PaymentRequestDto dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID = " + dto.getOrderId()));
        if (order.getStatus() == OrderStatus.Cancelled) {
            throw new RuntimeException("Cannot process payment for a cancelled order.");
        }
        Optional<Payment> existingOpt = paymentRepository.findByOrder_OrderId(order.getOrderId());
        if (existingOpt.isPresent() && existingOpt.get().getPaymentStatus() == PaymentStatus.Completed) {
            throw new DuplicateResourceException("Payment is already completed for Order ID = " + order.getOrderId());
        }
        
        String method = (dto.getPaymentMethod() != null && !dto.getPaymentMethod().isBlank())
                ? dto.getPaymentMethod()
                : (order.getPaymentMethod() != null ? order.getPaymentMethod() : "CARD");
        PaymentStatus status = method.equalsIgnoreCase("COD") ? PaymentStatus.Pending : PaymentStatus.Completed;
        
        if (dto.getTransactionId() != null && paymentRepository.findByTransactionId(dto.getTransactionId()).isPresent()) {
            throw new DuplicateResourceException("Transaction ID already exists: " + dto.getTransactionId());
        }
        
        String txnId = (dto.getTransactionId() != null && !dto.getTransactionId().isBlank())
                ? dto.getTransactionId()
                : "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
        Payment payment = existingOpt.orElse(new Payment());
        payment.setOrder(order);
        payment.setUser(order.getUser());
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(method);
        payment.setPaymentStatus(status);
        payment.setTransactionId(txnId);
        payment.setPaymentDate(LocalDateTime.now());
        Payment saved = paymentRepository.save(payment);
        if (status == PaymentStatus.Completed) {
            order.setStatus(OrderStatus.Confirmed);
            orderRepository.save(order);
        }
        return mapToDto(saved);
    }
    public PaymentResponseDto getPaymentById(int paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID = " + paymentId));
        return mapToDto(payment);
    }
    public PaymentResponseDto getPaymentByOrderId(int orderId) {
        Payment payment = paymentRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for Order ID = " + orderId));
        return mapToDto(payment);
    }
    public List<PaymentResponseDto> getPaymentsByUserId(int userId) {
        return paymentRepository.findByUser_UserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    public List<PaymentResponseDto> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    public PaymentResponseDto refundPayment(int paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID = " + paymentId));
        if (payment.getPaymentStatus() == PaymentStatus.Refunded) {
            throw new RuntimeException("Payment is already refunded.");
        }
        if (payment.getPaymentStatus() != PaymentStatus.Completed) {
            throw new RuntimeException("Only completed payments can be refunded.");
        }
        payment.setPaymentStatus(PaymentStatus.Refunded);
        Payment updated = paymentRepository.save(payment);
        // Cancel order and restore product inventory
        if (payment.getOrder().getStatus() != OrderStatus.Cancelled) {
            orderService.cancelOrder(payment.getOrder().getOrderId());
        }
        return mapToDto(updated);
    }
    private PaymentResponseDto mapToDto(Payment payment) {
        return new PaymentResponseDto(
                payment.getPaymentId(),
                payment.getOrder().getOrderId(),
                payment.getUser().getUserId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getTransactionId(),
                payment.getPaymentDate()
        );
    }
	
}
