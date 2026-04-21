package com.botiga.com_botiga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botiga.com_botiga.DTO.UserCustomerDTO;
import com.botiga.com_botiga.mapper.UserCustomerMapper;
import com.botiga.com_botiga.model.Customer;
import com.botiga.com_botiga.model.User;
import com.botiga.com_botiga.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserCustomerMapper ucMapper;
    @Autowired
    UserRepository userRepository;

    public User addUser(UserCustomerDTO dto){
        User u = ucMapper.toUserEntity(dto);
        Customer c = ucMapper.toCustomerEntity(dto);

        return null;

    }

}
