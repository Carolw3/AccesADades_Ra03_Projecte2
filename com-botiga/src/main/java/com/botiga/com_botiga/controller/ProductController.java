package com.botiga.com_botiga.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.botiga.com_botiga.model.Product;
import com.botiga.com_botiga.service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequestMapping("/api")
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    // Es el endpoint para recurperar todos los Productos
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(products);
        }
    }
    
    @PostMapping("/products")
    public  ResponseEntity<Product> postPodcut(@RequestBody Product product) {
        Product resultado = productService.postroduct(product);
        
        if(resultado != null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        }
    }
    

}