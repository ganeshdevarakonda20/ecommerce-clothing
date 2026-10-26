package com.ecommerce.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name="payments")
public class Payment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="payment_id")
	private int paymentId;
	
	@OneToOne
	@JoinColumn(name="order_id",nullable = false,unique = true)
	private Order order;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable = false)
	private User user;
	
	@Column(name="payment_method",nullable = false)
	private String paymentMethod;
	
	@Column(name="amount",nullable = false)
	private double amount;
	
	@Enumerated(EnumType.STRING)
	@Column(name="payment_status",nullable = false)
	private PaymentStatus paymentStatus;
	
	@Column(name="transaction_id",unique = true)
	private String transactionId;
	
	@Column(name="payment_date",nullable = false)
	private LocalDateTime paymentDate;
	
	public Payment() {
		
	}

	public Payment(Order order, User user, String paymentMethod, double amount, PaymentStatus paymentStatus,
			String transactionId, LocalDateTime paymentDate) {
		super();
		this.order = order;
		this.user = user;
		this.paymentMethod = paymentMethod;
		this.amount = amount;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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
