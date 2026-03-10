package com.botiga.com_botiga.repository;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.botiga.com_botiga.model.Product;
import com.botiga.com_botiga.model.ProductCondition;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameStartingWithIgnoreCase(String name);

    List<Product> findByNameContainingIgnoreCase(String prefix);

    List<Product> findByCondition(ProductCondition condition);

    List<Product> findAllByOrderByRatingAsc();

    List<Product> findAllByOrderByRatingDesc();

    List<Product> findByRatingBetween(BigDecimal ratingMin, BigDecimal ratingMax);

    List<Product> findTop10ByConditionOrderByRatingDesc(ProductCondition condition);
    
}
