package com.botiga.com_botiga.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botiga.com_botiga.DTO.AddressDTO;
import com.botiga.com_botiga.DTO.CustomerAddressDTO;
import com.botiga.com_botiga.mapper.AddressMapper;
import com.botiga.com_botiga.mapper.CustomerAddressMApper;
import com.botiga.com_botiga.model.Address;
import com.botiga.com_botiga.model.Customer;
import com.botiga.com_botiga.repository.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    CustomerAddressMApper customerAddressMApper;

    @Transactional
    public CustomerAddressDTO saveAddresses(Long customerId, List<AddressDTO> addresses) {
        Optional<Customer> opCus = customerRepository.findById(customerId);

        if (opCus.isEmpty()) {
            return null; 
        }

        Customer cus = opCus.get();

        for (AddressDTO ad : addresses) {
            Address a = addressMapper.toEntity(ad);
            a.setCustomer(cus);
            cus.getAddresses().add(a);
        }

        customerRepository.save(cus);

        return customerAddressMApper.toCustomerAddressDTO(cus);
    }
    @Transactional
    public CustomerAddressDTO findCustomerAndAddressById(Long customerId){

        Optional<Customer> opCus = customerRepository.findById(customerId);
        if(opCus.isPresent()){
            Customer cus = opCus.get();
            CustomerAddressDTO caDTO = customerAddressMApper.toCustomerAddressDTO(cus);
            return caDTO;
        }

        return null;
    }
    @Transactional
    public CustomerAddressDTO deleteCustomerAdress(Long customerId){

        Optional<Customer> opCus = customerRepository.findById(customerId);
        if(opCus.isPresent()){
            Customer cus = opCus.get();
            cus.getAddresses().clear(); // recogemos la lsita para en memoria dejarla vacia y luego subirla a la base de datos pero vacia
            customerRepository.save(cus);
            CustomerAddressDTO caDTO = customerAddressMApper.toCustomerAddressDTO(cus);


            return caDTO;
        }

        return null;
    }

    public List<CustomerAddressDTO> getAllCustomers(){
        List<Customer> c = customerRepository.findAll();

        List<CustomerAddressDTO> cDto = new ArrayList<>();

        for(Customer cGet: c){
            cDto.add(customerAddressMApper.toCustomerAddressDTO(cGet));
        }
        return cDto;
    }



}
