package com.ecommerce.dto;

import java.time.LocalDateTime;

import com.ecommerce.entity.PaymentStatus;

public class PaymentResponseDto {
	
	private int paymentId;
	private int orderId;
	private int userId;
	private double amount;
	private String paymentMethod;
	private PaymentStatus paymentStatus;
	private String transactionId;
	private LocalDateTime paymentDate;
	
	public PaymentResponseDto() {
		
	}

	public PaymentResponseDto(int paymentId, int orderId, int userId, double amount, String paymentMethod,
			PaymentStatus paymentStatus, String transactionId, LocalDateTime paymentDate) {
		super();
		this.paymentId = paymentId;
		this.orderId = orderId;
		this.userId = userId;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.transactionId = transactionId;
		this.paymentDate = paymentDate;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	
	
}
