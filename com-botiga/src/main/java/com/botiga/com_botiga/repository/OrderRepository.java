package com.botiga.com_botiga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.botiga.com_botiga.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    
}
