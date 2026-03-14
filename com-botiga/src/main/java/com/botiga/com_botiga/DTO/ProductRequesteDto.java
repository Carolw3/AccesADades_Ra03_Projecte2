package com.botiga.com_botiga.DTO;

import java.math.BigDecimal;

import com.botiga.com_botiga.model.Product;
import com.botiga.com_botiga.model.ProductCondition;

public class ProductRequesteDto {

    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private BigDecimal price;
    private BigDecimal rating;
    private ProductCondition condition;

    public ProductRequesteDto(Product p){
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.stock = p.getStock();
        this.price = p.getPrice();
        this.rating = p.getRating();
        this.condition = p.getCondition();
    }

    public ProductRequesteDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public ProductCondition getCondition() {
        return condition;
    }

    public void setCondition(ProductCondition condition) {
        this.condition = condition;
    }

}
