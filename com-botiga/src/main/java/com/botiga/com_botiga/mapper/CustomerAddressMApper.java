package com.botiga.com_botiga.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.botiga.com_botiga.DTO.AddressDTO;
import com.botiga.com_botiga.DTO.CustomerAddressDTO;
import com.botiga.com_botiga.model.Address;
import com.botiga.com_botiga.model.Customer;

@Component
public class CustomerAddressMApper {

    @Autowired
    AddressMapper addressMapper;

    public CustomerAddressDTO toCustomerAddressDTO(Customer cus) {
        List<AddressDTO> addressDTOs = new ArrayList<>();

        for (Address a : cus.getAddresses()) {
            AddressDTO adDTO = addressMapper.toDTO(a);
            addressDTOs.add(adDTO);
        }

        return new CustomerAddressDTO(
                cus.getId(),
                cus.getUser().getEmail(),
                cus.getFirstName(),
                cus.getLastName(),
                cus.getPhone(),
                addressDTOs
        );

    }

}

