package com.botiga.com_botiga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.botiga.com_botiga.DTO.UserCustomerDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.botiga.com_botiga.model.User;
import com.botiga.com_botiga.service.UserService;



@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/usuari")
    public ResponseEntity<User> addUser(@RequestBody UserCustomerDTO dto) {
        
        User user = userService.addUser(dto);

        if(user == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }
    

}
