package com.botiga.com_botiga.DTO;

public class AddressDTO {

    private String address;
    private String city;
    private String postalCode;
    private String country;

    
    public AddressDTO() {
    }

    public AddressDTO(String address, String city, String postalCode, String country) {
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    
}
