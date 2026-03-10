package com.botiga.com_botiga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.botiga.com_botiga.model.Product;
import com.botiga.com_botiga.model.ProductCondition;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameStartingWithIgnoreCase(String name);

    List<Product> findByNameContainingIgnoreCase(String prefix);

    List<Product> findByCondition(ProductCondition condition);

    List<Product> findAllByOrderByRatingAsc();

    List<Product> findAllByOrderByRatingDesc();
    
}
