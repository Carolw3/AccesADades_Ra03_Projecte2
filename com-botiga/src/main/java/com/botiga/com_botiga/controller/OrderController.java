package com.botiga.com_botiga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.botiga.com_botiga.DTO.ErrorDto;
import com.botiga.com_botiga.DTO.OrderRequestDTO;
import com.botiga.com_botiga.DTO.OrderResponseDTO;
import com.botiga.com_botiga.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/order/{customerId}")
    public ResponseEntity<?> createOrder(@PathVariable Long customerId, @RequestBody OrderRequestDTO orderRequest) {

        OrderResponseDTO response = orderService.createOrder(customerId, orderRequest);

        if(response == null){
            return ResponseEntity.status(HttpStatus.OK).body(new Error("No se pudo postear la orden con la id : " + customerId));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PatchMapping("/order/{orderId}/process")
    public ResponseEntity<?> processOrder(@PathVariable Long orderId) {
        OrderResponseDTO response = orderService.processOrder(orderId);

        if(response == null){
            return ResponseEntity.status(HttpStatus.OK).body(new Error("No se pudo actualizar la orden con la id : " + orderId));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
