package com.botiga.com_botiga.DTO;

public class UserCustomerDTO {

    private String email;
    private String password;
    private String firstName;
    private String lasName;
    private String phone;


    public UserCustomerDTO(String email, String password, String firstName, String lasName, String phone) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lasName = lasName;
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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

    

}
