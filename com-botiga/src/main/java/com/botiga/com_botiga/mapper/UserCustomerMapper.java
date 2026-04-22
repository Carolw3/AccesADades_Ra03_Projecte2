package com.botiga.com_botiga.mapper;

import org.springframework.stereotype.Component;

import com.botiga.com_botiga.DTO.UserCustomerDTO;
import com.botiga.com_botiga.model.Customer;
import com.botiga.com_botiga.model.User;


@Component
public class UserCustomerMapper {

    public User toUserEntity(UserCustomerDTO dto){
        User user = new User (
            dto.getEmail(),
            dto.getPassword()
        );
        return user;
    }

    public Customer toCustomerEntity(UserCustomerDTO dto){
        Customer customer = new Customer(
            dto.getFirstName(), 
            dto.getLasName(), 
            dto.getPhone()
        );

        return customer;
    }

    public UserCustomerDTO EntityToDto(User user, Customer customer){
        UserCustomerDTO dto = new UserCustomerDTO(
            user.getId(),
            user.getEmail(),  
            customer.getFirstName(), 
            customer.getLastName(), 
            customer.getPhone()
        );
        return dto;
    }
}
