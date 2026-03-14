package com.botiga.com_botiga.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.botiga.com_botiga.model.Product;
import com.botiga.com_botiga.model.ProductCondition;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameStartingWithIgnoreCase(String name);

    List<Product> findByNameContainingIgnoreCase(String prefix);

    List<Product> findByCondition(ProductCondition condition);

    List<Product> findAllByOrderByRatingAsc();

    List<Product> findAllByOrderByRatingDesc();

    List<Product> findAllByOrderByPriceAsc();

    List<Product> findAllByOrderByPriceDesc();

    List<Product> findByRatingBetween(BigDecimal ratingMin, BigDecimal ratingMax);

    List<Product> findTop10ByConditionOrderByRatingDesc(ProductCondition condition);

    @Query(value = """
    SELECT *
    FROM productes
    WHERE price BETWEEN :min AND :max
    AND status = 1
    AND (:prefix IS NULL OR name LIKE CONCAT(:prefix, '%'))
    ORDER BY price DESC
    """, nativeQuery = true)
        
    List<Product> findProductsByPriceDesc(
        @Param("min") BigDecimal min,
        @Param("max") BigDecimal max,
        @Param("prefix") String prefix,
        Pageable pageable
    );

    @Query(value = """
    SELECT *
    FROM productes
    WHERE price BETWEEN :min AND :max
    AND status = 1
    AND (:prefix IS NULL OR name LIKE CONCAT(:prefix, '%'))
    ORDER BY price ASC
    """, nativeQuery = true)
        
    List<Product> findProductsByPriceAsc(
        @Param("min") BigDecimal min,
        @Param("max") BigDecimal max,
        @Param("prefix") String prefix,
        Pageable pageable
    );

    @Query(value = """
    SELECT *
    FROM productes
    WHERE rating BETWEEN :min AND :max
    AND status = 1
    AND (:prefix IS NULL OR name LIKE CONCAT(:prefix, '%'))
    ORDER BY price DESC
    """, nativeQuery = true)
        
    List<Product> findProductsByRatingDesc(
        @Param("min") BigDecimal min,
        @Param("max") BigDecimal max,
        @Param("prefix") String prefix,
        Pageable pageable
    );

    @Query(value = """
    SELECT *
    FROM productes
    WHERE rating BETWEEN :min AND :max
    AND status = 1
    AND (:prefix IS NULL OR name LIKE CONCAT(:prefix, '%'))
    ORDER BY price ASC
    """, nativeQuery = true)
        
    List<Product> findProductsByRatingAsc(
        @Param("min") BigDecimal min,
        @Param("max") BigDecimal max,
        @Param("prefix") String prefix,
        Pageable pageable
    );

    @Query(value = """
    SELECT *
    FROM productes
    WHERE status = 1
    ORDER BY (quality / price) DESC
    LIMIT 5
    """, nativeQuery = true)
    List<Product> findTop5BestQualityPrice();

}
