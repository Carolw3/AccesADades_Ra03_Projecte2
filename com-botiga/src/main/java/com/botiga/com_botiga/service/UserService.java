package com.botiga.com_botiga.service;

import java.util.Optional;

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

    public UserCustomerDTO getUser(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        
        if(optionalUser.isPresent()){
            User user = optionalUser.get();

            Optional<Customer> customerOptional = customerRepository.findByUserId(id);
            Customer customer = customerOptional.get();

            UserCustomerDTO userCusomer = ucMapper.EntityToDto(user, customer);
            return userCusomer;
        }
        return null;
    }


    public UserCustomerDTO patchUserCustomer(Long id, String email, String phone){

        Optional<User> opUser = userRepository.findById(id);
        if (opUser.isPresent()){
            User user = opUser.get();
            user.setEmail(email);
            User userAct = userRepository.save(user);

            Optional<Customer> opCustomer = customerRepository.findByUserId(id);
            Customer customer = opCustomer.get();
            customer.setPhone(phone);
            Customer customerAct = customerRepository.save(customer);

            return ucMapper.EntityToDto(userAct, customerAct);
        }


        return null;
    }

}
