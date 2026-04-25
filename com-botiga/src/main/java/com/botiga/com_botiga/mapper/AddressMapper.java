package com.botiga.com_botiga.mapper;

import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import com.botiga.com_botiga.DTO.AddressDTO;
import com.botiga.com_botiga.model.Address;

@Component
public class AddressMapper {

    public Address toEntity (AddressDTO ad){

        Address address = new Address(ad.getAddress(), ad.getCity(), ad.getPostalCode(), ad.getCountry());

        return address;
    }

    public AddressDTO toDTO (Address a){
        AddressDTO adDTO = new AddressDTO(a.getAddress(), a.getCity(), a.getPostalCode(), a.getCountry());

        return adDTO;
    }

}
