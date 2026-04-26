package com.botiga.com_botiga.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botiga.com_botiga.DTO.UserCustomerDTO;
import com.botiga.com_botiga.DTO.UserRolesDTO;
import com.botiga.com_botiga.mapper.UserCustomerMapper;
import com.botiga.com_botiga.model.Customer;
import com.botiga.com_botiga.model.Role;
import com.botiga.com_botiga.model.User;
import com.botiga.com_botiga.repository.CustomerRepository;
import com.botiga.com_botiga.repository.RoleRepository;
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

    @Autowired
    RoleRepository roleRepository;


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

    @Transactional
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

    @Transactional
    public List<UserCustomerDTO> getAllUsers(){
        
        List<User> users = userRepository.findAll();
        List<UserCustomerDTO> ucDTOs = new ArrayList<>();

        for(User u : users){
            Long userId = u.getId();
            Optional<Customer> opCus = customerRepository.findByUserId(userId);
            if(opCus.isPresent()){
                Customer c = opCus.get();
                UserCustomerDTO dto = ucMapper.EntityToDto(u, c);
                ucDTOs.add(dto);
            }
        }

        return ucDTOs;
    }

    @Transactional
    public UserRolesDTO addRolesToUser(Long userId, List<Integer> roleIds) {

        // 1. Busquem l'usuari
        Optional<User> opUser = userRepository.findById(userId);
        if (opUser.isEmpty()) return null;
        User user = opUser.get();

        // 2. Busquem els rols i els afegim a l'usuari
        for (Integer roleId : roleIds) {
            Optional<Role> opRole = roleRepository.findById(roleId);
            if (opRole.isEmpty()) return null;
            Role role = opRole.get();
            user.getRoles().add(role);
        }

        userRepository.save(user);

        // 3. Construïm el DTO de resposta
        List<String> roleNames = new ArrayList<>();
        for (Role r : user.getRoles()) {
            roleNames.add(r.getName());
        }

        return new UserRolesDTO(user.getId(), user.getEmail(), roleNames);
    }

}
