package com.botiga.com_botiga.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {
    private Long id;
    private Long customerId;
    private String orderStatus;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private List<OrderItemResponseDTO> items;

    public OrderResponseDTO(Long id, Long customerId, String orderStatus, BigDecimal totalAmount,
            LocalDateTime orderDate, List<OrderItemResponseDTO> items) {
        this.id = id;
        this.customerId = customerId;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.items = items;
    }

    public Long getId() { 
        return id; 
    }
    public Long getCustomerId() { 
        return customerId; 
    }
    public String getOrderStatus() { 
        return orderStatus; 
    }
    public BigDecimal getTotalAmount() { 
        return totalAmount; 
    }
    public LocalDateTime getOrderDate() {
         return orderDate; 
        }
    public List<OrderItemResponseDTO> getItems() { 
        return items; 
    }

    public OrderResponseDTO() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setItems(List<OrderItemResponseDTO> items) {
        this.items = items;
    }

    
}
