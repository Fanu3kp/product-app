package com.productapp.backend.service;

import com.productapp.backend.entity.Order;
import com.productapp.backend.entity.Product;
import com.productapp.backend.repository.OrderRepository;
import com.productapp.backend.repository.ProductRepository;
import com.productapp.backend.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.email.from:noreply@productapp.com}")
    private String emailFrom;

    @Value("${app.order.email:fanuelotuya2@gmail.com}")
    private String orderEmail;

    @Transactional
    public Order placeOrder(OrderRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setStock(product.getStock() - request.getQuantity());
        productRepository.save(product);

        Order order = new Order();
        order.setProductId(product.getId());
        order.setProductName(product.getName());
        order.setQuantity(request.getQuantity());
        order.setCustomerEmail(request.getCustomerEmail());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        Order savedOrder = orderRepository.save(order);

        sendOrderEmail(savedOrder);

        return savedOrder;
    }

    private void sendOrderEmail(Order order) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(orderEmail);
            message.setSubject("New Order Received - Product App");
            message.setText("Dear Admin,\n\nA new order has been placed.\n\n"
                    + "Product: " + order.getProductName() + "\n"
                    + "Quantity: " + order.getQuantity() + "\n"
                    + "Order ID: " + order.getId() + "\n"
                    + "Customer Email: " + order.getCustomerEmail() + "\n"
                    + "Status: " + order.getStatus() + "\n"
                    + "Order Date: " + order.getOrderDate() + "\n\n"
                    + "Thank you!\n\n"
                    + "Product App Team");
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("WARNING: Order email failed for order " + order.getId() + ": " + e.getMessage());
        }
    }
}
