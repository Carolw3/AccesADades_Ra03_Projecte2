package com.botiga.com_botiga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.botiga.com_botiga.DTO.AddressDTO;
import com.botiga.com_botiga.DTO.CustomerAddressDTO;
import com.botiga.com_botiga.service.CustomerService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequestMapping("/api")
@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/address/{customerId}")
    public ResponseEntity<CustomerAddressDTO> addAddressForCustomer(@PathVariable Long customerId, @RequestBody List<AddressDTO> addresses){

        CustomerAddressDTO cADto = customerService.saveAddresses(customerId,addresses );
        if(cADto == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(cADto);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CustomerAddressDTO> findCustomerAndAddressById(@PathVariable Long customerId){

        CustomerAddressDTO cADto = customerService.findCustomerAndAddressById(customerId);
        if(cADto == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(cADto);
        }
    }
}
