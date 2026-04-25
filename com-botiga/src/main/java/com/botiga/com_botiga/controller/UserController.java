package com.botiga.com_botiga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.botiga.com_botiga.DTO.ErrorDto;
import com.botiga.com_botiga.DTO.UserCustomerDTO;
import com.botiga.com_botiga.model.User;
import com.botiga.com_botiga.service.UserService;




@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/usuari")
    public ResponseEntity<?> addUser(@RequestBody UserCustomerDTO dto) {
        
        User user = userService.addUser(dto);

        if(user == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("No se pudo postera correctamente el usuario"));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    @GetMapping("/usuari/{id}")
    public ResponseEntity<?> getUsuari(@PathVariable long id) {

        UserCustomerDTO userDto = userService.getUser(id);

        if(userDto == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("No se econtro el usuario con la id: " + id));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        }
    }

    @PatchMapping("/usuari/{id}/email/phone")
    public ResponseEntity<?> patchUserCustomer(@PathVariable Long id, @RequestParam String email, @RequestParam String phone){

        UserCustomerDTO userDto = userService.patchUserCustomer(id, email, phone);

        if(userDto == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("No se pudo actulizar el usuario con la id: " + id));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        }
    }

    @GetMapping("/usuaris")
    public ResponseEntity<List<?>>  getAllUsers() {

        List<UserCustomerDTO> llista =  userService.getAllUsers();

        if(llista == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(llista);
        }
    }
    
    
    

}
