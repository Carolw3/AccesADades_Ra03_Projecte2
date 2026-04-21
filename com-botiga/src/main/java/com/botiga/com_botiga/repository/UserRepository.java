package com.botiga.com_botiga.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.botiga.com_botiga.DTO.UserCustomerDTO;
import com.botiga.com_botiga.model.Product;
import com.botiga.com_botiga.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
