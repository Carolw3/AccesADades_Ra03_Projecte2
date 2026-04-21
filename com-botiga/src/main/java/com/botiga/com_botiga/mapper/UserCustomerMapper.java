package com.botiga.com_botiga.mapper;

import com.botiga.com_botiga.DTO.UserCustomerDTO;
import com.botiga.com_botiga.model.Customer;
import com.botiga.com_botiga.model.User;

public class UserCustomerMapper {

    public User toUserEntity(UserCustomerDTO dto){
        User user = new User (
            dto.getEmail(),
            dto.getPassword()
        );

        return user;
    }

    public Customer toCustomerEntity(UserCustomerDTO dto){
        
    }
}
