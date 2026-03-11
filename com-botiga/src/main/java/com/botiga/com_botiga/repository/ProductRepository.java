package com.botiga.com_botiga.repository;

import java.math.BigDecimal;
import java.util.List;

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
    
/* 
    @Query(value = "SELECT * FROM product WHERE price BETWEEN :min AND :max AND prefix = :prefix AND status = 1 ORDER BY price ASC LIMIT :limit", nativeQuery = true)
List<Product> findProductsByPriceAsc(@Param("min") int min,
                                    @Param("max") int max,
                                    @Param("prefix") String prefix,
                                    @Param("limit") int limit);

@Query(value = "SELECT * FROM product WHERE price BETWEEN :min AND :max AND prefix = :prefix AND status = 1 ORDER BY price DESC LIMIT :limit", nativeQuery = true)
List<Product> findProductsByPriceDesc(@Param("min") int min,
                                    @Param("max") int max,
                                    @Param("prefix") String prefix,
                                    @Param("limit") int limit);

@Query(value = "SELECT * FROM product WHERE price BETWEEN :min AND :max AND prefix = :prefix AND status = 1 ORDER BY rating ASC LIMIT :limit", nativeQuery = true)
List<Product> findProductsByRatingAsc(@Param("min") int min,
                                    @Param("max") int max,
                                    @Param("prefix") String prefix,
                                    @Param("limit") int limit);

@Query(value = "SELECT * FROM product WHERE price BETWEEN :min AND :max AND prefix = :prefix AND status = 1 ORDER BY rating DESC LIMIT :limit", nativeQuery = true)
List<Product> findProductsByRatingDesc(@Param("min") int min,
                                    @Param("max") int max,
                                    @Param("prefix") String prefix,
                                    @Param("limit") int limit);
*/
}
