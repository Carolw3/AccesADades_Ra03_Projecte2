package com.botiga.com_botiga.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "productes")
@Where(clause = "status = true") // Filtre per borrat lògic
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "rating", precision = 3, scale = 1)
    private BigDecimal rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_condition", nullable = false, length = 20)
    private ProductCondition condition;

    @Column(name = "status", nullable = false)
    private Boolean status = true; // true = actiu, false = esborrat lògic

    @CreationTimestamp
    @Column(name = "data_created", nullable = false, updatable = false)
    private LocalDateTime dataCreated;

    @UpdateTimestamp
    @Column(name = "data_updated")
    private LocalDateTime dataUpdated;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = true;
        }
        dataCreated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataUpdated = LocalDateTime.now();
    }
}
