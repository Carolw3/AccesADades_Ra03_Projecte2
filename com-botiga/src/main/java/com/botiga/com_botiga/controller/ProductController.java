package com.botiga.com_botiga.controller;

import java.util.List;
import java.util.Optional;

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

import com.botiga.com_botiga.model.Product;
import com.botiga.com_botiga.service.ProductService;



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

    //Obtener un producto por su ID de producto
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        Optional<Product> prod = productService.getProductById(id);

        if(prod.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.ok(prod.get());
        }
    }
    //Guarda un producto
    @PostMapping("/product")
    public  ResponseEntity<Product> postPodcut(@RequestBody Product product) {
        Product resultado = productService.postroduct(product);
        
        if(resultado != null){
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PatchMapping("/product/{id}/stock")
    public ResponseEntity<Product> patchEstoc(@PathVariable() long id, @RequestParam() Integer stock){
        Product resultado = productService.patchEstoc(id, stock);
        
        if(resultado == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        }
    }


}