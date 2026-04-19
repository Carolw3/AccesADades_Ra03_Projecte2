package com.botiga.com_botiga.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "users")
@Where( clause = "status = true")
public class User {

    @Id
    @GeneratedValue(strategy= GeneratedType.IDENTITY)
    private Long id;

    @Column(unique= true, nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false)
    private Boolean status = true;

    @CreationTimestamp
    @Column(nullable=false, updatable=false)
    private LocalDateTime dataCreated;

    @UpdateTimestamp
    @Column()
    private LocalDateTime dataUpdated;



}
