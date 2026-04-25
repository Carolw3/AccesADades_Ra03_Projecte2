package com.botiga.com_botiga.DTO;

import java.util.List;

public class CustomerAddressDTO {
    private long id;
    private String email;
    private String firstName;
    private String lasName;
    private String phone;
    private List<AddressDTO> addresses;


    public CustomerAddressDTO(long id, String email, String firstName, String lasName, String phone,
            List<AddressDTO> addresses) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lasName = lasName;
        this.phone = phone;
        this.addresses = addresses;
    }

    public CustomerAddressDTO() {
    }

    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLasName() {
        return lasName;
    }
    public void setLasName(String lasName) {
        this.lasName = lasName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public List<AddressDTO> getAddresses() {
        return addresses;
    }
    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    
}
