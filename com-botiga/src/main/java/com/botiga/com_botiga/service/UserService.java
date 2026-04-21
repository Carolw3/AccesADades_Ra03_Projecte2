package com.botiga.com_botiga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botiga.com_botiga.DTO.UserCustomerDTO;
import com.botiga.com_botiga.mapper.UserCustomerMapper;
import com.botiga.com_botiga.model.Customer;
import com.botiga.com_botiga.model.User;
import com.botiga.com_botiga.repository.CustomerRepository;
import com.botiga.com_botiga.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    UserCustomerMapper ucMapper;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    CustomerRepository customerRepository;


    @Transactional 
    public User addUser(UserCustomerDTO dto){
        User u = ucMapper.toUserEntity(dto);
        userRepository.save(u); // ← guarda primero para obtener el ID

        Customer c = ucMapper.toCustomerEntity(dto);
        c.setUser(u); // ← añade esta línea
        customerRepository.save(c);
        return u;
    }

}
