package com.botiga.com_botiga.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "order")
@Where(clause = "status = true")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @CreationTimestamp
    @Column(name = "order_date", updatable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @//////////////////////////////////////
    private String orderStatus;

    @/////////////////////////////////////
    private Boolean status;


    @CreationTimestamp
    @Column(name = "data_updated", updatable = false)
    private LocalDateTime dataUpdated;

    @CreationTimestamp
    @Column(name = "data_created", updatable = false)
    private LocalDateTime dataCreated;



    
}
