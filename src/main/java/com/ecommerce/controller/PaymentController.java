package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.PaymentRequestDto;
import com.ecommerce.dto.PaymentResponseDto;
import com.ecommerce.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	
	@Autowired
    private PaymentService paymentService;
    @PostMapping("/process")
    public ResponseEntity<PaymentResponseDto> processPayment(@Valid @RequestBody PaymentRequestDto dto) {
        PaymentResponseDto response = paymentService.processPayment(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable int paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDto> getPaymentByOrderId(@PathVariable int orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(paymentService.getPaymentsByUserId(userId));
    }
    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }
    @PutMapping("/{paymentId}/refund")
    public ResponseEntity<PaymentResponseDto> refundPayment(@PathVariable int paymentId) {
        return ResponseEntity.ok(paymentService.refundPayment(paymentId));
    }
	
}
