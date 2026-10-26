package com.ecommerce.dto;

import jakarta.validation.constraints.NotNull;

public class PaymentRequestDto {
	
	@NotNull(message="Order ID is required")
	private Integer orderId;
	private String paymentMethod;
	private String transactionId;
	
	public PaymentRequestDto() {
		
	}

	public PaymentRequestDto(@NotNull(message = "Order ID is required") Integer orderId, String paymentMethod,
			String transactionId) {
		super();
		this.orderId = orderId;
		this.paymentMethod = paymentMethod;
		this.transactionId = transactionId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	
	
}
