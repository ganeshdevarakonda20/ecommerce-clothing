package com.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;

public class OrderRequestDto {
    
    @NotBlank(message = "Payment method is required (e.g. CARD, UPI, COD)")
    private String paymentMethod;
    
    private String shippingAddress;

    public OrderRequestDto() {
    }

    public OrderRequestDto(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderRequestDto(String paymentMethod, String shippingAddress) {
        this.paymentMethod = paymentMethod;
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
