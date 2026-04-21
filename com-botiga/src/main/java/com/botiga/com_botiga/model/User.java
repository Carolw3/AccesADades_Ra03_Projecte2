package com.botiga.com_botiga.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "users")
@Where( clause = "status = true")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns        = @JoinColumn(name = "userId"),
        inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private List<Role> roles = new ArrayList<>();

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
