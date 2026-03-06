package com.botiga.com_botiga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.botiga.com_botiga.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    
}
