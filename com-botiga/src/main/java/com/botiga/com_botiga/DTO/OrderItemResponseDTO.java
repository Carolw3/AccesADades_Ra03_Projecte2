package com.botiga.com_botiga.DTO;

import java.math.BigDecimal;

public class OrderItemResponseDTO {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;

    public OrderItemResponseDTO(Long productId, String productName, Integer quantity, BigDecimal unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getProductId() { 
        return productId; 
    }

    public String getProductName() { 
        return productName; 
    }

    public Integer getQuantity() { 
        return quantity; 
    }

    public BigDecimal getUnitPrice() { 
        return unitPrice; 
    }

    public OrderItemResponseDTO() {
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }


    
}
