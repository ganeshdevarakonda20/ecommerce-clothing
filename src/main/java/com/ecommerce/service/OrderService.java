package com.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dto.OrderItemResponseDto;
import com.ecommerce.dto.OrderRequestDto;
import com.ecommerce.dto.OrderResponseDto;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

@Service
@Transactional
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderResponseDto mapToOrderResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUser().getUserId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus().name());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setCreatedDate(order.getCreatedDate());
        dto.setLastLoginDate(order.getLastLoginDate());

        List<OrderItemResponseDto> itemDtos = new ArrayList<>();
        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                OrderItemResponseDto itemDto = new OrderItemResponseDto();
                itemDto.setOrderItemId(item.getOrderItemId());
                itemDto.setProductId(item.getProduct().getProductId());
                itemDto.setProductName(item.getProduct().getName());
                itemDto.setPrice(item.getPrice());
                itemDto.setQuantity(item.getQuantity());
                itemDto.setSubTotal(item.getQuantity() * item.getPrice());
                itemDto.setImageUrl(item.getProduct().getImageUrl());
                itemDtos.add(itemDto);
            }
        }

        dto.setItems(itemDtos);
        return dto;
    }

    public OrderResponseDto placeOrder(int userId, OrderRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID = " + userId));
        Cart cart = cartRepository.findByUserUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user ID = " + userId));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty. Please add products before placing an order.");
        }

        LocalDateTime now = LocalDateTime.now();
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(now);
        order.setCreatedDate(now);
        order.setLastLoginDate(now);
        order.setStatus(OrderStatus.Confirmed);
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setTotalAmount(cart.getTotalPrice());

        
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();
            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName() 
                        + ". Available stock: " + product.getStockQuantity());
            }

            
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        cartItemRepository.deleteByCartCartId(cart.getCartId());
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        return mapToOrderResponseDto(savedOrder);
    }

    public OrderResponseDto getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID = " + orderId));
        return mapToOrderResponseDto(order);
    }

    public List<OrderResponseDto> getOrdersByUser(int userId) {
        List<Order> orders = orderRepository.findByUserUserIdOrderByOrderDateDesc(userId);
        List<OrderResponseDto> dtos = new ArrayList<>();
        for (Order order : orders) {
            dtos.add(mapToOrderResponseDto(order));
        }
        return dtos;
    }


    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDto> dtos = new ArrayList<>();
        for (Order order : orders) {
            dtos.add(mapToOrderResponseDto(order));
        }
        return dtos;
    }


    public OrderResponseDto updateOrderStatus(int orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID = " + orderId));
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return mapToOrderResponseDto(updatedOrder);
    }

    public OrderResponseDto cancelOrder(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID = " + orderId));

        if (order.getStatus() == OrderStatus.Delivered) {
            throw new RuntimeException("Cannot cancel an order that has already been delivered.");
        }
        if (order.getStatus() == OrderStatus.Cancelled) {
            throw new RuntimeException("Order is already cancelled.");
        }

        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus(OrderStatus.Cancelled);
        Order updatedOrder = orderRepository.save(order);
        return mapToOrderResponseDto(updatedOrder);
    }
}
